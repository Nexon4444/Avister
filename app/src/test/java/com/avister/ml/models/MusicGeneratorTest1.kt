package com.avister.ml.models

import org.junit.Test
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

internal class MusicGeneratorTest {

    val notesList = listOf("c", "d", "e", "f", "g", "h")
    val musicGenerator = MusicGenerator(notesList, loadFileToMappedBuffer("C:\\Users\\PC\\Documents\\Programowanie\\Studia\\PracaMagisterska\\Projekt\\Avister\\app\\src\\main\\assets\\mnist.tflite"), 4)

    fun loadFileToMappedBuffer(fileName: String): MappedByteBuffer {
        val path = Paths.get(fileName)
        val fileChannel: FileChannel = Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ)) as FileChannel
        val mappedByteBuffer: MappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size())
        return mappedByteBuffer
//        val charBuffer = Charset.forName("UTF-8").decode(mappedB` yteBuffer)
    }

//    @Test
//    fun test() {
//
//        val mappedBuffer = loadFileToMappedBuffer("C:\\Users\\PC\\Documents\\Programowanie\\Studia\\PracaMagisterska\\Projekt\\Avister\\app\\src\\main\\assets\\mnist.tflite")
//        val x = 3
//    }


    @Test
    fun prepareSequences() {
        musicGenerator.prepareSequences()
    }

    @Test
    fun getNotesArray() {
    }
}