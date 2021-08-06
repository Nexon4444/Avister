package com.avister.midiGeneration

import android.util.Rational

data class GeneratorConfig(val duration: Rational, val barsNum: Int) {
    companion object {
        fun generatorConfigFactoryCreate(generatorConfigInner: GeneratorConfigInner): GeneratorConfig {
            return GeneratorConfig(generatorConfigInner.duration, generatorConfigInner.barsNum)
        }
    }
}
data class GeneratorConfigInner(val duration: Rational, val barsNum: Int)
