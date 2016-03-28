# appium-maven-archetype
The aim of this project is to provide a maven archetype for appium projets using the appium-handler available in https://github.com/agomezmoron/appium-handler 

## Prerequisites
1. [Appium installation and configuration](/Documentation/prerequisites/appiumInstall.md)
2. [Maven 3 configuration](/Documentation/prerequisites/maven3Installation.md)
3. [Java 8 installation](/Documentation/prerequisites/java8Installation.md)
4. [Emulators](/Documentation/prerequisites/emulatorsInstallation.md)


## How to use the archetype
TODO

### Archetype structure
The archetype consist of several important parts, everything has been mounted on the structure of a [Maven](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html) project.

###### In the core:

1. _pom.xml_: We have defined repositories for AppiumHandler, versions of plugins and technologies, properties of Phonegap and Appium, dependencies, profiles (explained [here](/README.md#basic-profiles)), and all the config for the Build phase.
2. _downloadApp.sh_: this script is executed before the project to download and install the app in the device/emulator.

###### In the src/test/resources:

1. Selectors: In this .properties we store the way to access to every element of every page. Using Xpath, ID, etc. That method modulates and encapsulates these variables, with the advantage that entails.
2. Suites: Here we can group sets of tests to run it together.
3. _test.properties & users.properties_: Here we store some common tests properties and the variables of the user's credentials.

###### In the src/test/java:

1. PageObject: As [Selenium](https://redmine.emergya.es/projects/qa/wiki/Proyecto_Selenium), here we have every PageObject, also the BasePage.java, and this BasePage has important functions as we will see in [Native & Hybrid applications support](/README.md#native--hybrid-applications-support).
2. Tests: We store the tests here too. Some _@before_ and _@after_ are defined here, also the abstract method checkLayout().
3. Utils: We can found here PropertiesHandler.java wich interacts with the AppiumHandler; the UserFactory.java to retrieve a UserTest instance depending on the provided domain; and UserTest.java represents a user to the session in course.

### Items selectors
As we explained [here] (/README.md#in-the-srctestresources), the selectors are _.properties_ files used to save the way to locate elements of the page (ID's, Xpath, etc). To make it work the _.properties_ file should have exactly the same name of the PageObject refered with the next changes: the _.properties_ file name should go in lowercase and without the suffix _Page_.
> Example:  
_LoginPage.java_ --> _login.properties_
>

### Native & Hybrid applications support
In theory this archetype should works in Android and IOs, also in Native & Hybrid apps.
But there is an important issue with Hybrid technologies. We have verified that the actions defined in Appium framework, such as: longTap(), swipe(), etc; Are NOT working Hybrid apps (Ionic for example). So we are defining, in BasePage, some kinds of alternative functions using JavaScript, to emulate the necessary actions.

### Basic profiles
As we explained [here] (/README.md#in-the-core), one of the _pom.xml_ content is the profiles. Two of them are about the OS on which the tests are run (depends of the [emulator](/Documentation/prerequisites/emulatorsInstallation.md)), Android uses, for example, an _.apk_ file to install the app, and IOs uses _.ipa_.
```
	<profile>
		<id>android</id>
		<properties>
			<phonePlatform>android</phonePlatform>
			<appium.app.name>app.apk</appium.app.name>
		</properties>
	</profile>
	<profile>
		<id>ios</id>
		<properties>
			<phonePlatform>ios</phonePlatform>
			<appium.app.name>app.ipa</appium.app.name>
		</properties>
	</profile>
```
> Important:  
To run the test are absolutely necessary two conditions:  
1. The emulator should be running.  
2. Appium should be running.
>

The other profile is about the [TestNG](http://testng.org/doc/documentation-main.html) suites, as set of tests to launch:
```
<profile>
	<id>acceptance-suite</id>
	<properties>
		<testNG.suite>src/test/resources/suites/acceptance.xml</testNG.suite>
	</properties>
</profile>
```
