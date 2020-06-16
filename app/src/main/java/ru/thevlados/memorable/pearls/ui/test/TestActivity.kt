package ru.thevlados.memorable.pearls.ui.test

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_test.*
import ru.thevlados.memorable.pearls.R
import ru.thevlados.memorable.pearls.archive
import ru.thevlados.memorable.pearls.lang
import ru.thevlados.memorable.pearls.ui.menu.archive.week
import ru.thevlados.memorable.pearls.ui.menu.archive.year
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


class TestActivity : AppCompatActivity() {
    var trueVerse: String = ""
    var numOfVerseNow = 0
    lateinit var pref: SharedPreferences
    private var translate = ""
    private lateinit var randomValues: MutableList<Int>
    var year = ""
    lateinit var test: test


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        year = intent.getStringExtra("year")
        val season = intent.getStringExtra("season")
        val quart = intent.getStringExtra("quart")
        pref = getSharedPreferences("settings", MODE_PRIVATE)
        when (pref.getString("theme", "")) {
            "dark" -> {
                if (Build.VERSION.SDK_INT >= 21) {
                    val window: Window = this.window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = this.resources.getColor(R.color.colorOnSecondary)
                }
            }
            "light" -> {
                if (Build.VERSION.SDK_INT >= 21) {
                    val window: Window = this.window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = this.resources.getColor(R.color.colorPrimaryVariant)
                }
            }
        }

        supportActionBar?.title = "$quart ${initLang(returnLang(pref.getString("lang", "")!!)).quart}, $year ${initLang(returnLang(pref.getString("lang", "")!!)).year}"
        val jsonString = application.assets.open("$season.json").bufferedReader().use {
            it.readText()
        }

        test = returnTest(returnLang(pref.getString("lang", "")!!))

        val quarter: Array<year> =
            Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
        val quartNow = quarter[quart.toInt()-1]

        randomValues = returnRandomListOfVerse()

        updateVerse(quartNow.weeks[randomValues[numOfVerseNow]-1])

