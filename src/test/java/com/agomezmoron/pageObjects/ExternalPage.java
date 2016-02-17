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

import java.util.Iterator;

import com.agomezmoron.appiumhandler.AppiumHandledDriver;

/**
 * Abstract class to abstract all the needed actions to switch the window.
 * This page objet supports handling external views for hybrid apps.
 * @author Alejandro Gomez <agommor@gmail.com>
 *
 */
public abstract class ExternalPage extends BasePage {

    /**
     * @see {@link BasePage}.
     */
    protected ExternalPage(AppiumHandledDriver driver) {
        super(driver);
        // to have time to create the other window
        driver.sleep(3);
        String currentWindow = this.driver.getWindowHandle();
        Iterator<String> iterator = this.driver.getWindowHandles().iterator();
        String handler = "";
        boolean switched = false;
        while (iterator.hasNext() && !switched) {
            handler = iterator.next();
            if (!handler.equals(currentWindow)) {
                // Switching to the Facebook window
                this.driver.switchTo().window(handler);
                switched = true;
            }
        }
    }

    /**
     * Method to switch to the main window.
     */
    public final void switchToCorrectWindow() {
        this.driver.sleep(5);
        this.driver.switchToMainWindow();
    }

}
