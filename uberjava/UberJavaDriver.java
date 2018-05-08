package uberjava;

import vehicles.Car;
import vehicles.Vehicle;
import locations.Location;
import java.lang.IllegalStateException;
import uberjava.UberJavaMain;
import uberjava.UberRider;

public class UberJavaDriver implements Runnable{

	private String name;
	private Car car;
	private boolean isUberXPremium;
	private static int sessionNumber;
	private boolean isInSession;
	private int totalMinutes;
	private double totalFares;
	private int totalTolls;
	private boolean running;
	private int rejections;
	private int numFares;
	private int totalMiles;
	private double totalAmenities;
	private int totalRating;
	private int totalCost;

	public UberJavaDriver(String name, Car car, boolean isPremium){
		this.name = name;
		this.car = car;
		this.isUberXPremium = isPremium;
		running = false;
		totalMinutes = 0;
		totalFares = 0.0;
		totalTolls = 0;
		isInSession = false;
		sessionNumber = 0;
		rejections = 0;
		numFares = 0;
		totalAmenities = 0.0;
		totalRating = 0;
	}

	public String getName(){
		return this.name;
	}

	public Car getCar(){
		return this.car;
	}
	
	public boolean isUberXPremium(){
		return this.isUberXPremium;
	}

	public boolean start(){
		if(running){
			return false;
		}
		running = true;
		new Thread(this).start();
		this.startSession();
		return true;
	}
	public int startSession(){
		if(isInSession){
			throw new IllegalStateException("You are already in session");
		}
		isInSession = true;
		return sessionNumber++;
	}

	public int endSession(){
		if(!isInSession){
			throw new IllegalStateException("There is no session to end.");
		}
		isInSession = false;
		return sessionNumber;
	}

	public UberStatistics getSessionStatistics(){
		if(isInSession){
			return new UberStatistics(this);
		}else{
			return null;
		}
	}

	public boolean getIsInSession(){
		return isInSession;
	}

	public int getSessionNumber(){
		return sessionNumber;
	}

	public Location getCurrentLocation(){
		return this.car.getCurrentLocation();
	}

	public int getSessionMinutes(){
		return totalMinutes;
	}

	public int getTotalMinutes(){
		return totalMinutes;
	}

	public double getTotalFares(){
		return totalFares;
	}

	public int getTotalTolls(){
		return totalTolls;
	}

	public int getRejections(){
		return rejections;
	}

	public int getNumFares(){
		return numFares;
	}

	public int getTotalMiles(){
		return totalMiles;
	}
	public double getTotalAmenities(){
		return totalAmenities;
	}
	
	public int getTotalRating(){
		return totalRating;
	}

