# appium-maven-archetype
The aim of this project is to provide a maven archetype for appium projets using the appium-handler available in https://github.com/agomezmoron/appium-handler 

## Prerequisites
1. [Appium installation and configuration](/Documentation/prerequisites/appiumInstall.md)
2. [Maven 3 configuration](/Documentation/prerequisites/maven3Installation.md)
3. [Java 8 installation](/Documentation/prerequisites/jave8Installation.md)
4. [Emulators](/Documentation/prerequisites/emulatorsInstallation.md)


## How to use the archetype
TODO

### Archetype structure
The archetype consist of several important parts, everything has been mounted on the structure of a Maven project.

###### In the root:

1. _pom.xml_: We have defined repositories for AppiumHandler, versions of plugins and technologies, properties of Phonegap and Appium, dependencies, profiles, and all the config for the Build phase.
2. _downloadApp.sh_: this script is executed before the project to download and install the app in the device/emulator.

###### In the src/test/resources:

1. Selectors: In this .properties we store the way to access to every element of every page. Using Xpath, ID, etc. That method modulates and encapsulates these variables, with the advantage that entails.
2. Suites: Here we can group sets of tests to run it together.
3. _test.properties & users.properties_: Here we store some common tests properties and the variables of the user's credentials.

###### In the src/test/java:

1. PageObject: As Selenium, here we have every PageObject, also the BasePage.java, and this BasePage has important functions as we will see in [Native & Hybrid applications support](/README.md#native--hybrid-applications-support).
2. Tests: We store the tests here too. Some _@before_ and _@after_ are defined here, also the abstract method checkLayout().
3. Utils: We can found here PropertiesHandler.java wich interacts with the AppiumHandler; the UserFactory.java to retrieve a UserTest instance depending on the provided domain; and UserTest.java represents a user to the session in course.

### Items selectors
As we explained [here] (/README.md#in-the-srctestresources), the selectors are _.properties_ files used to save the way to locate elements of the page (ID's, Xpath, etc). To make it work the _.properties_ file should have exactly the same name of the PageObject refered with the next changes: the _.properties_ file name should go in lowercase and without the suffix _Page_.
> Example:  _LoginPage.java_ --> _login.properties_
>

### Native & Hybrid applications support
In theory this archetype should works in Android and IOs, also in Native & Hybrid apps.
But there is an important issue with Hybrid technologies. We have verified that the actions defined in Appium framework, such as: longTap(), swipe(), etc; Are NOT working Hybrid apps (Ionic for example). So we are defining some kinds of alternative functions using JavaScript, to emulate the necessary actions.

### Basic profiles
TODO
