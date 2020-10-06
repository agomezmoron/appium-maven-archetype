/**
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Alejandro Gómez Morón
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.agomezmoron.tests;

import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.agomezmoron.appiumhandler.AppiumHandledDriver;
import com.agomezmoron.utils.PropertiesHandler;

/**
 * Main class for all the defined Tests. Responsible for setting up the Appium test Driver.
 * 
 * @author Alejandro Gomez <agommor@gmail.com>
 * @author Ivan Gomez <igomez@emergya.com>
 *
 */
public abstract class TestBase {

    /**
     * Make the driver static. This allows it to be created only once and used across all of the test classes.
     */
    public static AppiumHandledDriver driver;

    /**
     * This method runs before any other method.
     *
     * Appium follows a client - server model: We are setting up our appium client in order to connect to Device Farm's
     * appium server.
     *
     * We do not need to and SHOULD NOT set our own DesiredCapabilities Device Farm creates custom settings at the
     * server level. Setting your own DesiredCapabilities will result in unexpected results and failures.
     *
     * @throws MalformedURLException An exception that occurs when the URL is wrong
     */
    @BeforeSuite
    public void setUpAppium() throws MalformedURLException {
        // Load the file we'll use
        PropertiesHandler.getInstance().load("test.properties");

        final String URL_STRING = PropertiesHandler.getInstance().get("test.appiumLocation");

        URL url = new URL(URL_STRING);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", PropertiesHandler.getInstance().get("test.platform"));
        // Device name is not mandatory for cloud executions.
        if (StringUtils.isNotBlank(PropertiesHandler.getInstance().get("test.deviceName"))) {
            capabilities.setCapability("deviceName", PropertiesHandler.getInstance().get("test.deviceName"));
        }
        // By default the app should be located in the same root folder. If it isn't, this capability should be changed.
        capabilities.setCapability("app", PropertiesHandler.getInstance().get("test.app.name"));
        capabilities.setCapability("appHybrid", Boolean
                .parseBoolean(PropertiesHandler.getInstance().get("test.app.hybrid")));
        
		//iOS Only
		capabilities.setCapability( "automationName", PropertiesHandler.getInstance().get("test.automationName"));

        // Checking if chromedriver is installed correctly.
        File file = new File("/usr/local/bin/chromedriver");
        if (file.exists()) {
            capabilities.setCapability("chromedriverExecutable", "/usr/local/bin/chromedriver");
        }
        // Use a empty DesiredCapabilities object.
        driver = AppiumHandledDriver.buildInstance(url, capabilities);

        // If the driver is not ready.
        assertTrue("The driver is nos ready to test!", driver.isDriverReadyToTest());
    }

    /**
     * Method to initialize the test's page.
     */
    @BeforeTest
    public abstract void setUpPage();

    /**
     * Restart the app after every test class to go back to the main screen and to reset the behavior.
     */
    @AfterTest
    public void restartApp() {
        driver.resetApp();
    }

    /**
     * Always remember to quit.
     */
    @AfterSuite
    public void tearDownAppium() {
        driver.quit();
    }

    /**
     * Sleep the driver to leave time to charge the app.
     */
    public void waitForApp() {
        // This is needed because the app is not fully loaded after a restart.
        driver.sleep(5);
    }

    /**
     * It's mandatory for each test to check the layout of the page it's checking.
     */
    public abstract void checkLayout();
}
