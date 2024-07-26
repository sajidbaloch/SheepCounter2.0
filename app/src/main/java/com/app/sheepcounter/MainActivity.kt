package com.app.sheepcounter

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var totalCountView: TextView
    private lateinit var incrementButton: Button
    private var totalCount = 0
    private var missedSheepCount = 0
    private var visibleSheep = 0
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            addSheep()
            handler.postDelayed(this, 2000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        totalCountView = findViewById(R.id.total_count)
        incrementButton = findViewById(R.id.increment_button)
        val settingsButton: Button = findViewById(R.id.settings_button)

        incrementButton.setOnClickListener {
            incrementCount()
        }

        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        handler.post(runnable)
    }

    private fun incrementCount() {
        totalCount++
        updateTotalCount()
    }

    private fun addSheep() {
        val sheep = TextView(this).apply {
            text = "🐑"
            textSize = 50f
        }
        (findViewById<ViewGroup>(R.id.sheep_area)).addView(sheep)
        visibleSheep++
        updateSheepCounter()

        val animation = TranslateAnimation(0f, 1000f, 0f, 0f).apply {
            duration = 3000
            fillAfter = true
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    (findViewById<ViewGroup>(R.id.sheep_area)).removeView(sheep)
                    visibleSheep--
                    missedSheepCount++  // Increment missed sheep count
                    updateSheepCounter()
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }
        sheep.startAnimation(animation)
    }

    private fun updateSheepCounter() {
        // No longer needed
    }

    private fun updateTotalCount() {
        totalCountView.text = "Total Count: $totalCount"
    }
}
