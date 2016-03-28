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
package com.agomezmoron.pageObjects;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.agomezmoron.appiumhandler.AppiumHandledDriver;
import com.agomezmoron.utils.PropertiesHandler;

import io.appium.java_client.MobileElement;

/**
 * Base class for all the page objects.
 * 
 * @author Alejandro Gomez <agommor@gmail.com>
 * @author Ivan Gomez <igomez@emergya.com>
 *
 */
public abstract class BasePage {

    /**
     * Logger class initialization.
     */
    private static final Logger LOGGER = Logger.getLogger(BasePage.class);

    /**
     * The driver instance.
     */
    protected final AppiumHandledDriver driver;

    /**
     * TIMEOUT for elements.
     */
    protected static final long TIMEOUT = 20; // Seconds

    /**
     * Common prefix for selectors.
     */
    private static final String SELECTOR_PREFIX = "sam"; // Prefix used to concatenate the ID of the elements.

    /**
     * A base constructor that sets the page's driver. The page structure is being used within this test in order to
     * separate the page actions from the tests.
     *
     * @param driver the appium driver created.
     */
    protected BasePage(AppiumHandledDriver driver) {
        this.driver = driver;
    }

    /**
     * This method will check if the page showed represents the Page Object that is implemented for.
     * 
     * @return boolean is the showed screen is ready to be used by this Page Object.
     */
    public abstract boolean isReady();

    /**
     * This method builds the file selector path for each Page Object.
     * 
     * @return the file selectors path.
     */
    protected String getSelectorsFilePath() {
        String filePath = "selectors" + File.separatorChar;
        String baseName = this.getClass().getSimpleName().replace("Page", "").toLowerCase();
        filePath += baseName + ".properties";
        return filePath;
    }

