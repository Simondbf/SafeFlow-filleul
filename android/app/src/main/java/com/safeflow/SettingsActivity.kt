package com.safeflow

import android.app.AlertDialog
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var adminComponent: ComponentName

    companion object {
        private const val MASTER_CODE = "SOS-DEVS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        devicePolicyManager = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
        adminComponent = ComponentName(this, AdminReceiver::class.java)

        // Initialize views
        val tvVersion = findViewById<TextView>(R.id.tvVersion)
        val btnDiscord = findViewById<Button>(R.id.btnDiscord)
        val btnDangerZone = findViewById<Button>(R.id.btnDangerZone)

        tvVersion.text = "SafeFlow v1.0"

        btnDiscord.setOnClickListener {
            openDiscord()
        }

        btnDangerZone.setOnClickListener {
            showPasswordDialog()
        }
    }

    private fun showPasswordDialog() {
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
        input.hint = "Entrez le code"

        AlertDialog.Builder(this)
            .setTitle("🔓 Code Requis")
            .setMessage("Entrez le code pour désactiver la protection et permettre la désinstallation.")
            .setView(input)
            .setPositiveButton("Valider") { dialog, _ ->
                val enteredCode = input.text.toString().trim()
                if (enteredCode.equals(MASTER_CODE, ignoreCase = true)) {
                    // Correct code - disable admin
                    disableProtection()
                } else {
                    // Incorrect code
                    Toast.makeText(this, "❌ Code Incorrect", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Annuler") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .show()
    }

    private fun disableProtection() {
        try {
            if (devicePolicyManager.isAdminActive(adminComponent)) {
                devicePolicyManager.removeActiveAdmin(adminComponent)
                Toast.makeText(
                    this,
                    "✓ Protection Désactivée - Désinstallation possible",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Protection déjà désactivée",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Erreur lors de la désactivation", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openDiscord() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/safeflow"))
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