        btn_next.setOnClickListener {
            numOfVerseNow++
            if (numOfVerseNow < 12) {
                updateVerse(quartNow.weeks[randomValues[numOfVerseNow]-1])
            } else {
                scroll_question.visibility = View.GONE
                scroll_finish.visibility = View.VISIBLE
                supportActionBar?.subtitle = ""
                btn_again.setOnClickListener {
                    finish()
                    val intent = Intent(this, TestActivity::class.java)
                    intent.putExtra("year", year)
                    intent.putExtra("season", season)
                    intent.putExtra("quart", quart)
                    startActivity(intent)
                }
                btn_end.setOnClickListener {
                    finish()
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun updateVerse(weeks: week) {
        layout_edit_verse.helperText = test.edit_verse_hint
        layout_edit_verse.setHelperTextTextAppearance(R.style.standart)
        edit_verse.text = null
        btn_next.isEnabled = false
        when (pref.getString("lang", "")) {
            "ru" -> {
                supportActionBar?.subtitle = weeks.verse.link_small_ru
                text_link_headline.text = weeks.verse.link_small_ru
            }
            "en" -> {
                supportActionBar?.subtitle = weeks.verse.link_small_en
                text_link_headline.text = weeks.verse.link_small_en
            }
            "ua" -> {
                supportActionBar?.subtitle = weeks.verse.link_small_ua
                text_link_headline.text = weeks.verse.link_small_ua
            }
            "by" -> {
                supportActionBar?.subtitle = weeks.verse.link_small_by
                text_link_headline.text = weeks.verse.link_small_by
            }
        }
        text_date_desc.text = weeks.num_week.toString() + " " + test.week + ", " + returnDates(weeks.num_week_in_year, year.toInt())[0] + " — " + returnDates(weeks.num_week_in_year, year.toInt())[1]
        var verse = when (pref.getString("translate_"+pref.getString("lang", ""), "")) {
            "rst" -> weeks.verse.RST
            "bti" -> weeks.verse.BTI
            "cass" -> weeks.verse.CASS
            "nrp" -> weeks.verse.NRP
            "cslav" -> weeks.verse.CSLAV
            "srp" -> weeks.verse.SRP
            "ibl" -> weeks.verse.IBL
            "kjv" -> weeks.verse.KJV
            "nkjv" -> weeks.verse.NKJV
            "nasb" -> weeks.verse.NASB
            "csb" -> weeks.verse.CSB
            "esv" -> weeks.verse.ESV
            "gnt" -> weeks.verse.GNT
            "gw" -> weeks.verse.GW
            "nirv" -> weeks.verse.NIRV
            "niv" -> weeks.verse.NIV
            "nlt" -> weeks.verse.NLT
            "ubio" -> weeks.verse.UBIO
            "ukrk" -> weeks.verse.UKRK
            "utt" -> weeks.verse.UTT
            "bbl" -> weeks.verse.BBL
            else -> weeks.verse.RST
        }
        trueVerse = verse.replace(" ", "").replace("ё", "е").replace("!", "").replace("?", "").replace(".", "").replace("«", "").replace("»", "").replace("(", "").replace(")", "").replace(",", "").replace(";", "").replace("-", "").replace("—", "").replace(":", "").toLowerCase()
        btn_check.setOnClickListener {
            checkVerse(edit_verse.text.toString().replace("ё", "е").replace(" ", "").replace("!", "").replace("?", "").replace(".", "").replace("«", "").replace("»", "").replace(",", "").replace(";", "").replace("-", "").replace("—", "").replace(":", "").toLowerCase())
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun returnDates(w: Int, year: Int): List<String> {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val c = Calendar.getInstance()
        val test = Calendar.getInstance()
        c.firstDayOfWeek = Calendar.SATURDAY
        test.firstDayOfWeek = Calendar.SATURDAY
        c.time = Date()
        test.time = Date()
        c.set(Calendar.YEAR, year)
        test.set(Calendar.YEAR, year)
        test.set(Calendar.DAY_OF_YEAR, 1)
        if (test.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            c.set(Calendar.WEEK_OF_YEAR, w)
        } else {
            c.set(Calendar.WEEK_OF_YEAR, w+1)
        }
        c.time
        val sD: Calendar = c.clone() as Calendar
        sD.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        val eD: Calendar = c.clone() as Calendar
        if (test.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            eD.set(Calendar.WEEK_OF_YEAR, w+1)
        }
        eD.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
        return listOf(sdf.format(sD.time), sdf.format(eD.time))
    }


    private fun checkVerse(getVerse: String) {
        println(getVerse)
        println(trueVerse)
        if (getVerse == trueVerse) {
            layout_edit_verse.helperText = test.all_right
            layout_edit_verse.setHelperTextTextAppearance(R.style.ok)
            btn_next.isEnabled = true
        } else {
            layout_edit_verse.helperText = test.all_error
            layout_edit_verse.setHelperTextTextAppearance(R.style.error)
        }
    }

    private fun returnRandomListOfVerse(): MutableList<Int> {
        val randomVerses = mutableListOf<Int>()
        var count = 0

        while (true) {
            if (count < 12) {
                val randomInt = Random.nextInt(1,13)
                var isHaveVal = false
                randomVerses.forEach {
                    if (it == randomInt) {
                        isHaveVal = true
                    }
                }
                if (!isHaveVal) {
                    randomVerses.add(count, randomInt)
                    count++
                }
            } else {
                break
            }
        }

        return randomVerses
    }

    private fun returnLang (string: String): String {
        return application.assets.open("lang/$string.json").bufferedReader().use {
            it.readText()
        }
    }

    private fun initLang(str: String): archive {
        val lang: lang = Gson().fromJson<lang>(str, lang::class.java)
        layout_edit_verse.hint = lang.test.edit_verse
        layout_edit_verse.helperText = lang.test.edit_verse_hint
        btn_check.text = lang.test.btn_check
        btn_next.text = lang.test.btn_next
        text_end_headline.text = lang.test.text_end_headline
        text_end_desc.text = lang.test.text_end_desc
        btn_again.text = lang.finish.btn_again
        btn_end.text = lang.finish.btn_end
        return lang.archive
    }

    private fun returnTest(str: String): test {
        val lang: lang = Gson().fromJson<lang>(str, lang::class.java)
        return lang.test
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.close_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("PrivateResource")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val builder = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_MaterialComponents)
        builder.setMessage(returnTest(returnLang(pref.getString("lang", "")!!)).apply_exit)
        builder.setPositiveButton(returnTest(returnLang(pref.getString("lang", "")!!)).yes) { _: DialogInterface, _: Int ->
            this.finish()
        }
        builder.setNegativeButton(returnTest(returnLang(pref.getString("lang", "")!!)).no) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.cancel()
        }
        builder.show()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val builder = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_MaterialComponents)
        builder.setMessage(returnTest(returnLang(pref.getString("lang", "")!!)).apply_exit)
        builder.setPositiveButton(returnTest(returnLang(pref.getString("lang", "")!!)).yes) { _: DialogInterface, _: Int ->
            this.finish()
        }
        builder.setNegativeButton(returnTest(returnLang(pref.getString("lang", "")!!)).no) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.cancel()
        }
        builder.show()
    }
}
