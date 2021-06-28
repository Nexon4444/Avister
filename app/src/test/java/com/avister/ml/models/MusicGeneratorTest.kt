package com.avister.ml.models

import org.junit.Test
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

internal class MusicGeneratorTest {

    val notesList = listOf("C1", "D1", "E1", "F1", "G1", "H1")
    val musicGenerator = MusicGenerator(
        notesList,
        loadFileToMappedBuffer("C:\\Users\\PC\\Documents\\Programowanie\\Studia\\PracaMagisterska\\Projekt\\Avister\\app\\src\\main\\assets\\mnist.tflite"),
        4,
        ModelType.CPC
    )
    val sequenceLength = 3
    fun loadFileToMappedBuffer(fileName: String): MappedByteBuffer {
        val path = Paths.get(fileName)
        val fileChannel: FileChannel =
            Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ)) as FileChannel
        val mappedByteBuffer: MappedByteBuffer =
            fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size())
        return mappedByteBuffer
//        val charBuffer = Charset.forName("UTF-8").decode(mappedByteBuffer)
    }

//    @Test
//    fun test() {
//
//        val mappedBuffer = loadFileToMappedBuffer("C:\\Users\\PC\\Documents\\Programowanie\\Studia\\PracaMagisterska\\Projekt\\Avister\\app\\src\\main\\assets\\mnist.tflite")
//        val x = 3
//    }


    @Test
    fun prepareSequences() {
        val (preparedInput, preparedInputNormalized) = musicGenerator.prepareSequences(
            notesList,
            sequenceLength
        )
        print(preparedInput + preparedInputNormalized)
        assert(preparedInput.all { it.count { true } == sequenceLength })
    }


    @Test
    fun getNotesArray() {

    }

    @Test
    fun generateRandomArrayTest2() {
        val x = MusicGenerator.generateRandomArray(listOf(2, 2))
        assert(x is Array<*>)
    }



//    @Test
//    fun generateRandomArrayTest1() {
//        val x = MusicGenerator.generateRandomArray(listOf(1))
//        assert(x is Array)
//    }
}