# SafeFlow - Native Android App

A native Android application built with Kotlin that uses AccessibilityService to monitor and block specified applications.

## 🚀 Features

- **App Blocking**: Automatically closes blocked applications when they are opened
- **Simple UI**: Clean interface with a Protection Active switch
- **Accessibility Service**: Uses Android's AccessibilityService API for app monitoring
- **Easy Configuration**: Blocked apps list is easily modifiable in the code

## 📋 Requirements

- **Android Studio**: Arctic Fox or later
- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34
- **Language**: Kotlin

## 🛠️ Installation

1. **Open in Android Studio**:
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the `/app/android` directory
   - Click "OK"

2. **Sync Gradle**:
   - Android Studio will automatically prompt to sync Gradle
   - Wait for the sync to complete

3. **Build the App**:
   - Click "Build" → "Make Project"
   - Or use shortcut: `Ctrl+F9` (Windows/Linux) or `Cmd+F9` (Mac)

4. **Run on Device/Emulator**:
   - Connect an Android device via USB (with USB Debugging enabled)
   - Or start an Android emulator
   - Click the "Run" button (green play icon)
   - Select your device/emulator

## ⚙️ Setup & Configuration

### Granting Accessibility Permission

1. Open the SafeFlow app
2. Tap "Open Accessibility Settings" button
3. Find "SafeFlow" in the list
4. Toggle the switch to enable the service
5. Confirm the permission dialog
6. Return to the app

The Protection Active switch will automatically turn on once permission is granted.

### Modifying Blocked Apps

To change which apps are blocked:

1. Open `/app/android/app/src/main/java/com/safeflow/MyAccessibilityService.kt`
2. Locate the `BLOCKED_PACKAGES` list (around line 13):

```kotlin
private val BLOCKED_PACKAGES = listOf(
    "com.microsoft.bing"  // Currently blocking Bing for testing
    // Add more packages here:
    // "com.android.chrome",
    // "com.instagram.android"
)
```

3. Add or remove package names as needed
4. Rebuild and reinstall the app

### Finding Package Names

To find an app's package name:
- Use ADB: `adb shell pm list packages | grep <app_name>`
- Use Apps like "App Inspector" from Play Store
- Check the app's Play Store URL: `play.google.com/store/apps/details?id=<package_name>`

## 📁 Project Structure

```
/app/android/
├── build.gradle                    # Project-level Gradle config
├── settings.gradle                 # Project settings
├── gradle.properties              # Gradle properties
└── app/
    ├── build.gradle               # App-level Gradle config
    ├── proguard-rules.pro        # ProGuard rules
    └── src/main/
        ├── AndroidManifest.xml    # App manifest with service declaration
        ├── java/com/safeflow/
        │   ├── MainActivity.kt                # Main UI Activity
        │   └── MyAccessibilityService.kt     # Blocking service logic
        └── res/
            ├── layout/
            │   └── activity_main.xml         # Main screen layout
            ├── values/
            │   ├── strings.xml               # String resources
            │   └── colors.xml                # Color palette
            └── xml/
                └── accessibility_service_config.xml  # Service configuration
```

## 🎯 How It Works

1. **AccessibilityService**: The app registers an AccessibilityService that monitors system events
2. **Event Detection**: When a `TYPE_WINDOW_STATE_CHANGED` event occurs (app switch), the service checks the package name
3. **Blocking Logic**: If the package name matches any in the `BLOCKED_PACKAGES` list, the service performs `GLOBAL_ACTION_BACK`
4. **Result**: The blocked app is immediately closed, returning the user to the previous screen

## 🔧 Troubleshooting

### App not blocking:
- Ensure Accessibility permission is granted in Settings
- Check that the package name is correct in `BLOCKED_PACKAGES`
- Restart the device after granting permission

### Build errors:
- Sync Gradle files: "File" → "Sync Project with Gradle Files"
- Clean and rebuild: "Build" → "Clean Project" then "Rebuild Project"
- Check that Android SDK is properly installed

### Service not starting:
- Check Logcat for errors: View → Tool Windows → Logcat
- Filter by "SafeFlow" tag to see service logs

## 📱 Testing

1. Install the app on your device
2. Grant Accessibility permission
3. Ensure "Protection Active" switch is ON
4. Open the Bing app (or any blocked app you configured)
5. The app should immediately close with a back action

## 🔮 Future Enhancements

The code structure is designed for easy expansion:

- **Allowed Browser Feature**: Modify `BLOCKED_PACKAGES` dynamically to exclude user's preferred browser
- **UI App List**: Add a screen to manage blocked apps from the UI
- **Statistics**: Track how many times apps were blocked
- **Whitelist Mode**: Block all apps except whitelisted ones
- **Time-based Blocking**: Block apps only during certain hours

## 📄 License

This is a demonstration project for educational purposes.

## ⚠️ Important Notes

- This app requires Accessibility Service permission, which is powerful and should be used responsibly
- The app will continue blocking in the background as long as the service is enabled
- Battery usage may increase slightly due to constant monitoring
- Some system apps may not be blockable due to Android security restrictions
