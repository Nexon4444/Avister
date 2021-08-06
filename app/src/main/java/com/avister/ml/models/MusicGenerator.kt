package com.avister.ml.models

import androidx.core.text.isDigitsOnly
import com.avister.midiGeneration.GeneratorConfig
import com.avister.midiGeneration.Music
import com.avister.midiGeneration.rational2durationString
import org.jfugue.pattern.Pattern
import org.jfugue.theory.Chord
import org.jfugue.theory.Note

//import org.jfugue.pattern.Pattern
//import org.jfugue.theory.Note
import java.nio.MappedByteBuffer

//import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

//import kotlin.Random.Default.nextInt

open class MusicGenerator(
    notesList: List<String>,
    mappedByteBuffer: MappedByteBuffer,
    override val numThreads: Int,
    val modelType: ModelType,
    val generatorConfig: GeneratorConfig,
    device: Device = Device.CPU

) : AbstractModel(mappedByteBuffer, device) {

    private val pitchNames = notesList.toSortedSet()
    val noteToIntMap = pitchNames.mapIndexed { index, s -> s to index }.associateBy { it.first }
        .mapValues { it.value.second }
    val intToNoteMap = pitchNames.mapIndexed { index, s -> index to s }.associateBy { it.first }
        .mapValues { it.value.second }
    val nVocab = pitchNames.size

    fun prepareSequences(
        notes: List<String>,
        sequenceLength: Int
    ): Pair<List<IntArray>, List<FloatArray>> {
        val sequencesIn = (0 until notes.size - sequenceLength).map {
            notes.subList(it, it + sequenceLength).toTypedArray()
        }
        val sequencesOut = (0 until notes.size - sequenceLength).map { notes[it + sequenceLength] }
        val networkInput = sequencesIn.map { sequence -> sequence.map { element -> noteToIntMap[element] } }


        val networkOutput = sequencesOut.map { note -> noteToIntMap[note] }

        val numPatterns = networkInput.size

        val normalizedInput = networkInput.map { it.map { it!!.toFloat().div(nVocab) } }

        val normalizedInputFloatArray = normalizedInput.map { it.toTypedArray().toFloatArray() }
        val networkInputNoNulls: List<List<Int>> = networkInput.map { it.map { it!! } }
        val networkInputIntArray: List<IntArray> =
            networkInputNoNulls.map { it.toTypedArray().toIntArray() }
        return networkInputIntArray to normalizedInputFloatArray
    }

//    fun createPattern(noteToConvert: String): Pattern {
////        if ()
//        val note = Chord(noteToConvert)
////        val note = Note(noteToConvert.toByte())
//        if (modelType == ModelType.CPC) {
//            note.setDuration("q")
//        }
//        return note
//    }

    fun createMusicStringAccordingToModel(noteList: List<Music>): String {
        return when(modelType) {
            ModelType.CPC -> cpcImplementation(noteList)
        }
    }

    private fun cpcImplementation(noteList: List<Music>): String {
        val normal = rational2durationString[generatorConfig.duration]
        return noteList.joinToString("$normal+") + normal
    }

    fun createNoteOrChordFromString(notesString: String): Pattern =
        if (notesString.contains(".") || notesString.isDigitsOnly()) {
            val notesList = notesString.split('.')
//            val chordList= notesList.map { createNoteFromIntString(translateString2Int(it)) }
            val chordList= notesList.map { it }
            Pattern(createMusicStringAccordingToModel(chordList))
        } else Pattern(createMusicStringAccordingToModel(listOf(notesString)))

    fun createMidiPattern(noteStringList: List<String>): Pattern {
        val noteList = noteStringList.map{
            createNoteOrChordFromString(it)
        }
        val pattern = Pattern()
        for (el in noteList )
            pattern.add(el)

        return pattern
    }

    companion object {
        fun generateRandomArray(shape: List<Int>): Any =
            if (shape.size == 1) FloatArray(shape[0]) { ran.nextFloat() }
            else {
                (1..shape[0]).map { generateRandomArray(shape.subList(1, shape.size)) }
                    .toTypedArray()
            }

        fun translateString2Int(noteString: String): Int = noteString.toInt()
        fun createNoteFromIntString(noteInt: Int): String = "[$noteInt]"

        fun savePattern(pattern: Pattern){

        }
    }

//        fun generateRandomSequenceNumpy(size: Int, max: Int) = randint<Int>(0, size)

}
//    fun generateMusic():,

