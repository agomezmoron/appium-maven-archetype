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
package com.agomezmoron.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * @author Alejandro Gomez <agommor@gmail.com>
 * Class to read the properties files.
 *
 */
public class PropertiesHandler {

    /**
     * Logger class initialization.
     */
    private static final Logger LOGGER = Logger.getLogger(PropertiesHandler.class);

    /**
     * Properties cache.
     */
    private Map<String, Properties> properties;

    /**
     * Instance of the PropertiesHandler class.
     */
    private static PropertiesHandler instance = null;

    /**
     * Attribute to have control about which is the last read file.
     */
    private String lastReadFile;

    /**
     * Default constructor.
     */
    private PropertiesHandler() {
        this.properties = new HashMap<String, Properties>();
        this.lastReadFile = null;
    }

    /**
     * Returns an instance of the PropertiesHanler.
     * @return PropertiesHandler instance.
     */
    public static PropertiesHandler getInstance() {
        // singleton pattern
        if (instance == null) {
            instance = new PropertiesHandler();
        }
        return instance;
    }

    /**
     * Loads a properties file with the given filename.
     * @param filename of the properties file to read or the path to the file.
     * @return true/false if was read successfully.
     */
    public boolean load(String filename) {
        LOGGER.info("Reading properties from the file: " + filename);
        boolean read = false;
        // If it wasn't read previously
        if (!this.properties.containsKey(filename)) {
            LOGGER.info("There is no cached properties, reading them now from " + filename);
            this.lastReadFile = null;
            InputStream inputStream = getInputStream(filename);
            // if the file was loaded
            if (inputStream != null) {
                Properties propertiesFile = new Properties();
                try {
                    propertiesFile.load(inputStream);
                    // add the readed file into the cache
                    this.properties.put(filename, propertiesFile);
                    this.lastReadFile = filename;
                    read = true;
                    LOGGER.info(filename + " read and cached successfully!");
                } catch (IOException ex) {
                    LOGGER.error("An error occured reading properties from " + filename + ": " + ex);
                }
            } else {
                LOGGER.warn("Couldn't read properties from " + filename + ". It seems the file doesn't exist");
            }
        } else {
            read = true;
            this.lastReadFile = filename;
            LOGGER.info("Properties were cached, so we don't have to read anything");
        }
        return read;
    }

    /**
     * Returns an InputStream from a filename or file path.
     * @param filename of the properties file to read or the path to the file.
     * @return InputStream object.
     */
    private InputStream getInputStream(String filename) {
        LOGGER.info("Getting the inputStream for the filename " + filename);
        // checking if it's a full path
        File f = new File(propertyFileAbsolutePath(filename));
        InputStream inputStream = null;
        if (f.exists() && !f.isDirectory()) {
            LOGGER.info("It's a full path, and the file exists");
            try {
                inputStream = new FileInputStream(f);
            } catch (FileNotFoundException ex) {
                LOGGER.error("The file was found BUT couldn't be loaded: " + ex);
            }
        } else {
            LOGGER.info("Reading the file from the context");
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            inputStream = classLoader.getResourceAsStream(filename);
        }
        return inputStream;
    }

    private String propertyFileAbsolutePath(String filename) {
        String absoluteFileName = filename;
        LOGGER.info("Checking configuration from absoluth path " + absoluteFileName);
        File f = new File(absoluteFileName);
        if (f.exists() && !f.isDirectory()) {
            return absoluteFileName;
        }

        return filename;
    }

    /**
     * Retrieves the value of a given property.
     * @param propertyName to read.
     * @return value of the property or null if it doesn't exist.
     */
    public String get(String propertyName) {
        String value = null;
        // return the key just if the file was loaded previously and if the Properties object exists
        if (this.lastReadFile != null && this.properties.containsKey(this.lastReadFile)) {
            value = this.properties.get(this.lastReadFile).getProperty(propertyName);
        }
        return value;
    }

    /**
     * Refreshes all the loaded files reading them again.
     * If a file doesn't exist now, it will not be refreshed.
     */
    public void refreshLoadedFiles() {
        LOGGER.info("Refreshing " + this.properties.size() + " cached properties files");
        for (String filename : this.properties.keySet()) {
            Properties oldProperties = this.properties.get(filename);
            // removing the old Properties object to force reload it.
            this.properties.remove(filename);
            // if an error occurred, we put again the old properties
            if (!this.load(filename)) {
                LOGGER.error("An error occurred refreshing the " + filename
                        + " file. The old properties will be used again!");
                this.properties.put(filename, oldProperties);
            } else {
                LOGGER.info(filename + " refreshed successfully!");
            }

        }
    }

    /**
     * Refreshes a given property file.
     * @param fileName to read.
     * If a file doesn't exist now, it will not be refreshed.
     */
    public void refreshPropertyFile(String fileName) {
        LOGGER.info("Refreshing " + fileName + " cached property file");
        Properties oldProperties = this.properties.get(fileName);
        // removing the old Properties object to force reload it.
        this.properties.remove(fileName);
        // if an error occurred, we put again the old properties
        if (!this.load(fileName)) {
            LOGGER.error("An error occurred refreshing the " + fileName
                    + " file. The old properties will be used again!");
            this.properties.put(fileName, oldProperties);
        } else {
            LOGGER.info(fileName + " refreshed successfully!");
        }
    }

}
