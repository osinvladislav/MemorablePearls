package ru.thevlados.memorable.pearls.ui.trane

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.activity_trane.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.text_link
import kotlinx.android.synthetic.main.main_fragment.text_verse
import ru.thevlados.memorable.pearls.R
import ru.thevlados.memorable.pearls.ui.menu.archive.week
import ru.thevlados.memorable.pearls.ui.menu.archive.year
import kotlin.random.Random

class TraneActivity : AppCompatActivity() {
    lateinit var pref: SharedPreferences
    private var translate = ""
    private var alreadyLearned = mutableListOf<Int>()
    private var stateCardNow = ""
    private var rangeRandom = 12
    private var numOfVerseNow = 1
    private lateinit var randomValues: MutableList<Int>
    private var iterationNow = 12

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trane)
        val year = intent.getStringExtra("year")
        val season = intent.getStringExtra("season")
        val quart = intent.getStringExtra("quart")
        pref = getSharedPreferences("settings", MODE_PRIVATE)

        supportActionBar?.title = "$quart квартал, $year год"
        val jsonString = application.assets.open("$season-ru.json").bufferedReader().use {
            it.readText()
        }

        when (quart) {
            "1" -> card_verse.setCardBackgroundColor(resources.getColor(R.color.color_01))
            "2" -> card_verse.setCardBackgroundColor(resources.getColor(R.color.color_02))
            "3" -> card_verse.setCardBackgroundColor(resources.getColor(R.color.color_03))
            "4" -> card_verse.setCardBackgroundColor(resources.getColor(R.color.color_04))
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

        updateVerse(quartNow.weeks[randomValues[numOfVerseNow-1]-1])

        btn_yes.setOnClickListener {
            when {
                numOfVerseNow < iterationNow -> {
                    updateVerse(quartNow.weeks[randomValues[numOfVerseNow]-1])
                    rangeRandom -= 1
                    alreadyLearned.add(randomValues[numOfVerseNow-1])
                }
                numOfVerseNow == iterationNow -> {
                    rangeRandom -= 1
                    alreadyLearned.add(randomValues[numOfVerseNow-1])
                    if (alreadyLearned.size == 12) {
                        scroll_card.visibility = View.GONE
                        scroll_end.visibility = View.VISIBLE
                        btn_again_trane.setOnClickListener {
                            finish()
                            val intent = Intent(this, TraneActivity::class.java)
                            intent.putExtra("year", year)
                            intent.putExtra("season", season)
                            intent.putExtra("quart", quart)
                            startActivity(intent)
                        }
                        btn_finish.setOnClickListener {
                            finish()
                        }
                    } else {
                        numOfVerseNow = 0
                        iterationNow = rangeRandom
                        randomValues.clear()
                        randomValues = returnRandomListOfVerse()
                        updateVerse(quartNow.weeks[randomValues[numOfVerseNow]-1])
                    }
                }
            }
            numOfVerseNow++
        }

        btn_no.setOnClickListener {
            when {
                numOfVerseNow < iterationNow -> {
                    updateVerse(quartNow.weeks[randomValues[numOfVerseNow]-1])
                }
                numOfVerseNow == iterationNow -> {
                    if (alreadyLearned.size == 12) {
                        scroll_card.visibility = View.GONE
                        scroll_end.visibility = View.VISIBLE
                        btn_again_trane.setOnClickListener {
                            finish()
                            val intent = Intent(this, TraneActivity::class.java)
                            intent.putExtra("year", year)
                            intent.putExtra("quart", quart)
                            startActivity(intent)
                        }
                        btn_finish.setOnClickListener {
                            finish()
                        }
                    } else {
                        numOfVerseNow = 0
                        iterationNow = rangeRandom
                        randomValues.clear()
                        randomValues = returnRandomListOfVerse()
                        updateVerse(quartNow.weeks[randomValues[numOfVerseNow]-1])
                    }
                }
            }
            numOfVerseNow++
        }

    }

    private fun updateVerse(weeks: week) {
        supportActionBar?.subtitle = weeks.verse.link_small
        stateCardNow = "first"
        layout_btn.visibility = View.GONE
        text_link.visibility = View.VISIBLE
        text_verse.visibility = View.GONE
        text_link.text = weeks.verse.link_small
        val verse = when (translate) {
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
        card_verse.setOnClickListener {
            when (stateCardNow) {
                "first" -> {
                    text_link.visibility = View.GONE
                    text_verse.visibility = View.VISIBLE
                    layout_btn.visibility = View.VISIBLE
                    stateCardNow = "verse"
                }
                "verse" -> {
                    text_link.visibility = View.VISIBLE
                    text_verse.visibility = View.GONE
                    stateCardNow = "link"
                }
                "link" -> {
                    text_link.visibility = View.GONE
                    text_verse.visibility = View.VISIBLE
                    stateCardNow = "verse"
                }
            }
        }
    }


    private fun returnRandomListOfVerse(): MutableList<Int> {
        val randomVerses = mutableListOf<Int>()
        var count = 0

        while (true) {
            if (count < rangeRandom) {
                val randomInt = Random.nextInt(1,13)
                var isHaveVal = false
                randomVerses.forEach {
                    if (it == randomInt) {
                        isHaveVal = true
                    }
                }
                alreadyLearned.forEach {
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
