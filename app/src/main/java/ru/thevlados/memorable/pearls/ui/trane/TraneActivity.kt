package ru.thevlados.memorable.pearls.ui.trane

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_trane.*
import kotlinx.android.synthetic.main.main_fragment.text_link
import kotlinx.android.synthetic.main.main_fragment.text_verse
import ru.thevlados.memorable.pearls.*
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

        when (quart) {
            "1" -> card_verse.setCardBackgroundColor(resources.getColor(R.color.color_01))
            "2" -> card_verse.setCardBackgroundColor(resources.getColor(R.color.color_02))
            "3" -> card_verse.setCardBackgroundColor(resources.getColor(R.color.color_03))
            "4" -> card_verse.setCardBackgroundColor(resources.getColor(R.color.color_04))
        }

        val quarter: Array<year> =
            Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
        val quartNow = quarter[quart.toInt()-1]

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
                        supportActionBar?.subtitle = ""
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
        when (pref.getString("lang", "")) {
            "ru" -> {
                supportActionBar?.subtitle = weeks.verse.link_small_ru
                text_link.text = weeks.verse.link_small_ru
            }
            "en" -> {
                supportActionBar?.subtitle = weeks.verse.link_small_en
                text_link.text = weeks.verse.link_small_en
            }
            "ua" -> {
                supportActionBar?.subtitle = weeks.verse.link_small_ua
                text_link.text = weeks.verse.link_small_ua
            }
            "by" -> {
                supportActionBar?.subtitle = weeks.verse.link_small_by
                text_link.text = weeks.verse.link_small_by
            }
        }
        stateCardNow = "first"
        layout_btn.visibility = View.GONE
        text_link.visibility = View.VISIBLE
        text_verse.visibility = View.GONE
        val verse = when (pref.getString("translate_"+pref.getString("lang", ""), "")) {
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

    private fun returnLang (string: String): String {
        return application.assets.open("lang/$string.json").bufferedReader().use {
            it.readText()
        }
    }

    private fun initLang(str: String): archive {
        val lang: lang = Gson().fromJson<lang>(str, lang::class.java)
        btn_no.text = lang.trane.btn_no
        btn_yes.text = lang.trane.btn_yes
        text_end_headline.text = lang.trane.text_end_headline
        text_end_desc.text = lang.trane.text_end_desc
        btn_again_trane.text = lang.finish.btn_again
        btn_finish.text = lang.finish.btn_end
        return lang.archive
    }

    private fun returnTrane(str: String): trane {
        val lang: lang = Gson().fromJson<lang>(str, lang::class.java)
        return lang.trane
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
        builder.setMessage(returnTrane(returnLang(pref.getString("lang", "")!!)).apply_exit)
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
        builder.setMessage(returnTrane(returnLang(pref.getString("lang", "")!!)).apply_exit)
        builder.setPositiveButton(returnTest(returnLang(pref.getString("lang", "")!!)).yes) { _: DialogInterface, _: Int ->
            this.finish()
        }
        builder.setNegativeButton(returnTest(returnLang(pref.getString("lang", "")!!)).no) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.cancel()
        }
        builder.show()
    }
}
