import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Exercise {

	public static double[] createArray(int size){
	// simple for loop of i,j and stick elements in the array and return the array back
		int i;
		double newElement = 0;
		double [] array = new double[size];
		for(i = 0; i<size; i++){
				newElement = Math.random();
				array[i] = newElement;
			}
		return array;
	}
	public static long findByteSize(String fileName){
		Path file = Paths.get(fileName);
		long fileSize = 0;
		try{
				fileSize = Files.size(file);
		}
		catch(IOException err){
			System.out.println("I/O Exception: " +err);
		}
	return fileSize;
	}
	 // Displays the results
    public static void displayResults(String filename, long runTimeNanoseconds) {
        System.out.printf("\n%-34s", filename);
        System.out.printf("%8d bytes, saved in:", findByteSize(filename.trim()));
        System.out.printf("%7.2f milliseconds.\n", runTimeNanoseconds * 1e-6);
    } // end displayResults

}
