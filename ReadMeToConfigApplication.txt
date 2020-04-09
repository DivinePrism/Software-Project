**To get CSV writing to work, some file paths need to be configed***

To import the file into eclipse first create a new project:
File>>new>>JavaProject

import>>File System>>top Browse -> ECM Build Tracking Tool folder >> select all
>>into folder -> the javaProject folder you just created


BEFORE RUNNING CHANGE PATH IN CSVCreator.java and CSVReader.java to path to save csv.
The line of code to change in both files
CSV Creator
info = new FileWriter("/Users//ENTER PATH//ENTER PATH//" + name + ".csv",false);
CSV Reader
filereader = new FileReader("/Users//ENTER PATH//ENTER PATH//" + filename+ ".csv");  

use // if not working correctly.