package me.cassiano.canvas

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.cassiano.canvas.charts.ChartsFragment

class MainActivity : AppCompatActivity(), AppNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        if (savedInstanceState == null)
            showHome()
    }

    override fun showHome() {
        val manager = supportFragmentManager
        manager.runTransactionAsync {
            replace(R.id.containerFragment, HomeFragment())
        }
    }

    override fun showCharts() {
        val manager = supportFragmentManager
        manager.runTransactionAsync {
            replace(R.id.containerFragment, ChartsFragment())
            addToBackStack("charts")
        }
    }
}

interface AppNavigator {
    fun showHome()
    fun showCharts()
}