package com.avister.ml.models

import android.graphics.Bitmap
import android.util.Size
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.Delegate
//import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.Tensor
import org.tensorflow.lite.gpu.GpuDelegate
import org.tensorflow.lite.nnapi.NnApiDelegate
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
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


    val interpreter: Interpreter = Interpreter(
        mappedByteBuffer,
        Interpreter.Options().apply {
            setNumThreads(numThreads)
            delegate?.let { addDelegate(it) }
        })
    private val inputTensor: Tensor = interpreter.getInputTensor(0)

    private val outputTensor: Tensor = interpreter.getOutputTensor(0)

    val delegate: Delegate? = when (device) {
        Device.CPU -> null
        Device.NNAPI -> NnApiDelegate()
        Device.GPU -> GpuDelegate()
    }

    private val inputShape: Size = with(inputTensor.shape()) { Size(this[2], this[1]) }

    private val imagePixels = IntArray(inputShape.height * inputShape.width)

    private val imageBuffer: ByteBuffer =
        ByteBuffer.allocateDirect(4 * inputShape.height * inputShape.width).apply {
            order(ByteOrder.nativeOrder())
        }

    private val outputBuffer: TensorBuffer =
        TensorBuffer.createFixedSize(outputTensor.shape(), outputTensor.dataType())

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
//    }

    fun close() {
        interpreter.close()
        if (delegate is Closeable) {
            delegate.close()
        }
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap) {
        imageBuffer.rewind()
        bitmap.getPixels(imagePixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        for (i in 0 until inputShape.width * inputShape.height) {
            val pixel: Int = imagePixels[i]
            imageBuffer.putFloat(convertPixel(pixel))
        }
    }

    private fun convertPixel(color: Int): Float {
        return (255 - ((color shr 16 and 0xFF) * 0.299f
                + (color shr 8 and 0xFF) * 0.587f
                + (color and 0xFF) * 0.114f)) / 255.0f
    }

    companion object {
        private val LOG_TAG: String = MnistClassifier::class.java.simpleName
//        private const val MODEL_FILE_NAME: String = "mnist.tflite"

        fun generateRandomSequence(size: Int, max: Int) = List(100) {
            Random.nextInt()
        }

        fun List<Int>.toOneHot(): List<List<Int>> {
            val max = this.max() //maxOrNull()

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