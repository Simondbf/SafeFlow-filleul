# 📱 Building SafeFlow APK with GitHub Actions (No Android Studio Required!)

## 🎯 Overview

Since you're on a smartphone and can't use Android Studio, I've created a **GitHub Actions workflow** that will automatically build the APK for you in the cloud!

---

## ✅ What's Been Updated

### 1. SDK Versions Updated ✓
- **Min SDK**: 33 (Android 13)
- **Target SDK**: 35 (Android 15/16)
- Matches your device requirements!

### 2. Chrome Warning Added ✓
- New "Browser Information" card in the UI
- **Chrome**: Shows "⚠️ Warning: Contains intrusive ads and adult content." in red
- **Bing**: Shows "🚫 Blocked by SafeFlow" in green

### 3. Blocking Confirmed ✓
- `com.microsoft.bing` is in the BLOCKED_PACKAGES list
- Will be blocked immediately when opened

### 4. GitHub Actions Workflow Created ✓
- File: `.github/workflows/android.yml`
- Automatically builds APK when you push code to GitHub
- No Android Studio needed!

---

## 🚀 Step-by-Step: Building Your APK on GitHub

### Step 1: Create a GitHub Repository

**On your smartphone:**

1. Go to [github.com](https://github.com) in your browser
2. Sign in (or create an account if needed)
3. Click the **+** icon (top right) → **New repository**
4. Fill in:
   - **Repository name**: `SafeFlow-Android` (or any name)
   - **Visibility**: Public or Private (your choice)
   - ✅ Check "Add a README file"
5. Click **Create repository**

### Step 2: Upload Your Code to GitHub

**Option A: Using GitHub Web Interface (Easiest on Phone)**

1. In your new repository, click **Add file** → **Upload files**
2. Upload all files from your `/app` directory:
   - Upload the entire `android/` folder structure
   - Upload `.github/workflows/android.yml`
   - Upload documentation files (README.md, etc.)
3. Write commit message: "Initial commit - SafeFlow Android App"
4. Click **Commit changes**

**Option B: Using Git (If available)**

```bash
# Navigate to your project
cd /app

# Initialize git (if not already done)
git init

# Add GitHub remote (replace USERNAME and REPO_NAME)
git remote add origin https://github.com/USERNAME/REPO_NAME.git

# Add all files
git add .

# Commit
git commit -m "Initial commit - SafeFlow Android App"

# Push to GitHub
git push -u origin main
```

### Step 3: Trigger the Build

**Automatic Trigger:**
- The workflow runs automatically when you push code to `main` or `master` branch
- Wait a few seconds, then proceed to Step 4

**Manual Trigger (Alternative):**
1. Go to your repository on GitHub
2. Click the **Actions** tab
3. Click **Android CI - Build APK** (left sidebar)
4. Click **Run workflow** button (right side)
5. Select branch: `main`
6. Click **Run workflow**

### Step 4: Monitor the Build

1. Stay on the **Actions** tab
2. You'll see a yellow dot 🟡 - Build is running (takes 3-5 minutes)
3. Green checkmark ✅ - Build successful!
4. Red X ❌ - Build failed (check logs)

**While waiting:**
- You can click on the workflow run to see live logs
- Watch the progress of each step

### Step 5: Download Your APK

Once the build is successful (green checkmark):

1. Click on the workflow run name (e.g., "Create android.yml")
2. Scroll down to the **Artifacts** section (bottom of page)
3. Click **SafeFlow-Debug-APK** to download
4. A zip file will download to your device

**On your smartphone:**
5. Open your file manager
6. Navigate to Downloads folder
7. Extract the zip file (long-press → Extract)
8. You'll find: `app-debug.apk` (this is your app!)

### Step 6: Install the APK

1. Tap on `app-debug.apk`
2. Android may show: "Install unknown apps"
   - Tap **Settings**
   - Enable **Allow from this source**
   - Go back and tap `app-debug.apk` again
3. Tap **Install**
4. Wait for installation
5. Tap **Open** or find "SafeFlow" in your app drawer

---

## 🎨 What You'll See in the App

### Main Screen:
1. **SafeFlow** title
2. **App Protection System** subtitle
3. **Protection Status** card with switch
4. **Browser Information** card showing:
   - **Chrome**: ⚠️ Warning: Contains intrusive ads and adult content. (in red)
   - **Bing Search**: 🚫 Blocked by SafeFlow (in green)

### First Launch:
1. Tap "Open Accessibility Settings"
2. Find SafeFlow in the list
3. Toggle it ON
4. Confirm the permission
5. Return to SafeFlow app
6. "Protection Active" switch will be ON automatically

---

## 🔧 Making Changes & Rebuilding

### To Update the App:

1. **Make your changes** to the code files
2. **Push to GitHub** (same process as Step 2)
3. **GitHub Actions runs automatically**
4. **Download new APK** from Artifacts
5. **Install over the old version** (no need to uninstall)

### Common Changes:

**Add more blocked apps:**
Edit: `android/app/src/main/java/com/safeflow/MyAccessibilityService.kt`
```kotlin
private val BLOCKED_PACKAGES = listOf(
    "com.microsoft.bing",
    "com.android.chrome",      // Add Chrome
    "com.instagram.android"    // Add Instagram
)
```

**Change warning text:**
Edit: `android/app/src/main/res/layout/activity_main.xml`
Find the Chrome warning TextView and change the `android:text` value.

---

## 📊 GitHub Actions Workflow Details

**What it does:**
1. ✅ Checks out your code
2. ✅ Sets up Java 17 (required for Android builds)
3. ✅ Grants execute permission to gradlew
4. ✅ Caches Gradle files (faster subsequent builds)
5. ✅ Builds the Debug APK
6. ✅ Uploads APK as artifact (available for 30 days)
7. ✅ Creates build summary with instructions

**Build time:** 3-5 minutes (first time), 2-3 minutes (subsequent builds)

**Free tier limits:**
- GitHub provides 2,000 free Action minutes per month for private repos
- Unlimited for public repos
- Each build takes ~3 minutes, so you can build 600+ times per month

---

## 🐛 Troubleshooting

### ❌ Build Failed

**Check the logs:**
1. Click on the failed workflow run
2. Click on **build** job
3. Expand the failed step (red X)
4. Read the error message

**Common issues:**

1. **"SDK not found"**
   - Usually auto-fixed by the workflow
   - If persists, ensure all files were uploaded correctly

2. **"Gradle sync failed"**
   - Check that `gradlew` has execute permission
   - Workflow automatically handles this

3. **"Missing files"**
   - Ensure entire `android/` folder was uploaded
   - Re-upload missing files

### 🟡 Build Taking Too Long

- Normal build time: 3-5 minutes
- If >10 minutes: Cancel and restart
- GitHub Actions may be under heavy load

### 📥 Can't Download Artifact

- Artifacts are only available for 30 days
- If expired, trigger a new build
- Make sure you're logged into GitHub

### 📱 Can't Install APK

1. **Enable Unknown Sources:**
   - Settings → Apps → Special access → Install unknown apps
   - Select your browser/file manager
   - Toggle "Allow from this source"

2. **"App not installed":**
   - Uninstall old version first
   - Clear device cache
   - Restart device and try again

3. **"Parse error":**
   - APK may be corrupted
   - Download again from GitHub
   - Ensure zip was fully extracted

---

## 🎯 Quick Reference: GitHub Actions Commands

**View all workflow runs:**
```
Your Repo → Actions tab
```

**Manually trigger build:**
```
Actions → Android CI - Build APK → Run workflow
```

**Download APK:**
```
Actions → Click workflow run → Scroll to Artifacts → Download
```

**Check build logs:**
```
Actions → Click workflow run → Click "build" job
```

---

## ✅ Updated Files Summary

| File | What Changed |
|------|-------------|
| `android/app/build.gradle` | SDK: minSdk 33, targetSdk 35 |
| `android/app/src/main/res/layout/activity_main.xml` | Added Browser Information card with Chrome warning |
| `android/app/src/main/res/values/colors.xml` | Added warning_red color (#D32F2F) |
| `android/app/src/main/res/values/strings.xml` | Added browser_info_title string |
| `android/app/src/main/java/com/safeflow/MyAccessibilityService.kt` | Confirmed: com.microsoft.bing in BLOCKED_PACKAGES ✓ |
| `.github/workflows/android.yml` | **NEW** - GitHub Actions workflow |

---

## 🎉 You're All Set!

**Next Steps:**
1. ✅ Create GitHub repository
2. ✅ Upload code from `/app` directory
3. ✅ GitHub Actions builds APK automatically
4. ✅ Download APK from Artifacts
5. ✅ Install on your device
6. ✅ Grant Accessibility permission
7. ✅ Test with Bing app (should close immediately)

**The APK will be built in the cloud - no Android Studio needed!** 🚀

---

## 📞 Need Help?

**Build issues:**
- Check the Actions tab logs for detailed error messages
- Ensure all files were uploaded correctly
- Verify folder structure matches the original

**App issues:**
- Ensure your device is Android 13 (API 33) or higher
- Check that Accessibility permission is granted
- View Logcat logs if available (requires ADB)

---

**Happy Building! 📱⚡**
