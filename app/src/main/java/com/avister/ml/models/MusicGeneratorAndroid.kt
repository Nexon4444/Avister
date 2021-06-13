package com.avister.ml.models

import android.content.Context
import com.avister.utilities.ConfigurationManager
import com.beust.klaxon.Klaxon
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import java.nio.MappedByteBuffer

class MusicGeneratorAndroid(context: Context, device: Device = Device.CPU) :
    MusicGenerator(
        createNotesArrayFromContext(context),
        getModelMappedByteBuffer(
            context, getConfigurationManager(context, "modelFileName")
        ),
        getConfigurationManager(context, "numThreads").toInt()

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



    companion object {
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
