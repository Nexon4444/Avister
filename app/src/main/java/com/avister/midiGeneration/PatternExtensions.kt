package com.avister.midiGeneration

//import com.avister.player.MidiFile
//import com.avister.player.MidiTrack
//import com.avister.player.TimeSignature
//import org.jfugue.Tempo
import android.content.Context
import android.content.ContextWrapper
import android.os.Environment
import com.leff.midi.MidiFile
import com.leff.midi.MidiTrack
import com.leff.midi.event.meta.Tempo
import com.leff.midi.event.meta.TimeSignature
import org.jfugue.Pattern
import java.io.File
import java.io.IOException
typealias Note = String
val durationMap = {
    val max = 1920L
    mapOf(
        "w" to max,
        "h" to max / 2,
        "q" to max / 4,
        "i" to max / 8,
        "s" to max / 16,
        "t" to max / 32,
        "x" to max / 64,
        "o" to max / 128
    )
}.invoke()

val midiMap: Map<String, Int> = {
    val notes = listOf("A", "B-", "B", "C", "C#", "D", "E-", "E", "F", "F#", "G", "G#")
    val completeNotesList = (0..8).flatMap { notes }.subList(0, 88)
    val octavesList = listOf(
        "0".repeat(3),
        "1".repeat(12),
        "2".repeat(12),
        "3".repeat(12),
        "4".repeat(12),
        "5".repeat(12),
        "6".repeat(12),
        "7".repeat(12),
        "8".repeat(1)
    )
    val octavesListSplitted = octavesList.flatMap { it.toCharArray().toList() }
//    val completeNotesList = octavesList.flatMap { octaveN -> notes.map { note -> note + octaveN } }
    val completeNotesListWithOctaves =
        completeNotesList.zip(octavesListSplitted) { first, second -> first + second }
    val intPitch = 21..108
    val notePitchPairs = completeNotesListWithOctaves.zip(intPitch)
    val mapNoteToInt = notePitchPairs.associateBy({ it.first }, { it.second })
    mapNoteToInt
}.invoke()

//fun createMidiMap(): Map<String, Int> {
//    val notes = listOf("A", "B-", "B", "C", "C#", "D", "E-", "E", "F", "F#", "G", "G#")
//    val completeNotesList = (0..8).flatMap{notes}.subList(0, 88)
//    val octavesList = listOf("0".repeat(3), "1".repeat(12), "2".repeat(12), "3".repeat(12), "4".repeat(12), "5".repeat(12), "6".repeat(12), "7".repeat(12), "8".repeat(1))
//    val octavesListSplitted = octavesList.flatMap { it.toCharArray().toList() }
////    val completeNotesList = octavesList.flatMap { octaveN -> notes.map { note -> note + octaveN } }
//    val completeNotesListWithOctaves = completeNotesList.zip(octavesListSplitted){first, second -> first+second}
//    val intPitch = 21..108
//    val notePitchPairs = completeNotesListWithOctaves.zip (intPitch)
//    val mapNoteToInt = notePitchPairs.associateBy ( {it.first}, {it.second} )
//    return mapNoteToInt
//}
fun Note.extractDuration(): String {
    return if (this.takeLast(1) == ".") {
        this.takeLast(2)
    } else {
        this.takeLast(1)
    }
}

fun Note.extractName(): Note = if (this.elementAt(2) == '-' || this.elementAt(2) == '#') {
    this.take(3)
} else {
    this.take(2)
}

fun Pattern.saveAsMidi(applicationContext: Context, filename: String) {
    val musicStringList = this.musicString.split(" ")
    val tempoTrack = MidiTrack()
    val noteTrack = MidiTrack()
    val ts = TimeSignature()
    val channel = 0
    var tick = 0L
    musicStringList.forEachIndexed { index, note ->
        val pitch: Int = midiMap[note.extractName()] ?: error("Null value in midiMap")
        val duration = durationMap[note.extractDuration()] ?: error("No entry for duration string!")
        val velocity = 100
        noteTrack.insertNote(channel, pitch, velocity, tick, duration)
        tick += duration
    }
    ts.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION)

    val tracks: MutableList<MidiTrack> = ArrayList()
    tracks.add(tempoTrack)
    tracks.add(noteTrack)

    val midi = MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks)

// 4. Write the MIDI data to a file

// 4. Write the MIDI data to a file
    val cw = ContextWrapper(applicationContext)
    val directory = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
//    val pattern: Pattern = Pattern("C4")
    val output = File(directory, filename)
    try {
        midi.writeToFile(output)
    } catch (e: IOException) {
        System.err.println(e)
    }
}

//fun saveAsMidi(applicationContext: Context, filename: String) {
//    // 1. Create some MidiTracks
//
//    // 1. Create some MidiTracks
//    val tempoTrack = MidiTrack()
//    val noteTrack = MidiTrack()
//
//// 2. Add events to the tracks
//// Track 0 is the tempo map
//
//// 2. Add events to the tracks
//// Track 0 is the tempo map
//    val ts = TimeSignature()
//    ts.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION)
//
//    val tempo = Tempo()
//    tempo.setBpm(228F)
//
//    tempoTrack.insertEvent(ts)
//    tempoTrack.insertEvent(tempo)
//
//// Track 1 will have some notes in it
//
//// Track 1 will have some notes in it
//    val NOTE_COUNT = 80
//    val notes = listOf("C5", "D5", "E5", "F5", "G5", "A5", "B5", "C6")
//    val channel = 0
//    var tick = 0L
//    notes.forEachIndexed { index, note ->
//        val pitch: Int = midiMap[note] ?: error("Null value in midiMap")
//        val duration = durationMap[durationStr] ?: error("No entry for duration string!")
//        val velocity = 100
//        noteTrack.insertNote(channel, pitch, velocity, tick, duration)
//        tick += duration
//    }
//
//// 3. Create a MidiFile with the tracks we created
//
//// 3. Create a MidiFile with the tracks we created
//    val tracks: MutableList<MidiTrack> = ArrayList()
//    tracks.add(tempoTrack)
//    tracks.add(noteTrack)
//
//    val midi = MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks)
//
//// 4. Write the MIDI data to a file
//
//// 4. Write the MIDI data to a file
//    val cw = ContextWrapper(applicationContext)
//    val directory = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
////    val pattern: Pattern = Pattern("C4")
//    val output = File(directory, filename)
//    try {
//        midi.writeToFile(output)
//    } catch (e: IOException) {
//        System.err.println(e)
//    }
//}

fun createNote(pitchString: String) {

}