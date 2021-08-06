package com.avister.midiGeneration

//import org.jfugue.midi.MidiFileManager
//import org.jfugue.pattern.Pattern

import android.app.Activity
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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.avister.R
import com.avister.midiGeneration.fragments.ComplexGeneratorFragment
import com.avister.midiGeneration.fragments.SimpleGeneratorFragment
import com.avister.ml.models.ModelType
import com.avister.ml.models.MusicGenerator
import com.avister.ml.models.MusicGeneratorAndroid
import com.avister.utilities.ConfigurationManager
import com.avister.utilities.currentDateTimeAsString
import org.jfugue.pattern.Pattern
import java.io.File

import android.app.PendingIntent.getActivity
import androidx.fragment.app.FragmentTransaction
import com.avister.midiGeneration.fragments.AbstractGeneratorFragment


class GenerateMidiActivity : FragmentActivity() {
    val tempo = 120
    val repeat = 1
    lateinit var displayedFragment: AbstractGeneratorFragment
//    fun setLayout(mainLayout: Int, secondaryLayout: Int): ViewGroup {
//        val placeHolder = findViewById<Layout>(mainLayout)
//        layoutInflater.inflate(secondaryLayout, placeHolder)
//        return placeHolder
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_midi)
        val config = ConfigurationManager(this)
        val contentViews = listOf(R.layout.simple_music_generator_settings, R.layout.complicated_music_generator_settings)
        val generateMidiButton = findViewById<Button>(R.id.generate_midi_button)
        generateMidiButton.setOnClickListener {
            generateAndSavePattern(createGeneratorConfig())
        }


        val spinner: Spinner = findViewById(R.id.spinnerModelGenerationChoice)
        val items = config["generationModelsNames"]
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                when(position) {
//                    0 -> print("0")
                    0 -> replaceFragment(SimpleGeneratorFragment.newInstance(), R.id.fragment_container_view)
                    1 -> replaceFragment(ComplexGeneratorFragment.newInstance(), R.id.fragment_container_view)
                }
//                setLayout(R.layout.activity_generate_midi, contentViews[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun generateAndSavePattern(generatorConfig: GeneratorConfig): Pair<Pattern, File> {
        val configurationManager = ConfigurationManager(this)
        val musicGeneratorAndroid =
            MusicGeneratorAndroid(this, configurationManager["modelFileName"][0], ModelType.CPC, generatorConfig)
        val sead = MusicGenerator.generateRandomArray(listOf(100)) as FloatArray

        val cw = ContextWrapper(applicationContext)
        val directory = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val pattern: Pattern = musicGeneratorAndroid.generateMidiPattern(sead)
        val file = pattern.saveAsMidi(this, filesDir, "sheet_" + currentDateTimeAsString() + ".mid")
        return (pattern to file)
    }

    fun replaceFragment(fragmentToReplace: Fragment, viewId: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        displayedFragment = fragmentToReplace as AbstractGeneratorFragment

        transaction.replace(viewId, fragmentToReplace)
        transaction.addToBackStack(null)
        transaction.commit()

//        supportFragmentManager
//                .beginTransaction()
//                .replace(viewId, fragmentToReplace)
//                .addToBackStack(fragmentToReplace.toString())
//                .commit()
    }

    fun createGeneratorConfig(): GeneratorConfig{
        return GeneratorConfig.generatorConfigFactoryCreate(displayedFragment.createGeneratorConfigInner())
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