/**
 * This class is used to create a CSV
 * @author Jeff Wang, Robert Reinhard, Ethan Hudak
 * @version 1.5
 * @date 11/26/2019 
 */
import javax.swing.JOptionPane;

import java.io.FileWriter;
public class CSVCreator {

	StringBuilder builder;
	
	/**
	 * Creates the string builder to construct the csv file
	 */
	CSVCreator(){
		builder = new StringBuilder("");
	}
	
	/**
	 * Adds data to the string builder
	 * @param data - a string that holds the data
	 */
	protected void add(String data) {
		builder.append(data + ",");
	}
	
	/**
	 * This method adds a line return in the String Builder. This allows for more precise formating. 
	 */
	protected void endline() {
		builder.append("\n");
	}
	
	/**
	 * This method constructs the csv from the string builder which is used to add all of the components
	 * @param name - a String type that passes the name of the csv file to be created
	 */
	public void createCSV(String name) {
		
		//end of file marker for proper loading of blank data fields
		builder.append("eof");
	
		FileWriter info = null;
		try{
		//path of where you want it (/buildtest.csv is name of the file you want called) 
		//true allows to append	
		
		//The path will need to be changed for different computers using this file
		//but leave ' + name + ".csv"' at the end, as this creates the name of the file. 
		info = new FileWriter("/Users//Jeff//Desktop//" + name + ".csv",false);
		String sendOut = builder.toString();			//write to the csv
		info.write(sendOut);			//actaully sending it to csv
		info.flush();
		
		info.close();
		
		//Confirmation Message
		JOptionPane.showMessageDialog(null,  "Saved to a .csv file.", "Saved", JOptionPane.PLAIN_MESSAGE);
		
		}catch(Exception e){
			//Error Message
			JOptionPane.showMessageDialog(null,  "The data was not saved to the CSV.", "Did Not Save", JOptionPane.ERROR_MESSAGE);
			System.err.println("ERROR");
		}
	}
	
	/**
	 * This method returns the full string of the String Builder
	 * @return - a String containing the string builder
	 */
	public String toString() {
		return builder.toString();
	}
}