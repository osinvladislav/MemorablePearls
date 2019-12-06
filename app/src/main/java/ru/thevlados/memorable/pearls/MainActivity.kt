package ru.thevlados.memorable.pearls

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_fragment.*
import ru.thevlados.memorable.pearls.ui.start.StartActivity


class MainActivity : AppCompatActivity() {
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = getSharedPreferences("settings", MODE_PRIVATE)
        if (!pref.contains("name")) {
            startActivity(Intent(this, StartActivity::class.java))
        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_main, R.id.navigation_archive, R.id.navigation_tests, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun resetActionBar(childAction: Boolean) {
        if (childAction) { // [Undocumented?] trick to get up button icon to show
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

}
