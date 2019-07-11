package myTests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import myFunctions.Youtube;

// Sanity tests for YouTube site
public class tests_sanity_exersize1 extends tests{
	

	//#1 Finding and playing a video
	@Test
	public void findAndPlayVideo() throws InterruptedException {
		
		Youtube.openSample(driver);	// Our function that finds and opens the sample video.
		
		new Actions(driver).pause(5000).sendKeys("k").build().perform();	// Waiting for a few second and then pressing "k" (pause).
		
		// Test was successful if:
		// When video is playing, after waiting for a few seconds, the "current time" won't be 0:00.
		Assert.assertFalse(driver.findElement(By.xpath("//span[@class='ytp-time-current']")).getText().equals("0:00"));	// Verifying that the "time=0:00" is "false".

	}	
	
}
