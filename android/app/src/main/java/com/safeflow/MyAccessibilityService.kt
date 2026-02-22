package com.safeflow

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class MyAccessibilityService : AccessibilityService() {

    companion object {
        private const val TAG = "SafeFlow"
        
        // BLOCKED PACKAGES LIST - Easy to modify for future features
        // Example: Add "Allowed Browser" logic by removing the user's chosen browser from this list
        private val BLOCKED_PACKAGES = listOf(
            "com.microsoft.bing"  // Bing Search (for testing)
            // Add more packages here as needed:
            // "com.android.chrome",
            // "com.instagram.android"
        )
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        try {
            Log.d(TAG, "SafeFlow Accessibility Service Connected")
            Log.d(TAG, "Blocking packages: $BLOCKED_PACKAGES")
        } catch (e: Exception) {
            Log.e(TAG, "Error in onServiceConnected: ${e.message}")
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        try {
            // Null check for event
            if (event == null) {
                return
            }

            // Only process window state changed events
            if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                // Null-safe package name extraction
                val packageName = event.packageName?.toString()
                
                // Check if package name is null or empty
                if (packageName.isNullOrEmpty()) {
                    return
                }
                
                // Check if this package should be blocked
                if (isBlockedPackage(packageName)) {
                    Log.d(TAG, "Blocked app detected: $packageName - Closing...")
                    
                    try {
                        // Perform back action to close the app
                        performGlobalAction(GLOBAL_ACTION_BACK)
                        Log.d(TAG, "Back action performed on: $packageName")
                    } catch (e: Exception) {
                        Log.e(TAG, "Error performing back action: ${e.message}")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in onAccessibilityEvent: ${e.message}")
        }
    }

    private fun isBlockedPackage(packageName: String): Boolean {
        return try {
            BLOCKED_PACKAGES.any { blockedPackage ->
                packageName.equals(blockedPackage, ignoreCase = true)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error checking blocked package: ${e.message}")
            false
        }
    }

    override fun onInterrupt() {
        try {
            Log.d(TAG, "SafeFlow Accessibility Service Interrupted")
        } catch (e: Exception) {
            Log.e(TAG, "Error in onInterrupt: ${e.message}")
        }
    }

    override fun onDestroy() {
        try {
            super.onDestroy()
            Log.d(TAG, "SafeFlow Accessibility Service Destroyed")
        } catch (e: Exception) {
            Log.e(TAG, "Error in onDestroy: ${e.message}")
        }
    }
}
