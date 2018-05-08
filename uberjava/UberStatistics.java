package uberjava;

import uberjava.UberJavaDriver;

public class UberStatistics{

	private int sessionNumber;
	private boolean isInSession;
	private int totalMinutes;

	public UberStatistics(UberJavaDriver driver){
		this.sessionNumber = driver.getSessionNumber();
		this.isInSession = driver.getIsInSession();
		this.totalMinutes = driver.getSessionMinutes();
	}

	public String toString(){
		return "UberStatistics\nSessionNumber: "+ sessionNumber +"\nIs in Session: "+ isInSession+ "TotalMinutes of Session: "+ totalMinutes;
	}
}