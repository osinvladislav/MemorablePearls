package ru.thevlados.memorable.pearls.ui.menu.main

import android.annotation.SuppressLint
import android.content.*
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.archive_fragment.view.*
import kotlinx.android.synthetic.main.main_fragment.view.*
import ru.thevlados.memorable.pearls.R
import ru.thevlados.memorable.pearls.archive
import ru.thevlados.memorable.pearls.lang
import ru.thevlados.memorable.pearls.main
import ru.thevlados.memorable.pearls.ui.detail.DetailActivity
import ru.thevlados.memorable.pearls.ui.menu.archive.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*


class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var pref: SharedPreferences
    private lateinit var mps: MediaPlayer

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.main_fragment, container, false)
        pref = activity!!.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)

        val sdfh = SimpleDateFormat("HH")
        val currentHour = sdfh.format(Date())
        val name = pref.getString("name", "")
        when {
            currentHour.toInt() < 5 -> {
                v.text_headline.text = initLang(returnLang(pref.getString("lang", "")!!), v).text_night + ", $name!"
            }
            currentHour.toInt() in 5..10 -> {
                v.text_headline.text = initLang(returnLang(pref.getString("lang", "")!!), v).text_morning + ", $name!"
            }
            currentHour.toInt() in 11..16 -> {
                v.text_headline.text = initLang(returnLang(pref.getString("lang", "")!!), v).text_day + ", $name!"
            }
            currentHour.toInt() in 17..22 -> {
                v.text_headline.text = initLang(returnLang(pref.getString("lang", "")!!), v).text_evening + " $name!"
            }
            currentHour.toInt() > 22 -> {
                v.text_headline.text = initLang(returnLang(pref.getString("lang", "")!!), v).text_night + ", $name!"
            }
        }

        val sdfYear = SimpleDateFormat("yyyy")
        val currentYeaR = sdfYear.format(Date())
        pref.edit().putString("year", currentYeaR).apply()

        returnYears(currentYeaR.toInt())

        val cal = getInstance()
        cal.add(DAY_OF_MONTH, -5)

        val jsonString = activity!!.application.assets.open(pref.getString("seas", "") + ".json").bufferedReader().use{
            it.readText()
        }

        val quart: Array<year> =
            Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)

        quart.forEachIndexed { index, it ->
            if (cal.get(WEEK_OF_YEAR) in it.start_week..it.end_week) {
                when (index) {
                    0 -> {
                        v.card_mp_today.setCardBackgroundColor(resources.getColor(R.color.color_1))
                        pref.edit().putString("q", "1q").apply()
                        pref.edit().putString("qq", "1q").apply()
                    }
                    1 -> {
                        v.card_mp_today.setCardBackgroundColor(resources.getColor(R.color.color_2))
                        pref.edit().putString("q", "2q").apply()
                        pref.edit().putString("qq", "2q").apply()
                    }
                    2 -> {
                        v.card_mp_today.setCardBackgroundColor(resources.getColor(R.color.color_3))
                        pref.edit().putString("q", "3q").apply()
                        pref.edit().putString("qq", "3q").apply()
                    }
                    3 -> {
                        v.card_mp_today.setCardBackgroundColor(resources.getColor(R.color.color_4))
                        pref.edit().putString("q", "4q").apply()
                        pref.edit().putString("qq", "4q").apply()
                    }
                }
                it.weeks.forEach { item: week ->
                    if (cal.get(WEEK_OF_YEAR) == item.num_week_in_year) {
                        v.card_mp_today.text_date.text =
                            item.num_week.toString() + " "+initLangArch(returnLang(pref.getString("lang", "")!!)).week+", " + returnDates(item.num_week_in_year)[0] + "  —  " + returnDates(
                                item.num_week_in_year
                            )[1]
                        pref.edit().putString("v", item.num_week.toString()).apply()
                        when (pref.getString("translate_" + pref.getString("lang", ""), "")) {
                            "rst" -> {
                                v.card_mp_today.text_verse.text = item.verse.RST
                            }
                            "bti" -> {
                                v.card_mp_today.text_verse.text = item.verse.BTI
                            }
                            "nrp" -> {
                                v.card_mp_today.text_verse.text = item.verse.NRP
                            }
                            "cslav" -> {
                                v.card_mp_today.text_verse.text = item.verse.CSLAV
                            }
                            "srp" -> {
                                v.card_mp_today.text_verse.text = item.verse.SRP
                            }
                            "cass" -> {
                                v.card_mp_today.text_verse.text = item.verse.CASS
                            }
                            "ibl" -> {
                                v.card_mp_today.text_verse.text = item.verse.IBL
                            }
                            "kjv" -> {
                                v.card_mp_today.text_verse.text = item.verse.KJV
                            }
                            "nkjv" -> {
                                v.card_mp_today.text_verse.text = item.verse.NKJV
                            }
                            "nasb" -> {
                                v.card_mp_today.text_verse.text = item.verse.NASB
                            }
                            "csb" -> {
                                v.card_mp_today.text_verse.text = item.verse.CSB
                            }
                            "esv" -> {
                                v.card_mp_today.text_verse.text = item.verse.ESV
                            }
                            "gnt" -> {
                                v.card_mp_today.text_verse.text = item.verse.GNT
                            }
                            "gw" -> {
                                v.card_mp_today.text_verse.text = item.verse.GW
                            }
                            "nirv" -> {
                                v.card_mp_today.text_verse.text = item.verse.NIRV
                            }
                            "niv" -> {
                                v.card_mp_today.text_verse.text = item.verse.NIV
                            }
                            "nlt" -> {
                                v.card_mp_today.text_verse.text = item.verse.NLT
                            }
                            "ubio" -> {
                                v.card_mp_today.text_verse.text = item.verse.UBIO
                            }
                            "ukrk" -> {
                                v.card_mp_today.text_verse.text = item.verse.UKRK
                            }
                            "utt" -> {
                                v.card_mp_today.text_verse.text = item.verse.UTT
                            }
                            "bbl" -> {
                                v.card_mp_today.text_verse.text = item.verse.BBL
                            }
                        }
                        when (pref.getString("lang", "")) {
                            "ru" -> v.card_mp_today.text_link.text = item.verse.link_small_ru + " "
                            "en" -> v.card_mp_today.text_link.text = item.verse.link_small_en + " "
                            "ua" -> v.card_mp_today.text_link.text = item.verse.link_small_ua + " "
                            "by" -> v.card_mp_today.text_link.text = item.verse.link_small_by + " "
                        }
                        v.card_mp_today.setOnClickListener {
                            val intent = Intent(context, DetailActivity::class.java)
                            intent.putExtra("text_date", v.text_date.text as String)
                            intent.putExtra("text_verse_rst", item.verse.RST)
                            intent.putExtra("text_verse_bti", item.verse.BTI)
                            intent.putExtra("text_verse_cass", item.verse.CASS)
                            intent.putExtra("text_verse_nrp", item.verse.NRP)
                            intent.putExtra("text_verse_cslav", item.verse.CSLAV)
                            intent.putExtra("text_verse_srp", item.verse.SRP)
                            intent.putExtra("text_verse_ibl", item.verse.IBL)
                            intent.putExtra("text_verse_kjv", item.verse.KJV)
                            intent.putExtra("text_verse_nkjv", item.verse.NKJV)
                            intent.putExtra("text_verse_nasb", item.verse.NASB)
                            intent.putExtra("text_verse_csb", item.verse.CSB)
                            intent.putExtra("text_verse_esv", item.verse.ESV)
                            intent.putExtra("text_verse_gnt", item.verse.GNT)
                            intent.putExtra("text_verse_gw", item.verse.GW)
                            intent.putExtra("text_verse_nirv", item.verse.NIRV)
                            intent.putExtra("text_verse_niv", item.verse.NIV)
                            intent.putExtra("text_verse_nlt", item.verse.NLT)
                            intent.putExtra("text_verse_ubio", item.verse.UBIO)
                            intent.putExtra("text_verse_ukrk", item.verse.UKRK)
                            intent.putExtra("text_verse_utt", item.verse.UTT)
                            intent.putExtra("text_verse_bbl", item.verse.BBL)
                            when (pref.getString("lang", "")) {
                                "ru" -> {
                                    intent.putExtra("text_link_full", item.verse.link_full_ru)
                                    intent.putExtra("text_link_short", item.verse.link_small_ru)
                                }
                                "en" -> {
                                    intent.putExtra("text_link_full", item.verse.link_full_en)
                                    intent.putExtra("text_link_short", item.verse.link_small_en)
                                }
                                "ua" -> {
                                    intent.putExtra("text_link_full", item.verse.link_full_ua)
                                    intent.putExtra("text_link_short", item.verse.link_small_ua)
                                }
                                "by" -> {
                                    intent.putExtra("text_link_full", item.verse.link_full_by)
                                    intent.putExtra("text_link_short", item.verse.link_small_by)
                                }
                            }
                            ContextCompat.startActivity(v.context, intent, null)
                        }
                        v.card_mp_today.setOnLongClickListener {
                            startDialogMenu(v, v.text_verse.text.toString(), v.text_link.text.toString())
                            true
                        }
                        v.text_open_now.setOnClickListener {
                            val args = Bundle()
                            args.putString("quart", (index + 1).toString())
                            findNavController().navigate(R.id.navigation_archive, args)
                        }
                    }
                }
            }
        }
        v.text_trans_now.setOnClickListener {
            val args = Bundle()
            args.putString("whatIs", "trane")
            findNavController().navigate(R.id.navigation_tests, args)
        }

        v.text_check_know.setOnClickListener {
            val args = Bundle()
            args.putString("whatIs", "test")
            findNavController().navigate(R.id.navigation_tests, args)
        }

        v.text_switch_translate.setOnClickListener {
            findNavController().navigate(R.id.navigation_settings)
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    @SuppressLint("SimpleDateFormat")
    private fun returnDates(w: Int): List<String> {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val c = Calendar.getInstance()
        val test = Calendar.getInstance()
        c.firstDayOfWeek = Calendar.SATURDAY
        test.firstDayOfWeek = Calendar.SATURDAY
        c.time = Date()
        test.time = Date()
        if (test.get(DAY_OF_WEEK) == SATURDAY) {
            c.set(Calendar.WEEK_OF_YEAR, w)
        } else {
            c.set(Calendar.WEEK_OF_YEAR, w+1)
        }
        c.time
        val sD: Calendar = c.clone() as Calendar
        sD.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        val eD: Calendar = c.clone() as Calendar
        if (test.get(DAY_OF_WEEK) == SATURDAY) {
            eD.set(Calendar.WEEK_OF_YEAR, w+1)
        }
        eD.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
        return listOf(sdf.format(sD.time), sdf.format(eD.time))
    }

    private fun returnYears(cY: Int) {
        var i = cY
        loop@ while (true) {
            when (i) {
                2017 -> {
                    pref.edit().putString("seas", "1s").apply()
                    break@loop
                }
                2018 -> {
                    pref.edit().putString("seas", "2s").apply()
                    break@loop
                }
                2019 -> {
                    pref.edit().putString("seas", "3s").apply()
                    break@loop
                }
                2020 -> {
                    pref.edit().putString("seas", "4s").apply()
                    break@loop
                }
                2021 -> {
                    pref.edit().putString("seas", "5s").apply()
                    break@loop
                }
                2022 -> {
                    pref.edit().putString("seas", "6s").apply()
                    break@loop
                }
                else -> {
                    i -= 6
                    continue@loop
                }
            }
        }

        pref.edit().putString("1y", (cY-3).toString()).apply()
        pref.edit().putString("2y", (cY-2).toString()).apply()
        pref.edit().putString("3y", (cY-1).toString()).apply()
        pref.edit().putString("4y", cY.toString()).apply()
        pref.edit().putString("5y", (cY+1).toString()).apply()
        pref.edit().putString("6y", (cY+2).toString()).apply()

    }

    private fun startDialogMenu (v: View, textCopy: String, link_short: String) {
        val items = arrayOf(initLangArch(returnLang(pref.getString("lang", "")!!)).copy,initLangArch(returnLang(pref.getString("lang", "")!!)).listen)
        MaterialAlertDialogBuilder(context!!)
            .setItems(items) { _: DialogInterface, i: Int ->
                when (i) {
                    0 -> {
                        val clipboard =
                            context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText(link_short, "$textCopy $link_short")
                        clipboard.setPrimaryClip(clip)
                        Snackbar.make(v, initLangArch(returnLang(pref.getString("lang", "")!!)).copy_notify, Snackbar.LENGTH_LONG)
                            .show()
                    }
                    1 -> {
                        mps = MediaPlayer()
                        val afd = activity!!.assets.openFd("audio/"+pref.getString("lang", "")+"/"+pref.getString("season", "")+"/"+pref.getString("q", "")+"/"+pref.getString("v", "")+".mp3")
                        mps.setDataSource(afd.fileDescriptor,afd.startOffset,afd.length)
                        mps.prepare()
                        mps.isLooping = false
                        mps.start()
                    }
                }


            }
            .show()
    }


    private fun returnLang (string: String): String {
        return activity!!.application.assets.open("lang/$string.json").bufferedReader().use {
            it.readText()
        }
    }

    private fun initLang(str: String, v: View): main {
        val lang = Gson().fromJson<lang>(str, lang::class.java)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = lang.main.text_action_bar
        v.text_date.text = lang.main.text_date
        v.text_verse.text = lang.main.text_verse
        v.text_actions.text = lang.main.text_actions
        v.text_open_now.text = lang.main.text_open_now
        v.text_trans_now.text = lang.main.text_trans_now
        v.text_check_know.text = lang.main.text_check_know
        v.text_switch_translate.text = lang.main.text_switch_translate
        return lang.main
    }

    private fun initLangArch(str: String): archive {
        val lang = Gson().fromJson<lang>(str, lang::class.java)
        return lang.archive
    }
}
