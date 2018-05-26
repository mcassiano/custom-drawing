package me.cassiano.canvas.charts

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import me.cassiano.canvas.R
import java.util.*
import kotlin.math.roundToInt

class ChartsFragment : Fragment() {

    private val colors = arrayOf(Color.BLUE, Color.CYAN, Color.YELLOW, Color.RED)
    private val rand = Random()
    private val bars = mutableListOf<Bar>()

    private lateinit var histogramView: HistogramChart
    private lateinit var addButton: Button

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_charts, container, false)
    }

    override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?
    ) {
        histogramView = view.findViewById(R.id.histogramChart)
        addButton = view.findViewById(R.id.button2)
        addButton.setOnClickListener { addNewBar() }
    }


    private fun addNewBar() {
        val valueDp = 40 + Math.abs(rand.nextInt()) % 100f
        val color = colors[Math.abs(rand.nextInt()) % colors.size]
        val newBar = Bar("", "", valueDp, "${valueDp.roundToInt()} elements", color)
        bars.add(newBar)
        histogramView.bars = bars
    }


}