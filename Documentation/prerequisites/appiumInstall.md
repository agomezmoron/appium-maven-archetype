# Appium installation and configuration

This steps are adapted to Ubuntu OS, running Appium test for Android emulator. Before goind ahead:

```
**WARNING!** : Don't use "sudo" unless is specified in the steps.
```

### Steps

* Install Ruby:
```
sudo apt-get install -y ruby
```
* Install curl:
```
sudo apt-get install -y curl
```
* Using ruby, install **Homebrew** (it's an osX project but __linuxbrew__ is the one for UNIX). It's important because with homebrew we'll be able
to **install npm packages without sudo access** (appium needs to be installed without sudo executions):
```
ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/linuxbrew/go/install)"
```
* Open your .bashrc file:
```
sudo gedit .bashrc
```
* Add the following lines:
```
export PATH="$HOME/.linuxbrew/bin:$PATH"
export MANPATH="$HOME/.linuxbrew/share/man:$MANPATH"
export INFOPATH="$HOME/.linuxbrew/share/info:$INFOPATH"
export
PATH=${PATH}:/home/myuser/android-sdk-linux/platform-tools:/home/myuser/android-s
dk-linux/tools
export ANDROID_HOME=/home/myuser/android-sdk-linux
```
* Reload the .bashrc configuration:
```
source ~/.bashrc
```
* Install g++:
```
sudo apt-get install -y g++
```
* Instal npm (using linuxbrew):
```
brew install node
```
* Install Appium globally:
```
npm install -g appium
```
* Install Appium client:
```
npm install wd
```
* Check Appium version:
```
appium -v
```
* Install Chromedriver:
 1. Download 2.20 (or upper) from https://sites.google.com/a/chromium.org/chromedriver/downloads
 2. Then move the above chromedriver to "/usr/local/bin".
 3. Then change permissions:
```
sudo su
# Change to root user
[sudo] password for user:
# Write your user pass
chmod 7555 /usr/local/bin/chromedriver # Giving permissions
chmod 777 /usr/local/bin/chromedriver # Giving permissions
# Checking version
chromedriver --version
```
### Running Appium

To starts Appium you just have to type __appium__ in a console and Appium process will start up.
```
appium
# info: Welcome to Appium v1.4.16 (REV ae6877eff263066b26328d457bd285c0cc62430d)
# info: Appium REST http interface listener started on 0.0.0.0:4723
# info: Console LogLevel: debug
```
