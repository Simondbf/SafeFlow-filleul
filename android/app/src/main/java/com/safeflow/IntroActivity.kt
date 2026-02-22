package com.safeflow

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class IntroActivity : AppCompatActivity() {

    private var currentStep = 0
    private val totalSteps = 3

    private lateinit var titleText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var nextButton: Button
    private lateinit var startButton: Button
    private lateinit var stepIndicator: TextView
    private lateinit var discordButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if first run
        val prefs = getSharedPreferences("SafeFlowPrefs", MODE_PRIVATE)
        val isFirstRun = prefs.getBoolean("isFirstRun", true)

        if (!isFirstRun) {
            // Not first run, go directly to MainActivity
            startMainActivity()
            return
        }

        setContentView(R.layout.activity_intro)

        // Initialize views
        titleText = findViewById(R.id.introTitle)
        descriptionText = findViewById(R.id.introDescription)
        nextButton = findViewById(R.id.nextButton)
        startButton = findViewById(R.id.startButton)
        stepIndicator = findViewById(R.id.stepIndicator)
        discordButton = findViewById(R.id.discordButton)

        // Set up buttons
        nextButton.setOnClickListener {
            if (currentStep < totalSteps - 1) {
                currentStep++
                updateContent()
            }
        }

        startButton.setOnClickListener {
            // Mark as not first run
            prefs.edit().putBoolean("isFirstRun", false).apply()
            startMainActivity()
        }

        discordButton.setOnClickListener {
            openDiscord()
        }

        // Show first step
        updateContent()
    }

    private fun updateContent() {
        when (currentStep) {
            0 -> {
                // Step 1: Welcome
                titleText.text = "Bienvenue sur SafeFlow"
                descriptionText.text = "SafeFlow protège votre navigation en bloquant automatiquement les sites indésirables.\n\nRestez concentré et en sécurité."
                nextButton.visibility = View.VISIBLE
                startButton.visibility = View.GONE
                discordButton.visibility = View.GONE
                stepIndicator.text = "1 / 3"
            }
            1 -> {
                // Step 2: Permissions
                titleText.text = "Permissions Requises"
                descriptionText.text = "SafeFlow a besoin du service d'accessibilité pour détecter et bloquer les applications.\n\nVos données restent privées et locales."
                nextButton.visibility = View.VISIBLE
                startButton.visibility = View.GONE
                discordButton.visibility = View.GONE
                stepIndicator.text = "2 / 3"
            }
            2 -> {
                // Step 3: Community
                titleText.text = "Rejoignez la Communauté"
                descriptionText.text = "Partagez vos retours, demandez de l'aide, et restez informé des dernières mises à jour.\n\nRejoignez notre Discord !"
                nextButton.visibility = View.GONE
                startButton.visibility = View.VISIBLE
                discordButton.visibility = View.VISIBLE
                stepIndicator.text = "3 / 3"
            }
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

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
