import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;

import org.junit.Assert;
import org.junit.Test;

public class TextBuddyTest {

	@Test
	public void testInitializeFile() throws IOException{
		File file = new File("tempInit.txt");
		TextBuddy.initializeFile(file);
		Assert.assertTrue("File exists", file.exists());
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
	public void testFunctionAdd() throws IOException{
		String fileName = "tempAdd.txt";
		String[] splitString = {"add","little","brown","fox"};
				
		// Clear the file every time this test is run to start with a clean file
		PrintWriter pw = new PrintWriter(fileName);
		pw.close();
		
		File file1 = new File(fileName);
		File file2 = new File("testAdd.txt");
		TextBuddy.functionAdd(fileName, splitString);
		
		Assert.assertEquals(FileUtils.readLines(file2), FileUtils.readLines(file1));
	}
	
	@Test
	public void testGetFileName() {
		String[] args =  {"tempText.txt"};
		Assert.assertEquals(args[0],TextBuddy.getFileName(args));
	}
	
	@Test
	public void testFunctionSort() throws IOException{
		
		String fileName = "tempSort.txt";
		File file1 = new File(fileName);
		
		BufferedWriter fw = new BufferedWriter(new FileWriter(file1));

		fw.write("potato");
		fw.newLine();
		fw.write("coconut");
		fw.newLine();
		fw.write("ashtray");
		fw.newLine();
		fw.write("banana");
		fw.close();
		
		File file2 = new File("testSort.txt");
		TextBuddy.functionSort(fileName);

		Assert.assertEquals(FileUtils.readLines(file1), FileUtils.readLines(file2));
	}
	

}
