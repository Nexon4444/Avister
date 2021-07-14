package com.avister.midiGeneration

import android.app.Activity
import android.os.Environment
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.avister.R
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.io.InputStream

internal class GenerateMidiActivityTest {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(GenerateMidiActivity::class.java)

    fun testOnActivity(func: (Activity) -> Unit): ActivityScenario<GenerateMidiActivity> =
        activityTestRule.scenario.onActivity {
            func(it)
        }

    @Test
    fun clickGenerateAndSavePattern() {
        testOnActivity {
            Espresso.onView(ViewMatchers.withId(R.id.generate_midi_button))
                .perform(ViewActions.click())
            val file = File(Environment.getExternalStorageDirectory(), "/test.midi")
            val inputStream: InputStream = file.inputStream()
            val inputString = inputStream.bufferedReader().use { it.readText() }
        }
    }

    @Test
    fun generateSaveAndReadPattern() {
        testOnActivity {
            if (it is GenerateMidiActivity) {
                val file = it.generateAndSavePattern().second
//                val file = File(Environment.getExternalStorageDirectory(), "/test.midi")
                val inputStream: InputStream = file.inputStream()
                val inputString = inputStream.bufferedReader().use { it.readText() }
            }
        }
    }
}