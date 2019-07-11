package myTests;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import myFunctions.myException;
import myFunctions.Youtube;

//Error handling tests for YouTube site:
//#1  Verifying error due to invalid video uploading.
//#2  Verifying error due to creating a new brand account with an invalid name.
//#3  Verifying error due to adding an invalid Google-Analytics-ID. 
//#4  Verifying error due to saving a watermark without a file.
public class tests_error_exersize3 extends tests {	
	

	//#1	Verifying error due to invalid video uploading	
	//		We will upload a video from from Google-photos.
	//		Since we would like to perform an error check, our sample video will be over 15 minutes, which exceeds the allowable time for a simple user like us.
	@Test	
	public  void N_uploadInvalidVideoCheck() throws Exception {
		
		Youtube.enterAndLogIn(driver);
		
		driver.findElement(By.xpath("//button[@aria-label='Create a video or post']")).click();	// Clicking upload button.
		if (false==driver.findElement(By.xpath("//*[@id='items']/ytd-compact-link-renderer[1]")).isDisplayed()) {	// If we managed to open the upload-menu, it contains the "upload video" title.
			fail("Failed openig the upload-menu");
		}	
		driver.findElement(By.xpath("//*[@id='items']/ytd-compact-link-renderer[1]")).click();	// Selecting upload-video option. 
		if (false==driver.getPageSource().contains("Select files to upload")){	// // If we managed to open the uploading-page, it contains the line: "Select files to upload".
			fail("Failed opening the upload-page");
		}
		driver.findElement(By.xpath("//button[@aria-label='Import video from Google Photos']")).click();	// Clicking upload from Google-Photos.	
		if(false==driver.findElement(By.xpath("//iframe[@class='picker modal-dialog-bg']")).getAttribute("aria-hidden").equals("true")){	// If we managed to open the Google-Photo frame, it's source code will change.
			fail("Failed opening Google-Photo frame");
		}	
		//**********************
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='picker-frame']"))); // Switching to Google-Photos-frame, to work with it's elements.
	
		try{	
			// When a video uploaded, there is a button called "YouTube Studio" that appears even after the upload fails.
			// However if the video managed to upload, this button is replaced by another button called "Return to YouTube Studio (beta)". 

			WebElement sampleVideo = driver.findElement(By.xpath("//div[@role='option']"));	// Defining the thumbnail of our sample-video .
			new Actions(driver).doubleClick(sampleVideo).perform(); // Doubleclicking on the sample-video to upload.
			driver.switchTo().defaultContent();	// Switching back to the main-frame.
			if(false==driver.findElement(By.xpath("//img[@class='upload-thumb-img']")).isDisplayed()) {	// If we managed to start the uploading, a new title appears: "Upload status"
				fail("Failed starting the upload");
			}									
			Thread.sleep(200000); // We'll wait a little over 3 minutes to let the video upload even on a slow computer.														 
			if(driver.getPageSource().contains("Return to YouTube Studio (beta)")) {	// The page is expected to not contain the new button.
				fail("The test went wrong - invalid video uploaded");
			}
			throw new myException("expected error");				
		}
		
