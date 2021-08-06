package com.avister.midiGeneration.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avister.R
import com.avister.midiGeneration.GeneratorConfigInner

class ComplexGeneratorFragment : AbstractGeneratorFragment() {
    override fun createGeneratorConfigInner(): GeneratorConfigInner {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_complex_generator, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ComplexGeneratorFragment().apply {
            }
    }
}