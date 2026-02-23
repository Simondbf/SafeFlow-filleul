package com.safeflow

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class IntroActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private val fragments = mutableListOf<Fragment>()

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

        viewPager = findViewById(R.id.viewPager)
        
        // Create fragments
        val page1 = IntroPage1Fragment().apply {
            setOnNextClickListener(object : IntroPage1Fragment.OnNextClickListener {
                override fun onNextClicked() {
                    viewPager.currentItem = 1
                }
            })
        }
        
        val page2 = IntroPage2Fragment().apply {
            setOnNextClickListener(object : IntroPage2Fragment.OnNextClickListener {
                override fun onNextClicked() {
                    viewPager.currentItem = 2
                }
            })
        }
        
        val page3 = IntroPage3Fragment().apply {
            setOnNextClickListener(object : IntroPage3Fragment.OnNextClickListener {
                override fun onNextClicked() {
                    viewPager.currentItem = 3
                }
            })
        }
        
        val page4 = IntroPage4Fragment().apply {
            setOnFinishClickListener(object : IntroPage4Fragment.OnFinishClickListener {
                override fun onFinishClicked() {
                    // Mark as not first run
                    prefs.edit().putBoolean("isFirstRun", false).apply()
                    startMainActivity()
                }
            })
        }
        
        fragments.add(page1)
        fragments.add(page2)
        fragments.add(page3)
        fragments.add(page4)
        
        // Set up ViewPager2
        val adapter = IntroPagerAdapter(this, fragments)
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false // Disable swipe, only navigate via buttons
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private class IntroPagerAdapter(
        activity: FragmentActivity,
        private val fragments: List<Fragment>
    ) : FragmentStateAdapter(activity) {
        
        override fun getItemCount(): Int = fragments.size
        
        override fun createFragment(position: Int): Fragment = fragments[position]
    }
}
