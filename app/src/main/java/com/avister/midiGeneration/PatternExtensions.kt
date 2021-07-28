package com.avister.midiGeneration

//import com.avister.player.MidiFile
//import com.avister.player.MidiTrack
//import com.avister.player.TimeSignature
//import org.jfugue.Tempo
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.leff.midi.MidiFile
import com.leff.midi.MidiTrack
import com.leff.midi.event.meta.TimeSignature
import org.jfugue.pattern.Pattern
import java.io.File
import java.io.IOException
typealias Music = String
val durationMap = run {
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
}

val midiMap: Map<String, Int> = run {
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
//    val completeNotesList = octavesList.flatMap { octaveN -> notes.map { note -> note + octaveN } }
    mapNoteToInt
}

fun Music.extractNotesFromChord() = this.split("+")

fun Music.extractDuration(): Music {
    return if (this.takeLast(1) == ".") {
        this.takeLast(2)
    } else {
        this.takeLast(1)
    }
}

fun Music.extractName(): Music = if (this.elementAt(1) == '-' || this.elementAt(1) == '#') {
    this.take(3)
} else {
    this.take(2)
}

fun Pattern.saveAsMidi(applicationContext: Context, output: File, filename: String): File {
    val musicStringList = this.toString().split(" ")
    val tempoTrack = MidiTrack()
    val noteTrack = MidiTrack()
    val ts = TimeSignature()
    val channel = 0
    var tick = 0L
    musicStringList.forEachIndexed { index, musicString ->
        val noteList = musicString.extractNotesFromChord()
        val pitches = noteList.map { midiMap[it.extractName()] ?: error("Null value in midiMap")}
        val durations = noteList.map { durationMap[it.extractDuration()] ?: error("No entry for duration string!")}
        val maxDuration = durations.maxOrNull()
        val pitchsAndDurations = pitches.zip(durations)
//        val pitch: Int = midiMap[musicString.extractName()] ?: error("Null value in midiMap")
//        val duration = durationMap[musicString.extractDuration()] ?: error("No entry for duration string!")
        val velocity = 100
        pitchsAndDurations.map { (pitch, duration) -> noteTrack.insertNote(channel, pitch, velocity, tick, duration)}
        tick += maxDuration!!
    }
    ts.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION)

    val tracks: MutableList<MidiTrack> = ArrayList()
    tracks.add(tempoTrack)
    tracks.add(noteTrack)

    val midi = MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks)

    val outUri = Uri.parse(output.toString() + File.separator + filename)
    val outFile = File(outUri.toString())
    try {
        outFile.createNewFile()
        midi.writeToFile(outFile)
    } catch (e: IOException) {
        throw e
//        System.err.println(e)
    }
    return output
}