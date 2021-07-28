package com.avister.ml.models

import android.graphics.Bitmap
import android.util.Size
import java.io.Closeable
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import kotlin.random.Random

abstract class AbstractModel(mappedByteBuffer: MappedByteBuffer, device: Device = Device.CPU) {

    // = ConfigurationManager(context
//    init {
////        val json = configFile.readText()
////        val configuration = Klaxon().parse<Configuration>(json)
//
//        numThreads = Integer.parseInt(configurationManager["numThreads"])
//        modelName = configurationManager["modelFileName"]
//
////        numThreads =
//        // Create the ImageClassifier instance.
////        val options = ImageClassifierOptions.builder()
////            .setMaxResults(1)
////            .setNumThreads(numThreads)
////            .build()
////        val options = ImageClassifierOptions.builder().setMaxResults(1).build()
////        val context = Context.INPUT_SERVICE
////        val imageClassifier = ImageClassifier.createFromFile(File(modelPath))//
////        val imageClassifier = createFromFileAndOptions(context, modelPath, options)
//    }

    //    abstract val configurationManager: ConfigurationManager
    abstract val numThreads: Int
//    abstract val modelFileName: String





    fun convertPixel(color: Int): Float {
        return (255 - ((color shr 16 and 0xFF) * 0.299f
                + (color shr 8 and 0xFF) * 0.587f
                + (color and 0xFF) * 0.114f)) / 255.0f
    }
    //    fun classify(image: Bitmap): Recognition {
//        convertBitmapToByteBuffer(image)
//
//        val start = SystemClock.uptimeMillis()
//        interpreter.run(imageBuffer, outputBuffer.buffer.rewind())
//        val end = SystemClock.uptimeMillis()
//        val timeCost = end - start
//
//        val probs = outputBuffer.floatArray
//        val top = probs.argMax()
//        Log.v(LOG_TAG, "classify(): timeCost = $timeCost, top = $top, probs = ${probs.contentToString()}")
//        return Recognition(top, probs[top], timeCost)
    companion object {
        private val LOG_TAG: String = MnistClassifier::class.java.simpleName
        val ran = Random.Default
        fun generateRandomSequence(size: Int, max: Int) = List(100) {
            Random.nextInt()
        }


        fun List<Int>.toOneHot(): List<List<Int>> {
            val max = this.maxOrNull()

            return if (max != null) {
                listOf(this.map {
                    listOf(
                        List(it) { 0 },
                        listOf(1),
                        List(max - it) { 0 }).flatten()
                }).flatten()
            } else {
                throw Exception(IllegalArgumentException("Max == null!"))
            }

        }
    }
}