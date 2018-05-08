package uberjava;

import vehicles.Car;
import vehicles.Vehicle;
import uberjava.Fare;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import locations.Location;
import java.lang.NumberFormatException;
import uberjava.UberRider;


public class UberJavaMain{

	public static String findRideNumber(String info){
		Pattern rNumPat = Pattern.compile("#([0-9]+[A-Z]?+[0-9]+)");
		Matcher m = rNumPat.matcher(info);
		String rideNumber = "before";
		while(m.find()){
			rideNumber = m.group(1);
		}
		return rideNumber;
	}
	public static String findRider(String info){
		Pattern riderPat = Pattern.compile("Rider: ([A-Z][a-z]+)( [A-Z][a-z]+)?");
		Matcher m = riderPat.matcher(info);
		String rider = "My little buddy";
		while(m.find()){
			rider = m.group(1)+ m.group(2);
		}
		return rider;
	}

	public static double findFare(String info){
		Pattern farePat = Pattern.compile("([0-9]+\\.[0-9]+)");
		Matcher m = farePat.matcher(info);
		String amt = "before";
		while(m.find()){
			amt = m.group();
		}
		double fare = Double.parseDouble(amt);
		return fare;
	}

	public static int findPeople(String info){
		Pattern peoplePat = Pattern.compile("([0-9] )");
		Matcher m = peoplePat.matcher(info);
		String amt = "before";
		while(m.find()){
			amt = m.group();
		}
		String newAmt = amt.substring(0,1);
		int people = Integer.parseInt(newAmt);
		return people;
	}

	public static String findFrom(String info){
		Pattern fromPat = Pattern.compile("(FROM ([A-Z][a-z]+)( [A-Z][a-z]+)?( t)?)");
		Matcher m = fromPat.matcher(info);
		String from = "before";
		while(m.find()){
			from = m.group(0);
		}
		String [] arr = from.split(" ");
		String newFrom = "";
		for(String word: arr){
			if(!(word.equals("FROM") || word.equals("to") || word.equals("t"))){
				newFrom += word + " ";
			}
		}
		String actualFrom = newFrom.trim();
		return  actualFrom;
	}

	public static String findTo(String info){
		Pattern toPat = Pattern.compile("(to [A-Z][a-z]+)( [A-Z][a-z]+)?");
		Matcher m = toPat.matcher(info);
		String to = "before";
		while(m.find()){
			to = m.group();
		}
		return to.substring(3);
	}

	public static boolean isSurgePricing(String info){
		Pattern surgePat = Pattern.compile("SURGE PRICING");
		Matcher m = surgePat.matcher(info);
		boolean surgePricing = false;
		if(m.find()){
			surgePricing = true;
		}
		return surgePricing;
	}

	public static boolean isDoublePricing(String info){
		Pattern doublePat = Pattern.compile("DOUBLE SURGE PRICING");
		Matcher m = doublePat.matcher(info);
		boolean doublePricing = false;
		if(m.find()){
			doublePricing = true;
		}
		return doublePricing;
	}

	public static boolean isUberXPremium(String info){
		Pattern premPat = Pattern.compile("UberJavaX Premium");
		Matcher m = premPat.matcher(info);
		boolean isUberXPremium = false;
		if(m.find()){
			isUberXPremium = true;
		}
		return isUberXPremium;

	}

	public static int getMinutes(String rideNumber){
		URL link1 = null;
		URLConnection conn = null;
		BufferedReader reader = null;
		String newInfo = "";
		StringBuilder builder = new StringBuilder();
		String minutes = "";

		try{

			link1 = new URL("https://cs.usfca.edu/~dhalperin/distance.cgi?rideNumber="+rideNumber);

			conn = link1.openConnection();
			conn.setReadTimeout(5000);
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null){
				builder.append(line);
			}
		
			reader.close();

		} catch(MalformedURLException e){
			System.out.println("Please enter a valid URL.");
		} catch(IOException e){
			System.out.println("Error.");
		}

		newInfo = builder.toString();

		Pattern minutesPatt = Pattern.compile("(Minutes: )([0-9]+)");
		Matcher minuteMatch = minutesPatt.matcher(newInfo);
		while(minuteMatch.find()){
			minutes = minuteMatch.group(2);
		}
		
