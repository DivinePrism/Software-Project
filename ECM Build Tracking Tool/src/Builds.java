/**
 * This class will build the window that will allow a user to load existing
 * builds and provide a list of existing loads that match their search 
 * criteria.
 * Note - The current implementation of the CSVReader requires specific data
 * positions be selected to select the correct data for each text field when 
 * loading a previously save sub-build. 
 * @author Robert Reinhard
 * @version 1.7
 * @date 11/11/2019
 */
/*
 * For presenting and retrieving builds produce only the build id's and 
 * use them for the options for choosing builds and also use them for retrieving the actual build from the database
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Builds extends JFrame {
	
	//Create a dimension minimum
	private final int MIN_WINDOW_WIDTH = 1000, MIN_WINDOW_HEIGHT = 900;
	private final Dimension minsize = new Dimension(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
	private final Dimension panelsize = new Dimension(500, 30);
	
	//Main components for retrieving info
	JTextField searchbar;
	JButton execute;
	JButton clear;
	JButton save;
	
	//ArrayList to hold JLabels and JTextFields in the window that edits the existing builds
	ArrayList<Object> changelog = new ArrayList<Object>();
	
	//Boolean so Panels do not overwrite each other
	boolean searchresult;
	boolean saved;
	
	public Builds() {
		super("Builds");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		setPreferredSize(minsize);
		setMinimumSize(minsize);
		setResizable(true);
		
		setLocationRelativeTo(null);
		
		setWindowComponents();
		
		pack();
		setVisible(true);
	}
	
	/**
	 * This method creates the windows and components
	 */
	private void setWindowComponents() {
		
		JPanel window = new JPanel();
		window.setLayout(new BorderLayout());
		
		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(new BoxLayout(panelNorth, BoxLayout.X_AXIS));
		panelNorth.setSize(500, 40);
		
		
		JLabel search = new JLabel("Search Existing Builds:");
		JPanel panelsearch = new JPanel();
		panelsearch.add(search);
		
		//search bar - input for searching data base
		searchbar = new JTextField(40);			
		JPanel panelsearchbar = new JPanel();
		panelsearchbar.add(searchbar);
		
		//Search button - searches data base with input form searchbar
		execute = new JButton("Search");
		JPanel panelexecute = new JPanel();
		panelexecute.add(execute);
		
		//Back Button for returning to the previous window/screen
		JButton back = new JButton("Back");
		JPanel panelback = new JPanel();
		panelback.add(back);
		
		panelNorth.add(panelback);		
		panelNorth.add(Box.createHorizontalGlue());
		panelNorth.add(Box.createHorizontalGlue());
		panelNorth.add(panelsearch);
		panelNorth.add(panelsearchbar);
		panelNorth.add(panelexecute);
		panelNorth.add(Box.createHorizontalGlue());
		panelNorth.add(Box.createHorizontalGlue());
		panelNorth.add(Box.createHorizontalGlue());
		window.add(panelNorth, BorderLayout.NORTH);
		add(window);

		//SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData 
		//Example options for searched info
		ArrayList<String> list = new ArrayList<String>(5);
		int arraysize = 10;
		for(int i = 0; i < arraysize; i ++){
			list.add("Option" + (i + 1));
		}
		
		searchresult = true;
		
		//Action Listener for Search Button
		execute.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				
				if(searchresult) {
				
					JButton searched = (JButton) event.getSource();
					
					String text = searchbar.getText();	
					
					//THIS MUST BE CHANGED ONCE LINKED TO DATABASE//////////////////////////////////////////////////////////////////////////////////////////
	//				if(text.equals("option")) {
					if(true) {
	
						System.out.println(text);		//String Searched
						
						/*
						 * Send to Data Base and retrieve matches of the string//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						 * Return a list or something from the data base and pass it to matches
						 * to create a list of options for the user to choose from
						 */					
						matches(window, list);	
						searchresult = false;
					}
				}
				else {
					JOptionPane.showMessageDialog(null,  "Clear the old search before starting a new search.", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				revalidate();
			}
		});
		
		//Action Listener for Back button
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {				
				dispose();		//closes this window
				
				//Code goes here to open previous window//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
			}
		});
		
		revalidate();
		repaint();
				
	}
	
	/**
	 * This method displays the matches from the database with the searched 
	 * terms
	 * @param window - the main JPanel that holds the search bar and results of the search
	 * @param matches - an ArrayList of Strings that match the searched term from the data base
	 */
	private void matches(JPanel window, ArrayList<String> matches) {
		
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		
		int size = matches.size();
		JButton select;
		
		for(int i = 0; i < size; i ++) {
						
			JPanel row = new JPanel();
			row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
			row.setSize(panelsize);

			select = new JButton((String) matches.get(i));
			JPanel panelselect = new JPanel();
			panelselect.add(select);
			
			//This gives each option created the ability to be selected
			select.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event) {
					JButton temp = (JButton) event.getSource();
					String text = temp.getText();
					
					remove(window);
					revalidate();
					repaint();
				
					selectedbuild(text);
					
				}
			});
			
			JLabel name = new JLabel("Info on this build.");
			JPanel panelname = new JPanel();
			panelname.add(name);
			
			row.add(panelselect);
			row.add(panelname);
			
			panelCenter.add(row);
		}

		JScrollPane scroller = new JScrollPane(panelCenter);
		
		window.add(scroller, BorderLayout.CENTER);
		
		JPanel panelSouth = new JPanel();
		panelSouth.setSize(500, 40);
		
		clear = new JButton("Clear Search");
		JPanel panelclear = new JPanel();
		panelclear.add(clear);		
		
		panelSouth.add(panelclear);
		
		window.add(panelSouth, BorderLayout.SOUTH);

		clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				searchresult = true;
				
				//Clear search area
				panelCenter.removeAll();
				window.remove(scroller);
				
				//Remove Clear Button
				window.remove(panelSouth);
				
				revalidate();
				repaint();
			}
		});
				
	}
	
	/**
	 * This method generates a new window that displays the sub builds of the 
	 * build chosen from the search results. It also displays an option to create
	 * a new sub-build
	 * @param selected - a String passing the name of the search result selected by the user
	 */
	private void selectedbuild(String selected) {
		
		//Main window panel
		JPanel window = new JPanel();
		window.setLayout(new BorderLayout());
		
		//panelNorth holds the header
		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(new BoxLayout(panelNorth, BoxLayout.Y_AXIS));
		
		//First Level of panelNorth
		JPanel level1 = new JPanel();
		level1.setLayout(new BoxLayout(level1,  BoxLayout.X_AXIS));
		
		JLabel title = new JLabel("Build " + selected);
		JPanel paneltitle = new JPanel();		
		paneltitle.add(title);

		JButton back = new JButton("Back");
		JPanel panelback = new JPanel();
		panelback.add(back);
		
		level1.add(back);
		level1.add(Box.createHorizontalGlue());
		level1.add(title);
		level1.add(Box.createHorizontalGlue());
		
		panelNorth.add(level1);
		
		//second Level of panelNorth
		JPanel level2 = new JPanel();
		level2.setLayout(new BoxLayout(level2, BoxLayout.X_AXIS));
		
		JButton subbuild = new JButton("Generate New Sub-Build");
		JPanel panelsubbuild = new JPanel();
		panelsubbuild.add(subbuild);
		
		
		JTextField versionnew = new JTextField(14);
		JPanel panelversionnew = new JPanel();
		panelversionnew.add(versionnew);
		
		level2.add(Box.createHorizontalGlue());
		level2.add(panelsubbuild);
		level2.add(new JLabel("New Version:"));
		level2.add(panelversionnew);
		level2.add(Box.createHorizontalGlue());
		panelNorth.add(level2);
		
		window.add(panelNorth, BorderLayout.NORTH);
		
		//Action Listener for the new sub build button. 
		subbuild.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent event) {
				
				LocalDate today = LocalDate.now();

				DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/YYYY");
				String datetoday = today.format(formatters);

				
				String version = versionnew.getText();
				
				if(version.equals("") || version.equals(null)) {
					JOptionPane.showMessageDialog(null,  "Enter a version number for the new sub-build.", "Missing Version", JOptionPane.WARNING_MESSAGE);
				}
				else {
					remove(window);
					revalidate();
					repaint();				
					
					changelog(selected, datetoday, version, true);	
				}
			}
		});
		
		//Center Panel, it holds the the options
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		
		//Number of versions/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		int numversions = 10;
		
		for(int i = 0; i < numversions; i ++) {
			
			JButton chosen = new JButton("Edit");
			JPanel panelchosen = new JPanel();
			panelchosen.add(chosen);
			
			
			//SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData 
			//Needs to be replaced with the Date retrieved from the Build
			String date = "xx/xx/xxxx";
			
			//SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData 
			//Needs to be replaced with the Version retrieved from the build
			Double versionnumber = 0.0;
			
			//SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData 
			//remove the + i next to version 
			JLabel information = new JLabel("Date: " + date + " -- Version: " + (versionnumber + i + 1));	
			
			JPanel panelinfo = new JPanel();
			panelinfo.add(information);
			
			JPanel row = new JPanel();
			row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
			row.add(panelchosen);
			row.add(panelinfo);
			
			panelCenter.add(row);	
			
			//SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData SampleData 
			String version = "Version " + Integer.toString(i+1);
			
			chosen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					
					String name = chosen.getText();
					
					remove(window);
					revalidate();
					repaint();
					changelog(selected, date, version, false);
				}
			});
			
		}
		
		JScrollPane scroller = new JScrollPane(panelCenter);
		window.add(scroller, BorderLayout.CENTER);

		add(window);
		revalidate();
		
		//ActionListener for back button - returns to previous screen
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				window.removeAll();
				remove(window);				
				setWindowComponents();
			}
		});
	}
	
	//Builds Change log window
	/**
	 * This method generates a grid of editable text boxes for the user to fill out
	 * and save the data to a CSV file
	 * @param name - a String that passes the name of the Build 
	 * @param date - a String that passes the date the Build was created
	 * @param version - a String that passes the version of the build
	 * @param newbuild - a boolean that passes whether the build is a new sub build or editing an existing one
	 */
	private void changelog(String name, String date, String version, Boolean newbuild) {
		
		//Reset saved boolean
		saved = false;
		
		//If build has been loaded
		CSVReader reader = null;
		
		if(!newbuild) {
			reader = new CSVReader(name + " " + version);
		}
		
		//Holds all component for information retrieval and CSV creation		
		JPanel window = new JPanel();
		window.setLayout(new BorderLayout());
		
		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(new BoxLayout(panelNorth, BoxLayout.Y_AXIS));
		
		//Header Label
		JPanel level1= new JPanel();
		level1.setLayout(new BoxLayout(level1, BoxLayout.X_AXIS));
		level1.setSize(new Dimension(500, 40));
		
		JLabel change = new JLabel("Change Log");
		JPanel panelchange = new JPanel();
		panelchange.add(change);
		
		JButton back = new JButton("Back");
		JPanel panelback = new JPanel();
		panelback.add(back);
		
		level1.add(panelback);
		level1.add(Box.createHorizontalGlue());
		level1.add(Box.createHorizontalGlue());
		level1.add(change);
		level1.add(Box.createHorizontalGlue());
		level1.add(Box.createHorizontalGlue());
		level1.add(Box.createHorizontalGlue());
		
		panelNorth.add(level1);
		
		//Identifying information
		JPanel level2= new JPanel();
		level2.setLayout(new BoxLayout(level2, BoxLayout.X_AXIS));
		level2.setSize(new Dimension(500, 40));
		
		JLabel title = new JLabel(date);
		JPanel paneltitle = new JPanel();
		paneltitle.add(title);
		
		JLabel numversion = new JLabel(version);
		JPanel panelnumverse = new JPanel();
		panelnumverse.add(numversion);
		
		level2.add(paneltitle);
		level2.add(panelnumverse);
		panelNorth.add(level2);
		window.add(panelNorth, BorderLayout.NORTH);
		
		//Grid for holding information
		JPanel grid = new JPanel();
		GridLayout grlayout = new GridLayout(8,6);
		grid.setLayout(grlayout);		
		
		//Header Row components
		JLabel program = new JLabel("Programs");
		JLabel loadname = new JLabel("LOADNAME");
		JLabel ecmname = new JLabel("ECM Name");
		JLabel loadtype = new JLabel("Load Type");
		JLabel request = new JLabel("Request Install");
		JLabel sites = new JLabel("Sites Installed");
		
		
		
		//Row Label components
		JLabel ads = new JLabel("ADS");
		JLabel cnd = new JLabel("CND");
		JLabel wcs = new JLabel("WCS");
		JLabel spy = new JLabel("SPY");
		JLabel rpl = new JLabel("RPL");
		JLabel stm = new JLabel("STM");
		JLabel wciop = new JLabel("WCIOP");		
		
		//Header Panels
		JPanel panelprogram = new JPanel();
		JPanel panelloadname = new JPanel();
		JPanel panelecmname = new JPanel();
		JPanel panelloadtype = new JPanel();
		JPanel panelrequest = new JPanel();
		JPanel panelsites = new JPanel();
		
		//Row Label Panels
		JPanel panelads = new JPanel();
		JPanel panelcnd = new JPanel();
		JPanel panelwcs = new JPanel();
		JPanel panelspy = new JPanel();
		JPanel panelrpl = new JPanel();
		JPanel panelstm = new JPanel();
		JPanel panelwciop = new JPanel();
		
		//Add Header Components
		panelprogram.add(program);
		panelloadname.add(loadname);
		panelecmname.add(ecmname);
		panelloadtype.add(loadtype);
		panelrequest.add(request);
		panelsites.add(sites);
		
		//Add Row Label components
		panelads.add(ads);
		panelcnd.add(cnd);
		panelwcs.add(wcs);
		panelspy.add(spy);
		panelrpl.add(rpl);
		panelstm.add(stm);
		panelwciop.add(wciop);
		
		//When adding components to the grid the grid fills row by row from left to right
		//Add Panels to Grid
		grid.add(panelprogram);
		grid.add(panelloadname);
		grid.add(panelecmname);
		grid.add(panelloadtype);
		grid.add(panelrequest);
		grid.add(panelsites);
		
		//Add to ArrayList  - for CSV creation
		changelog.add(program);
		changelog.add(loadname);
		changelog.add(ecmname);
		changelog.add(loadtype);
		changelog.add(request);
		changelog.add(sites);
				
		//add ads row  		CSV position start 10
		grid.add(panelads);
		changelog.add(ads);
		
		if(newbuild) {
			fillgrid(grid);
		}
		else {
			fillgridwithdata(grid, 10, reader);
		}
		
		//add CND row		CSV position start 16
		grid.add(panelcnd);
		changelog.add(cnd);
		if(newbuild) {
			fillgrid(grid);
		}
		else {
			fillgridwithdata(grid, 16, reader);
		}
		
		//add WCS row		CSV position start 22
		grid.add(panelwcs);
		changelog.add(wcs);
		if(newbuild) {
			fillgrid(grid);
		}
		else {
			fillgridwithdata(grid, 22, reader);
		}
		
		//add SPY row		CSV position start 28
		grid.add(panelspy);
		changelog.add(spy);
		if(newbuild) {
			fillgrid(grid);
		}
		else {
			fillgridwithdata(grid, 28, reader);
		}
		
		//add RPL row		CSV position start 34
		grid.add(panelrpl);
		changelog.add(rpl);
		if(newbuild) {
			fillgrid(grid);
		}
		else {
			fillgridwithdata(grid, 34, reader);
		}
		
		//add STM row		CSV position start 40
		grid.add(panelstm);
		changelog.add(stm);
		if(newbuild) {
			fillgrid(grid);
		}
		else {
			fillgridwithdata(grid, 40, reader);
		}
		
		//add WCIOP row		CSV position start 46
		grid.add(panelwciop);
		changelog.add(wciop);
		if(newbuild) {
			fillgrid(grid);
		}
		else {
			fillgridwithdata(grid, 46, reader);
		}
				
		window.add(grid, BorderLayout.CENTER);
		
		//South Panel
		JPanel panelSouth = new JPanel();
		
		save = new JButton("Save");		//Needs action listener to collect all input
		JPanel panelsubmit = new JPanel();
		
		panelsubmit.add(save);
		
		panelSouth.add(panelsubmit);
		window.add(panelSouth, BorderLayout.SOUTH);
				
		//Add main window
		add(window);
		revalidate();
		
		//ActionListener for Save button to save all data that has been written in the JTextFrames
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//Boolean for exit after saving
				saved = true;
//				printdata(name, date, version);		//uncomment to test data input
				sendtoCSV(name, date, version);
			}
		});		
		
		//ActionListener for back button to return to previous screen
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int result;
				
				if(!saved) {
					result = JOptionPane.showConfirmDialog(null,
										"If you exit, your information will not be saved.", 
										"Warning", 
										JOptionPane.YES_NO_OPTION);
				}
				else {
					result = 0;
				}
				
				if(result == 0) {
				window.removeAll();
				remove(window);				
				selectedbuild(name);
				
				/*Clear the ArrayList so that the created components are deleted and 
				 * do not overlap between loaded builds
				 */
				changelog.clear();
				}
			}
		});
	}
	
	/**
	 * This method generates the editable text boxes for the changelog method
	 * @param grid - the JPanel that holds the grid of text boxes and column and row headers
	 */
	private void fillgrid(JPanel grid) {
		
		for(int i = 0; i < 5; i++) {
			JTextField text = new JTextField(12);
			changelog.add(text);
			
			JPanel panel = new JPanel();
			panel.add(text);
			
			grid.add(panel);
		}
	}	
	
	/**
	 * This method generates editable text boxes for the changelog method and
	 * it fills these text boxes with data from a previously loaded sub-build
	 * @param grid - the JPanel that holds the grid of text boxes and column and row headers
	 * @param position - an integer that holds the position of the correct data to fill the text box with
	 * @param reader - a csv file reader that is preloaded with the data from the previously created sub build
	 */
	private void fillgridwithdata(JPanel grid, int position, CSVReader reader) {
		for(int i = 0; i < 5; i++) {
			JTextField text = new JTextField(12);
			text.setText(reader.read(position + i));
			
			changelog.add(text);
			
			JPanel panel = new JPanel();
			panel.add(text);
			
			grid.add(panel);
		}
	}
	
	/**
	 * This method retrieves the data input by the user and creates a CSV from it
	 * @param name - a String that passes the name of the Build 
	 * @param date - a String that passes the date the Build was created
	 * @param version - a String that passes the version of the build
	 */
	private void sendtoCSV(String name, String date, String version) {
		
		int size = changelog.size();
		
		//Generic initializations for type checking
		JLabel label = new JLabel();
		JTextField text = new JTextField();
		
		CSVCreator csvcreator = new CSVCreator();
		
		System.out.println("here1: " + csvcreator.toString() + "\n");
		
		//Information for storing the files in Data base should be retrieved form here
		//This is the final form where they will not be edited again//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		csvcreator.add(name);
		csvcreator.endline();
		csvcreator.add(date);
		csvcreator.endline();
		csvcreator.add(version);
		csvcreator.endline();
		
		System.out.println("here3: " + size);
		
		for (int i = 0; i < size; i ++) {

			Object item = changelog.get(i);
			if (item.getClass().equals(label.getClass())) {
				 label = (JLabel) item;
				 csvcreator.add(label.getText());
			}
			
			if (item.getClass().equals(text.getClass())){
				text = (JTextField) item;
				String data = text.getText();
				if(data.equals(null)) {
					System.out.println("here");
				}
				csvcreator.add(text.getText());
			}
			
			//Formating return spacing for CSVFile
			switch(i) {
			case 5: 		//ADS
			case 11:		//CNC
			case 17: 		//WCS
			case 23: 		//SPY
			case 29: 		//RPL
			case 35: 		//STM
			case 41: 		//WCIOP
				csvcreator.endline();
				break;
			}
		}
		

		//Create CSV from info
		csvcreator.createCSV(name + " " + version);
		
		/*Clear the ArrayList so that the created components are deleted and 
		 * do not overlap between loaded builds
		 */
		changelog.clear();
		
		System.out.println("Here2: " + csvcreator.toString() + "\n\n");
	}
	
	/**
	 * This method is a test method that prints the info input by the user
	 * @param name - a String that passes the name of the Build 
	 * @param date - a String that passes the date the Build was created
	 * @param version - a String that passes the version of the build
	 */
	private void printdata(String name, String date, String version) {
		
		int size = changelog.size();
		JLabel label = new JLabel();
		JTextField text = new JTextField();
		
		System.out.println("Name of Build: " + name);
		System.out.println("Date of Build: " + date);
		System.out.println("Name of Version: " + version);
		
		for (int i = 0; i < size; i ++) {
			
			Object item = changelog.get(i);
			if (item.getClass().equals(label.getClass())) {
				 label = (JLabel) item;
				 System.out.println(label.getText());
			}
			
			if (item.getClass().equals(text.getClass())){
				text = (JTextField) item;
				System.out.println(text.getText());
			}
		}
	}
}