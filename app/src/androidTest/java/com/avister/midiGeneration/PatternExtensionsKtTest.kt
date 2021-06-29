package com.avister.midiGeneration

import android.app.Activity
import android.content.ContextWrapper
import android.os.Environment
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.jfugue.Pattern
import org.junit.Rule
import org.junit.Test
import java.io.File


class PatternExtensionsKtAndroidTest {
    val filename = "test.midi"
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

//    @Test
//    fun saveAsMidiMapTest() {
//        testOnActivity {
//            saveAsMidi(it, filename)
//            val cw = ContextWrapper(it)
//            val directory = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
////    val pattern: Pattern = Pattern("C4")
//            val savedFile = File(directory, filename)
//            assert(savedFile.exists())
//        }
//    }

    @Test
    fun patternSaveAsMidiTest() {
        testOnActivity {
            val noteString =
                listOf("C5q", "D5q", "E5q", "F5q", "G5q", "A5q", "B5q", "C6q").joinToString(" ")
            val pattern = Pattern(noteString)
            pattern.saveAsMidi(it, filename)
        }
    }
}