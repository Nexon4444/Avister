package com.avister.ml.models

import android.content.Context
import android.os.SystemClock
import android.util.Log
import android.util.Size
import com.avister.utilities.ConfigurationManager
import com.beust.klaxon.Klaxon
import org.jfugue.midi.MidiFileManager
import org.jfugue.pattern.Pattern
import org.tensorflow.lite.Delegate
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.Tensor
import org.tensorflow.lite.gpu.GpuDelegate
import org.tensorflow.lite.nnapi.NnApiDelegate
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.Closeable
import java.io.File
import java.nio.FloatBuffer
import java.nio.MappedByteBuffer


class MusicGeneratorAndroid(
    context: Context,
    modelFileName: String,
    modelType: ModelType,
    device: Device = Device.CPU
) :
    MusicGenerator(
        createNotesArrayFromContext(context),
        getModelMappedByteBuffer(
            context, modelFileName
        ),
        getConfigurationManager(context, "numThreads").toInt(),
        modelType
    ) {
    val configurationManager: ConfigurationManager = ConfigurationManager(context)
    override val numThreads: Int = Integer.parseInt(configurationManager["numThreads"])
    val modelFileName: String = configurationManager["modelFileName"]
    val notesArray: List<String>

    init {
        val notesString = context.assets.open(configurationManager["notes"]).bufferedReader()
            .use { it.readText() }
        notesArray = Klaxon().parseArray(notesString)!!
    }

    val interpreter: Interpreter = Interpreter(
        getModelMappedByteBuffer(
            context, getConfigurationManager(context, "modelFileName")
        ),
        Interpreter.Options().apply {
            setNumThreads(numThreads)
            delegate?.let { addDelegate(it) }
        })

    val inputTensor: Tensor = interpreter.getInputTensor(0)
    val outputTensor: Tensor = interpreter.getOutputTensor(0)

    fun close() {
        interpreter.close()
        if (delegate is Closeable) {
            delegate.close()
        }
    }

    private val outputBuffer: TensorBuffer =
        TensorBuffer.createFixedSize(outputTensor.shape(), outputTensor.dataType())

    val delegate: Delegate? = when (device) {
        Device.CPU -> null
        Device.NNAPI -> NnApiDelegate()
        Device.GPU -> GpuDelegate()
    }

    val inputShape: Size = with(inputTensor.shape()) { Size(this[2], this[1]) }

    val inputMatrix = IntArray(inputShape.height * inputShape.width)

    fun createFloatBuffer(floatArray: FloatArray): FloatBuffer = FloatBuffer.wrap(floatArray)

    fun generateMusic(size: Int, sead: FloatArray): MutableList<String> {
        var predictionInput = sead
        val result: MutableList<String> = List(size) { "" }.toMutableList()
        for (i in 0 until size) {
            val (noteFloat, note) = generateNote(predictionInput)
            val nextPredictionInput =
                predictionInput.slice(1 until predictionInput.size).toFloatArray() + noteFloat
            predictionInput = nextPredictionInput
            result[i] = note
        }
        return result//(0..size).map { generateNoteArray(createFloatBuffer(initialArray)) }
    }

    fun generateMidiPattern(size: Int, sead: FloatArray): Pattern {
        val generatedMusic = generateMusic(size, sead)
        val midiString = createMidiPattern(generatedMusic)
        return Pattern(midiString)
    }

    fun createMidiFile(noteList: List<String>, pathname: String) {
//        val player = Player()
        val patternString = createMidiPattern(noteList)
        val pattern = Pattern(patternString)
//        val  x= pattern.z
//        val midiFileManager = MidiFileManager()
        try {
            MidiFileManager.savePatternToMidi(pattern, File(pathname))
        } catch (e: Exception) {
            throw e
        }
    }

    fun convertIntNoteToFloat(note: Int) {

    }

    fun generateNote(inputArray: FloatArray): Pair<Float, String> {
        val inputBuffer = createFloatBuffer(inputArray)
        val prediction = generateNoteArray(inputBuffer)
        val resultIndex: Int = prediction.argMax()
        val result: String? = intToNoteMap[resultIndex]

        return resultIndex.toFloat() / nVocab.toFloat() to result!!
    }

    fun generateNoteArray(input: FloatBuffer): FloatArray {
        val start = SystemClock.uptimeMillis()
        val x = outputTensor.dataType()
        val y = outputTensor.shape()
        val output = createFloatBuffer(FloatArray(outputTensor.shape()[1]))
        interpreter.run(input, output)

//        interpreter.run(inputBuffer, outputBuffer.buffer.rewind())
        val end = SystemClock.uptimeMillis()
        val timeCost = end - start

        val generated = output.array()
//        val top = probs.argMax()
        println(outputTensor.toString())
        Log.v(
            LOG_TAG,
            "generated(): timeCost = $timeCost, generated = ${generated.contentToString()}"
        )
        return generated
    }

    companion object {

        private val LOG_TAG: String = MusicGeneratorAndroid::class.java.simpleName

        fun createNotesArrayFromContext(context: Context): List<String> {
            val configurationManager = ConfigurationManager(context)
            val notesString: String =
                context.assets.open(configurationManager["notes"]).bufferedReader()
                    .use { it.readText() }
            val notesArray: List<String> = Klaxon().parseArray(notesString)
                ?: throw IllegalArgumentException("notesString can't be null!")
            return notesArray
        }

        fun getModelMappedByteBuffer(context: Context, modelFileName: String): MappedByteBuffer {
            return FileUtil.loadMappedFile(context, modelFileName)
        }

        fun getConfigurationManager(context: Context, key: String): String {
            val configurationManager = ConfigurationManager(context)
            return configurationManager[key]
        }


    }
}

fun FloatArray.argMax(): Int {
    return this.withIndex().maxBy { it.value }?.index
        ?: throw IllegalArgumentException("Cannot find arg max in empty list")
}