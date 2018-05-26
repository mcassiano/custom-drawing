package me.cassiano.canvas

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class HomeFragment : Fragment() {

    private lateinit var navigator: AppNavigator
    private lateinit var chartsButton: Button

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context !is AppNavigator)
            throw IllegalStateException()

        navigator = context
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
                R.layout.fragment_home,
                container,
                false
        )
    }

    override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?
    ) {
        chartsButton = view.findViewById(R.id.chartsButton)
        chartsButton.setOnClickListener { navigator.showCharts() }
    }
}