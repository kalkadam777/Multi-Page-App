package com.example.multi_pageapp

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.multi_pageapp.receivers.AirplaneModeReceiver

class MainActivity : AppCompatActivity() {
    private lateinit var airplaneModeReceiver: AirplaneModeReceiver

    override fun onResume() {
        super.onResume()
        airplaneModeReceiver = AirplaneModeReceiver()
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneModeReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(airplaneModeReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host_fragment)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment)
        if (navController.currentDestination?.id == R.id.mainFragment) {
            super.onBackPressed()
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
