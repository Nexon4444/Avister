package com.avister.midiGeneration

import android.content.ContextWrapper
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.avister.R
import com.avister.ml.models.ModelType
import com.avister.ml.models.MusicGenerator
import com.avister.ml.models.MusicGeneratorAndroid
import com.avister.utilities.ConfigurationManager
import org.jfugue.Pattern
import org.jfugue.Player
//import org.jfugue.midi.MidiFileManager
//import org.jfugue.pattern.Pattern
import java.io.File


class GenerateMidiActivity : AppCompatActivity() {
    val tempo = 120
    val repeat = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_midi)

        val generateMidiButton = findViewById<Button>(R.id.generate_midi_button)
        generateMidiButton.setOnClickListener {
            generateAndSavePattern()
        }
    }

    fun generateAndSavePattern(): Pair<Pattern, File> {
        val configurationManager = ConfigurationManager(this)
        val musicGeneratorAndroid =
            MusicGeneratorAndroid(this, configurationManager["modelFileName"], ModelType.CPC)
        val sead = MusicGenerator.generateRandomArray(listOf(100)) as FloatArray

        val cw = ContextWrapper(applicationContext)
        val directory = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val file = File(directory, "/test.midi")
        val pattern: Pattern = musicGeneratorAndroid.generateMidiPattern(8, sead)
//        val file = File(Environment.getExternalStorageDirectory(), )
//        pattern.savePattern(file)
        val player = Player()
        player.saveMidi(pattern, file)
//        MidiFileManager.savePatternToMidi(pattern.setTempo(tempo).repeat(repeat), file)
        return (pattern to file)
    }

    fun exampleSave() {
        val cw = ContextWrapper(applicationContext)
        val directory = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val pattern: Pattern = Pattern("C4")
        val file = File(directory, "test.midi")
        val player = Player()
        player.saveMidi(pattern, file)
//        MidiFileManager.savePatternToMidi(pattern, file)
    }

}