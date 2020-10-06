package com.agomezmoron.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.agomezmoron.pageObjects.TestAppPage;

public class TestAppPageTest  extends TestBase{
	
	/** Logger class initialization. */
	private static final Logger LOGGER = LogManager.getLogger(TestAppPageTest.class);

	@Override
	public void setUpPage() {
		driver.launchApp();		
	}

	@Override
	public void checkLayout() {		
	}
	
	/** First iOS Test*/
	@Test(description = "firstiOSTest")
	public void firstiOSTest() {

		// Variable declaration and definition
		TestAppPage testAppPage = new TestAppPage(driver);

		try {
			
			//check if the page is ready
			testAppPage.isReady();
			
			//Action: click on the switch button
			testAppPage.clickOnSwitchButton();

		} catch (AssertionError e) {
			LOGGER.error("[Assert ERROR] - ", e);
			org.testng.Assert.fail();
		}
	}

}
