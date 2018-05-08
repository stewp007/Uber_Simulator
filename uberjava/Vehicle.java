package uberjava;

import exceptions.ValueNotSetException;
import locations.Location;
abstract class Vehicle{
	private Location currentLocation;
	private String make;
	private String year;
	private Integer purchaseCost;
	private Integer milesPG;


	public Vehicle(String make, String year, Location location){
		this.make = make;
		this.year = year;
		this.currentLocation = location;
		purchaseCost = null;
		milesPG = null;
	}

	void setPurchaseCost(int cost){
		this.purchaseCost = cost;
	}
	int getPurchaseCost(){
		if(purchaseCost == null){
			throw new ValueNotSetException("You must set the purchase cost.");
			
		}
		return purchaseCost;
	}
}