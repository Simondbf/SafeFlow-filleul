package com.safeflow

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var blockedSitesManager: BlockedSitesManager
    private lateinit var etSiteInput: EditText
    private lateinit var btnBlock: Button
    private lateinit var imgShield: ImageView
    private lateinit var imgSettings: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        blockedSitesManager = BlockedSitesManager(this)

        // Initialize views
        etSiteInput = findViewById(R.id.etSiteInput)
        btnBlock = findViewById(R.id.btnBlock)
        imgShield = findViewById(R.id.imgShield)
        imgSettings = findViewById(R.id.imgSettings)

        // Set up block button
        btnBlock.setOnClickListener {
            val site = etSiteInput.text.toString().trim()
            if (site.isEmpty()) {
                Toast.makeText(this, "Entrez un site à bloquer", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val added = blockedSitesManager.addBlockedSite(site)
            if (added) {
                Toast.makeText(this, "✓ Site bloqué: $site", Toast.LENGTH_SHORT).show()
                etSiteInput.text.clear()
            } else {
                Toast.makeText(this, "Site déjà bloqué ou invalide", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up settings button
        imgSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Update shield status
        updateShieldStatus()
    }

    override fun onResume() {
        super.onResume()
        updateShieldStatus()
    }

    private fun updateShieldStatus() {
        val isServiceEnabled = isAccessibilityServiceEnabled()
        
        if (isServiceEnabled) {
            // Green shield - service OK
            imgShield.setImageResource(R.drawable.ic_shield_green)
        } else {
            // Red shield - service KO
            imgShield.setImageResource(R.drawable.ic_shield_red)
        }
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        return try {
            val service = "${packageName}/${MyAccessibilityService::class.java.name}"
            val enabledServices = Settings.Secure.getString(
                contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            enabledServices?.contains(service) == true
        } catch (e: Exception) {
            false
        }
    }
}
