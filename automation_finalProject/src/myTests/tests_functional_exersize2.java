package myTests;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import myFunctions.Youtube;

// Functional tests for YouTube site:
//#1  Checks successful change of site language. 
//#2  Checks successful subscription to a channel.
//#3  Checks successful addition of a video to the "watch later" list.
//#4  Checks successful transition to the next video.
public class tests_functional_exersize2 extends tests{
	
	
	
	//#1	Checks successful change of site language 
	//		We'll change the site language from English (default) to Hebrew in the Settings menu.
	@Test		
	public  void changeLanguageCheck() throws InterruptedException {
		
		driver.get("http://youtube.com");	// Opening YouTube.
		if(false==driver.findElement(By.xpath("//*[@id='logo-icon-container']")).isEnabled()) {	// If we managed to access YouTube,  it contains a "main-logo" button.
			fail("Failed entering youTube");			
		}
		driver.findElement(By.xpath("//button[@aria-label='Settings']")).click();	// Clicking settings-button.
		if(false==driver.findElement(By.xpath("//yt-formatted-string[contains (.,'Language')]")).isDisplayed()) {	// If we managed to open the settings-menu, it displays a "Language" title.
			fail("Failed opening settings menu");	
		}
		driver.findElement(By.xpath("//yt-formatted-string[contains (.,'Language')]")).click();	// Clicking the language title.
		if(false==driver.findElement(By.xpath("//p[@class='style-scope ytd-account-settings' and contains(.,'עברית')]")).isDisplayed()) {	// If we managed to open the language menu, it displays a "Hebrew" title.
			fail("Failed opening language menu");	
		}		
		driver.findElement(By.xpath("//p[@class='style-scope ytd-account-settings' and contains(.,'עברית')]")).click();	// Clicking the Hebrew language.
		if(driver.findElements(By.xpath("//p[@class='style-scope ytd-account-settings' and contains(.,'עברית')]")).size() > 0) {	// If we managed to choose the language, the menu closes and the title above can't be found any more.
			fail("Failed choosing language");	
		}
		
		// Test was successful if:
		// the language of the site has been changed to Hebrew, in particular the "log-in" button name has also changed to - "כניסה".   
		Assert.assertTrue(driver.getPageSource().contains("כניסה"));	
	}

		
	
