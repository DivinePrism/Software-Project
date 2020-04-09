/**\
 * This class is a holder for information on the programs, identifiers, and locations. This class allows the selected 
 * options to be recorded without the possibility of them being corrupted.
 * @author Robert Reinhard
 * @version 1.0
 * @date 11/22/2019
 *
 */
public class PIL {
	
	public String program;
	public String identifier;
	public String location;
	
	/**
	 * This method creates the PIL object
	 * @param program - the name of the program selected
	 * @param identifier - the name of the identifier selected
	 * @param location - the name of the location selected
	 */
	public PIL(String program, String identifier, String location) {
		this.program = program;
		this.identifier = identifier;
		this.location = location;
	}
	
	/**
	 * This method gets the program
	 * @return - the string name for the program
	 */
	public String getProgram() {
		return program;
	}

	/**
	 * This method gets the identifier
	 * @return - the string name for the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * This method gets the location
	 * @return - the string name for the location
	 */
	public String getLocation() {
		return location;
	}
	
	

}
