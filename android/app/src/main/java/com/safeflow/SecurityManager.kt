package com.safeflow

import android.content.Context
import android.content.SharedPreferences
import java.util.*

class SecurityManager(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "SafeFlowSecurityPrefs"
        private const val KEY_UNLOCK_CODE = "unlockCode"
        private const val MASTER_KEY = "SOS-DEVS"
        private const val CODE_LENGTH = 8
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * Generate and store a random 8-character unlock code
     */
    fun generateUnlockCode(): String {
        val code = generateRandomCode(CODE_LENGTH)
        prefs.edit().putString(KEY_UNLOCK_CODE, code).apply()
        return code
    }

    /**
     * Get the stored unlock code, or generate one if it doesn't exist
     */
    fun getUnlockCode(): String {
        var code = prefs.getString(KEY_UNLOCK_CODE, null)
        if (code == null) {
            code = generateUnlockCode()
        }
        return code
    }

    /**
     * Validate the entered code against stored code or master key
     */
    fun validateCode(enteredCode: String): Boolean {
        val storedCode = getUnlockCode()
        return enteredCode.equals(storedCode, ignoreCase = true) || 
               enteredCode.equals(MASTER_KEY, ignoreCase = true)
    }

    /**
     * Generate a random alphanumeric code
     */
    private fun generateRandomCode(length: Int): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        val sb = StringBuilder(length)
        
        for (i in 0 until length) {
            sb.append(chars[random.nextInt(chars.length)])
        }
        
        return sb.toString()
    }

    /**
     * Get the master key (for reference/debugging)
     */
    fun getMasterKey(): String {
        return MASTER_KEY
    }

    /**
     * Reset the unlock code (generate a new one)
     */
    fun resetUnlockCode(): String {
        return generateUnlockCode()
    }
}
