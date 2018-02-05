import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class Assgn2_14cms13 {
	
	public static void main(String[] args){
		double motorData[][] = new double[1000][8];
		readFile(motorData);
		checkMotorArray(motorData);
		System.out.println("Analysis of motor data complete! Analysis can be found in the Motor.txt file.");
	}
	// Method invoked to read the file and read the values into a 2D array
	public static void readFile(double motorData[][]){ 
		int i = 0;
		int j; 
		try {	
    	Scanner reader = new Scanner(new BufferedReader(new FileReader("Logger.csv"))); // Initialize scanner
	    String nextLine;
	    String line[];
	    	while (reader.hasNextLine()) {
	    		nextLine = reader.nextLine();
	    		line = nextLine.split(",");
	    		for(j=0;j<line.length;j++){
	    			motorData[i][j] = Double.parseDouble(line[j]); // Convert strings to doubles
	    		}
	    		i++;
	     }		
		reader.close(); // Close reader
	}
		catch (FileNotFoundException e) {
		System.exit(0); 
		} 
	}
	// short method to write to the text file. The file is titled Motor.txt. The method attempts to create a file, and if it can't, it throws an exception
	public static void writeToFile(String lineToWrite, boolean test){
		try{
			BufferedWriter fileToWrite = new BufferedWriter(new FileWriter(new File("Motor.txt"), true));
			if(test == true)
				fileToWrite.newLine();
			fileToWrite.write(lineToWrite);
			fileToWrite.newLine();
			fileToWrite.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	//method to check if the motors are on, and if it is found that the motor was on record that data and write it to the text file
	public static void checkMotorArray(double[][] motorData){
		int i,j;
		boolean isEmpty = true; // assumed that the column is initially empty.
		writeToFile("Summary of Motor Data", false);
		for(j = 1; j<8;j++){
			writeToFile("Motor " + j, true);
			isEmpty = true;
			for(i = 0; i<1000; i++){
				if(motorData[i][j] >1){
					i = aboveOneAmp(motorData, i, j);
					isEmpty  = false; // some data was found, no longer an empty column
				}
					
			}
			if(isEmpty)
				writeToFile("Motor " + j +" did not run", false);
		}
	}
	//takes in the index where values above one amp were found. Finds the final time where the motor turned off and calculates the average current on the interval. 
	//The method also returns the index where the time ended so that the rows are not accounted for more then once.
	public static int aboveOneAmp(double[][] motorData,int i, int j){
		double totalCurrent = 0;
		double averageCurrent = 0;
		double finalTime = 0;
		double initialTime = motorData[i] [0];
		while(motorData[i][j] >1){
			totalCurrent += motorData[i][j];
			i++;
		}
		//decrease i by one, because at this index, and the condition to break out of the loop, the value at that i must've been less then one amp, or the motor would be "off"
		i--;
		finalTime = motorData[i][0];
		averageCurrent = totalCurrent / (finalTime - initialTime);
		//Now need to check if the average current was above 8 amps on the interval, and indicate this on the file if it's the case
		if(averageCurrent > 8)
			writeToFile(String.format("MAX CURRENT EXCEEDED!!\n  %.3f amps, running from %.0fs to %.0fs (%.0f seconds).",
					averageCurrent,initialTime,finalTime, (finalTime-initialTime)), false);
		else
			writeToFile(String.format("%.3f amps, running from %.0fs to %.0fs (%.0f seconds).",
					averageCurrent,initialTime,finalTime,(finalTime-initialTime)), false);
		return i; //the index of the final time to continue looping through, otherwise this method will run for each value above one amp in the interval
	}
		
}
