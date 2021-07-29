package com.avister.midiGeneration

//import org.jfugue.midi.MidiFileManager
//import org.jfugue.pattern.Pattern

import android.content.ContextWrapper
import android.os.Bundle
import android.os.Environment
import android.text.Layout
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.avister.R
import com.avister.ml.models.ModelType
import com.avister.ml.models.MusicGenerator
import com.avister.ml.models.MusicGeneratorAndroid
import com.avister.utilities.ConfigurationManager
import com.avister.utilities.currentDateTimeAsString
import org.jfugue.pattern.Pattern
import java.io.File


class GenerateMidiActivity : AppCompatActivity() {
    val tempo = 120
    val repeat = 1
    fun setLayout(mainLayout: Int, secondaryLayout: Int): ViewGroup {
        val placeHolder = findViewById<Layout>(mainLayout)
        layoutInflater.inflate(secondaryLayout, placeHolder)
        return placeHolder
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_midi)
        val config = ConfigurationManager(this)
        val contentViews = listOf(R.layout.simple_music_generator_settings, R.layout.complicated_music_generator_settings)
        val generateMidiButton = findViewById<Button>(R.id.generate_midi_button)
        generateMidiButton.setOnClickListener {
            generateAndSavePattern()
        }


        val spinner: Spinner = findViewById(R.id.spinnerModelGenerationChoice)
//create a list of items for the spinner.
//create a list of items for the spinner.
        val items = config["generationModelsNames"]
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
//set the spinners adapter to the previously created one.
//set the spinners adapter to the previously created one.
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                setLayout(R.layout.activity_generate_midi, contentViews[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
//        spinner.setOnItemClickListener { , view, position, id ->  }
    }

    fun generateAndSavePattern(): Pair<Pattern, File> {
        val configurationManager = ConfigurationManager(this)
        val musicGeneratorAndroid =
            MusicGeneratorAndroid(this, configurationManager["modelFileName"] as String, ModelType.CPC)
        val sead = MusicGenerator.generateRandomArray(listOf(100)) as FloatArray

        val cw = ContextWrapper(applicationContext)
        val directory = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
//        val file = File(directory, "/test.midi")
        val pattern: Pattern = musicGeneratorAndroid.generateMidiPattern(24, sead)
//        val file = File(Environment.getExternalStorageDirectory(), )
//        pattern.savePattern(file)
        val file = pattern.saveAsMidi(this, filesDir, "sheet_" + currentDateTimeAsString() + ".mid")
//        val player = Player()
//        player.saveMidi(pattern, file)
//        MidiFileManager.savePatternToMidi(pattern.setTempo(tempo).repeat(repeat), file)
        return (pattern to file)
    }

//    fun exampleSave() {
//        val cw = ContextWrapper(applicationContext)
//        val directory = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
//        val pattern: Pattern = Pattern("C4")
//        val file = File(directory, "test.midi")
//        val player = Player()
//        player.saveMidi(pattern, file)
////        MidiFileManager.savePatternToMidi(pattern, file)
//    }

}