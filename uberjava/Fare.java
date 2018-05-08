package uberjava;

import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.StringBuilder;
import uberjava.UberJavaDriver;


abstract class Fare{

	public static String getFare(UberJavaDriver driver){
		URL link1 = null;
		URLConnection conn = null;
		BufferedReader reader = null;
		String newName = "";
		StringBuilder builder = new StringBuilder();
		String name = driver.getName();

		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(name);
		boolean found = matcher.find();
		try{

			if(!found){

				link1 = new URL("https://cs.usfca.edu/~dhalperin/nextFare.cgi?driver="+name);

			}else{
				newName = name.replace(" ", "+");
				link1 = new URL("https://cs.usfca.edu/~dhalperin/nextFare.cgi?driver="+newName);
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

		return builder.toString();
			
		
	}
}