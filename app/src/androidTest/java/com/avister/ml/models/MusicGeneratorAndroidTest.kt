package com.avister.ml.models

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.avister.midiGeneration.GenerateMidiActivity
import com.avister.utilities.ConfigurationManager
import org.jfugue.theory.Note
import org.junit.Before
import org.junit.Rule
import org.junit.Test

//import org.mockito.kotlin.doReturn
//import org.mockito.kotlin.mock

//
internal class MusicGeneratorAndroidTest {
//    private val mockContext = MockContext()

    //    val mock = mock<Context> {
//        on {  } doReturn "text"
//    }

    val notesList = listOf("C4", "D3", "E3", "F4", "G3", "B3")
    val sequenceLength = 3

    @get:Rule
    val activityTestRule = ActivityScenarioRule(GenerateMidiActivity::class.java)

    //    fun x = activityTestRule.scenario.onActivity {
//        it
//    }
    fun testOnActivity(func: (Activity) -> Unit): ActivityScenario<GenerateMidiActivity> =
        activityTestRule.scenario.onActivity {
            func(it)
        }

    @Test
    fun getTensorSize() {
        testOnActivity {
            val configurationManager = ConfigurationManager(it)
            val musicGeneratorAndroid =
                MusicGeneratorAndroid(it, configurationManager["modelFileName"], ModelType.CPC)
//            val inputTensor = musicGeneratorAndroid.inputTensor.shape().toTypedArray()
//            val outputTensor = musicGeneratorAndroid.inputTensor.shape().toTypedArray()
            val musicGeneratorAndroidInputShapeHeight = musicGeneratorAndroid.inputShape.height
            val musicGeneratorAndroidInputShapeWidth = musicGeneratorAndroid.inputShape.width
            val musicGeneratorAndroidInputShape = musicGeneratorAndroid.inputShape
            assert(musicGeneratorAndroid.notesArray is ArrayList && musicGeneratorAndroid.notesArray.isNotEmpty())
        }
    }


    @Test
    fun testNoteJsonToString() {
        testOnActivity {
            val configurationManager = ConfigurationManager(it)
            val musicGeneratorAndroid =
                MusicGeneratorAndroid(it, configurationManager["modelFileName"], ModelType.CPC)
            assert(musicGeneratorAndroid.notesArray is ArrayList && musicGeneratorAndroid.notesArray.isNotEmpty())
        }
    }

    @Before
    fun setup() {

    }

    @Test
    fun prepareSequences() {
        testOnActivity {
            val configurationManager = ConfigurationManager(it)
            val musicGeneratorAndroid =
                MusicGeneratorAndroid(it, configurationManager["modelFileName"], ModelType.CPC)
            val (preparedInput, preparedInputNormalized) = musicGeneratorAndroid.prepareSequences(
                notesList,
                sequenceLength
            )
            assert(preparedInput.all { it.count { true } == sequenceLength })
        }
//        val (preparedInput, preparedInputNormalized) = musicGeneratorAndroid.prepareSequences(notesList, sequenceLength)
//        print(preparedInput + preparedInputNormalized)
//        assert(preparedInput.all { it.count { it !== null} == sequenceLength })
    }

    @Test
    fun generateMusic() {
        testOnActivity {
            val configurationManager = ConfigurationManager(it)
            val musicGeneratorAndroid =
                MusicGeneratorAndroid(it, configurationManager["modelFileName"], ModelType.CPC)
            val (preparedInput, preparedInputNormalized) = musicGeneratorAndroid.prepareSequences(
                notesList,
                sequenceLength
            )
            val randArray = MusicGenerator.generateRandomArray(listOf(100)) as FloatArray
            val generatedString = musicGeneratorAndroid.generateMusic(50, randArray)
            assert(generatedString.size == 50)
        }
    }

    @Test
    fun generateMidiPattern() {
        testOnActivity {
            val configurationManager = ConfigurationManager(it)
            val musicGeneratorAndroid =
                MusicGeneratorAndroid(it, configurationManager["modelFileName"], ModelType.CPC)
            val (preparedInput, preparedInputNormalized) = musicGeneratorAndroid.prepareSequences(
                notesList,
                sequenceLength
            )
            val randArray = MusicGenerator.generateRandomArray(listOf(100)) as FloatArray
            val pattern = musicGeneratorAndroid.generateMidiPattern(5, randArray)
            assert(pattern.toString().isNotEmpty())
        }
    }

    @Test
    fun createMidiStringPattern() {

    }

    @Test
    fun createNoteOrChordFromStringTest(){
        testOnActivity {
            val testString1 = "C4"
            val configurationManager = ConfigurationManager(it)
            val musicGeneratorAndroid =
                MusicGeneratorAndroid(it, configurationManager["modelFileName"], ModelType.CPC)

            val note: Note = musicGeneratorAndroid.createNoteOrChordFromString(testString1)
            assert(note.value == "C4".toByte())
        }

    }

    @Test
    fun dubSaveTest() {
        activityTestRule.scenario.onActivity {
            it.exampleSave()
        }
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
