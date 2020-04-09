/**
 * This is the driver class to test the GUI applications of the ECM Build Tracking Tool
 * @author Robert Reinhard
 * @version 1.1
 * @date 10/11/2019
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Driver {
	static BufferedReader stdin = new BufferedReader (new InputStreamReader(System.in));
	public static void main(String[] args) {
		System.out.println("Select from the following menu:");
		System.out.println("0.subBuilds");
		System.out.println("1.New Builds");
		System.out.println("2.Quit");
		boolean quit = false;
		while(!quit){
			int user = -1;
			try{
				String inputString = stdin.readLine().trim();
				user = Integer.parseInt(inputString);
			}
			catch(Exception e){
				System.err.println("Enter a valid number please!");
			}
			switch(user){
			case 0:
				Builds builds = new Builds();
				break;
			case 1:
				New_Build new_build = new New_Build();
				break;
			case 2:
				System.out.println("GoodBye");
				System.exit(0);
				break;
			default:
				System.out.println("Pick again!");
				break;
			}

		}
	}
}
