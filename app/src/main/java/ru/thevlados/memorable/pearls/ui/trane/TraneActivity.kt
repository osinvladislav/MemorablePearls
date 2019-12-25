package ru.thevlados.memorable.pearls.ui.trane

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_trane.*
import kotlinx.android.synthetic.main.activity_trane.text_verse
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.tests_fragment.*
import kotlinx.android.synthetic.main.tests_fragment.view.*
import ru.thevlados.memorable.pearls.R
import ru.thevlados.memorable.pearls.ui.menu.archive.verse
import ru.thevlados.memorable.pearls.ui.menu.archive.week
import ru.thevlados.memorable.pearls.ui.menu.archive.year
import kotlin.random.Random

class TraneActivity : AppCompatActivity() {
    var trueVerse: String = ""
    var numOfVerseNow = 0
    lateinit var pref: SharedPreferences
    private var translate = ""
    private lateinit var randomValues: MutableList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trane)
        val year = intent.getStringExtra("year")
        val quart = intent.getStringExtra("quart")
        pref = getSharedPreferences("settings", MODE_PRIVATE)

        supportActionBar?.title = "$quart квартал, $year год"
        val jsonString = application.assets.open("$year-ru.json").bufferedReader().use {
            it.readText()
        }

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
                btn_again.setOnClickListener {
                    finish()
                    val intent = Intent(this, TraneActivity::class.java)
                    intent.putExtra("year", year)
                    intent.putExtra("quart", quart)
                    startActivity(intent)
                }
                btn_end.setOnClickListener {
                    finish()
                }
            }
        }
    }

    private fun updateVerse(weeks: week) {
        layout_edit_verse.helperText = "Регистр букв и знаки препинания за исключением точек, знаков вопроса и восклицательных знаков не учитываются."
        layout_edit_verse.setHelperTextTextAppearance(R.style.standart)
        edit_verse.text = null
        supportActionBar?.subtitle = weeks.verse.link_small
        text_link_headline.text = weeks.verse.link_small
        text_date_desc.text = weeks.num_week.toString() + " неделя, " + weeks.date_begin + " — " + weeks.date_finish
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
        text_verse.text = verse
        trueVerse = verse.replace("«", "").replace("»", "").replace(",", "").replace(";", "").replace("-", "").replace("—", "").replace(":", "").toLowerCase()
        btn_check.setOnClickListener {
            checkVerse(edit_verse.text.toString().replace("«", "").replace("»", "").replace(",", "").replace(";", "").replace("-", "").replace("—", "").replace(":", "").toLowerCase())
        }
    }

    private fun checkVerse(getVerse: String) {
        if (getVerse == trueVerse) {
            layout_edit_verse.helperText = "Все правильно!"
            layout_edit_verse.setHelperTextTextAppearance(R.style.ok)
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
