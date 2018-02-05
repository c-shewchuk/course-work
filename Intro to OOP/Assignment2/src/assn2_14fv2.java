import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

// Francesco Virga ---- 14fv2 ---- 10168598 -- Feb 13 2017
public class assn2_14fv2 {
	
	public static void main(String args[]){
	double[][] data = new double[1000][8]; // Initialize array for CSV data
	importdata(data); // Import data
	analysedata(data); // Call analysedata method
	JOptionPane.showMessageDialog(null,
			"----------- Data analysis complete -----------\nRefer to \"MotorSummary.txt\" for results.",
			"Success",
			JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void importdata(double data[][]){ // Import data method
		int i = 0; // Row counter
		int j; // Column counter
	try {	
    	Scanner reader = new Scanner(new BufferedReader(new FileReader("Logger.csv"))); // Initialize scanner
	    String nextLine;
	    String line[];
	    while (reader.hasNextLine()) {
	    	nextLine = reader.nextLine(); // Store next line
	    	line = nextLine.split(","); // Split line into separate strings and assign to line[i]
	    	for(j=0;j<line.length;j++){ // Loop through each column
	    	data[i][j] = Double.parseDouble(line[j]); // Convert strings to doubles
	    	}
	    	i++; // Increment row counter
	     }		
		reader.close(); // Close reader
	}
	catch (FileNotFoundException e) { // Catch if file not found
		JOptionPane.showMessageDialog(null,"File not found!","Error",JOptionPane.ERROR_MESSAGE);
		System.exit(0); // Exit program
	} 
	}
	
	public static void analysedata(double data[][]){ // Scan through data, call readings method when currents above 1 amp found
		int i;
		int j;
		boolean empty; // Boolean to specify if motor had any readings above 8
		writetofile("Motor Use Summary",false); // Write string to file
		for(j=1;j<8;j++){ // Loop through data columns other than time
			writetofile("Motor " + j,true); // Write string to file
			empty = true;
			for(i=0;i<1000;i++){ // Loop through data rows
					if(data[i][j] > 1){ // If current exceeds 1
						i = readings(data,i,j); // Call readings method and return row counter
						empty = false;
					}
			}
			if(empty == true) // If motor did not reach currents above 1 amp
				writetofile("* Motor did not run *",false); // Write string to file
		}
	}
	
	public static int readings(double data[][],int i,int j){  // Method to computer average current and time boundaries
		double timef; // Final time
		double total = 0; // Total current
		double avgcurrent; // Average current
		double timei = data[i][0]; // Store initial time
		while(data[i][j]>1){ // Loop until current has fallen below 1 amp
			total += data[i][j]; // Add current values
			i++; // Increment row counter
		}
		i--; // Decrease row counter by 1 to set to last current value
		timef = data[i][0]; // Store final time
		avgcurrent = total/(timef - timei); // Calculate average current
		
		if(avgcurrent > 8){ // If average current exceeds 8
			writetofile(String.format("*** MAX CURRENT EXCEEDED ***\n %.3f amps, running from %.0fs to %.0fs (%.0f seconds).",
						avgcurrent,timei,timef,timef-timei), false);  // Write string to file
		}
		else{
		writetofile(String.format("%.3f amps, running from %.0fs to %.0fs (%.0f seconds).",
				avgcurrent,timei,timef,timef-timei), false); // Write string to file
		}
		return i; // Return row counter
	}
	
	public static void writetofile(String st, boolean ln){ // Method that writes to text file "MotorSummary.txt"
		//BufferedWriter write = null;  // Initialize BufferedWriter
		try{
		BufferedWriter write = new BufferedWriter(new FileWriter(new File("MotorSummary.txt"),true)); // Create/open file "MotorSummary.txt", and initialize writer
		if(ln == true) // If a line needed to separate motor readings
			write.newLine(); // Write line
        write.write(st); // Write input string
        write.newLine(); // Write line
		write.close(); // CLose writer
		}
		catch(IOException e){ // Catch if IO exception
			e.printStackTrace();
		}
	}
}
