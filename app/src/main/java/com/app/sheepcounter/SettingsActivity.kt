package com.app.sheepcounter

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_layout)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("SheepCounterPrefs", MODE_PRIVATE)

        // Find views
        val frequencySeekBar: SeekBar = findViewById(R.id.frequency_seekbar)
        val durationSeekBar: SeekBar = findViewById(R.id.duration_seekbar)
        val resetButton: Button = findViewById(R.id.reset_button)
        val frequencyLabel: TextView = findViewById(R.id.frequency_label)
        val durationLabel: TextView = findViewById(R.id.duration_label)

        // Load saved preferences
        frequencySeekBar.progress = sharedPreferences.getInt("frequency", 10) // default to 10 seconds
        durationSeekBar.progress = sharedPreferences.getInt("duration", 5) // default to 5 seconds

        // Update label with current values
        frequencyLabel.text = "Sheep Appearance Frequency (${frequencySeekBar.progress} seconds)"
        durationLabel.text = "Sheep Duration (${durationSeekBar.progress} seconds)"

        // Set SeekBar listeners to update labels
        frequencySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                frequencyLabel.text = "Sheep Appearance Frequency (${progress} seconds)"
                savePreference("frequency", progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        durationSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                durationLabel.text = "Sheep Duration (${progress} seconds)"
                savePreference("duration", progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Set up reset button
        resetButton.setOnClickListener {
            frequencySeekBar.progress = 10 // Default frequency
            durationSeekBar.progress = 5 // Default duration
            savePreference("frequency", 10)
            savePreference("duration", 5)
        }
    }

    private fun savePreference(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }
}