package com.avister.ml.models
import android.app.Activity
import org.junit.Test
import org.junit.Before
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.avister.navigation.MainMenuActivity
import org.junit.Rule

//import org.mockito.kotlin.doReturn
//import org.mockito.kotlin.mock

//
internal class MusicGeneratorAndroidTest {
//    private val mockContext = MockContext()

    //    val mock = mock<Context> {
//        on {  } doReturn "text"
//    }
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainMenuActivity::class.java)
//    fun x = activityTestRule.scenario.onActivity {
//        it
//    }
    fun testOnActivity(func: (Activity) -> Unit): ActivityScenario<MainMenuActivity> = activityTestRule.scenario.onActivity {
        func(it)


//        val configurationManager = ConfigurationManager(it)
//        Assert.assertEquals("Avister", configurationManager["app_name"])
    }



    @Test
    fun testNoteJsonToString(){
         testOnActivity {
            val musicGeneratorAndroid = MusicGeneratorAndroid(it)
            assert(musicGeneratorAndroid.notesArray is ArrayList && musicGeneratorAndroid.notesArray.isNotEmpty())
        }
//        activityTestRule.scenario.onActivity {
//            val configurationManager = ConfigurationManager(it)
//            assertEquals("Avister", configurationManager["app_name"])
//        }
    }

    @Test
    fun testPrepareSequences() {
        testOnActivity {
            val musicGeneratorAndroid = MusicGeneratorAndroid(it)
            val seq = musicGeneratorAndroid.prepareSequences()
            val x = 3
        }
    }

    @Before
    fun setup() {

    }

    @Test
    fun generateMusic(){
//        musicGeneratorAndroid.predict()
    }

//    @Test
//    fun testGenerateRandomSequenceSize() {
//        val generatedSequence = MusicGeneratorAndroid.generateRandomSequence(100, 10)
//        assert(true) { generatedSequence.size == 100 }
//    }
//
//    @Test
//    fun testGenerateRandomSequenceEachElementBetween0and10() {
//        val generatedSequence = MusicGeneratorAndroid.generateRandomSequence(100, 10)
//        assert(true) { generatedSequence.all { it in 1..9 }  }
//    }
}