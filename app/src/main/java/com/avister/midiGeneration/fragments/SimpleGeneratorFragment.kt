package com.avister.midiGeneration.fragments

import android.os.Bundle
import android.util.Rational
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.avister.R
import com.avister.midiGeneration.GeneratorConfigInner
import com.avister.utilities.ConfigurationManager


/**
 * A simple [Fragment] subclass.
 * Use the [SimpleGeneratorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SimpleGeneratorFragment : AbstractGeneratorFragment() {
    lateinit var durationSpinner: Spinner
    lateinit var barsSpinner: Spinner
    lateinit var durations2Rational: Map<Int, Rational>
    lateinit var numBars2Int: Map<Int, Int>

    override fun createGeneratorConfigInner(): GeneratorConfigInner {
        return GeneratorConfigInner(createDurationRational(), createBarsNum())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val configurationManager = ConfigurationManager(requireContext())
        var view = inflater.inflate(R.layout.fragment_simple_generator, container, false)
        initializeMaps(configurationManager)

        val (durationSpinnerTemp, viewTempWithDuration) = createSpinner(
            view,
            R.id.spinner_durations,
            configurationManager["durations"]
        )
        durationSpinner = durationSpinnerTemp

        val (barsSpinnerTemp, viewTempWithBar) = createSpinner(
            viewTempWithDuration,
            R.id.spinner_bars,
            configurationManager["numBars"]
        )
        barsSpinner = barsSpinnerTemp

        return viewTempWithBar
    }

    private fun initializeMaps(configurationManager: ConfigurationManager) {
        durations2Rational = configurationManager["durations"].mapIndexed { indx, dur ->
            indx to Rational.parseRational(dur)
        }.toMap()
        numBars2Int =
            configurationManager["numBars"].mapIndexed { indx, barNum -> indx to barNum.toInt() }
                .toMap()
    }

    fun createSpinner(view: View, viewId: Int, items: List<String>): Pair<Spinner, View> {
        val spinner: Spinner = view.findViewById(viewId)
        val adapter =
            ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, items)
        spinner.adapter = adapter
        return spinner to view
    }

    fun createDurationRational(): Rational {
        return durations2Rational[durationSpinner.selectedItemPosition]!!
    }

    fun createBarsNum(): Int {
        return numBars2Int[barsSpinner.selectedItemPosition]!!
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SimpleGeneratorFragment().apply {
            }
    }


}