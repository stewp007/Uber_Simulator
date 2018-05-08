package vehicles;

import locations.Location;
import java.util.ArrayList;

public class Car extends Vehicle{
	private String name;
	private boolean parked;
	private static ArrayList<Car> cars = new ArrayList<Car>();

	public Car(String name, int year, String make){
		super(make, year, Location.getByName("San Francisco"));
		this.name = name;
		this.parked = true;
		cars.add(this);
	}

	public Car(String name, int year, String make, Location location){
		super(make, year, location);
		this.name = name;
		this.parked = true;
		cars.add(this);
	}

	public String getName(){
		return name;
	}

	public boolean isParked(){
		return parked;
	}

	public void park(){
		parked = true;
	}

	public double getMilesPerGallon(){
		return super.getMilesPerGallon();
	}

	public boolean driveForward(Location toWhere){
		if(parked){
			parked = false;
			return super.driveForward(toWhere);
		}
		return false;
	}


	static Car[] getCars(){
		return cars.toArray(new Car[cars.size()]);
	}

	public String toString(){
		return "Name: "+ name + "\n"+ super.toString();
	}
}