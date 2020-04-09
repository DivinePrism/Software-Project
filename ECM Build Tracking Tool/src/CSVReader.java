/**
 * This class is used to read a CSV created by the CSVCreator. 
 * Note: The current format of the CSVReader is meant to give direct access to 
 * any of the individual data points. 
 * @author Robert Reinhard
 * @version 1.0
 * @date 12/09/2019
 */

import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JOptionPane;

public class CSVReader {
	
	String[] csv;
	
	/**
	 * This method creates the CSVReader. It takes a name to retrieve the file. 
	 * Once the file has been successfully retrieved, the file is read line by line
	 * into a String builder until there are no more lines to be read. When there 
	 * are no more lines to be read, the string builder is converted to a single
	 * String and split into an array of Strings based on the commas. The array 
	 * is then left to be accessed by accessor methods.
	 * @param filename - a String type that passes the name of the targeted CSV file
	 */
	public CSVReader(String filename) {
		
		try {
			FileReader filereader;
			
			//Use filename to retrieve file with the path given. 
			//Create a folder called CSV Repository and add the path to that folder below.
			filereader = new FileReader("/Users//Jeff//Desktop//" + filename+ ".csv");
			
			BufferedReader reader = new BufferedReader(filereader);
			StringBuilder csvreader = new StringBuilder("");
			
			//Temporary string for the read lines
			String row;
			
			//Reads each line and adds it to the string builder
			while ((row = reader.readLine()) != null) {
				csvreader.append(row);
			}
			
			//split the builder into the array by the ','s
			csv = csvreader.toString().split(",");
			
			reader.close();
			filereader.close();
		}
		catch (Exception e) {
			//Error Message
			JOptionPane.showMessageDialog(null,  "The CSV was unable to be opened.", "File Open Error", JOptionPane.ERROR_MESSAGE);
			System.err.println("ERROR");
		}
	}
	
	/**
	 * This method retrieves data from the array based on its position in the array. 
	 * @param position - an Integer type that passes the position of the requested data
	 * @return - the requested data from the String array
	 */
	protected String read(int position) {
		return csv[position];
	}
	
	/**
	 * This method retrieves the size of the array holding the data. 
	 * @return - returns the size
	 */
	protected int size() {
		return csv.length;
	}
	
	/**
	 * This method is a test method that outputs the positions and the data in 
	 * the listed positions. 
	 */
	protected void datapostions() {
		for(int i = 0; i < csv.length; i ++) {
			System.out.println(i + ".\t" + csv[i]);
		}
	}

}
