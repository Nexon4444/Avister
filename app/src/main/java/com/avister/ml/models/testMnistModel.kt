package com.ml.models

import android.os.Build
import androidx.annotation.RequiresApi
import org.tensorflow.lite.Interpreter
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun loadFileToMappedBuffer(fileName: String): MappedByteBuffer {
    val path = Paths.get(fileName)
    val fileChannel: FileChannel =
        Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ)) as FileChannel
    val mappedByteBuffer: MappedByteBuffer =
        fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size())
    return mappedByteBuffer
}

@RequiresApi(Build.VERSION_CODES.O)
fun main() {

    val interpreter: Interpreter = Interpreter(
        loadFileToMappedBuffer("C:\\Users\\PC\\Documents\\mnist.tflite")
    )
}


