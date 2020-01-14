package ru.thevlados.memorable.pearls.ui.test

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.main_fragment.*
import ru.thevlados.memorable.pearls.R
import ru.thevlados.memorable.pearls.ui.menu.archive.week
import ru.thevlados.memorable.pearls.ui.menu.archive.year
import ru.thevlados.memorable.pearls.ui.trane.TraneActivity
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        year = intent.getStringExtra("year")
        val season = intent.getStringExtra("season")
        val quart = intent.getStringExtra("quart")
        pref = getSharedPreferences("settings", MODE_PRIVATE)

        supportActionBar?.title = "$quart квартал, $year год"
        val jsonString = application.assets.open("$season-ru.json").bufferedReader().use {
            it.readText()
        }

        val quarter: Array<year> =
            Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
        val quartNow = quarter[quart.toInt()-1]

        when (pref.getString("translate", "")) {
            "radio_rst" -> {
                translate = "rst"
            }
            "radio_bti" -> {
                translate = "bti"
            }
            "radio_nrp" -> {
                translate = "nrp"
            }
            "radio_cslav" -> {
                translate = "cslav"
            }
            "radio_srp" -> {
                translate = "srp"
            }
            "radio_ibl" -> {
                translate = "ibl"
            }
        }

        randomValues = returnRandomListOfVerse()

        updateVerse(quartNow.weeks[randomValues[numOfVerseNow]-1])

        btn_next.setOnClickListener {
            numOfVerseNow++
            if (numOfVerseNow < 12) {
                updateVerse(quartNow.weeks[randomValues[numOfVerseNow]-1])
            } else {
                scroll_question.visibility = View.GONE
                scroll_finish.visibility = View.VISIBLE
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
        layout_edit_verse.helperText = "Регистр букв и знаки препинания за исключением точек, знаков вопроса и восклицательных знаков не учитываются."
        layout_edit_verse.setHelperTextTextAppearance(R.style.standart)
        edit_verse.text = null
        btn_next.isEnabled = false
        supportActionBar?.subtitle = weeks.verse.link_small
        text_link_headline.text = weeks.verse.link_small
        text_date_desc.text = weeks.num_week.toString() + " неделя, " + returnDates(weeks.num_week_in_year, year.toInt())[0] + " — " + returnDates(weeks.num_week_in_year, year.toInt())[1]
        var verse = when (translate) {
            "rst" -> weeks.verse.RST
            "bti" -> weeks.verse.BTI
            "cass" -> weeks.verse.CASS
            "nrp" -> weeks.verse.NRP
            "cslav" -> weeks.verse.CSLAV
            "srp" -> weeks.verse.SRP
            "ibl" -> weeks.verse.IBL
            else -> weeks.verse.RST
        }
        trueVerse = verse.replace(" ", "").replace("!", "").replace("?", "").replace(".", "").replace("«", "").replace("»", "").replace("(", "").replace(")", "").replace(",", "").replace(";", "").replace("-", "").replace("—", "").replace(":", "").toLowerCase()
        btn_check.setOnClickListener {
            checkVerse(edit_verse.text.toString().replace(" ", "").replace("!", "").replace("?", "").replace(".", "").replace("«", "").replace("»", "").replace(",", "").replace(";", "").replace("-", "").replace("—", "").replace(":", "").toLowerCase())
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
        if (getVerse == trueVerse) {
            layout_edit_verse.helperText = "Все правильно!"
            layout_edit_verse.setHelperTextTextAppearance(R.style.ok)
            btn_next.isEnabled = true
        } else {
            layout_edit_verse.helperText = "Вы допустили ошибку. Перепроверьте свой текст еще раз."
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

}