		int rideMinutes = Integer.parseInt(minutes);
		
		
		return rideMinutes;

	}
	public static int getDistance(String rideNumber){
		URL link1 = null;
		URLConnection conn = null;
		BufferedReader reader = null;
		String newInfo = "";
		StringBuilder builder = new StringBuilder();
		String distance = "";

		try{

			link1 = new URL("https://cs.usfca.edu/~dhalperin/distance.cgi?rideNumber="+rideNumber);

			conn = link1.openConnection();
			conn.setReadTimeout(5000);
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null){
				builder.append(line);
			}
		
			reader.close();

		} catch(MalformedURLException e){
			System.out.println("Please enter a valid URL.");
		} catch(IOException e){
			System.out.println("Disrance Error.");
		}

		newInfo = builder.toString();

		Pattern distancePatt = Pattern.compile("(Distance: )([0-9]+)( miles)");
		Matcher distanceMatch = distancePatt.matcher(newInfo);
		while(distanceMatch.find()){
			distance = distanceMatch.group(2);
		}
		
		int rideDistance = Integer.parseInt(distance);
		
		
		return rideDistance;

	}

	public static int getTolls(String rideNumber){
		URL link1 = null;
		URLConnection conn = null;
		BufferedReader reader = null;
		String newInfo = "";
		StringBuilder builder = new StringBuilder();
		String tolls = "";

		try{

			link1 = new URL("https://cs.usfca.edu/~dhalperin/distance.cgi?rideNumber="+rideNumber);

			conn = link1.openConnection();
			conn.setReadTimeout(5000);
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null){
				builder.append(line);
			}
		
			reader.close();
			

		} catch(MalformedURLException e){
			System.out.println("Please enter a valid URL.");
		} catch(IOException e){
			System.out.println("error.");
		}

		newInfo = builder.toString();

		Pattern tollPatt = Pattern.compile("(Tolls: \\$)([0-9]+)");
		Matcher tollMatch = tollPatt.matcher(newInfo);
		while(tollMatch.find()){
			tolls = tollMatch.group(2);
		}
		int rideTolls = 0;
		try{
			rideTolls = Integer.parseInt(tolls);
		} catch(NumberFormatException e){
			System.out.println("To Toll.");
		}
		rideTolls = Integer.parseInt(tolls);
		return rideTolls;
	}
	public static double getAmenities(String rideNumber){
		URL link1 = null;
		URLConnection conn = null;
		BufferedReader reader = null;
		String newInfo = "";
		StringBuilder builder = new StringBuilder();
		String amenities = "";

		try{

			link1 = new URL("https://cs.usfca.edu/~dhalperin/distance.cgi?rideNumber="+rideNumber);

			conn = link1.openConnection();
			conn.setReadTimeout(5000);
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null){
				builder.append(line);
			}
		
			reader.close();

		} catch(MalformedURLException e){
			System.out.println("Please enter a valid URL.");
		} catch(IOException e){
			System.out.println("Amenities Error.");
		}

		newInfo = builder.toString();

		Pattern amenitiesPatt = Pattern.compile("(Amenities.+)([0-9]+\\.[0-9]+)");
		Matcher amenitiesMatch = amenitiesPatt.matcher(newInfo);
		while(amenitiesMatch.find()){
			amenities = amenitiesMatch.group(2);
		}
		
		double rideAmenities = Double.parseDouble(amenities);
		
		
		return rideAmenities;

	}
	public static int milesFromStart(String rideNumber, String startLocation){
		URL link1 = null;
		URLConnection conn = null;
		BufferedReader reader = null;
		String newInfo = "";
		StringBuilder builder = new StringBuilder();
		String newLocation = "";

		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(startLocation);
		boolean found = matcher.find();
		
		try{
			if(!found){
				String url = ("https://cs.usfca.edu/~dhalperin/distance.cgi?rideNumber="+ rideNumber + "&start=" + startLocation);
				link1 = new URL(url);

			}else{
				newLocation = startLocation.replace(" ", "+");
				String url1 = ("https://cs.usfca.edu/~dhalperin/distance.cgi?rideNumber=" + rideNumber + "&start=" + newLocation);
				link1 = new URL(url1);
			}
			

			conn = link1.openConnection();
			conn.setReadTimeout(5000);
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null){
				builder.append(line);
			}
		
			reader.close();
			
		} catch(MalformedURLException e){
			System.out.println("Please enter a valid URL.");
		} catch(IOException e){
			System.out.println("incorrect.");
		}

		newInfo = builder.toString();
		

		Pattern milesPatt = Pattern.compile("([0-9]+)( miles)");
		Matcher milesMatch = milesPatt.matcher(newInfo);
		String miles = "before";

		while(milesMatch.find()){
			miles = milesMatch.group(1);
		}
		
		
		int newMiles = 0;
		try{
			newMiles = Integer.parseInt(miles);
		} catch(NumberFormatException e){
			System.out.println("Miles error.");
		}
		
		
		
		return  newMiles;
	

	}
	public static int minutesFromStart(String rideNumber, String startLocation){
		URL link1 = null;
		URLConnection conn = null;
		BufferedReader reader = null;
		String newInfo = "";
		StringBuilder builder = new StringBuilder();
		String newLocation = "";

		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(startLocation);
		boolean found = matcher.find();

		try{
			if(!found){
				String url = ("https://cs.usfca.edu/~dhalperin/distance.cgi?rideNumber="+ rideNumber + "&start=" + startLocation);
				link1 = new URL(url);

			}else{
				newLocation = startLocation.replace(" ", "+");
				String url1 = ("https://cs.usfca.edu/~dhalperin/distance.cgi?rideNumber=" + rideNumber + "&start=" + newLocation);
				link1 = new URL(url1);
			}
			

			conn = link1.openConnection();
			conn.setReadTimeout(5000);
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null){
				builder.append(line);
			}
		
			reader.close();

		} catch(MalformedURLException e){
			System.out.println("Please enter a valid URL.");
		} catch(IOException e){
		}

		newInfo = builder.toString();
		
		
		Pattern minutesPatt = Pattern.compile("([0-9]+)( minutes)");
		Matcher minutesMatch = minutesPatt.matcher(newInfo);
		String minutes = "before";

		while(minutesMatch.find()){
			minutes = minutesMatch.group(1);
		}
		int newMinutes = 0;
		try{
			newMinutes = Integer.parseInt(minutes);
		} catch(NumberFormatException e){
			System.out.println("Error. no minutes detected.");
		}
		
		return  newMinutes;
		

	}
	public static void rejectRide(String rideNumber){
		URL link1 = null;
		URLConnection conn = null;
		BufferedReader reader = null;
		String newInfo = "";
		StringBuilder builder = new StringBuilder();
		

		try{

			link1 = new URL("https://cs.usfca.edu/~dhalperin/reject.cgi?rideNumber="+rideNumber);

			conn = link1.openConnection();
			conn.setReadTimeout(5000);
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null){
				builder.append(line);
			}
		
			reader.close();

		} catch(MalformedURLException e){
			System.out.println("Please enter a valid URL.");
		} catch(IOException e){
		}

	}

	public static int getRating(String rideNumber){
		URL link1 = null;
		URLConnection conn = null;
		BufferedReader reader = null;
		String newInfo = "";
		int goldStar = 0;
		StringBuilder builder = new StringBuilder();
		

		try{

			link1 = new URL("https://cs.usfca.edu/~dhalperin/rating.cgi?rideNumber="+rideNumber);

			conn = link1.openConnection();
			conn.setReadTimeout(5000);
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null){
				builder.append(line);
			}
		
			reader.close();

		} catch(MalformedURLException e){
			System.out.println("Please enter a valid URL.");
		} catch(IOException e){
			System.out.println("Error.");
		}

		newInfo = builder.toString();

		Pattern goldPatt = Pattern.compile("golden-star.jpg");
		Matcher goldMatch = goldPatt.matcher(newInfo);
		while(goldMatch.find()){
			goldStar ++;
		}

		return goldStar;


	}
	public static void main(String [] args){
		SSLVerificationPatch.applyPath();
		int minutesToStart = 0;
		int milesToStart = 0;
		int c = 1;
		while(c >0){
			try{
			Thread.sleep(1000);
			} catch(InterruptedException e){}
				
			UberJavaDriver driver1 = new UberJavaDriver("Stewart", new Car("Stew's Audi", 2018, "Audi R8"), false);
			driver1.getCar().setPurchaseCost(180000);
			driver1.getCar().setMilesPerGallon(20);
			driver1.start();

			try{
				Thread.sleep(1000);
			} catch(InterruptedException e){}
			
			UberJavaDriver driver2 = new UberJavaDriver("Prateek", new Car("Prateek's Lamborghini", 2019, "Lamborghini Aventador"), true);
			driver2.getCar().setPurchaseCost(199800);
			driver2.getCar().setMilesPerGallon(11);
			driver2.start();

			try{
				Thread.sleep(1000);
			} catch(InterruptedException e){}
				
			UberJavaDriver driver3 = new UberJavaDriver("Lauren", new Car("Lauren's Porsche", 2018, "Porsche 911 Turbo S"), true);
			driver3.getCar().setPurchaseCost(200400);
			driver3.getCar().setMilesPerGallon(12.5);
			driver3.start();

			try{
				Thread.sleep(5000);
			} catch(InterruptedException e){}
			for(UberRider rider: UberRider.getRiders()){
				System.out.println("\n" + rider);
			}
			c--;
		}
		
	
	}
		
		
}