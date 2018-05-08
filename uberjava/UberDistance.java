package uberjava;

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

public class UberDistance{
	public static int getMinutes(String rideNumber){
		URL link1 = null;
		URLConnection conn = null;
		BufferedReader reader = null;
		String newInfo = "";
		StringBuilder builder = new StringBuilder();
		String minutes = "";

		try{

			link1 = new URL("https://cs.usfca.edu/~dhalperin/uberdistance.cgi?rideNumber="+rideNumber);

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

		Pattern minutesPatt = Pattern.compile("(Minutes: )([0-9]+)");
		Matcher minuteMatch = minutesPatt.matcher(newInfo);
		while(minuteMatch.find()){
			minutes = minuteMatch.group(1);
		}
		System.out.println(minutes);
		int rideMinutes = 0;
		try{
			rideMinutes = Integer.parseInt(minutes);
		} catch(NumberFormatException e){}
		
		return rideMinutes;

	}

	public static int getTolls(String rideNumber){
		URL link1 = null;
		URLConnection conn = null;
		BufferedReader reader = null;
		String newInfo = "";
		StringBuilder builder = new StringBuilder();
		String tolls = "";

		try{

			link1 = new URL("https://cs.usfca.edu/~dhalperin/uberdistance.cgi?rideNumber="+rideNumber);

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

		Pattern tollPatt = Pattern.compile("Tolls: $(d+)");
		Matcher tollMatch = tollPatt.matcher(newInfo);
		while(tollMatch.find()){
			tolls = tollMatch.group(1);
		}
		int rideTolls = Integer.parseInt(tolls);
		return rideTolls;
	}

	public static int getDistance(String rideNumber){
		URL link1 = null;
		URLConnection conn = null;
		BufferedReader reader = null;
		String newInfo = "";
		StringBuilder builder = new StringBuilder();
		String distance = "";

		try{

			link1 = new URL("https://cs.usfca.edu/~dhalperin/uberdistance.cgi?rideNumber="+rideNumber);

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

		Pattern disPatt = Pattern.compile("Distance: (d+)");
		Matcher disMatch = disPatt.matcher(newInfo);
		while(disMatch.find()){
			distance = disMatch.group(1);
		}
		int rideDistance = Integer.parseInt(distance);
		return rideDistance;
	}

	public static Location getStartLocation(String rideNumber){
		URL link1 = null;
		URLConnection conn = null;
		BufferedReader reader = null;
		String newInfo = "";
		StringBuilder builder = new StringBuilder();
		String startLocation = "";

		try{

			link1 = new URL("https://cs.usfca.edu/~dhalperin/uberdistance.cgi?rideNumber="+rideNumber);

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

		Pattern fromPatt = Pattern.compile("FROM: ([A-Z][a-z]+)( [A-Z][a-z]+)?");
		Matcher fromMatch = fromPatt.matcher(newInfo);
		String from = "before";

		while(fromMatch.find()){
			from = fromMatch.group(0);
		}
		String [] arr = from.split(" ");
		String newFrom = "";
		for(String word: arr){
			if(!(word.equals("FROM") || word.equals("to") || word.equals("t"))){
				newFrom += word + " ";
			}
		}
		String actualFrom = newFrom.trim();
		return  Location.getByName(actualFrom);
	}
	public static Location getEndLocation(String rideNumber){
		URL link1 = null;
		URLConnection conn = null;
		BufferedReader reader = null;
		String newInfo = "";
		StringBuilder builder = new StringBuilder();
		String endLocation = "";

		try{

			link1 = new URL("https://cs.usfca.edu/~dhalperin/uberdistance.cgi?rideNumber="+rideNumber);

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

		Pattern toPatt = Pattern.compile("(To: ([A-Z][a-z]+)( [A-Z][a-z]+)?)");
		Matcher toMatch = toPatt.matcher(newInfo);
		String to = "before";

		while(toMatch.find()){
			to = toMatch.group(0);
		}
		String [] arr = to.split(" ");
		String newTo = "";
		for(String word: arr){
			if(!(word.equals("FROM") || word.equals("to") || word.equals("t"))){
				newTo += word + " ";
			}
		}
		String actualTo = newTo.trim();
		return  Location.getByName(actualTo);
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
		System.out.println(startLocation);
		try{
			if(!found){
				String url = "https://cs.usfca.edu/~dhalperin/uberdistance.cgi?rideNumber="+ rideNumber + "&start=" + startLocation;
				link1 = new URL(url);

			}else{
				newLocation = startLocation.replace(" ", "+");
				System.out.println(newLocation);
				String url1 = "https://cs.usfca.edu/~dhalperin/uberdistance.cgi?rideNumber=" + rideNumber + "&start=" + newLocation;
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
			System.out.println(builder);
		} catch(MalformedURLException e){
			System.out.println("Please enter a valid URL.");
		} catch(IOException e){
			System.out.println("incorrect.");
		}

		newInfo = builder.toString();
		System.out.println(newInfo);

		Pattern milesPatt = Pattern.compile("<i>([0-9]+)( miles)</i>");
		Matcher milesMatch = milesPatt.matcher(newInfo);
		String miles = "before";

		while(milesMatch.find()){
			miles = milesMatch.group(1);
		}
		
		System.out.println(miles);
		int newMiles = Integer.parseInt(miles);
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
				link1 = new URL("https://cs.usfca.edu/~dhalperin/uberdistance.cgi?rideNumber="+rideNumber+"&start="+startLocation);

			}else{
				newLocation = startLocation.replace(" ", "+");
				link1 = new URL("https://cs.usfca.edu/~dhalperin/uberdistance.cgi?rideNumber="+rideNumber+"&start="+newLocation);
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
		System.out.println(newInfo);
		
		Pattern minutesPatt = Pattern.compile("([0-9]+)( minutes)");
		Matcher minutesMatch = minutesPatt.matcher(newInfo);
		String minutes = "before";

		while(minutesMatch.find()){
			minutes = minutesMatch.group(0);
		}
		int newMinutes = Integer.parseInt(minutes);
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
		}

		newInfo = builder.toString();

		Pattern goldPatt = Pattern.compile("(d+) minutes");
		Matcher goldMatch = goldPatt.matcher(newInfo);
		while(goldMatch.find()){
			goldStar ++;
		}

		return goldStar;


	}

	public static void main(String []args){
		SSLVerificationPatch.applyPath();
	}

}
