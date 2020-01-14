package ru.thevlados.memorable.pearls.ui.menu.archive

import android.annotation.SuppressLint
import android.content.*
import android.content.Intent.getIntent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.archive_fragment.*
import kotlinx.android.synthetic.main.archive_fragment.view.*
import ru.thevlados.memorable.pearls.MainActivity
import ru.thevlados.memorable.pearls.R
import ru.thevlados.memorable.pearls.ui.detail.DetailActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*


class ArchiveFragment : Fragment() {
    private lateinit var viewModel: ArchiveViewModel
    private lateinit var valNow: String
    private lateinit var pref: SharedPreferences
    private var stateNow: String = ""
    private lateinit var mp: MediaPlayer
    private lateinit var mps: MediaPlayer


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

        initSeason(v)


        v.card_season_1.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(childAction = true)
            setHasOptionsMenu(true)
            valNow = "quart"

            val lol = v.text_season_first.text.split(" ")

            val jsonString = returnJson(lol[0]+"s-ru")
            pref.edit().putString("season", lol[0]+"s").apply()

            val quart: Array<year> =
                Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
            pref.edit().putString("s", pref.getString("1y", "")).apply()
            funJson(v, quart)
        }
        v.card_season_2.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(true)
            setHasOptionsMenu(true)
            valNow = "quart"
            val lol = v.text_season_second.text.split(" ")

            val jsonString = returnJson(lol[0]+"s-ru")
            pref.edit().putString("season", lol[0]+"s").apply()

            val quart: Array<year> =
                Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
            pref.edit().putString("s", pref.getString("2y", "")).apply()
            funJson(v, quart)
        }
        v.card_season_3.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(childAction = true)
            setHasOptionsMenu(true)
            valNow = "quart"
            val lol = v.text_season_third.text.split(" ")

            val jsonString = returnJson(lol[0]+"s-ru")
            pref.edit().putString("season", lol[0]+"s").apply()

            val quart: Array<year> =
                Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
            pref.edit().putString("s", pref.getString("3y", "")).apply()
            funJson(v, quart)
        }
        v.card_season_4.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(childAction = true)
            setHasOptionsMenu(true)
            valNow = "quart"
            val lol = v.text_season_fourth.text.split(" ")

            val jsonString = returnJson(lol[0]+"s-ru")
            pref.edit().putString("season", lol[0]+"s").apply()

            val quart: Array<year> =
                Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
            pref.edit().putString("s", pref.getString("4y", "")).apply()
            funJson(v, quart)
        }
        v.card_season_5.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(childAction = true)
            setHasOptionsMenu(true)
            valNow = "quart"
            val lol = v.text_season_fifth.text.split(" ")

            val jsonString = returnJson(lol[0]+"s-ru")
            pref.edit().putString("season", lol[0]+"s").apply()

            val quart: Array<year> =
                Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
            pref.edit().putString("s", pref.getString("5y", "")).apply()
            funJson(v, quart)
        }

        if (arguments != null) {
            v.card_season_3.performClick()
            when (arguments!!.getString("quart", "")) {
                "1" -> v.card_q_1.performClick()
                "2" -> v.card_q_2.performClick()
                "3" -> v.card_q_3.performClick()
                "4" -> v.card_q_4.performClick()
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
        v.text_date_1_q.text = returnDates(quart[0].start_week, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[0].end_week, pref.getString("s", "")!!.toInt())[1]
        v.text_year_1_q.text = quart[0].name_quarter
        v.card_q_1.setOnClickListener {
            pref.edit().putString("q", "1q").apply()
            setListener(v, quart, 0)
        }
        v.text_date_2_q.text = returnDates(quart[1].start_week, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[1].end_week, pref.getString("s", "")!!.toInt())[1]
        v.text_year_2_q.text = quart[1].name_quarter
        v.card_q_2.setOnClickListener {
            pref.edit().putString("q", "2q").apply()
            setListener(v, quart, 1)
        }
        v.text_date_3_q.text = returnDates(quart[2].start_week, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[2].end_week, pref.getString("s", "")!!.toInt())[1]
        v.text_year_3_q.text = quart[2].name_quarter
        v.card_q_3.setOnClickListener {
            pref.edit().putString("q", "3q").apply()
            setListener(v, quart, 2)
        }
        v.text_date_4_q.text = returnDates(quart[3].start_week, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[3].end_week, pref.getString("s", "")!!.toInt())[1]
        v.text_year_4_q.text = quart[3].name_quarter
        v.card_q_4.setOnClickListener {
            pref.edit().putString("q", "4q").apply()
            setListener(v, quart, 3)
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
        c.set(YEAR, year)
        test.set(YEAR, year)
        test.set(DAY_OF_YEAR, 1)
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


    private fun returnJson (string: String): String {
        return activity!!.application.assets.open("$string.json").bufferedReader().use {
            it.readText()
        }
    }

    private fun startActivity(
        archiveFragment: ArchiveFragment,
        quart: Array<year>, num1: Int, num2: Int, v: View) {
        val intent = Intent(archiveFragment.context, DetailActivity::class.java)
        intent.putExtra("text_date", quart[num1].weeks[num2].num_week.toString() + " неделя, " + returnDates(quart[num1].weeks[num2].num_week_in_year, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[num1].weeks[num2].num_week_in_year, pref.getString("s", "")!!.toInt())[1])
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
        val afd = activity!!.assets.openFd("audio/"+pref.getString("season", "")+"/"+pref.getString("q", "")+"/all.mp3")
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
            quart[n_q].weeks[0].num_week.toString() + " неделя, " + returnDates(quart[n_q].weeks[0].num_week_in_year, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[n_q].weeks[0].num_week_in_year, pref.getString("s", "")!!.toInt())[1]
        v.text_date_2.text =
            quart[n_q].weeks[1].num_week.toString() + " неделя, " + returnDates(quart[n_q].weeks[1].num_week_in_year, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[n_q].weeks[1].num_week_in_year, pref.getString("s", "")!!.toInt())[1]
        v.text_date_3.text =
            quart[n_q].weeks[2].num_week.toString() + " неделя, " + returnDates(quart[n_q].weeks[2].num_week_in_year, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[n_q].weeks[2].num_week_in_year, pref.getString("s", "")!!.toInt())[1]
        v.text_date_4.text =
            quart[n_q].weeks[3].num_week.toString() + " неделя, " + returnDates(quart[n_q].weeks[3].num_week_in_year, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[n_q].weeks[3].num_week_in_year, pref.getString("s", "")!!.toInt())[1]
        v.text_date_5.text =
            quart[n_q].weeks[4].num_week.toString() + " неделя, " + returnDates(quart[n_q].weeks[4].num_week_in_year, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[n_q].weeks[4].num_week_in_year, pref.getString("s", "")!!.toInt())[1]
        v.text_date_6.text =
            quart[n_q].weeks[5].num_week.toString() + " неделя, " + returnDates(quart[n_q].weeks[5].num_week_in_year, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[n_q].weeks[5].num_week_in_year, pref.getString("s", "")!!.toInt())[1]
        v.text_date_7.text =
            quart[n_q].weeks[6].num_week.toString() + " неделя, " + returnDates(quart[n_q].weeks[6].num_week_in_year, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[n_q].weeks[6].num_week_in_year, pref.getString("s", "")!!.toInt())[1]
        v.text_date_8.text =
            quart[n_q].weeks[7].num_week.toString() + " неделя, " + returnDates(quart[n_q].weeks[7].num_week_in_year, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[n_q].weeks[7].num_week_in_year, pref.getString("s", "")!!.toInt())[1]
        v.text_date_9.text =
            quart[n_q].weeks[8].num_week.toString() + " неделя, " + returnDates(quart[n_q].weeks[8].num_week_in_year, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[n_q].weeks[8].num_week_in_year, pref.getString("s", "")!!.toInt())[1]
        v.text_date_10.text =
            quart[n_q].weeks[9].num_week.toString() + " неделя, " + returnDates(quart[n_q].weeks[9].num_week_in_year, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[n_q].weeks[9].num_week_in_year, pref.getString("s", "")!!.toInt())[1]
        v.text_date_11.text =
            quart[n_q].weeks[10].num_week.toString() + " неделя, " + returnDates(quart[n_q].weeks[10].num_week_in_year, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[n_q].weeks[10].num_week_in_year, pref.getString("s", "")!!.toInt())[1]
        v.text_date_12.text =
            quart[n_q].weeks[11].num_week.toString() + " неделя, " + returnDates(quart[n_q].weeks[11].num_week_in_year, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[n_q].weeks[11].num_week_in_year, pref.getString("s", "")!!.toInt())[1]
        v.text_date_13.text =
            quart[n_q].weeks[12].num_week.toString() + " неделя, " + returnDates(quart[n_q].weeks[12].num_week_in_year, pref.getString("s", "")!!.toInt())[0] + "  —  " + returnDates(quart[n_q].weeks[12].num_week_in_year, pref.getString("s", "")!!.toInt())[1]

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

        v.card_mp_1.setOnLongClickListener {
            startDialogMenu(v, v.text_verse_1.text.toString(), v.text_link_1.text.toString())
            pref.edit().putString("v", "1").apply()
            true
        }

        v.card_mp_2.setOnLongClickListener {
            startDialogMenu(v, v.text_verse_2.text.toString(), v.text_link_2.text.toString())
            pref.edit().putString("v", "2").apply()
            true
        }

        v.card_mp_3.setOnLongClickListener {
            startDialogMenu(v, v.text_verse_3.text.toString(), v.text_link_3.text.toString())
            pref.edit().putString("v", "3").apply()
            true
        }

        v.card_mp_4.setOnLongClickListener {
            startDialogMenu(v, v.text_verse_4.text.toString(), v.text_link_4.text.toString())
            pref.edit().putString("v", "4").apply()
            true
        }

        v.card_mp_5.setOnLongClickListener {
            startDialogMenu(v, v.text_verse_5.text.toString(), v.text_link_5.text.toString())
            pref.edit().putString("v", "5").apply()
            true
        }

        v.card_mp_6.setOnLongClickListener {
            startDialogMenu(v, v.text_verse_6.text.toString(), v.text_link_6.text.toString())
            pref.edit().putString("v", "6").apply()
            true
        }
        v.card_mp_7.setOnLongClickListener {
            startDialogMenu(v, v.text_verse_7.text.toString(), v.text_link_7.text.toString())
            pref.edit().putString("v", "7").apply()
            true
        }

        v.card_mp_8.setOnLongClickListener {
            startDialogMenu(v, v.text_verse_8.text.toString(), v.text_link_8.text.toString())
            pref.edit().putString("v", "8").apply()
            true
        }

        v.card_mp_9.setOnLongClickListener {
            startDialogMenu(v, v.text_verse_9.text.toString(), v.text_link_9.text.toString())
            pref.edit().putString("v", "9").apply()
            true
        }

        v.card_mp_10.setOnLongClickListener {
            startDialogMenu(v, v.text_verse_10.text.toString(), v.text_link_10.text.toString())
            pref.edit().putString("v", "10").apply()
            true
        }

        v.card_mp_11.setOnLongClickListener {
            startDialogMenu(v, v.text_verse_11.text.toString(), v.text_link_11.text.toString())
            pref.edit().putString("v", "11").apply()
            true
        }

        v.card_mp_12.setOnLongClickListener {
            startDialogMenu(v, v.text_verse_12.text.toString(), v.text_link_12.text.toString())
            pref.edit().putString("v", "12").apply()
            true
        }

        v.card_mp_13.setOnLongClickListener {
            startDialogMenu(v, v.text_verse_13.text.toString(), v.text_link_13.text.toString())
            pref.edit().putString("v", "13").apply()
            true
        }
    }

    private fun startDialogMenu (v: View, textCopy: String, link_short: String) {
        val items = arrayOf("Скопировать", "Прослушать")
        MaterialAlertDialogBuilder(context!!)
            .setItems(items) { _: DialogInterface, i: Int ->
                when (i) {
                    0 -> {
                        val clipboard =
                            context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText(link_short, "$textCopy $link_short")
                        clipboard.setPrimaryClip(clip)
                        Snackbar.make(v, "Стих скопирован в буфер обмена", Snackbar.LENGTH_LONG)
                            .show()
                    }
                    1 -> {
                        println("audio/"+pref.getString("season", "")+"/"+pref.getString("q", "")+"/"+pref.getString("v", "")+".mp3")
                        mps = MediaPlayer()
                        val afd = activity!!.assets.openFd("audio/"+pref.getString("year", "")+"/"+pref.getString("q", "")+"/"+pref.getString("v", "")+".mp3")
                        mps.setDataSource(afd.fileDescriptor,afd.startOffset,afd.length)
                        mps.prepare()
                        mps.isLooping = false
                        mps.start()
                    }
                }


            }
            .show()
    }

    @SuppressLint("SetTextI18n")
    private fun initSeason(v: View) {
        when (pref.getString("seas", "")) {
            "1s" -> {
                v.card_season_1.setCardBackgroundColor(resources.getColor(R.color.color_04))
                v.text_season_first.text = "4 сезон"
                v.text_year_first.text = pref.getString("1y", "") + " год"
                v.card_season_2.setCardBackgroundColor(resources.getColor(R.color.color_05))
                v.text_season_second.text = "5 сезон"
                v.text_year_second.text = pref.getString("2y", "") + " год"
                v.card_season_3.setCardBackgroundColor(resources.getColor(R.color.color_01))
                v.text_season_third.text = "1 сезон"
                v.text_year_third.text = pref.getString("3y", "") + " год"
                v.card_season_4.setCardBackgroundColor(resources.getColor(R.color.color_02))
                v.text_season_fourth.text = "2 сезон"
                v.text_year_fourth.text = pref.getString("4y", "") + " год"
                v.card_season_5.setCardBackgroundColor(resources.getColor(R.color.color_03))
                v.text_season_fifth.text = "3 сезон"
                v.text_year_fifth.text = pref.getString("5y", "") + " год"
            }
            "2s" -> {
                v.card_season_1.setCardBackgroundColor(resources.getColor(R.color.color_05))
                v.text_season_first.text = "5 сезон"
                v.text_year_first.text = pref.getString("1y", "") + " год"
                v.card_season_2.setCardBackgroundColor(resources.getColor(R.color.color_01))
                v.text_season_second.text = "1 сезон"
                v.text_year_second.text = pref.getString("2y", "") + " год"
                v.card_season_3.setCardBackgroundColor(resources.getColor(R.color.color_02))
                v.text_season_third.text = "2 сезон"
                v.text_year_third.text = pref.getString("3y", "") + " год"
                v.card_season_4.setCardBackgroundColor(resources.getColor(R.color.color_03))
                v.text_season_fourth.text = "3 сезон"
                v.text_year_fourth.text = pref.getString("4y", "") + " год"
                v.card_season_5.setCardBackgroundColor(resources.getColor(R.color.color_04))
                v.text_season_fifth.text = "4 сезон"
                v.text_year_fifth.text = pref.getString("5y", "") + " год"
            }
            "3s" -> {
                v.card_season_1.setCardBackgroundColor(resources.getColor(R.color.color_01))
                v.text_season_first.text = "1 сезон"
                v.text_year_first.text = pref.getString("1y", "") + " год"
                v.card_season_2.setCardBackgroundColor(resources.getColor(R.color.color_02))
                v.text_season_second.text = "2 сезон"
                v.text_year_second.text = pref.getString("2y", "") + " год"
                v.card_season_3.setCardBackgroundColor(resources.getColor(R.color.color_03))
                v.text_season_third.text = "3 сезон"
                v.text_year_third.text = pref.getString("3y", "") + " год"
                v.card_season_4.setCardBackgroundColor(resources.getColor(R.color.color_04))
                v.text_season_fourth.text = "4 сезон"
                v.text_year_fourth.text = pref.getString("4y", "") + " год"
                v.card_season_5.setCardBackgroundColor(resources.getColor(R.color.color_05))
                v.text_season_fifth.text = "5 сезон"
                v.text_year_fifth.text = pref.getString("5y", "") + " год"
            }
            "4s" -> {
                v.card_season_1.setCardBackgroundColor(resources.getColor(R.color.color_02))
                v.text_season_first.text = "2 сезон"
                v.text_year_first.text = pref.getString("1y", "") + " год"
                v.card_season_2.setCardBackgroundColor(resources.getColor(R.color.color_03))
                v.text_season_second.text = "3 сезон"
                v.text_year_second.text = pref.getString("2y", "") + " год"
                v.card_season_3.setCardBackgroundColor(resources.getColor(R.color.color_04))
                v.text_season_third.text = "4 сезон"
                v.text_year_third.text = pref.getString("3y", "") + " год"
                v.card_season_4.setCardBackgroundColor(resources.getColor(R.color.color_05))
                v.text_season_fourth.text = "5 сезон"
                v.text_year_fourth.text = pref.getString("4y", "") + " год"
                v.card_season_5.setCardBackgroundColor(resources.getColor(R.color.color_01))
                v.text_season_fifth.text = "1 сезон"
                v.text_year_fifth.text = pref.getString("5y", "") + " год"
            }
            "5s" -> {
                v.card_season_1.setCardBackgroundColor(resources.getColor(R.color.color_03))
                v.text_season_first.text = "3 сезон"
                v.text_year_first.text = pref.getString("1y", "") + " год"
                v.card_season_2.setCardBackgroundColor(resources.getColor(R.color.color_04))
                v.text_season_second.text = "4 сезон"
                v.text_year_second.text = pref.getString("2y", "") + " год"
                v.card_season_3.setCardBackgroundColor(resources.getColor(R.color.color_05))
                v.text_season_third.text = "5 сезон"
                v.text_year_third.text = pref.getString("3y", "") + " год"
                v.card_season_4.setCardBackgroundColor(resources.getColor(R.color.color_01))
                v.text_season_fourth.text = "1 сезон"
                v.text_year_fourth.text = pref.getString("4y", "") + " год"
                v.card_season_5.setCardBackgroundColor(resources.getColor(R.color.color_02))
                v.text_season_fifth.text = "2 сезон"
                v.text_year_fifth.text = pref.getString("5y", "") + " год"
            }
        }
    }
}


data class year (val name_quarter: String, val start_week: Int, val end_week: Int, val weeks: Array<week>)

data class week (val num_week: Int, val num_week_in_year: Int, val verse: verse)

data class verse (val link_full: String, val link_small: String, val RST: String, val BTI: String, val CASS: String, val NRP: String, val CSLAV: String, val SRP: String, val IBL: String)