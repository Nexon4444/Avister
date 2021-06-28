package com.avister.utilities


import android.content.Context
import android.content.res.Resources
import com.avister.R
import java.io.InputStream
import java.util.*


class ConfigurationManager(context: Context) {
    private val properties = Properties()

    init {
        val resources: Resources = context.resources
        val rawResource: InputStream = resources.openRawResource(R.raw.config)


        properties.load(rawResource)
    }

    operator fun get(configElement: String): String {
        when (val prop = properties[configElement]) {
            is String -> return prop
            else -> throw IllegalArgumentException("Config Element " + configElement + " is not reachable")
        }
    }
//    fun loadConfiguration() {
//
//    }
//
//    fun getConfigurationElement(confName: String) {
//        properties[]
//    }
}