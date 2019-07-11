package myFunctions;

import static org.junit.Assert.fail;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;


// Functions that work with YouTube
public class Youtube {
	
	
	// A function that enters to YouTube, searches for and opens the sample video: "Ed Sheeran - Perfect".
	public static void openSample(WebDriver driver) throws InterruptedException {		 
			
			driver.get("http://youtube.com");	// Entering YouTube.
			if(false==driver.findElement(By.xpath("//*[@id='logo-icon-container']")).isEnabled()){	// If we managed to enter YouTube, it contains a main-logo button.
				fail ("Failed entering youTube");			
			}	
			
			driver.findElement(By.xpath("//input[@id='search']")).sendKeys("ed sheeran - perfect"+Keys.ENTER);	// Entering video name into the search field.						
			if(false==driver.findElement(By.xpath("//paper-button[@id='button']")).isEnabled()){	// If we managed to perform a search, there is arrange-search-results button on the page.
				fail ("Failed searching for the sample video");		
			}	
			
			driver.findElement(By.linkText("Ed Sheeran - Perfect (Official Music Video)")).click();	// Clicking the found video thumbnail.
			Thread.sleep(4000);					
			if(false==driver.getTitle().contains("Ed Sheeran - Perfect")){	// If we managed to open the video, the page title contains its name.
				fail ("Failed opening the sample video link");
			}
			return;				
	}	
	
	
	
	// A function that logs in as an existing registered YouTube user, created specifically for the tests:
	// username: SVCname12345@gmail.com | password: SVC12345
	public static void enterAndLogIn (WebDriver driver) throws InterruptedException {			 
			
			driver.get("http://youtube.com");	// Entering YouTube.
			
			if(false==driver.findElement(By.xpath("//*[@id='logo-icon-container']")).isEnabled()){	// If we managed to enter YouTube, it contains a main-logo button.
				fail ("Failed entering youtube");
			}	
			driver.findElement(By.xpath("//paper-button[@aria-label='Sign in']")).click();	// Clicking sign-in button
			if(false==driver.findElement(By.xpath("//div[@title='Google']")).isEnabled()){	// If we managed to enter the log-in window, it contains a Google header.
				fail ("Failed entering login window");
			}	
			driver.findElement(By.id("identifierId")).sendKeys("SVCname12345@gmail.com"+Keys.ENTER);	// Typing the username into the required text box.
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@class='whsOnd zHQkBf']")).sendKeys("SVC12345"+Keys.ENTER);	// Typing the password into the required text box.
			Thread.sleep(2000);		// Adding a waiting, to load the signed in user details.
					
			if(false==driver.findElement(By.xpath("//button[@aria-label='Notifications']")).isEnabled()){	// If we managed to log in, the page contains a "user-notifications" button.
				fail ("Failed to log in");
			}
			return;
	
	}
	
}