	public void run(){
		int minutesToStart = 0;
		int milesToStart = 0;
		String rider = null;
		UberRider passenger = null;
		while(running){
			while(this.getSessionMinutes() <= 1440){
				String rideInfo = Fare.getFare(this);
				String rideNum = UberJavaMain.findRideNumber(rideInfo);
				Location rideFrom = Location.getByName(UberJavaMain.findFrom(rideInfo));
				Location rideTo = Location.getByName(UberJavaMain.findTo(rideInfo));
				rider = UberJavaMain.findRider(rideInfo);
		
				if(rideFrom == getCurrentLocation()){
					milesToStart = 0;
					minutesToStart = 0;
				}else{
					milesToStart = UberJavaMain.milesFromStart(rideNum, getCar().getCurrentLocation().getName());
					minutesToStart = UberJavaMain.minutesFromStart(rideNum, getCar().getCurrentLocation().getName());
				}
				if(minutesToStart > 300){
					UberJavaMain.rejectRide(rideNum);
					rejections ++;
					break;
				}
				if(isUberXPremium){
					totalAmenities += UberJavaMain.getAmenities(rideNum);
					
				}
				if(UberRider.getUberRider(rider) == null){
					passenger = new UberRider(rider);
				}else{
					passenger = UberRider.getUberRider(rider);
				}
				int minutes = UberJavaMain.getMinutes(rideNum);
				totalMinutes += minutesToStart;
				totalMinutes += minutes;
				passenger.addMinutes(minutes);
				double fare = UberJavaMain.findFare(rideInfo);
				passenger.addTotalPaid(fare);
				totalFares += fare;
				passenger.addTotalFares();
				numFares ++;
				if(getName().equals("Stewart")){
					passenger.addStewFares();
				} else if(getName().equals("Prateek")){
					passenger.addPrateekFares();
				} else{
					passenger.addLaurenFares();
				}
				totalTolls += UberJavaMain.getTolls(rideNum);
				int milesOfRide = UberJavaMain.getDistance(rideNum);
				totalMiles += milesOfRide;
				totalMiles += milesToStart;
				getCar().driveForward(rideFrom);
				getCar().park();
				getCar().driveForward(rideTo);
				getCar().park();
				totalRating += UberJavaMain.getRating(rideNum);


			}

			if(!(getCurrentLocation().getName().equals("San Francisco"))){
				getCar().driveForward(Location.getByName("San Francisco"));
			}
			System.out.println("Name of Driver: "+ this.getName() +"\n Is UberXPremium: "+ this.isUberXPremium());
			System.out.println("Total rides given: "+ this.getNumFares());
			System.out.println("Total fares: "+ this.getTotalFares()+"\nTotal miles driven: "+ this.getTotalMiles()+
							"\nDrove "+ (this.getSessionMinutes()/60) +" hours and "+ (this.getTotalMinutes()%60) + " minutes.");
			System.out.println("Total Earned: "+ (this.getTotalFares() * 0.75));
			if(getName().equals("Stewart")){
				System.out.println("Total Cost: "+ (((0.12676 * this.getSessionMinutes()) + this.getTotalTolls() + (0.0694*this.getSessionMinutes()))+(((this.getTotalMiles())/this.getCar().getMilesPerGallon())*3.50)+this.getTotalAmenities()));
				System.out.println("Cost of Vehicle OwnerShip: "+ (0.12676 * this.getSessionMinutes()));
				System.out.println("Operating cost: "+ (0.0694*this.getSessionMinutes()));
				System.out.println("Cost for gasoline: "+ (((this.getTotalMiles())/this.getCar().getMilesPerGallon())*3.50));
				System.out.println("Cost of Tolls: "+ this.getTotalTolls());
				System.out.println("Cost of Amenities: "+ this.getTotalAmenities());
				try{
					System.out.println("Average rating: "+ (this.getTotalRating()/this.getNumFares()));
				}catch(ArithmeticException e){
					System.out.println("Average Rating: 0.0");
				}
				
				System.out.println("Effectively hourly Earnings: "+ ((this.getTotalFares() * (3/4))-((0.12676 * this.getSessionMinutes()) + this.getTotalTolls() + (0.0694*this.getSessionMinutes()))/(this.getSessionMinutes())*60));
			} else if(getName().equals("Prateek")){
				System.out.println("Total Cost: "+ (((0.14069 * this.getSessionMinutes()) + this.getTotalTolls() + (0.07708 * this.getSessionMinutes()))+(((this.getTotalMiles())/this.getCar().getMilesPerGallon())*3.50)+this.getTotalAmenities()));
				System.out.println("Cost of Vehicle OwnerShip: "+ (0.14069 * this.getSessionMinutes()));
				System.out.println("Operating cost: "+ (0.07708*this.getSessionMinutes()));
				System.out.println("Cost for gasoline: "+ ((this.getTotalMiles())/this.getCar().getMilesPerGallon())*3.50);
				System.out.println("Cost of Tolls: "+ this.getTotalTolls());
				System.out.println("Cost of Amenities: "+ this.getTotalAmenities());
				try{
					System.out.println("Average rating: "+ (this.getTotalRating()/this.getNumFares()));
				}catch(ArithmeticException e){
					System.out.println("Average Rating: 0.0");
				}
				System.out.println("Effectively hourly Earnings: "+ ((this.getTotalFares() * (3/4))-((0.14069 * this.getSessionMinutes()) + this.getTotalTolls() + (0.07708 * this.getSessionMinutes()))/(this.getSessionMinutes())*60));
			} else{
				System.out.println("Total Cost: "+ (((0.141134 * this.getSessionMinutes()) + this.getTotalTolls() + (0.077315 * this.getSessionMinutes()))+(((this.getTotalMiles())/this.getCar().getMilesPerGallon())*3.50)+this.getTotalAmenities()));
				System.out.println("Cost of Vehicle OwnerShip: "+ (0.141134 * this.getSessionMinutes()));
				System.out.println("Operating cost: "+ (0.077315*this.getSessionMinutes()));
				System.out.println("Cost for gasoline: "+ ((this.getTotalMiles())/this.getCar().getMilesPerGallon())*3.50);
				System.out.println("Cost of Tolls: "+ this.getTotalTolls());
				System.out.println("Cost of Amenities: "+ this.getTotalAmenities());
				try{
					System.out.println("Average rating: "+ (this.getTotalRating()/this.getNumFares()));
				}catch(ArithmeticException e){
					System.out.println("Average Rating: 0.0");
				}
				System.out.println("Effectively hourly Earnings: "+ ((this.getTotalFares() * (3/4))-((0.141134 * this.getSessionMinutes()) + this.getTotalTolls() + (0.077315 * this.getSessionMinutes()))/(this.getSessionMinutes())*60));
			}
			endSession();
			running = false;

		}

		
	}

	public String toString(){
		return "Name: "+ name+ "\nCar "+ car+ "\nUberX Premium: "+ isUberXPremium;
	}

}