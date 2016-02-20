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
		File testFile = new File("tempInit.txt");
		TextBuddy.initializeFile(testFile);
		Assert.assertTrue("File exists", testFile.exists());
	}
	
	@Test
	public void testSplitStringBySpaces()
	{
		String unsplitString = "add little brown fox";
		String[] expectedString = {"add","little","brown","fox"};
		String[] splitString = TextBuddy.splitStringBySpaces(unsplitString);
		Assert.assertArrayEquals("Strings are not the same",expectedString, splitString);
	}
	
	@Test
	public void testGetFileName() {
		String[] args =  {"tempText.txt"};
		Assert.assertEquals(args[0],TextBuddy.getFileName(args));
	}
	
	@Test
	public void testFunctionAdd() throws IOException{
		String fileName = "tempAdd.txt";
		String[] splitString = {"add","little","brown","fox"};
		String expectedOutput = "added to tempAdd.txt: \"little brown fox\"\r\n";
				
		// Clear the file every time this test is run to start with a clean file
		PrintWriter pw = new PrintWriter(fileName);
		pw.close();
		
		// Preparing test file to test function
		File testFile = new File(fileName);
		TextBuddy.functionAdd(testFile, splitString);
		
		// Expected output/file to compare to 
		File expected = new File("testAdd.txt");		
		
		// Check output
		Assert.assertEquals(expectedOutput, outContent.toString());
		// Check file contents 
		Assert.assertEquals(FileUtils.readLines(expected), FileUtils.readLines(testFile));
	}
	
	@Test
	public void testFunctionSort() throws IOException{
		
		// Preparing test file to test function
		String fileName = "tempSort.txt";
		File testFile = initTestFile(fileName);
		TextBuddy.functionSort(testFile);
		
		// Expected output/file to compare to 
		File expected = new File("testSort.txt");

		Assert.assertEquals(FileUtils.readLines(testFile), FileUtils.readLines(expected));
	}
	
	@Test
	public void testFunctionClear() throws IOException
	{
		String fileName = "tempClear.txt";
		File testFile = initTestFile(fileName);		
		File expected = new File("testClear.txt");
		
		TextBuddy.functionClear(testFile);
		Assert.assertEquals(FileUtils.readLines(expected), FileUtils.readLines(testFile));
	}
	
	@Test
	public void testFunctionDelete() throws IOException
	{
		String fileName = "tempDel.txt";
		File testFile = initTestFile(fileName);	
		File expected = new File("testDel.txt");
		String[] splitString = {"delete","4"};
		
		TextBuddy.functionDelete(testFile, splitString);
		Assert.assertEquals(FileUtils.readLines(expected), FileUtils.readLines(testFile));
	}
	
	@Test
	public void testFunctionDisplay() throws IOException
	{
		String fileName = "tempDisp.txt";
		File testFile = initTestFile(fileName);	
		TextBuddy.functionDisplay(testFile);
		String expected = "1: potato\r\n2: coconut\r\n3: ashtray\r\n4: banana\r\n";
		String result =  outContent.toString();
	    Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testFunctionSearch() throws IOException 
	{
		String fileName = "tempSearch.txt";
		File testFile = initTestFile(fileName);	
		
		String[] testLine1 = {"search","banana"};
		String[] testLine2 = {"search","notbanana"};
		String expectedLine1 = "Found 1 instance(s) of the word banana";
		String expectedLine2 = "Unable to find notbanana";
		
		Assert.assertEquals(expectedLine1, TextBuddy.functionSearch(testFile, testLine1));
		Assert.assertEquals(expectedLine2, TextBuddy.functionSearch(testFile, testLine2));
		
	}
	
	
	// ******************** End of Public Method tests ******************************

	
}