    /**
     * This method interacts with Appium to retrieve the needed element. By ID
     * 
     * @param key of the item to be selected. In the related selector file should exists an entry with: key.type and key.name
     * @return the selected {@link MobileElement} object.
     */
    public MobileElement getElementById(String key) {
        MobileElement element = null;
        PropertiesHandler handler = PropertiesHandler.getInstance();
        handler.load(this.getSelectorsFilePath());
        String type = handler.get(key + ".type"); // Could be null if the ID is from external pages.
        String name = handler.get(key + ".name");

        if (type == null && StringUtils.isNotBlank(name)) {
            element = this.getElementByIdJustId(name); // For external ID's.
        } else {
            if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(name)) {
                element = this.getElementById(type, name);
            } else {
                LOGGER.error("Trying to retrieve from " + this.getSelectorsFilePath() + " file the item with the key "
                        + key + " but " + key + ".type and/or " + key + ".name are missing!");
            }
        }
        return element;
    }

    /**
     * This method interacts with appium to retrieve the needed element. By Name
     * 
     * @param key of the item to be selected. In the related selector file should exists an entry with: key.type and key.name
     * @return the selected {@link MobileElement} object.
     */
    public MobileElement getElementByName(String key) {
        MobileElement element = null;
        PropertiesHandler handler = PropertiesHandler.getInstance();
        handler.load(this.getSelectorsFilePath());
        String name = handler.get(key + ".name");

        if (StringUtils.isNotBlank(name)) {
            element = this.getElementByName(null, name);
        } else {
            LOGGER.error("Trying to retrieve from " + this.getSelectorsFilePath() + " file the item with the key " + key
                    + " but " + key + ".name is missing!");
        }
        return element;
    }

    /**
     * This method interacts with appium to retrieve the needed element. By xpath
     * 
     * @param key of the item to be selected. In the related selector file should exists an entry with: key.xpath
     * @return the selected {@link MobileElement} object.
     */
    public MobileElement getElementByXPath(String key) {
        MobileElement element = null;
        PropertiesHandler handler = PropertiesHandler.getInstance();
        handler.load(this.getSelectorsFilePath());
        String xpath = handler.get(key + ".xpath");

        if (StringUtils.isNotBlank(xpath)) {
            element = this.driver.findElementsByXPath(xpath).get(0); // Take the first or unique one.
        } else {
            LOGGER.error("Trying to retrieve from " + this.getSelectorsFilePath() + " file the item with the key " + key
                    + " but " + key + ".xpath is missing!");
        }
        return element;
    }

    /**
     * This method interacts with appium to retrieve the list of needed elements. By xpath
     * 
     * @param key of the items to be selected. In the related selector file should exists an entry with: key.xpath
     * @return a List of the selected {@link MobileElement} object.
     */
    public List<MobileElement> getElementsByXPath(String key) {
        List<MobileElement> element = null;
        PropertiesHandler handler = PropertiesHandler.getInstance();
        handler.load(this.getSelectorsFilePath());
        String xpath = handler.get(key + ".xpath");

        if (StringUtils.isNotBlank(xpath)) {
            element = this.driver.findElementsByXPath(xpath);
        } else {
            LOGGER.error("Trying to retrieve from " + this.getSelectorsFilePath() + " file the item(s) with the key "
                    + key + " but " + key + ".xpath is missing!");
        }
        return element;
    }

    /**
     * This method interacts with appium to retrieve the needed element by ID.
     * 
     * @param type of the element to be retrieved.
     * @param name of the element to be retrieved.
     * @return the built id selector.
     */
    private MobileElement getElementById(String type, String name) {
        this.driver.waitFor(By.id(this.buildIdSelector(type, name)), TIMEOUT);
        return this.driver.findElementById(this.buildIdSelector(type, name));
    }

    /**
     * This method interacts with appium to retrieve the needed element by ID (just with ID).
     * 
     * @param id of the element to be retrieved.
     * @return the built id selector.
     */
    private MobileElement getElementByIdJustId(String id) {
        this.driver.waitFor(By.id(id), TIMEOUT);
        return this.driver.findElementById(id);
    }

    /**
     * This method interacts with appium to retrieve the needed element by Name.
     * 
     * @param name of the element to be retrieved.
     * @return the built name selector.
     */
    private MobileElement getElementByName(String type, String name) {
        this.driver.waitFor(By.name(name), TIMEOUT);
        return this.driver.findElementByName(name);
    }

    /**
     * This method checks if a {@link MobileElement} is displayed and visible for appium. By ID
     * 
     * @param key of the item to be selected. In the related selector file should exists an entry with: key.type and key.name
     * @return true if the element exists and it's visible.
     */
    protected boolean isElementVisibleById(String key) {
        boolean showed = false;
        MobileElement element = this.getElementById(key);
        if (isVisible(element)) {
            showed = true;
        }
        return showed;
    }

    /**
     * This method checks if a {@link MobileElement} is displayed and visible for appium. By Name
     * 
     * @param key of the item to be selected. In the related selector file should exists an entry with and key.name
     * @return true if the element exists and it's visible.
     */
    protected boolean isElementVisibleByName(String key) {
        boolean showed = false;
        MobileElement element = this.getElementByName(key);
        if (isVisible(element)) {
            showed = true;
        }
        return showed;
    }

    /**
     * This method checks if a {@link MobileElement} is displayed and visible for appium. By xpath
     * 
     * @param key of the item to be selected. In the related selector file should exists an entry with and key.xpath
     * @return true if the element exists and it's visible.
     */
    protected boolean isElementVisibleByXPath(String key) {
        boolean showed = false;
        MobileElement element = this.getElementByXPath(key);
        if (element != null) {
            showed = true;
        }
        return showed;
    }

    /**
     * It checks if an element is visible for appium.
     * @param element to be checked.
     * @return if is showed the element
     */
    private boolean isVisible(MobileElement element) {
        boolean showed = false;
        if (element != null && element.isDisplayed()) {
            showed = true;
        }
        return showed;
    }

    /**
     * This method builds the id selector using the UI logic.
     * 
     * @param type of the element to be retrieved.
     * @param name of the element to be retrieved.
     * @return the built id selector.
     */
    private String buildIdSelector(String type, String name) {
        String buttonSelector = SELECTOR_PREFIX + "-" + type + "-" + name;
        return buttonSelector;
    }
}