	//#2	Checks successful subscription to a channel
	//		We'll open a video, and use the "subscription" button.
	@Test		
	public  void subscribeCheck() throws InterruptedException {
		
		Youtube.enterAndLogIn(driver);	// Our function, that enters YouTube and logs in 
		Youtube.openSample(driver);	// Our function, that finds and opens the sample video

		String channelName = driver.findElement(By.xpath("//yt-formatted-string[@id='owner-name']")).getText();	// Saving the channel name.
		driver.findElement(By.xpath("//ytd-subscribe-button-renderer[@class='style-scope ytd-video-secondary-info-renderer']")).click();	// Clicking Subscribe-button
		driver.navigate().refresh();	// Reloading the page, to update the Subscribe-button status in source code.				
		if(driver.getPageSource().contains("subscribed\":false")){	// If we managed to click the Subscribe-button, its value (in source code) changes  to "true" instead of the "false"
			fail("Failed clicking the subscription button");
		}
		driver.findElement(By.xpath("//yt-icon-button[@id='guide-button']")).click();	// Clicking user's Guide-menu
		if(false==driver.findElement(By.xpath("//span[contains (.,'Subscriptions')]")).isDisplayed()) {	// If we managed to open the Guide-menu, it contains a "subscriptions" title
			fail("Failed opening the guide menu");
		}
				
		// Test was successful if:
		// the Guide-menu, including the "subscription" section, contains our channel name. 
		Assert.assertTrue(driver.findElement(By.xpath("//ytd-guide-renderer[@id='guide-renderer']")).getText().contains(channelName));
		
		
		//We'll cancel the subscription at the end of the test so that we can run the same test again: 
		driver.navigate().refresh();	// Reloading the page, to close the Guide-menu.
		driver.findElement(By.xpath("//ytd-subscribe-button-renderer[@class='style-scope ytd-video-secondary-info-renderer']")).click();	// Clicking Unsubscribe button.
		driver.findElement(By.xpath("//paper-button[@aria-label='Unsubscribe']")).click();	// Selecting the Unsubscribe option.
		
	}


	
	//#3	Checks successful addition of a video to the "Watch Later" list
	//		We'll open a video, and use the "saving" button.
	@Test		
	public  void watchLaterCheck() throws InterruptedException {
		
		Youtube.enterAndLogIn(driver);
		Youtube.openSample(driver);

		driver.findElement(By.xpath("//button[@aria-label='Save']")).click();	// Clicking Save-button.
		if (false==driver.findElement(By.xpath("//div[@id='playlists']")).getAttribute("class").contains("scrollable")){	// If we managed to open the save-menu, an element named "scrollable" will be added to it's source code. 
			fail("Failed opening the saving menu ");
		}
		driver.findElement(By.xpath("//yt-formatted-string[@title='Watch later']")).click();	// Selecting the Watch-later option.
		if(driver.findElements(By.xpath("//paper-checkbox[@active]")).size() == 0){	// If we managed to select it, an element named "active" will be added to it's source code.
			fail("Failed selecting the Watch-later option ");
		}
		driver.findElement(By.xpath("//button[@aria-label='Cancel']")).click();	// Closing the save-menu.
		if (driver.findElement(By.xpath("//div[@id='playlists']")).getAttribute("class").contains("scrollable")){	// If we managed to close the save-menu, the element above named "scrollable"  will be removed  .
			fail("Failed closing the saving menu ");
		}
		driver.findElement(By.xpath("//yt-icon[@id='guide-icon']")).click();	// Clicking user's Guide-menu.
		if(false==driver.findElement(By.xpath("//span[contains (.,'Watch later')]")).isDisplayed()) {	// If we managed to open the Guide-menu, it contains a "Watch later" title.
			fail("Failed opening the user's Guide menu ");
		}
		driver.findElement(By.xpath("//a[@title='Watch later']")).click();	// Clicking the Watch-later title.
		if(false==driver.findElement(By.xpath("//h1[@id='title']")).getText().contains("Watch later")) {	// If we managed to open the Watch-later page, it contains a "Watch later" subtitle	.			
			fail("Failed opening the Watch-later page");
		}
		
		// Test was successful if:
		// The Watch-later page contains the added video title.
		Assert.assertTrue(driver.getPageSource().contains("Ed Sheeran - Perfect (Official Music Video)"));
		
		
		// We'll remove the video from the list at the end of the test so that we can run the same test again .
		driver.findElement(By.xpath("//paper-button[@aria-label='Edit']")).click();	// Clicking Edit-watch-list link.
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@class='yt-uix-button yt-uix-button-size-default yt-uix-button-default remove-watched-button']")).click();	// Clicking clear-watch-list.
		
	}
	
	
	
	//#4	Checks successful transition to the next video.
		//		We'll open a video, and use the "forward" button.
		@Test		
		public  void nextVideoCheck() throws InterruptedException {
			
			Youtube.openSample(driver);	// Our function, that finds and opens the sample video.

			String firstVideo = driver.getTitle();	// Saving the title of the current page.
			String nextVideo = driver.findElement(By.xpath("//h3[@class='style-scope ytd-compact-video-renderer']")).getText();	// Saving the name of the next-video, that located near the current video.
			driver.findElement(By.xpath("//a[@aria-label='Next (SHIFT+n)']")).click();	// Clicking on a "forward" button.
			Thread.sleep(2000);
			if ((driver.getTitle().equals(firstVideo))) { // If we managed to click the button and to move to other video, then the page title has changed.
				fail ("Failed move to another video!");						
			} 
			
			// Test was successful if:
			// the title of the new page contains the name of the "next video" we've saved before.
			Assert.assertTrue(driver.getTitle().contains(nextVideo) ); // Verifying that the statement above is "true"
		}
	
	
}
