import java.io.File;
import java.io.IOException;
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
	public void testFunctionAdd() throws IOException{
		String fileName = "tempInit.txt";
		String[] splitString = {"1: little brown fox"};
		TextBuddy.functionAdd(fileName, splitString);
		
		File file1 = new File(fileName);
		File file2 = new File("testAdd.txt");
		
		Assert.assertTrue("The files differ!", FileUtils.contentEquals(file1, file2));
		
		
	}
	
	@Test
	public void testGetFileName() {
		String[] args =  {"tempText.txt"};
		Assert.assertEquals(args[0],TextBuddy.getFileName(args));
	}
	

	

}
