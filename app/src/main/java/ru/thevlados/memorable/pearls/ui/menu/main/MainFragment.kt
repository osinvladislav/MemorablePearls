package ru.thevlados.memorable.pearls.ui.menu.main

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import kotlinx.android.synthetic.main.main_fragment.view.*
import ru.thevlados.memorable.pearls.R
import ru.thevlados.memorable.pearls.ui.detail.DetailActivity
import ru.thevlados.memorable.pearls.ui.menu.archive.year
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*


class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var pref: SharedPreferences

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
            currentHour.toInt() < 6 -> {
                v.text_headline.text = "Доброй ночи, $name!"
            }
            currentHour.toInt() in 7..10 -> {
                v.text_headline.text = "Доброе утро, $name!"
            }
            currentHour.toInt() in 11..16 -> {
                v.text_headline.text = "Добрый день, $name!"
            }
            currentHour.toInt() in 17..22 -> {
                v.text_headline.text = "Добрый вечер, $name!"
            }
            currentHour.toInt() > 22 -> {
                v.text_headline.text = "Доброй ночи, $name!"
            }
        }

        val sdfYear = SimpleDateFormat("yyyy")
        val currentYeaR = sdfYear.format(Date())
        pref.edit().putString("year", currentYeaR).apply()

        returnYears(currentYeaR.toInt())

        val cal = getInstance()
        cal.add(DAY_OF_MONTH, -5)

        val jsonString = activity!!.application.assets.open(pref.getString("seas", "") + "-ru.json").bufferedReader().use{
            it.readText()
        }

        val quart: Array<year> = Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)

        quart.forEachIndexed {index, it ->
            if (cal.get(WEEK_OF_YEAR) in it.start_week..it.end_week) {
                when (index) {
                    0 -> {
                        v.card_mp_today.setCardBackgroundColor(resources.getColor(R.color.color_01))
                        pref.edit().putString("q", "1q").apply()
                    }
                    1 -> {
                        v.card_mp_today.setCardBackgroundColor(resources.getColor(R.color.color_02))
                        pref.edit().putString("q", "2q").apply()
                    }
                    2 -> {
                        v.card_mp_today.setCardBackgroundColor(resources.getColor(R.color.color_03))
                        pref.edit().putString("q", "3q").apply()
                    }
                    3 -> {
                        v.card_mp_today.setCardBackgroundColor(resources.getColor(R.color.color_04))
                        pref.edit().putString("q", "4q").apply()
                    }
                }
                it.weeks.forEach {item ->
                    if (cal.get(WEEK_OF_YEAR) == item.num_week_in_year) {
                        v.card_mp_today.text_date.text = item.num_week.toString() + " неделя, " + returnDates(item.num_week_in_year)[0]  + "  —  " + returnDates(item.num_week_in_year)[1]
                        pref.edit().putString("v", item.num_week.toString()).apply()
                        when (pref.getString("translate", "")) {
                             "radio_rst" -> {
                                v.card_mp_today.text_verse.text = item.verse.RST
                             }
                            "radio_bti" -> {
                                v.card_mp_today.text_verse.text = item.verse.BTI
                            }
                            "radio_nrp" -> {
                                v.card_mp_today.text_verse.text = item.verse.NRP
                            }
                            "radio_cslav" -> {
                                v.card_mp_today.text_verse.text = item.verse.CSLAV
                            }
                            "radio_srp" -> {
                                v.card_mp_today.text_verse.text = item.verse.SRP
                            }
                            "radio_ibl" -> {
                                v.card_mp_today.text_verse.text = item.verse.IBL
                            }
                        }
                        v.card_mp_today.text_link.text = item.verse.link_small + " "
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
                            intent.putExtra("text_link_full", item.verse.link_full)
                            intent.putExtra("text_link_short", item.verse.link_small)
                            ContextCompat.startActivity(v.context, intent, null)
                        }

                        v.text_open_now.setOnClickListener {
                            val args = Bundle()
                            args.putString("quart", (index+1).toString())
                            findNavController().navigate(R.id.navigation_archive, args)
                        }
                    }
                }
            }
        }

        v.text_trans_now.setOnClickListener {
            pref.edit().putString("whatIs", "trane").apply()
            findNavController().navigate(R.id.navigation_tests)
        }

        v.text_check_know.setOnClickListener {
            pref.edit().putString("whatIs", "test").apply()
            findNavController().navigate(R.id.navigation_tests)
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
                else -> {
                    i -= 5
                    continue@loop
                }
            }
        }

        pref.edit().putString("1y", (cY-2).toString()).apply()
        pref.edit().putString("2y", (cY-1).toString()).apply()
        pref.edit().putString("3y", cY.toString()).apply()
        pref.edit().putString("4y", (cY+1).toString()).apply()
        pref.edit().putString("5y", (cY+2).toString()).apply()

    }

}
