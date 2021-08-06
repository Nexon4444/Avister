package com.avister.midiGeneration.fragments

import androidx.fragment.app.Fragment
import com.avister.midiGeneration.GeneratorConfigInner

abstract class AbstractGeneratorFragment: Fragment() {
    abstract fun createGeneratorConfigInner(): GeneratorConfigInner
}