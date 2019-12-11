package ru.thevlados.memorable.pearls.ui.menu.archive

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import kotlinx.android.synthetic.main.archive_fragment.*
import kotlinx.android.synthetic.main.archive_fragment.view.*
import ru.thevlados.memorable.pearls.MainActivity
import ru.thevlados.memorable.pearls.R
import ru.thevlados.memorable.pearls.ui.detail.DetailActivity


class ArchiveFragment : Fragment() {
    private lateinit var viewModel: ArchiveViewModel
    private lateinit var valNow: String
    private lateinit var pref: SharedPreferences
    private var stateNow: String = ""
    private lateinit var mp: MediaPlayer


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.archive_fragment, container, false)
        pref = activity!!.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)
        valNow = "year"
        setHasOptionsMenu(true)
        v.isFocusableInTouchMode = true
        v.requestFocus()
        v.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    when (valNow) {
                        "week" -> {
                            valNow = "quart"
                            scroll_quarters.visibility = View.VISIBLE
                            scroll_weeks.visibility = View.GONE
                            mp.stop()
                        }
                        "quart" -> {
                            valNow = "year"
                            scroll_quarters.visibility = View.GONE
                            scroll_years.visibility = View.VISIBLE
                            (activity as MainActivity?)?.resetActionBar(
                                childAction = false
                            )
                        }
                        else -> {
                            activity?.onBackPressed()
                        }
                    }
                    return@OnKeyListener true
                }
            }
            false
        })
        v.card_first.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(childAction = true)
            setHasOptionsMenu(true)
            valNow = "quart"
            val jsonString = returnJson("2017-ru")

            val quart: Array<year> =
                Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
            funJson(v, quart)
            pref.edit().putString("year", "2017").apply()
        }
        v.card_second.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(true)
            setHasOptionsMenu(true)
            valNow = "quart"
            val jsonString = returnJson("2018-ru")

            val quart: Array<year> =
                Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
            funJson(v, quart)
            pref.edit().putString("year", "2018").apply()
        }
        v.card_third.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(childAction = true)
            setHasOptionsMenu(true)
            valNow = "quart"
            val jsonString = returnJson("2019-ru")

            val quart: Array<year> =
                Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
            funJson(v, quart)
            pref.edit().putString("year", "2019").apply()
        }
        v.card_fourth.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(childAction = true)
            setHasOptionsMenu(true)
            valNow = "quart"
            val jsonString = returnJson("2020-ru")

            val quart: Array<year> =
                Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
            funJson(v, quart)
            pref.edit().putString("year", "2020").apply()
        }
        v.card_fifth.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(childAction = true)
            setHasOptionsMenu(true)
            valNow = "quart"
            val jsonString = returnJson("2021-ru")

            val quart: Array<year> =
                Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
            funJson(v, quart)
            pref.edit().putString("year", "2021").apply()
        }
        if (arguments?.getBoolean("isQuart") != null) {
            if (arguments?.getBoolean("isQuart")!!) {
                when {
                    arguments?.getInt("int") == 3 -> {
                        v.card_third.performClick()
                    }
                    arguments?.getInt("int") == 4 -> {
                        v.card_fourth.performClick()
                    }
                    arguments?.getInt("int") == 5 -> {
                        v.card_fifth.performClick()
                    }
                }
                when {
                    arguments?.getInt("int1") == 1 -> {
                        v.card_1_q.performClick()
                    }
                    arguments?.getInt("int1") == 2 -> {
                        v.card_2_q.performClick()
                    }
                    arguments?.getInt("int1") == 3 -> {
                        v.card_3_q.performClick()
                    }
                    arguments?.getInt("int1") == 4 -> {
                        v.card_4_q.performClick()
                    }
                }
            }
        }
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ArchiveViewModel::class.java)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                print(valNow)
                if (valNow == "week") {
                    valNow = "quart"
                    scroll_quarters.visibility = View.VISIBLE
                    scroll_weeks.visibility = View.GONE
                    mp.stop()
                } else if (valNow == "quart") {
                    valNow = "year"
                    scroll_quarters.visibility = View.GONE
                    scroll_years.visibility = View.VISIBLE
                    (activity as MainActivity?)?.resetActionBar(false)
                }
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }

    @SuppressLint("SetTextI18n")
    fun funJson(v: View, quart: Array<year>) {
        v.scroll_quarters.visibility = View.VISIBLE
        v.scroll_years.visibility = View.GONE
        v.text_date_1_q.text = quart[0].date_from + "  —  " + quart[0].date_to
        v.text_year_1_q.text = quart[0].name_quarter
        v.card_1_q.setOnClickListener {
            pref.edit().putString("q", "1q").apply()
            setListener(v, quart, 0)
        }
        v.text_date_2_q.text = quart[1].date_from + "  —  " + quart[1].date_to
        v.text_year_2_q.text = quart[1].name_quarter
        v.card_2_q.setOnClickListener {
            pref.edit().putString("q", "2q").apply()
            setListener(v, quart, 1)
        }
        v.text_date_3_q.text = quart[2].date_from + "  —  " + quart[2].date_to
        v.text_year_3_q.text = quart[2].name_quarter
        v.card_3_q.setOnClickListener {
            pref.edit().putString("q", "3q").apply()
            setListener(v, quart, 2)
        }
        v.text_date_4_q.text = quart[3].date_from + "  —  " + quart[3].date_to
        v.text_year_4_q.text = quart[3].name_quarter
        v.card_4_q.setOnClickListener {
            pref.edit().putString("q", "4q").apply()
            setListener(v, quart, 3)
        }
    }

    private fun returnJson (string: String): String {
        return activity!!.application.assets.open("$string.json").bufferedReader().use {
            it.readText()
        }
    }

    private fun startActivity(
        archiveFragment: ArchiveFragment,
        quart: Array<year>, num1: Int, num2: Int, v: View) {
        val intent = Intent(archiveFragment.context, DetailActivity::class.java)
        intent.putExtra("text_date", quart[num1].weeks[num2].num_week.toString() + " неделя, " + quart[num1].weeks[num2].date_begin + "  —  " + quart[num1].weeks[num2].date_finish)
        intent.putExtra("text_verse_rst", quart[num1].weeks[num2].verse.RST)
        intent.putExtra("text_verse_bti", quart[num1].weeks[num2].verse.BTI)
        intent.putExtra("text_verse_cass", quart[num1].weeks[num2].verse.CASS)
        intent.putExtra("text_verse_nrp", quart[num1].weeks[num2].verse.NRP)
        intent.putExtra("text_verse_cslav", quart[num1].weeks[num2].verse.CSLAV)
        intent.putExtra("text_verse_srp", quart[num1].weeks[num2].verse.SRP)
        intent.putExtra("text_verse_ibl", quart[num1].weeks[num2].verse.IBL)
        intent.putExtra("text_link_full", quart[num1].weeks[num2].verse.link_full)
        intent.putExtra("text_link_short", quart[num1].weeks[num2].verse.link_small)
        ContextCompat.startActivity(v.context, intent, null)
    }

    private fun setTextik(quart: Array<year>, q: Int, w: Int, t: Int, v: View) {
        println("text_verse_$t")
        val item = v.findViewById<TextView>(resources.getIdentifier("text_verse_$t", "id", context!!.packageName))
        item.text = when (pref.getString("translate", "")) {
            "radio_rst" -> {
                quart[q].weeks[w].verse.RST
            }
            "radio_bti" -> {
                quart[q].weeks[w].verse.BTI
            }
            "radio_nrp" -> {
                quart[q].weeks[w].verse.NRP
            }
            "radio_cslav" -> {
                quart[q].weeks[w].verse.CSLAV
            }
            "radio_srp" -> {
                quart[q].weeks[w].verse.SRP
            }
            "radio_ibl" -> {
                quart[q].weeks[w].verse.IBL
            }
            else -> "Ошибка"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setListener (v: View, quart: Array<year>, n_q: Int) {
        valNow = "week"
        v.scroll_quarters.visibility = View.GONE
        v.scroll_weeks.visibility = View.VISIBLE
        stateNow = ""
        v.img_music.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        mp = MediaPlayer()
        val afd = activity!!.assets.openFd("audio/"+pref.getString("year", "")+"/"+pref.getString("q", "")+"/all.mp3")
        mp.setDataSource(afd.fileDescriptor,afd.startOffset,afd.length)
        mp.prepare()
        mp.isLooping = true
        v.card_music.setOnClickListener {
            if (stateNow == "" || stateNow == "play") {
                mp.start()
                v.img_music.setImageResource(R.drawable.ic_pause_black_24dp)
                stateNow = "pause"
            } else if (stateNow == "pause") {
                mp.pause()
                v.img_music.setImageResource(R.drawable.ic_play_arrow_black_24dp)
                stateNow = "play"
            }
        }
        v.card_mp_1.setCardBackgroundColor(resources.getColor(resources.getIdentifier("color_0"+(n_q+1), "color", context!!.packageName)))
        v.card_mp_1.setOnClickListener {
            pref.edit().putString("v", "1").apply()
            startActivity(this, quart, n_q, 0, v)
        }
        v.card_mp_2.setCardBackgroundColor(resources.getColor(resources.getIdentifier("color_0"+(n_q+1), "color", context!!.packageName)))
        v.card_mp_2.setOnClickListener {
            pref.edit().putString("v", "2").apply()
            startActivity(this, quart, n_q, 1, v)
        }
        v.card_mp_3.setCardBackgroundColor(resources.getColor(resources.getIdentifier("color_0"+(n_q+1), "color", context!!.packageName)))
        v.card_mp_3.setOnClickListener {
            pref.edit().putString("v", "3").apply()
            startActivity(this, quart, n_q, 2, v)
        }
        v.card_mp_4.setCardBackgroundColor(resources.getColor(resources.getIdentifier("color_0"+(n_q+1), "color", context!!.packageName)))
        v.card_mp_4.setOnClickListener {
            pref.edit().putString("v", "4").apply()
            startActivity(this, quart, n_q, 3, v)
        }
        v.card_mp_5.setCardBackgroundColor(resources.getColor(resources.getIdentifier("color_0"+(n_q+1), "color", context!!.packageName)))
        v.card_mp_5.setOnClickListener {
            pref.edit().putString("v", "5").apply()
            startActivity(this, quart, n_q, 4, v)
        }
        v.card_mp_6.setCardBackgroundColor(resources.getColor(resources.getIdentifier("color_0"+(n_q+1), "color", context!!.packageName)))
        v.card_mp_6.setOnClickListener {
            pref.edit().putString("v", "6").apply()
            startActivity(this, quart, n_q, 5, v)
        }
        v.card_mp_7.setCardBackgroundColor(resources.getColor(resources.getIdentifier("color_0"+(n_q+1), "color", context!!.packageName)))
        v.card_mp_7.setOnClickListener {
            pref.edit().putString("v", "7").apply()
            startActivity(this, quart, n_q, 6, v)
        }
        v.card_mp_8.setCardBackgroundColor(resources.getColor(resources.getIdentifier("color_0"+(n_q+1), "color", context!!.packageName)))
        v.card_mp_8.setOnClickListener {
            pref.edit().putString("v", "8").apply()
            startActivity(this, quart, n_q, 7, v)
        }
        v.card_mp_9.setCardBackgroundColor(resources.getColor(resources.getIdentifier("color_0"+(n_q+1), "color", context!!.packageName)))
        v.card_mp_9.setOnClickListener {
            pref.edit().putString("v", "9").apply()
            startActivity(this, quart, n_q, 8, v)
        }
        v.card_mp_10.setCardBackgroundColor(resources.getColor(resources.getIdentifier("color_0"+(n_q+1), "color", context!!.packageName)))
        v.card_mp_10.setOnClickListener {
            pref.edit().putString("v", "10").apply()
            startActivity(this, quart, n_q, 9, v)
        }
        v.card_mp_11.setCardBackgroundColor(resources.getColor(resources.getIdentifier("color_0"+(n_q+1), "color", context!!.packageName)))
        v.card_mp_11.setOnClickListener {
            pref.edit().putString("v", "11").apply()
            startActivity(this, quart, n_q, 10, v)
        }
        v.card_mp_12.setCardBackgroundColor(resources.getColor(resources.getIdentifier("color_0"+(n_q+1), "color", context!!.packageName)))
        v.card_mp_12.setOnClickListener {
            pref.edit().putString("v", "12").apply()
            startActivity(this, quart, n_q, 11, v)
        }
        v.card_mp_13.setCardBackgroundColor(resources.getColor(resources.getIdentifier("color_0"+(n_q+1), "color", context!!.packageName)))
        v.card_mp_13.setOnClickListener {
            pref.edit().putString("v", "13").apply()
            startActivity(this, quart, n_q, 12, v)
        }
        v.text_date_1.text =
            quart[n_q].weeks[0].num_week.toString() + " неделя, " + quart[n_q].weeks[0].date_begin + "  —  " + quart[n_q].weeks[0].date_finish
        v.text_date_2.text =
            quart[n_q].weeks[1].num_week.toString() + " неделя, " + quart[n_q].weeks[1].date_begin + "  —  " + quart[n_q].weeks[1].date_finish
        v.text_date_3.text =
            quart[n_q].weeks[2].num_week.toString() + " неделя, " + quart[n_q].weeks[2].date_begin + "  —  " + quart[n_q].weeks[2].date_finish
        v.text_date_4.text =
            quart[n_q].weeks[3].num_week.toString() + " неделя, " + quart[n_q].weeks[3].date_begin + "  —  " + quart[n_q].weeks[3].date_finish
        v.text_date_5.text =
            quart[n_q].weeks[4].num_week.toString() + " неделя, " + quart[n_q].weeks[4].date_begin + "  —  " + quart[n_q].weeks[4].date_finish
        v.text_date_6.text =
            quart[n_q].weeks[5].num_week.toString() + " неделя, " + quart[n_q].weeks[5].date_begin + "  —  " + quart[n_q].weeks[5].date_finish
        v.text_date_7.text =
            quart[n_q].weeks[6].num_week.toString() + " неделя, " + quart[n_q].weeks[6].date_begin + "  —  " + quart[n_q].weeks[6].date_finish
        v.text_date_8.text =
            quart[n_q].weeks[7].num_week.toString() + " неделя, " + quart[n_q].weeks[7].date_begin + "  —  " + quart[n_q].weeks[7].date_finish
        v.text_date_9.text =
            quart[n_q].weeks[8].num_week.toString() + " неделя, " + quart[n_q].weeks[8].date_begin + "  —  " + quart[n_q].weeks[8].date_finish
        v.text_date_10.text =
            quart[n_q].weeks[9].num_week.toString() + " неделя, " + quart[n_q].weeks[9].date_begin + "  —  " + quart[n_q].weeks[9].date_finish
        v.text_date_11.text =
            quart[n_q].weeks[10].num_week.toString() + " неделя, " + quart[n_q].weeks[10].date_begin + "  —  " + quart[n_q].weeks[10].date_finish
        v.text_date_12.text =
            quart[n_q].weeks[11].num_week.toString() + " неделя, " + quart[n_q].weeks[11].date_begin + "  —  " + quart[n_q].weeks[11].date_finish
        v.text_date_13.text =
            quart[n_q].weeks[12].num_week.toString() + " неделя, " + quart[n_q].weeks[12].date_begin + "  —  " + quart[n_q].weeks[12].date_finish

        quart[n_q].weeks.forEachIndexed { index, _ ->
            setTextik(quart, n_q, index, index+1, v)
        }

        v.text_link_1.text = quart[n_q].weeks[0].verse.link_small + " "
        v.text_link_2.text = quart[n_q].weeks[1].verse.link_small + " "
        v.text_link_3.text = quart[n_q].weeks[2].verse.link_small + " "
        v.text_link_4.text = quart[n_q].weeks[3].verse.link_small + " "
        v.text_link_5.text = quart[n_q].weeks[4].verse.link_small + " "
        v.text_link_6.text = quart[n_q].weeks[5].verse.link_small + " "
        v.text_link_7.text = quart[n_q].weeks[6].verse.link_small + " "
        v.text_link_8.text = quart[n_q].weeks[7].verse.link_small + " "
        v.text_link_9.text = quart[n_q].weeks[8].verse.link_small + " "
        v.text_link_10.text = quart[n_q].weeks[9].verse.link_small + " "
        v.text_link_11.text = quart[n_q].weeks[10].verse.link_small + " "
        v.text_link_12.text = quart[n_q].weeks[11].verse.link_small + " "
        v.text_link_13.text = quart[n_q].weeks[12].verse.link_small + " "

    }
}


data class year (val name_quarter: String, val date_from: String, val date_to: String, val weeks: Array<week>)

data class week (val num_week: Int, val date_begin: String, val date_finish: String, val verse: verse)

data class verse (val link_full: String, val link_small: String, val RST: String, val BTI: String, val CASS: String, val NRP: String, val CSLAV: String, val SRP: String, val IBL: String)