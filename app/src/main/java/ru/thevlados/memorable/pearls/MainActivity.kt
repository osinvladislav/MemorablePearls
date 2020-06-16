package ru.thevlados.memorable.pearls

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.main_fragment.*
import ru.thevlados.memorable.pearls.ui.start.StartActivity


class MainActivity : AppCompatActivity() {
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = getSharedPreferences("settings", MODE_PRIVATE)
        if (!pref.contains("name")) {
            this.finish()
            startActivity(Intent(this, StartActivity::class.java))
        }
        when (pref.getString("theme", "")) {
            "dark" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                if (Build.VERSION.SDK_INT >= 21) {
                    val window: Window = this.window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = this.resources.getColor(R.color.colorOnSecondary)
                }
            }
            "light" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                if (Build.VERSION.SDK_INT >= 21) {
                    val window: Window = this.window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = this.resources.getColor(R.color.colorPrimaryVariant)
                }
            }
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

}


data class lang (val app_name: String, val start: start, val main: main, val archive: archive, val settings: settings, val tests: tests, val test: test, val trane: trane, val finish: finish)

data class start (val text_hello: String,
                  val edit_name: String,
                  val text_translate: String,
                  val radio_rst: String,
                  val radio_bti: String,
                  val radio_nrp: String,
                  val radio_cslav: String,
                  val radio_srp: String,
                  val radio_cass: String,
                  val radio_ibl: String,
                  val radio_kjv: String,
                  val radio_nkjv: String,
                  val radio_nasb: String,
                  val radio_csb: String,
                  val radio_esv: String,
                  val radio_gnt: String,
                  val radio_gw: String,
                  val radio_nirv: String,
                  val radio_niv: String,
                  val radio_nlt: String,
                  val radio_ubio: String,
                  val radio_ukrk: String,
                  val radio_utt: String,
                  val radio_bbl: String,
                  val text_lang: String,
                  val radio_ru: String,
                  val radio_en: String,
                  val radio_ua: String,
                  val radio_by: String,
                  val switch_dark: String,
                  val btn_enter: String)

data class main (val text_action_bar: String,
                 val text_date: String,
                 val text_verse: String,
                 val text_actions: String,
                 val text_open_now: String,
                 val text_trans_now: String,
                 val text_check_know: String,
                 val text_switch_translate: String,
                 val text_morning: String,
                 val text_night: String,
                 val text_evening: String,
                 val text_day: String)

data class archive (val text_action_bar: String,
                    val season: String,
                    val year: String,
                    val quart: String,
                    val week: String,
                    val error: String,
                    val copy: String,
                    val listen: String,
                    val copy_notify: String,
                    val text_music: String)

data class settings (val text_action_bar: String)

data class tests (val text_action_bar: String,
                  val text_title: String,
                  val text_trane: String,
                  val text_trane_desc: String,
                  val text_test: String,
                  val text_test_desc: String,
                  val text_title_trane: String,
                  val text_desc_trane: String,
                  val text_select_year_trane: String,
                  val text_select_quart_trane: String,
                  val btn_trane: String,
                  val text_title_test: String,
                  val text_desc_test: String,
                  val text_select_year_test: String,
                  val text_select_quart_test: String,
                  val btn_test: String)

data class test (val edit_verse: String,
                 val edit_verse_hint: String,
                 val btn_check: String,
                 val btn_next: String,
                 val text_end_headline: String,
                 val text_end_desc: String,
                 val week: String,
                 val all_right: String,
                 val all_error: String,
                 val apply_exit: String,
                 val yes: String,
                 val no: String)

data class trane (val btn_no: String,
                  val btn_yes: String,
                  val text_end_headline: String,
                  val text_end_desc: String,
                  val apply_exit: String)

data class finish (val btn_again: String,
                   val btn_end: String)

