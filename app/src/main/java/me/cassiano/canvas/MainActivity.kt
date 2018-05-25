package me.cassiano.canvas

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        showHome()
    }

    private fun showHome() {
        val manager = supportFragmentManager
        val homeFragment = HomeFragment()
        manager.runTransactionAsync {
            replace(R.id.containerFragment, homeFragment)
        }
    }
}
