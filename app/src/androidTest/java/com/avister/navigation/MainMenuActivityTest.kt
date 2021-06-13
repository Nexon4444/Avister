package com.avister.navigation

import android.app.Instrumentation.ActivityMonitor
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.avister.R
import com.avister.markSightReading.TestSightReadingActivity
import com.avister.midiGeneration.GenerateMidiActivity
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.reflect.typeOf


@RunWith(AndroidJUnit4ClassRunner::class)
class MainMenuActivityTest : TestCase() {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainMenuActivity::class.java)

    private inline fun <reified Activity> performTestOnButtons(id: Int){
        val activityMonitor: ActivityMonitor = getInstrumentation().addMonitor(Activity::class.java.name, null, false)
        onView(withId(id)).perform(click())
        val nextActivity =
            getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000)
        assertTrue(nextActivity::class == Activity::class)
        nextActivity.finish()
    }

    @Test
    fun playAvailableMidiFilesButtonTest () = performTestOnButtons<ChooseSongActivity>(R.id.play_available_midi_files)

    @Test
    fun generateMidiFilesButtonTest () = performTestOnButtons<GenerateMidiActivity>(R.id.generate_midi_files)

    @Test
    fun testSightReadingSkillButtonTest () = performTestOnButtons<TestSightReadingActivity>(R.id.test_sight_reading_skill)
}