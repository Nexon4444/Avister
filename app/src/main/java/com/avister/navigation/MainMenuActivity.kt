package com.avister.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.avister.R
import com.avister.markSightReading.TestSightReadingActivity
import com.avister.midiGeneration.GenerateMidiActivity

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        val playAvailableMidiFilesButton = findViewById<Button>(R.id.play_available_midi_files)
        val generateMidiFilesButton = findViewById<Button>(R.id.generate_midi_files)
        val testSightReadingSkillButton = findViewById<Button>(R.id.test_sight_reading_skill)
        playAvailableMidiFilesButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ChooseSongActivity::class.java
                )
            )
        }
        generateMidiFilesButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    GenerateMidiActivity::class.java
                )
            )
        }
        testSightReadingSkillButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    TestSightReadingActivity::class.java
                )
            )
        }

    }

}