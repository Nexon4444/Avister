package com.avister.ml.models

import android.content.Context
import android.os.ParcelFileDescriptor.open
import android.system.Os.open
import com.avister.utilities.ConfigurationManager
import com.avister.utilities.NoteValue
import com.beust.klaxon.*
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.Tensor
import org.tensorflow.lite.support.common.FileUtil
import java.io.StringReader
import java.nio.channels.AsynchronousFileChannel.open
import java.nio.channels.AsynchronousServerSocketChannel.open
import kotlin.random.Random.Default.nextInt
import java.nio.MappedByteBuffer

//import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

//import kotlin.Random.Default.nextInt

open class MusicGenerator(
    val notesList: List<String>,
    mappedByteBuffer: MappedByteBuffer,
    override val numThreads: Int,
    device: Device = Device.CPU
) : AbstractModel(mappedByteBuffer, device) {

    fun prepareSequences() {
        val pitchNames = notesList.toSortedSet()
            ?: throw NullPointerException("Notes array empty, check if note's file exists")
        val nVocab = pitchNames.size
        val noteToIntMap = pitchNames.mapIndexed { index, s -> index to s }.groupBy{it.first}
        val x = 3
        //TODO Prepare sequences
    }


//        fun generateRandomSequenceNumpy(size: Int, max: Int) = randint<Int>(0, size)

}
//    fun generateMusic():,

