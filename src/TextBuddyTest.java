import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TextBuddyTest {

	
	// ***************** Helper Methods ************************************
	
	//Following few methods are used to test output stream (console output)
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	}
	
	//To create a dummy test file for some of the tests
	private File initTestFile(String fileName) throws IOException {
		File testFile = new File(fileName);		
		BufferedWriter fw = new BufferedWriter(new FileWriter(testFile));

		fw.write("potato");
		fw.newLine();
		fw.write("coconut");
		fw.newLine();
		fw.write("ashtray");
		fw.newLine();
		fw.write("banana");
		fw.close();
		return testFile;
	}
	
	// ******************** End of Helper Methods ***********************************
	
	
	// ******************** Public Method tests *************************************
	@Test
	public void testInitializeFile() throws IOException{
		
		// Preparing test case to test function
		String[] args =  {"tempText.txt"};
		TextBuddy.initializeFile(args);
		String result =  outContent.toString();
				
		// Expected output/file to compare to 
		String expectedOutput = "Welcome to TextBuddy. tempText.txt is ready for use\r\n";
		
		// Check output
		Assert.assertEquals("Output differs", expectedOutput, result);		
		// Check if file exists
		Assert.assertTrue(new File("tempText.txt").exists());
	}
	
	@Test
	public void testSplitStringBySpaces()
	{
		// Preparing test case to test function
		String unsplitString = "add little brown fox";		
		String[] splitString = TextBuddy.splitStringBySpaces(unsplitString);
		
		// Expected output/file to compare to 
		String[] expectedString = {"add","little","brown","fox"};
		
		// Check output
		Assert.assertArrayEquals("Strings are not the same",expectedString, splitString);
	}
	
	@Test
	public void testGetFileName() {
		
		// Expected output/file to compare to 
		String[] args =  {"tempText.txt"};
		
		// Check output
		Assert.assertEquals(args[0],TextBuddy.getFileName(args));
	}
	
	@Test
	public void testFunctionAdd() throws IOException{
		String fileName = "tempAdd.txt";
		String[] splitString = {"add","little","brown","fox"};
				
		// Clear the file every time this test is run to start with a clean file
		PrintWriter pw = new PrintWriter(fileName);
		pw.close();
		
		// Preparing test file to test function
		File testFile = new File(fileName);
		
		// Expected output/file to compare to 
		File expectedFile = new File("testAdd.txt");	
		String expectedOutput = "added to tempAdd.txt: \"little brown fox\"";
		
		// Check output
		Assert.assertEquals("Output differs", expectedOutput, TextBuddy.functionAdd(testFile, splitString));
		// Check file contents 
		Assert.assertEquals("File differs", FileUtils.readLines(expectedFile), FileUtils.readLines(testFile));
	}
	
	@Test
	public void testFunctionSort() throws IOException{
		
		// Preparing test file to test function
		String fileName = "tempSort.txt";
		File testFile = initTestFile(fileName);
	
		// Expected output/file to compare to 
		File expectedFile = new File("testSort.txt");
		String expectedOutput = "Successfully sorted 4 lines";

		// Check output
		Assert.assertEquals("Output differs", expectedOutput, TextBuddy.functionSort(testFile));		
		// Check file contents 
		Assert.assertEquals("File differs", FileUtils.readLines(testFile), FileUtils.readLines(expectedFile));
	}
	
	@Test
	public void testFunctionClear() throws IOException
	{
		// Preparing test file to test function
		String fileName = "tempClear.txt";
		File testFile = initTestFile(fileName);		
		
		// Expected output/file to compare to
		File expectedFile = new File("testClear.txt");
		String expectedOutput = "all content deleted from tempClear.txt";
		
		// Check output
		Assert.assertEquals("Output differs", expectedOutput, TextBuddy.functionClear(testFile));		
		
		// Check file contents 
		Assert.assertEquals("File differs", FileUtils.readLines(expectedFile), FileUtils.readLines(testFile));
	}
	
	@Test
	public void testFunctionDelete() throws IOException
	{
		// Preparing test file to test function
		String fileName = "tempDel.txt";
		File testFile = initTestFile(fileName);			
		String[] splitString = {"delete","4"};
		
		// Expected output/file to compare to
		File expectedFile = new File("testDel.txt");		
		String expectedOutput = "deleted from tempDel.txt: \"banana\"";
		
		// Check output
		Assert.assertEquals("Output differs", expectedOutput, TextBuddy.functionDelete(testFile, splitString));	
		
		// Check file contents 
		Assert.assertEquals("File differs", FileUtils.readLines(expectedFile), FileUtils.readLines(testFile));
	}
	
	@Test
	public void testFunctionDisplay() throws IOException
	{
		// Preparing test file to test function
		String fileName = "tempDisp.txt";
		File testFile = initTestFile(fileName);	
		TextBuddy.functionDisplay(testFile);
		String result =  outContent.toString();
		
		// Expected output/file to compare to
		String expected = "1: potato\r\n2: coconut\r\n3: ashtray\r\n4: banana\r\n";
		
		// Check output
	    Assert.assertEquals("Output differs", expected, result);
	}
	
	@Test
	public void testFunctionSearch() throws IOException 
	{
		// Preparing test file to test function
		String fileName = "tempSearch.txt";
		File testFile = initTestFile(fileName);	
		String[] testLine1 = {"search","banana"};
		String[] testLine2 = {"search","notbanana"};
		
		// Expected output/file to compare to
		String expectedLine1 = "Found 1 instance(s) of the word banana";
		String expectedLine2 = "Unable to find notbanana";
		
		// Check output
		Assert.assertEquals("Output differs ( found case )", expectedLine1, TextBuddy.functionSearch(testFile, testLine1));
		Assert.assertEquals("Output differs ( not found case )", expectedLine2, TextBuddy.functionSearch(testFile, testLine2));
		
	}
	
	
	// ******************** End of Public Method tests ******************************

	
}
