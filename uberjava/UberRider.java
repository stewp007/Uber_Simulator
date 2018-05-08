package uberjava;

import java.util.ArrayList;

public class UberRider{
	private String name;
	private int totalFares;
	private int stewFares;
	private int prateekFares;
	private int laurenFares;
	private int minutes;
	private double totalPaid;
	private static ArrayList<UberRider> riders = new ArrayList<>();


	public UberRider(String name){
		this.name = name;
		totalFares = 0;
		stewFares = 0;
		prateekFares = 0;
		laurenFares = 0;
		minutes = 0;
		totalPaid = 0.0;
		riders.add(this);
	}
	public String getName(){
		return name;
	}

	public int getTotalFares(){
		return totalFares;
	}

	public int getStewFares(){
		return stewFares;
	}

	public int getPrateekFares(){
		return prateekFares;
	}

	public int getLaurenFares(){
		return laurenFares;
	}

	public int getMinutes(){
		return minutes;
	}

	public double getTotalPaid(){
		return totalPaid;
	}

	public void addTotalFares(){
		totalFares ++;
	}

	public void addStewFares(){
		stewFares ++;
	}

	public void addPrateekFares(){
		prateekFares ++;
	}

	public void addLaurenFares(){
		laurenFares ++;
	}

	public void addMinutes(int minutes){
		this.minutes += minutes;
	}

	public void addTotalPaid(double fare){
		totalPaid += fare;
	}

	public static ArrayList<UberRider> getRiders(){
		return riders;
	}
	
	public static UberRider getUberRider(String name){
		for(UberRider rider: riders){
			if(rider.getName().equals(name)){
				return rider;
			}
		}
		return null;
	}

@Override
	public String toString(){
		return "Name of Rider: "+ name + "\nNumber of Fares: "+ totalFares+ "\nFares with Stewart: "+stewFares+"\nFares with Prateek: "+prateekFares+"\nFares with Lauren: "+laurenFares+ "\nAmount paid to Uberjava: "+ totalPaid + "\nTotal minutes driven: "+ minutes;
	}

	public static void main(String [] args){
		UberRider rider1 = new UberRider("Stew");
		System.out.println(rider1);
	}

}
