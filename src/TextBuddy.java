
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TextBuddy {

	public static void main(String[] args) throws IOException {

		// Initialize scanner
		Scanner scanner = new Scanner(System.in);

		// Initialize file & print welcome message
		File file = initializeFile(args);

		// Executes the menu & functions in a loop
		executeMenuLoop(file, scanner);

	}

	// **************** Main functionality functions **************************

	// Function to execute the main menu within a while loop
	private static void executeMenuLoop(File file, Scanner scanner) throws IOException, FileNotFoundException {

		// Declaring some local variables
		String userCommand;
		String[] splitString;

		// Scan for first command
		userCommand = scanLine(scanner);

		// While loop runs until "exit"
		while (!userCommand.equals("exit")) {

			// Extracts command from a line (first word)
			splitString = splitStringBySpaces(userCommand);

			switch (splitString[0]) {
			
				case "add":
					printMsg(functionAdd(file, splitString));
					break;
	
				case "display":
					functionDisplay(file);
					break;
	
				case "delete":
					printMsg(functionDelete(file, splitString));
					break;
	
				case "clear":
					printMsg(functionClear(file));
					break;
	
				case "sort":
					printMsg(functionSort(file));
					break;
	
				case "search":
					printMsg(functionSearch(file, splitString));
					break;
	
				default:
					printMsg("Invalid Command");
					break;
			}
			userCommand = scanLine(scanner);
		}
	}

	// Function to sort the lines of the file
	public static String functionSort(File file) throws IOException {
		String stringLine;
		ArrayList<String> stringArr = new ArrayList<String>();

		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

		// Add the lines into an arraylist
		while ((stringLine = reader.readLine()) != null) {
			stringArr.add(stringLine);
		}
		reader.close();
		
		// Sort the arraylist of lines
		Collections.sort(stringArr);
		

		int lineCount = getNumLines(file);
		
		// Rewrite the newly sorted arrayList into the file
		BufferedWriter fw = new BufferedWriter(new FileWriter(file));
		for (int x = 0; x < lineCount; x++) {
			fw.write(stringArr.get(x));
			if (x != (lineCount - 1)) {
				fw.newLine();
			}
		}
		fw.close();
		

		return "Successfully sorted " + lineCount + " lines";
	}

	// Function to clear the text file
	public static String functionClear(File file) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(file);
		pw.close();

		return "all content deleted from " + file.getName();
	}

	// Function to delete a specific line from the text file
	public static String functionDelete(File file, String[] splitString) throws FileNotFoundException, IOException {

		String stringLine;
		String stringDeleted = "";
		int currLine = 1;
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuffer sb = new StringBuffer("");

		// Extract out the index of the line to be deleted
		int deletionIndex = Integer.parseInt(splitString[1]);

		// If the line number matches, ignore the line and save it into
		// stringDeleted, else write it to the buffer
		while ((stringLine = br.readLine()) != null) {
			if (deletionIndex != currLine) {
				sb.append(stringLine + "\n");
			} else {
				stringDeleted = stringLine;
			}
			currLine++;
		}
		br.close();

		writeFileFromBuffer(file, sb);
		return "deleted from " + file.getName() + ": \"" + stringDeleted + "\"";
	}

	// Function to display text from file, with line number appended to the
	// front of each line
	public static void functionDisplay(File file) throws FileNotFoundException, IOException {

		String lineOfText;
		int lineCounter = 0;

		FileInputStream in = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		// Loop to print out the lines as well as the associated line number
		while ((lineOfText = reader.readLine()) != null) {
			lineCounter++;
			printMsg(lineCounter + ": " + lineOfText);
		}

		// If file is empty, show corresponding message
		if (lineCounter == 0) {
			printMsg(file.getName() + " is empty");
		}
		reader.close();
	}

	// Function to append a line to the end of the file
	public static String functionAdd(File file, String[] splitString) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		StringBuilder builder = new StringBuilder();

		// Using string builder to append line to end of file, excluding the
		// command given
		for (int i = 1; i < splitString.length; i++) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(splitString[i]);
		}

		writer.write(builder.toString() + "\n");
		writer.close();

		return "added to " + file.getName() + ": \"" + builder.toString() + "\"";
	}

	// Function to find a specific word from the text file & print out which lines it is found in
	public static String functionSearch(File file, String[] splitString) throws FileNotFoundException, IOException {

		String stringLine;
		int lineCounter = 1;
		int linesFound = 0;
		BufferedReader br = new BufferedReader(new FileReader(file));
		String searchWord = splitString[1];

		// If word is found, print out the line and its corresponding line number 
		// Also increment linesFound to tabulate instances of the word found
		while ((stringLine = br.readLine()) != null) {
			if (stringLine.contains(searchWord)) {
				printMsg(lineCounter + ": " + stringLine);
				linesFound++;
			}
			lineCounter++;
		}

		br.close();
		
		if (linesFound == 0) {
			return "Unable to find " + searchWord;
		}

		return "Found " + linesFound + " line(s) containing the word " + searchWord;

	}
	// **************** End of Main functionality functions ****************

	// ***************** Helper Methods ************************************

	// Function to split string by spaces and save into a string array
	public static String[] splitStringBySpaces(String userCommand) {

		String[] splitString;
		splitString = userCommand.split("\\s+");
		return splitString;
	}

	// Function to get file name from program arguments
	public static String getFileName(String[] args) {
		String fileName;
		fileName = args[0];
		return fileName;
	}

	// Function to create the file if specified does not exist & print welcome
	// message
	public static File initializeFile(String[] args) throws IOException {
		String fileName = getFileName(args);
		File file = new File(fileName);

		if (!file.exists()) {
			file.createNewFile();
		}

		// welcome Message
		printMsg("Welcome to TextBuddy. " + fileName + " is ready for use");
		return file;
	}
	
	// Function to get number of lines of the file
	public static int getNumLines(File file) throws IOException {
	
		int lineCount = 0;
		FileInputStream in = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		// Loop to count number of lines
		while ((reader.readLine()) != null) {
			lineCount++;
		}
		reader.close();		
		return lineCount;
	}
	
	private static void printMsg(String msg) {
		System.out.println(msg);
	}

	private static String scanLine(Scanner scanner) {
		return scanner.nextLine();
	}

	private static void writeFileFromBuffer(File file, StringBuffer sb) throws IOException {
		FileWriter fw = new FileWriter(file);
		fw.write(sb.toString());
		fw.close();
	}

	// ************* End of Helper Methods ********************************
}
