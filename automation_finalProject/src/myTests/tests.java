package myTests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import myFunctions.Browser;

// The "father" class of all the tests classes. Contains the "before" and "after" actions for every tests.
public class tests {
	
	
	static WebDriver driver;
	static String browsername;
	boolean myExceptionCaught;	// An variable used in error-test and tells us if an exception is caught.

	
	
	// We'll choose the browser before the first test of the class, and the same browser will open before each new test of the same class.
	@Before 
	public void BrowserChoose() throws Exception {	
		
		if(driver ==null){
			driver=Browser.Choosing();	// Our function that opens a selected browser
			browsername = ((RemoteWebDriver) driver).getCapabilities().getBrowserName(); // Saving the name of the selected browser.
		} else { 
			driver=Browser.OpenAgain(browsername);	// Our function that opens the same browser we've selected from above.
		}
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //Since the elements on the site are loading relatively slowly, we'll add the option to wait until the elements will be enabled and clickable.
		myExceptionCaught = false;	// Reseting the variable before each test.	
	}
		
	
	
	// We'll close the browser at the end of each test.
	@After		
	public void closeBrowser() throws IOException{
		
		driver.quit();	// Closing all windows of the browser, opened by the test.
		Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe /T"); 	// Killing the geckodriver process in the end of the test, to avoid overloading the memory.
        Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");	// Killing the chromedriver process in the end of the test.
	 
	}
	
	
}
