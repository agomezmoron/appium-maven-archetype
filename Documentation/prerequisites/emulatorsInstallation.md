# Android emulators installation

* Download the Android SDK from http://developer.android.com/intl/es/sdk/installing/index.html
* Choose the "Stand-Alone Sdk Tools" option.
* After downloading it, unzip the SDK and locate it in the desired folder (ex: /home/youruser/android-sdk-linux)
* Set the ANDROID_HOME in the environment variables. Example:
```
# Android
export PATH=${PATH}:/home/amoron/android-sdk-linux/platform-tools:/home/amoron/android-sdk-linux/tools
export ANDROID_HOME=/home/amoron/android-sdk-linux
```
* Launch the sdk, typing __android__ in the console.
* Select the __Android API 22__ and download it. (Files: SDK Platform, Intel x86 Atom, Google APIs, Google APIs Intel x86 Atom System Image)
* Create an emulator, in the adroid SDK window. Then search for Tools manage avd and click create. (Set for example a NexusS using the API 22 and intel atom proccesor x86).
* To launch the emulator.

Note: To use our emulator in the debug mode, we must enable **developer mode**. To do that, once the emulator is started, you have to:
* Select Settings > About phone and tap Build number seven times.
* Go to Developer options, select the USB debugging checkbox.
* After doing that, open to Chrome and type in the address bar: __chrome://inspect/#device__s and we can inspect our emulator (if you don't see it, type __adb devices__ in a console to start the adb daemon).

 
```
