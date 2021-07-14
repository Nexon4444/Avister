package com.avister.midiGeneration

import android.app.Activity
import androidx.core.net.toUri
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
//import junit.framework.Assert.assertNotNull
import org.jfugue.pattern.Pattern
import org.junit.Rule
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.File


class PatternExtensionsKtAndroidTest {
    val fileName = "test2.midi"

    @get:Rule
    val activityTestRule = ActivityScenarioRule(GenerateMidiActivity::class.java)

    fun testOnActivity(func: (Activity) -> Unit): ActivityScenario<GenerateMidiActivity> =
        activityTestRule.scenario.onActivity {
            func(it)
        }

    @Test
    fun createMidiMapTest() {
        val map = midiMap
        assert(map.count() == 88)
    }

    @Test
    fun patternSaveAsMidiTest() {
        testOnActivity {
            val noteString =
                listOf("C5q", "D5q", "E5q", "F5q", "G5q", "A5q", "B5q", "C6q").joinToString(" ")
            val pattern = Pattern(noteString)

            pattern.saveAsMidi(it, it.filesDir, fileName)
            assertNotNull(File(it.filesDir.toUri().toString() + "/" + fileName))
        }
    }

    @Test
    fun patternSaveAsMidiWithChordsTest() {
        testOnActivity {
            val noteString =
                listOf("C5q+E-5q+G5q", "D5q", "E5q", "F5q", "G5q", "A5q", "B5q", "C6q").joinToString(" ")
            val pattern = Pattern(noteString)
            pattern.saveAsMidi(it, it.filesDir, fileName)
        }
    }
}

