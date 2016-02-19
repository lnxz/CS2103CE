
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

		// Getting the file name from argument
		String fileName = getFileName(args);
		File file = new File(fileName);

		// Creates file if file does not exist
		initializeFile(file);

		// Prints welcome message
		printWelcomeMsg(fileName);

		// Executes the menu & functions in a loop
		executeMenuLoop(fileName, scanner);

	}

	// Function to get file name from program arguments
	public static String getFileName(String[] args) {
		String fileName;
		fileName = args[0];
		return fileName;
	}

	// Function to execute the main menu within a while loop
	private static void executeMenuLoop(String fileName, Scanner scanner) throws IOException, FileNotFoundException {
		
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
				functionAdd(fileName, splitString);
				break;

			case "display":
				functionSort(fileName);
				functionDisplay(fileName);
				break;

			case "delete":
				functionDelete(fileName, splitString);
				break;

			case "clear":
				functionClear(fileName);
				break;

			default:
				printMsg("Invalid Command");
				break;
			}
			
			// Take in next command
			userCommand = scanLine(scanner);
		}
	}

	// Function to sort the lines of the file
	public static void functionSort(String fileName) throws IOException
	{
		String stringLine;
		ArrayList<String> stringArr = new ArrayList<String>();
		int lineCount = 0;

		FileInputStream in = new FileInputStream(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		// Loop to print out the lines as well as the associated line number
		while ((stringLine = reader.readLine()) != null) {
			stringArr.add(stringLine);
			lineCount++;
		}
		Collections.sort(stringArr);

		BufferedWriter fw = new BufferedWriter(new FileWriter(fileName));
		
		// If the line number matches, ignore the line and save it into stringDeleted, else write it to the buffer
		for (int x = 0; x < lineCount; x++)
		{
			fw.write(stringArr.get(x));
			if(x != (lineCount -1))
			{
				fw.newLine();
			}
		}
		
		fw.close();

		reader.close();
	}
	
	// Printing of welcome message
	public static void printWelcomeMsg(String fileName) {
		printMsg("Welcome to TextBuddy. " + fileName + " is ready for use");
	}

	// Function to clear the text file 
	private static void functionClear(String fileName) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(fileName);
		pw.close();

		printMsg("all content deleted from " + fileName);
	}

	// Function to delete a specific line from the text file
	private static void functionDelete(String fileName, String[] splitString)
			throws FileNotFoundException, IOException {
		
		String stringLine;
		String stringDeleted = "";
		int currLine = 1;
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		StringBuffer sb = new StringBuffer("");
		
		// Extract out the index of the line to be deleted
		int deletionIndex = Integer.parseInt(splitString[1]);	

		// If the line number matches, ignore the line and save it into stringDeleted, else write it to the buffer
		while ((stringLine = br.readLine()) != null) {
			if (deletionIndex != currLine) {
				sb.append(stringLine + "\n");
			} else {
				stringDeleted = stringLine;
			}
			currLine++;
		}
		br.close();

		writeFileFromBuffer(fileName, sb);
		printMsg("deleted from " + fileName + ": \"" + stringDeleted + "\"");
	}

	private static void writeFileFromBuffer(String fileName, StringBuffer sb) throws IOException {
		FileWriter fw = new FileWriter(new File(fileName));
		fw.write(sb.toString());
		fw.close();
	}

	// Function to display text from file, with line number appended to the front of each line
	private static void functionDisplay(String fileName) throws FileNotFoundException, IOException {
		
		String lineOfText;
		int lineCounter = 0;
		
		FileInputStream in = new FileInputStream(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		// Loop to print out the lines as well as the associated line number
		while ((lineOfText = reader.readLine()) != null) {
			lineCounter++;
			printMsg(lineCounter + ": " + lineOfText);
		}
		
		// If file is empty, show corresponding message
		if (lineCounter == 0) {
			printMsg(fileName + " is empty");
		}
		reader.close();
	}

	// Function to append a line to the end of the file
	public static void functionAdd(String fileName, String[] splitString) throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		StringBuilder builder = new StringBuilder();

		// Using string builder to append line to end of file, excluding the command given
		for (int i = 1; i < splitString.length; i++) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(splitString[i]);
		}
		
		printMsg("added to " + fileName + ": \"" + builder.toString() + "\"");
		writer.write(builder.toString());
		writer.write("\n");
		writer.close();
	}

	// Function to split string by spaces and save into a string array
	public static String[] splitStringBySpaces(String userCommand) {
		
		String[] splitString;
		
		// Split by white spaces
		splitString = userCommand.split("\\s+");
		return splitString;
	}


	private static String scanLine(Scanner scanner) {
		return scanner.nextLine();
	}

	// Function to create the file if specified does not exist
	public static void initializeFile(File file) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
	}

	private static void printMsg(String msg) {
		System.out.println(msg);
	}
}
