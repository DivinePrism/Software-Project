/**
 * This class will build the window that will allow a user to create a new Build 
 * by adding information about the new build and creating it.
 * @author Robert Reinhard
 * @version 1.9
 * @date 10/11/2019
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.util.ArrayList;

public class New_Build extends JFrame{

	//Create a dimension minimum
	private final int MIN_WINDOW_WIDTH = 1000, MIN_WINDOW_HEIGHT = 900;
	private final Dimension minsize = new Dimension(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
	
	//Main components made global for easier retrieving info
	private JPanel window;					//Main JPanel for all components
	private JTextField buildname;
	private JTextField configurations;
	private JComboBox sites;
	private JComboBox loads;
	private JTextField notes;
	private JButton submit;
	private JTextField dates;	
	
	//Structures for holding Components
	private int numcompilers;									//compiler info retrieval components
	private JTextField compilernames[] = new JTextField[9];
	private JTextField compilerversions[] = new JTextField[9];		
	private ArrayList<PIL> pils = new ArrayList<PIL>();			//Program, identifier, location info retrieval components
	
	//Boolean if Saved
	private boolean saved;
		
	public New_Build(){
		super("New Build");
		
		saved = false;
		
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
	 * This method builds the window components to create the GUI
	 */
	private void setWindowComponents() {		
		//Main Panel
		window = new JPanel();
		window.setLayout(new BorderLayout());
		
		//North panel set up
		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(new BoxLayout(panelNorth, BoxLayout.X_AXIS));
		
		JLabel header = new JLabel("Create a new Build");
		JPanel panelheader = new JPanel();
		panelheader.add(header);
		
		JButton back = new JButton("Back");
		JPanel panelback = new JPanel();
		panelback.add(back);
		
		panelNorth.add(panelback);
		panelNorth.add(Box.createHorizontalGlue());
		panelNorth.add(panelheader);
		panelNorth.add(Box.createHorizontalGlue());
		panelNorth.add(Box.createHorizontalGlue());
		
		window.add(panelNorth, BorderLayout.NORTH);
		
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
					dispose();		//closes this window
					
					//Code goes here to open previous window//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				}
			}
		});
		
		//Main Center Panel
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		
		JPanel fields = new JPanel();
		fields.setLayout(new BorderLayout());
		
		Dimension fieldsize = new Dimension(200, 35);
		
		// Creation of the panels for individual rows, Not all rows are in the same order as in the GUI
		JPanel row1 = new JPanel();		//Official Build Queues
		JPanel row2 = new JPanel();		//Compilers
		JPanel row3 = new JPanel();		//Configuration
		JPanel row4 = new JPanel();		//Site
		JPanel row5 = new JPanel();		//Load Compatibility Index
		JPanel row6 = new JPanel();		//Notes
		JPanel row7 = new JPanel();		//Submit Button
		JPanel row8 = new JPanel();		//Date Field
		JPanel row9 = new JPanel();		//Program, Identifier, Location		
		JPanel row10 = new JPanel();	//Container row for selected Programs with associated identifier and associated Location
		
		//Format Rows
		row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
		row2.setLayout(new BoxLayout(row2, BoxLayout.Y_AXIS));
		row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
		row4.setLayout(new BoxLayout(row4, BoxLayout.X_AXIS));
		row5.setLayout(new BoxLayout(row5, BoxLayout.X_AXIS));
		row6.setLayout(new BoxLayout(row6, BoxLayout.X_AXIS));
		row7.setLayout(new BoxLayout(row7, BoxLayout.X_AXIS));
		row8.setLayout(new BoxLayout(row8, BoxLayout.X_AXIS));
		row9.setLayout(new BoxLayout(row9, BoxLayout.X_AXIS));
		row10.setLayout(new BoxLayout(row10, BoxLayout.Y_AXIS));
		
		//Format Rows
		row1.setPreferredSize(fieldsize);
		row2.setPreferredSize(new Dimension(1000, 150));
		row3.setPreferredSize(fieldsize);
		row4.setPreferredSize(fieldsize);
		row5.setPreferredSize(fieldsize);
		row6.setPreferredSize(fieldsize);
		row7.setPreferredSize(fieldsize);
		row8.setPreferredSize(fieldsize);
		row9.setPreferredSize(new Dimension(1000, 150));
		row10.setPreferredSize(new Dimension(1000, 150));
		
		//Row 1		
		makebuildqueuecreator(row1, fieldsize);
		
		//Row 2		
		makecompilerfield(fieldsize, row2);
		
		//Row 3
		makeconfigurations(row3, fieldsize);
		
		//Row 4
		makesite(row4, fieldsize);
		
		//Row 5
		makeload(row5, fieldsize);
		
		//Row 6
		makenotes(row6, fieldsize);
		
		//Row 7  This is the bottom row because it is the submit button
		makesubmit(row7, fieldsize);
		
		//Row 8
		makedate(row8, fieldsize);
		
		//Row 9	 method for row10 called in part 3 of row9	
		JPanel rowbox = new JPanel();  	//holder JPanel for row10
		makepilcreator(row9, rowbox);
		
		//Row 10
		rowbox.setLayout(new BoxLayout(rowbox, BoxLayout.Y_AXIS));
		JScrollPane scroller10 = new JScrollPane(rowbox);
		row10.add(scroller10);
		
		//Add Rows to main field and Center Panel
		panelCenter.add(fields.add(row1));				//Build Queues
		panelCenter.add(fields.add(row2));				//Compilers
		panelCenter.add(fields.add(row3));				//Configuration
		panelCenter.add(fields.add(row4));				//Site
		panelCenter.add(fields.add(row5));				//Load Compatibility index
		panelCenter.add(fields.add(row8));				//Date field
		panelCenter.add(fields.add(row9));				//Programs, Identifiers, and Locations
		panelCenter.add(fields.add(row10));				//Selected Programs, Identifiers and Locations
		panelCenter.add(fields.add(row6));				//Notes
		window.add(row7, BorderLayout.SOUTH);			//Submit Button
		
		panelCenter.setVisible(true);
		
		window.add(panelCenter, BorderLayout.CENTER);
		
		add(window);
		
		pack();				
	}
	
	/**Row1
	 * This method creates the first option for entering a name of the build
	 * @param row1 - the JPanel that holds the first row
	 * @param fieldsize - the dimension used for formating individual components
	 */
	private void makebuildqueuecreator(JPanel row1, Dimension fieldsize) {
		
		JLabel build = new JLabel("Official Build Queues:");
		buildname = new JTextField(40);
		JPanel panelbuild = new JPanel();
		JPanel panelbuildname = new JPanel();
		
		panelbuild.add(build);
		panelbuildname.add(buildname);
		
		panelbuild.setPreferredSize(fieldsize);
		panelbuildname.setPreferredSize(fieldsize);
		
		row1.add(panelbuild);
		row1.add(panelbuildname);
	}
		
	/**
	 * Row2 part 1
	 * This method builds the JPanels that will hold the information for the 
	 * compilers in the newly created Build
	 * @param fieldsize - the dimension used for formating individual components
	 * @param row2 - the main JPanel that will hold the created JPanels
	 */
	private void makecompilerfield(Dimension fieldsize, JPanel row2) {
		
		JPanel main = new JPanel();
		JPanel rowa = new JPanel();
		
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		rowa.setLayout(new BoxLayout(rowa, BoxLayout.X_AXIS));		
		
		//Drop Down Options
		Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		
		JComboBox<Integer> numcompilers = new JComboBox<Integer>(numbers);
		numcompilers.setSelectedIndex(-1);
		
		JLabel compiler = new JLabel("Compilers:");
		
		JPanel panelnumcompilers = new JPanel();
		JPanel panelcompiler = new JPanel();
		
		panelnumcompilers.add(numcompilers);
		panelcompiler.add(compiler);
		
		rowa.add(panelcompiler);
		rowa.add(panelnumcompilers);
		
		panelnumcompilers.setPreferredSize(fieldsize);
		panelcompiler.setPreferredSize(fieldsize);
		
		main.add(rowa);
		
		numcompilers.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				
				JComboBox temp = (JComboBox)event.getSource();
				
				int selected = (Integer)temp.getSelectedItem();
				
				temp.addActionListener(this);
				
				dropdown(main, rowa, selected, fieldsize);
				
				revalidate();
			}
		});
		
		JScrollPane scroller = new JScrollPane(main);
		
		row2.add(scroller);
	}
	
	/**Row2 part 2
	 * This method creates editable text boxes based on the number of compilers the 
	 * user selects to fill out.
	 * @param main - the main JPanel that will hold the created JPanels
	 * @param rowa - The header JPanel for this section
	 * @param selected - the number of compilers the user selected to be entered
	 */
		private void dropdown(JPanel main, JPanel rowa, int selected, Dimension fieldsize) {
			numcompilers = selected;
			
			main.removeAll();
			main.add(rowa);
			
			if(selected != 0) {	
				
				JPanel rowb = new JPanel();
				rowb.setLayout(new BoxLayout(rowb, BoxLayout.X_AXIS));
				
				JLabel name = new JLabel("Name");
				JLabel version = new JLabel("Version");
				
				JPanel panelname = new JPanel();
				JPanel panelversion = new JPanel();
				
				panelname.setPreferredSize(fieldsize);
				panelversion.setPreferredSize(fieldsize);
				
				panelname.add(name);
				panelversion.add(version);
				rowb.add(panelname);
				rowb.add(panelversion);
				main.add(rowb);
				
				for(int i = 0; i < selected ; i++) {
					
					JPanel rownext = new JPanel();
					rownext.setLayout(new BoxLayout(rownext, BoxLayout.X_AXIS));
					
					JTextField compilername = new JTextField(25);
					JTextField compilerversion = new JTextField(25);
					
					compilernames[i] = compilername;
					compilerversions[i] = compilerversion;
					
					JPanel fieldname = new JPanel();
					JPanel fieldversion = new JPanel();
					
					fieldname.setPreferredSize(fieldsize);
					fieldversion.setPreferredSize(fieldsize);
					
					fieldname.add(compilername);
					fieldversion.add(compilerversion);
					rownext.add(fieldname);
					rownext.add(fieldversion);
					main.add(rownext);
				}
			}
		}
	
	/**Row3
	 * This method builds the row for the configurations section
	 * @param row3 - JPanel for holding all the components created
	 * @param fieldsize
	 */
	private void makeconfigurations(JPanel row3, Dimension fieldsize) {

		JLabel configuration = new JLabel("Configuration:");
		configurations = new JTextField(40);
		JPanel panelconfiguration = new JPanel();
		JPanel panelconfigurations = new JPanel();
		
		panelconfiguration.add(configuration);
		panelconfigurations.add(configurations);
		
		panelconfiguration.setPreferredSize(fieldsize);
		panelconfigurations.setPreferredSize(fieldsize);
		
		row3.add(panelconfiguration);
		row3.add(panelconfigurations);		
		
	}
	
	/**
	 * Row4
	 * This method builds the row for the site section.
	 * @param row4 - JPanel for holding all the components created
	 * @param fieldsize - the dimension used for formating individual components
	 */
	private void makesite(JPanel row4, Dimension fieldsize) {
		JLabel site = new JLabel("Site:");
		
		//Sample input for Site drop down options
		String[] sample = {"Site 1", "Site 2", "Site 3", "Site 4", "Site 5"};
		
		sites = new JComboBox<Object>(sample);			
		sites.setSelectedIndex(-1);
		
		JPanel panelsite = new JPanel();
		JPanel panelsites = new JPanel();
		
		panelsite.add(site);
		panelsites.add(sites);
		
		panelsite.setPreferredSize(fieldsize);
		panelsites.setPreferredSize(fieldsize);
		
		row4.add(panelsite);
		row4.add(panelsites);		
	}
	
	/**
	 * Row5
	 * This method builds the row for the load section
	 * @param row5 - JPanel for holding all the components created
	 * @param fieldsize - the dimension used for formating individual components
	 */
	private void makeload(JPanel row5, Dimension fieldsize) {
		
		String[] sample = {"Load 1", "Load 2", "Load 3", "Load 4", "Load 5"};
		
		
		JLabel load = new JLabel("Load Compatability Index:");
		loads = new JComboBox<Object>(sample);			
		loads.setSelectedIndex(-1);
		
		JPanel panelload = new JPanel();
		JPanel panelloads = new JPanel();
		
		panelload.add(load);
		panelloads.add(loads);
		
		panelload.setPreferredSize(fieldsize);
		panelloads.setPreferredSize(fieldsize);
		
		row5.add(panelload);
		row5.add(panelloads);
		
	}
	
	/**
	 * Row6
	 * This method builds the row for the Notes section
	 * @param row6 - JPanel for holding all the components created
	 * @param fieldsize - the dimension used for formating individual components
	 */
	private void makenotes(JPanel row6, Dimension fieldsize){
		JLabel note = new JLabel("Notes:");
		notes = new JTextField(40);
		JPanel panelnote = new JPanel();
		JPanel panelnotes = new JPanel();
		
		panelnote.add(note);
		panelnotes.add(notes);
		
		panelnote.setPreferredSize(fieldsize);
		panelnotes.setPreferredSize(fieldsize);
		
		row6.add(panelnote);
		row6.add(panelnotes);
		
	}
	
	/**
	 * Row7
	 * This method builds the row for the submit button section, this method also outputs all information input into 
	 * @param row7 - JPanel for holding all the components created
	 * @param fieldsize - the dimension used for formating individual components
	 */
	private void makesubmit(JPanel row7, Dimension fieldsize) {
		submit = new JButton("Save");		
		JPanel panelsubmit = new JPanel();
		
		panelsubmit.add(submit);		
		panelsubmit.setPreferredSize(fieldsize);
		
		row7.add(panelsubmit);
		
		submit.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent event) {
//				printdata();	//Output the inputed data to the console
				sendtoCSV();	//Create CSV from input
			}
		});
	}
	
	/**
	 * This method retrieves the data from the GUI and loads it into a CSV builder. It then builds the CSV.
	 */
	private void sendtoCSV() {
		
		CSVCreator csv = new CSVCreator();
		
		//Empty String for blank data fields
		String emptystr = "";
		
		//Strings for extracting data
		String databuildname = emptystr;
		String dataconfigs = emptystr;
		String datasites = emptystr;
		String dataloads = emptystr;
		String datadates = emptystr;
		String datanotes = emptystr;
		String datacompname = emptystr;
		String datacompvers = emptystr;
		
		//Extracting Data
		databuildname = buildname.getText();
		
		dataconfigs = configurations.getText();
		datasites = (String) sites.getSelectedItem();
		dataloads = (String) loads.getSelectedItem();
		datadates = dates.getText();
		datanotes = notes.getText();
		
		if(datasites == null)
			datasites = emptystr;
		if(dataloads == null)
			dataloads = emptystr;
		
		if (databuildname.equals(emptystr)) {

			JOptionPane.showMessageDialog(null,  "The Build does not have a name to be saved under.", "Missing Build Name", JOptionPane.WARNING_MESSAGE);
		}
		else {			
			saved = true;
			
			//Add info to CSV
			csv.add(databuildname);
			csv.endline();
			
			for(int i = 0; i < numcompilers; i ++) {
				datacompname = compilernames[i].getText();
				datacompvers = compilerversions[i].getText();
				
				if (datacompname == null)
					datacompname = emptystr;
				if (datacompvers == null)
					datacompvers = emptystr;
				
				csv.add(datacompname);
				csv.endline();
				csv.add(datacompvers);
				csv.endline();
			}
	
			csv.add(dataconfigs);
			csv.endline();
			csv.add(datasites);
			csv.endline();
			csv.add(dataloads);
			csv.endline();
			csv.add(datadates);
			csv.endline();
			
			int pilsize = pils.size();
			for(int i = 0; i < pilsize; i ++) {
				csv.add(pils.get(i).getProgram());
				csv.endline();				
				csv.add(pils.get(i).getIdentifier());
				csv.endline();
				csv.add(pils.get(i).getLocation());			
				csv.endline();
			}
			
			csv.add(datanotes);
			csv.endline();
			
			//Create CSV
			csv.createCSV(databuildname);
		}
	}
	
	/**
	 * This method is a test method to print all data that was input to the GUI
	 */
	private void printdata() {
		//Empty String for blank data fields
		String emptystr = "";
		
		//Strings for extracting data
		String databuildname = emptystr;
		String dataconfigs = emptystr;
		String datasites = emptystr;
		String dataloads = emptystr;
		String datadates = emptystr;
		String datanotes = emptystr;
		String datacompname = emptystr;
		String datacompvers = emptystr;
		
		//Extracting Data
		databuildname = buildname.getText();		
		dataconfigs = configurations.getText();
		datasites = (String) sites.getSelectedItem();
		dataloads = (String) loads.getSelectedItem();
		datadates = dates.getText();
		datanotes = notes.getText();
		
		if(datasites == null)
			datasites = emptystr;
		if(dataloads == null)
			dataloads = emptystr;
		
		if (databuildname.equals(emptystr)) {

			JOptionPane.showMessageDialog(null,  "The Build does not have a name to be saved under.", "Missing Build Name", JOptionPane.WARNING_MESSAGE);
		}
		else {	
			saved = true;
			
			//Print Data to console
			System.out.println("Build: " + databuildname);
			
			for(int i = 0; i < numcompilers; i ++) {
				datacompname = compilernames[i].getText();
				datacompvers = compilerversions[i].getText();
				
				if (datacompname == null)
					datacompname = emptystr;
				if (datacompvers == null)
					datacompvers = emptystr;
				
				System.out.println("Compiler Name " + (i+1) + ":" + datacompname);
				System.out.println("Compiler Version " + (i+1) + ":" + datacompvers);
			}
			
			System.out.println("Configurations: " + dataconfigs);
			System.out.println("Sites: " + datasites);
			System.out.println("Loads: " + dataloads);
			System.out.println("Dates: " + datadates);
			
			int pilsize = pils.size();
			for(int i = 0; i < pilsize; i ++) {
				System.out.println("Programs: " + pils.get(i).getProgram());
				System.out.println("Identifiers: " + pils.get(i).getIdentifier());
				System.out.println("Locations: " + pils.get(i).getLocation() + "\n");
			}
			
			System.out.println("Notes: " + datanotes + "\n");
		}
	}
	
	/**
	 * Row8
	 * This method builds the row for the date section
	 * @param row8 - JPanel for holding all the components created
	 * @param fieldsize
	 */
	private void makedate(JPanel row8, Dimension fieldsize) {
		JLabel date = new JLabel("Date:");
		dates = new JTextField(40);

		LocalDate today = LocalDate.now();
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/YYYY");
		String todaysdate = today.format(formatters);
		
		dates.setText(todaysdate);
		

		JPanel paneldate = new JPanel();
		JPanel paneldates = new JPanel();
		
		paneldate.setPreferredSize(fieldsize);
		paneldates.setPreferredSize(fieldsize);
		
		paneldate.add(date);
		paneldates.add(dates);
		
		row8.add(paneldate);
		row8.add(paneldates);
	}
	
	/**
	 * Row9 part 1
	 * This method populates the row for programs identifiers and locations. 
	 * @param row9 - JPanel for holding all the components created. 
	 */
	private void makepilcreator(JPanel row9, JPanel rowbox){
		
		Dimension optionsize = new Dimension(90, 40);
		Dimension listsize = new Dimension(90, 100);
		
		//Main JPanels to hold all components in Row9
		JPanel allprograms = new JPanel();
		JPanel allidentifiers = new JPanel();
		JPanel alllocations = new JPanel();

		allprograms.setLayout(new BoxLayout(allprograms, BoxLayout.Y_AXIS));
		allidentifiers.setLayout(new BoxLayout(allidentifiers, BoxLayout.Y_AXIS));
		alllocations.setLayout(new BoxLayout(alllocations, BoxLayout.Y_AXIS));

		allprograms.setMaximumSize(listsize);		
		allidentifiers.setMaximumSize(listsize);
		alllocations.setMaximumSize(listsize);
		
		//Column Header Labels
		JLabel program = new JLabel("Program");
		JLabel identifier = new JLabel("Program Identifier/Software Version");
		JLabel location = new JLabel("Location");
		
		JPanel panelprograms = new JPanel();
		JPanel panelidentifiers = new JPanel();
		JPanel panellocations = new JPanel();
		
		panelprograms.add(program);
		panelidentifiers.add(identifier);
		panellocations.add(location);
		
		panelprograms.setPreferredSize(optionsize);
		panelidentifiers.setPreferredSize(optionsize);
		panellocations.setPreferredSize(optionsize);
		
		//Add headers to columns
		allprograms.add(panelprograms);
		allidentifiers.add(panelidentifiers);
		alllocations.add(panellocations);
		
		ButtonGroup group1 = new ButtonGroup();
		
		ActionListener listener1 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JRadioButton tmpbutton = (JRadioButton) e.getSource();
				
				String selectedProgram = tmpbutton.getText();
				
				pilCreatorpart2(selectedProgram, allidentifiers, alllocations, panelidentifiers, panellocations, rowbox);
								
				alllocations.revalidate();				
			}
		};
		
		//Sample Program Input
		String[] samplePrograms = {"Program 1", "Program 2", "Program 3", "Program 4", "Program 5"};
		
		//Options for all programs
		int numoptions = samplePrograms.length;
		
		for(int i = 0; i < numoptions; i++) {
			JPanel option = new JPanel();
			
			JRadioButton selected = new JRadioButton(samplePrograms[i]);
			selected.setActionCommand(samplePrograms[i]);			
			group1.add(selected);			
			selected.addActionListener(listener1);			
			option.add(selected);			
			allprograms.add(option);
			
		}
		
		//Scroll bar pane's to contain the Column panels and allow scroll bars for more options
		JScrollPane scroller1 = new JScrollPane(allprograms);
		JScrollPane scroller2 = new JScrollPane(allidentifiers);
		JScrollPane scroller3 = new JScrollPane(alllocations);
		
		//Adding final panes and panels to main row panel
		row9.add(scroller1);
		row9.add(scroller2);
		row9.add(scroller3);
	}

	/**
	 * Row9 part 2
	 * This method continues the creation of panels and components. It creates the options for the second column in row9.
	 * @param selected1 - the selected option from the first column
	 * @param allidentifiers - the column panel that will hold all the identifier option panels
	 * @param alllocations - the column panel that will hold all the location option panels
	 * @param panelidentifiers - the header for the identifiers column
	 * @param panellocations - the header for the locations column
	 */
	private void pilCreatorpart2(String selectedProgram, JPanel allidentifiers, JPanel alllocations, JPanel panelidentifiers, JPanel panellocations, JPanel rowbox) {
		
		allidentifiers.removeAll();
		allidentifiers.add(panelidentifiers);
		
		alllocations.removeAll();
		alllocations.add(panellocations);
		
		
		
		ButtonGroup group2 = new ButtonGroup();
		
		
		ActionListener listener2 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JRadioButton tmpbutton = (JRadioButton) e.getSource();		
				String selectedIdentifier = tmpbutton.getText();
				pilCreatorpart3(selectedProgram, selectedIdentifier, alllocations, panellocations, rowbox);
				
			}
		};
		
		//Sample Identifier Input
		String[] sampleIdentifier = {"Identifier 1", "Identifier 2", "Identifier 3", "Identifier 4", "Identifier 5"};
		

		//Sample options for all identifiers
		int numoptions = sampleIdentifier.length;
		
		for(int i = 0; i < numoptions; i++) {
			JPanel option = new JPanel();			
			JRadioButton selected = new JRadioButton(sampleIdentifier[i]);
			selected.setActionCommand(sampleIdentifier[i]);			
			group2.add(selected);			
			selected.addActionListener(listener2);			
			option.add(selected);			
			allidentifiers.add(option);
		}
		
		allidentifiers.revalidate();
	}
	
	/**
	 * Row9 part 3
	 * This method continues the creation of panels and components. It creates the options for the third column in row9.
	 * @param selected2 - the option selected in the second column
	 * @param alllocations - the column panel that will hold all the location option panels
	 * @param panellocations - the header for the locations column
	 */
	private void pilCreatorpart3(String selectedProgram, String selectedIdentifier, JPanel alllocations, JPanel panellocations, JPanel rowbox) {
				
		alllocations.removeAll();
		alllocations.add(panellocations);
		
		
		ButtonGroup group3 = new ButtonGroup();
		
		ActionListener listener2 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JRadioButton tmpbutton = (JRadioButton) e.getSource();
				String selectedLocation = tmpbutton.getText();
				
				rowbox.setVisible(true);
				
				container(selectedProgram, selectedIdentifier, selectedLocation, rowbox);
				
			}
		};
		
		//Sample Location Input
		String[] sampleLocation = {"Location 1", "Location 2", "Location 3", "Location 4", "Location 5"};
		
		//Options for all locations
		int numoptions = sampleLocation.length;
		
		for(int i = 0; i < numoptions; i++) {
			JPanel option = new JPanel();			
			JRadioButton selected = new JRadioButton(sampleLocation[i]);
			selected.setActionCommand(sampleLocation[i]);			
			group3.add(selected);			
			selected.addActionListener(listener2);			
			option.add(selected);
			alllocations.add(option);
		}
			
		alllocations.revalidate();
	}
	
	//Row 10
	/**
	 * Row10
	 * This method fills rowbox with components that have been selected form the PID creator.
	 *  It allows the user to also remove unwanted components that have been added. 
	 * @param rowbox - JPanel for holding all the components created
	 */
	private void container(String selectedProgram, String selectedIdentifier, String selectedLocation, JPanel rowbox) {
		//Create row to add components to add the info for program identifier and location
		//Add button to remove that selected option
		//Button remove that row in the JPanel
		
		JButton remove = new JButton("Remove");
		JPanel panelremove = new JPanel();
		panelremove.add(remove);
		
		JLabel program = new JLabel(selectedProgram);
		JPanel panelprogram = new JPanel();
		panelprogram.add(program);
		
		JLabel identifier = new JLabel(selectedIdentifier);
		JPanel panelidentifier = new JPanel();
		panelidentifier.add(identifier);
		
		JLabel location = new JLabel(selectedLocation);
		JPanel panellocation = new JPanel();
		panellocation.add(location);
		
		JPanel row = new JPanel();
		row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
		
		row.add(panelremove);
		row.add(panelprogram);
		row.add(panelidentifier);
		row.add(panellocation);
		
		rowbox.add(row);		
		
		//Record which options have been selected
		PIL pil = new PIL(selectedProgram, selectedIdentifier, selectedLocation);
		
		pils.add(pil);
				
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				pils.remove(pil);
				rowbox.remove(row);
				rowbox.revalidate();
			}
		});				

		rowbox.revalidate();
	}
}