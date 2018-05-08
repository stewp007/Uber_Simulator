package vehicles;

import exceptions.ValueNotSetException;
import locations.Location;

public abstract class Vehicle{

	private Location currentLocation;
	private String make;
	private int year;
	private Integer purchaseCost;
	private Double milesPG;


	public Vehicle(String make, int year, Location location){
		this.make = make;
		this.year = year;
		this.currentLocation = location;
		this.purchaseCost = null;
		this.milesPG = null;
	}

	public void setPurchaseCost(int cost){
		this.purchaseCost = cost;
	}
	public int getPurchaseCost(){
		if(purchaseCost == null){
			throw new ValueNotSetException("You must set the purchase cost.");
			
		}
		return purchaseCost;
	}

	public void setMilesPerGallon(double miles){
		this.milesPG = miles;
	}

	public double getMilesPerGallon(){
		if(milesPG == null){
			throw new ValueNotSetException("You must set the miles per gallon.");
		}
		return milesPG;
	}

	public String getMake(){
		return make;
	}

	public int getYear(){
		return year;
	}

	public Location getCurrentLocation(){
		return currentLocation;
	}

	boolean driveForward(Location toWhere){
		if(currentLocation.equals(toWhere)){
			return false;
		}
		currentLocation = toWhere;
		return true;
	}
	
	public String toString(){
		return "Make: "+ make + "\nYear: "+ year + "\nLocation: "+ currentLocation;
	}
}