		catch(myException ex) {		// We'll catch the above error so that the test won't stop.	
				System.out.println("***Exception catched: The video doesn't match the upload restrictions. Uploading failed, as expected. ***");
				myExceptionCaught = true;	// Our variable, tells us that an exception is caught.
		}					
		finally {	
			Assert.assertTrue(myExceptionCaught);	// Test was successful if we caught an exception as expected.		
		}
				
	}

	
	//#2	Verifying error due to creating a new brand account with an invalid name
	//		We'll enter into user settings to create a new brand account.
	//		Since we would like to perform an error check, we will insert an invalid name - "1234" (while a valid name should contain at least one letter).					
	@Test			
	public  void N_createInvalidNamedBrandCheck() throws Exception {
		
		Youtube.enterAndLogIn(driver);
		
		driver.findElement(By.xpath("//button[@aria-label='Account profile photo that opens list of alternate accounts']")).click();	// Clicking user logo.
		if(false==driver.findElement(By.xpath("//a[@href='/account']")).isDisplayed()){	// If we managed to open the user-menu, it contains a title named "settings".
			fail("Failed opening the user menu");
		}
		driver.findElement(By.xpath("//a[@href='/account']")).click();	// Clicking settings-title.
		if(false==driver.getPageSource().contains("Choose how you appear")){	// If we managed to open the account-settings page, it contains a title "Choose how you appear".
			fail("Failed opening the account-settings page");
		}
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[@href='/create_channel?action_create_new_channel_redirect=true']")).click();	// Clicking  "create-new-channel" link.
		if(false==driver.getPageSource().contains("To create a new channel")){	// If we managed to enter the channel-settings page, it contains a title "To create a new channel".
			fail("Failed entering the channel-settings page");
		}
		
		try{
			// If incorrect brand name is entered - the page will display an error message stating that the name does not include letters.
			
			driver.findElement(By.xpath("//input[@type='text']")).sendKeys("1234", Keys.ENTER);	// Entering invalid brand name into the text box.	
			Thread.sleep(2000);
			if(false==driver.getPageSource().contains("A page name must include letters")){	// The page is expected to contain the error message.
				fail("The test went wrong - invalid brand account name accepted");
			} 
			throw new myException("expected error");				
		}
		
		catch(myException ex) {	
			System.out.println("***Exception catched: Invalid brand account name entered. Creation failed, as expected. ***");
			myExceptionCaught = true;
		}
		finally{
			Assert.assertTrue(myExceptionCaught);
		}	
	}

	
	
	//#3	Verifying error due to adding an invalid Google-Analytics-ID
	//		We'll enter the advanced settings in user account and add a Google-Analytics-ID 
	//		Since we'd like to perform an error check, we'll insert an invalid id - "AB12345" (A valid id must begin with letters "UA")				
	@Test		 
	public  void N_addInvalidAnaliticsIdCheck() throws Exception {
		
		Youtube.enterAndLogIn(driver);
		
		driver.get("https://www.youtube.com/advanced_settings");	// Opening the advanced-settings page. 
		if(false==driver.getPageSource().contains("Account Information")){	// If we managed to open the advanced-settings page, it contains a title "Account Information".
			 fail("Failed openning the advanced settings page");
		}
			
		try {
			// When incorrect number entered, it won't be saved and an error message will appear stating that a correct number must be entered.
			
			driver.findElement(By.xpath("//input[@id='analytics_id']")).clear();	// Clearing the text box, in case it contains previous id.
			driver.findElement(By.xpath("//input[@id='analytics_id']")).sendKeys("AB12345", Keys.ENTER);	// Entering invalid id into the text box.
			if (false==driver.findElement(By.xpath("//button[@aria-label='Close']")).isDisplayed()) {	// If we managed to process the id, a relevant message appears, and it contains a closing button.
				fail("Failed processing the id number");
			}						
			if(false==driver.getPageSource().contains("Please enter a valid Google Analytics id")) {	// The page is expected to contain the error message.
				fail("The test went wrong - invalid Analytics id accepted");
			}
			throw new myException("expected error");				
		}
		
		catch(myException ex) {		
			System.out.println("***Exception catched: Invalid Google Analytics id entered. The id was not saved, as expected. ***");
			myExceptionCaught = true;
		}
		finally {	
			Assert.assertTrue(myExceptionCaught);	
		}	
	}
	
	
	//#4	Verifying error due to saving a watermark without a file
	//		We'll enter the advanced settings in user account and add a watermark.
	//		Since we'd like to perform an error check, we'll save the watermark without any loaded file.	
	@Test			
	public  void N_uploadNoFileWatermarkCheck() throws Exception {
		
		Youtube.enterAndLogIn(driver);
		
		driver.get("https://www.youtube.com/branding");	// Opening the branding-settings page 
		if(false==driver.getPageSource().contains("Branding watermark")) {	// If we managed to open the branding-settings page, it contains a title "Branding watermark".
			fail("Failed openning the branding-settings page");
		}	
		driver.findElement(By.xpath("//span[@class='yt-uix-button-content'and contains(.,'Add a watermark')]")).click();	// Clicking the add-watermark button.
		if(false==driver.findElement(By.xpath("//div[@aria-labelledby='yt-dialog-title-1']")).getAttribute("tabindex").equals("0")) {	// If we managed to open the watermark-uploading frame, it's index will change to "0" instead of "-1" in source code.			
			fail("Failed openning the frame of file-loading");
		}
		WebElement saveButton = driver.findElement(By.xpath("//button[@class='yt-uix-button yt-uix-button-size-default yt-uix-button-primary channel-watermark-uploader-next']"));	// Define the saving-button.
		
		try {				
			// When uploading an invalid file, a watermark is not saved and a message appears stating that an image file must be provided.
			saveButton.click();	// Clicking the saving-button when no file uploaded. 
			Thread.sleep(500);
			saveButton.click();	// (Because of some bug in firefox browser, nor single-click nor double-click won't work for this button. Only clicking twice.)
			Thread.sleep(3000);
			driver.switchTo().frame("channel-watermark-uploader");	// Moving to the watermark-uploading frame, to check it.
			if(false==driver.getPageSource().contains("Please provide an image file")){	// The frame is expected to contain the error message.
				fail("Failed- The test went wrong - empty file accepted");
			}	
			throw new myException("expected error");				
		}
		
		catch(myException ex) {
			System.out.println("***Exception catched: No file uploaded. The watermark was not saved, as expected. ***");
			myExceptionCaught = true;
		}
		finally {
			Assert.assertTrue(myExceptionCaught);	
		}		
	}
	
	
	

}
