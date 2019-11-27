package ru.thevlados.memorable.pearls.ui.menu.archive

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import kotlinx.android.synthetic.main.archive_fragment.*
import kotlinx.android.synthetic.main.archive_fragment.view.*
import kotlinx.android.synthetic.main.main_fragment.view.*
import ru.thevlados.memorable.pearls.MainActivity
import ru.thevlados.memorable.pearls.R
import ru.thevlados.memorable.pearls.ui.detail.DetailActivity
import ru.thevlados.memorable.pearls.ui.menu.tests.TestsFragment


class ArchiveFragment : Fragment() {
    private lateinit var viewModel: ArchiveViewModel
    private lateinit var valNow: String
    private lateinit var pref: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.archive_fragment, container, false)
        pref = activity!!.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)
        valNow = "year"
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
                        }
                        "quart" -> {
                            valNow = "year"
                            scroll_quarters.visibility = View.GONE
                            scroll_years.visibility = View.VISIBLE
                            (activity as MainActivity?)?.resetActionBar(false)
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
            (activity as MainActivity?)?.resetActionBar(true)
            setHasOptionsMenu(true)
            valNow = "quart"
            val jsonString = returnJson("2017-ru")

            val quart: Array<year> =
                Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
            funJson(v, quart)
        }
        v.card_second.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(true)
            setHasOptionsMenu(true)
            valNow = "quart"
            val jsonString = returnJson("2018-ru")

            val quart: Array<year> =
                Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
            funJson(v, quart)
        }
        v.card_third.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(true)
            setHasOptionsMenu(true)
            valNow = "quart"
            val jsonString = returnJson("2019-ru")

            val quart: Array<year> =
                Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
            funJson(v, quart)
        }
        v.card_fourth.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(true)
            setHasOptionsMenu(true)
            valNow = "quart"
            val jsonString = returnJson("2020-ru")

            val quart: Array<year> =
                Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
            funJson(v, quart)
        }
        v.card_fifth.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(true)
            setHasOptionsMenu(true)
            valNow = "quart"
            val jsonString = returnJson("2021-ru")

            val quart: Array<year> =
                Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)
            funJson(v, quart)
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
            valNow = "week"
            v.scroll_quarters.visibility = View.GONE
            v.scroll_weeks.visibility = View.VISIBLE
            v.card_mp_1.setCardBackgroundColor(resources.getColor(R.color.color_01))
            v.card_mp_1.setOnClickListener {
                startActivity(this, quart, 0, 0, v)
            }
            v.card_mp_2.setCardBackgroundColor(resources.getColor(R.color.color_01))
            v.card_mp_2.setOnClickListener {
                startActivity(this, quart, 0, 1, v)
            }
            v.card_mp_3.setCardBackgroundColor(resources.getColor(R.color.color_01))
            v.card_mp_3.setOnClickListener {
                startActivity(this, quart, 0, 2, v)
            }
            v.card_mp_4.setCardBackgroundColor(resources.getColor(R.color.color_01))
            v.card_mp_4.setOnClickListener {
                startActivity(this, quart, 0, 3, v)
            }
            v.card_mp_5.setCardBackgroundColor(resources.getColor(R.color.color_01))
            v.card_mp_5.setOnClickListener {
                startActivity(this, quart, 0, 4, v)
            }
            v.card_mp_6.setCardBackgroundColor(resources.getColor(R.color.color_01))
            v.card_mp_6.setOnClickListener {
                startActivity(this, quart, 0, 5, v)
            }
            v.card_mp_7.setCardBackgroundColor(resources.getColor(R.color.color_01))
            v.card_mp_7.setOnClickListener {
                startActivity(this, quart, 0, 6, v)
            }
            v.card_mp_8.setCardBackgroundColor(resources.getColor(R.color.color_01))
            v.card_mp_8.setOnClickListener {
                startActivity(this, quart, 0, 7, v)
            }
            v.card_mp_9.setCardBackgroundColor(resources.getColor(R.color.color_01))
            v.card_mp_9.setOnClickListener {
                startActivity(this, quart, 0, 8, v)
            }
            v.card_mp_10.setCardBackgroundColor(resources.getColor(R.color.color_01))
            v.card_mp_10.setOnClickListener {
                startActivity(this, quart, 0, 9, v)
            }
            v.card_mp_11.setCardBackgroundColor(resources.getColor(R.color.color_01))
            v.card_mp_11.setOnClickListener {
                startActivity(this, quart, 0, 10, v)
            }
            v.card_mp_12.setCardBackgroundColor(resources.getColor(R.color.color_01))
            v.card_mp_12.setOnClickListener {
                startActivity(this, quart, 0, 11, v)
            }
            v.card_mp_13.setCardBackgroundColor(resources.getColor(R.color.color_01))
            v.card_mp_13.setOnClickListener {
                startActivity(this, quart, 0, 12, v)
            }
            v.text_date_1.text =
                quart[0].weeks[0].num_week.toString() + " неделя, " + quart[0].weeks[0].date_begin + "  —  " + quart[0].weeks[0].date_finish
            v.text_date_2.text =
                quart[0].weeks[1].num_week.toString() + " неделя, " + quart[0].weeks[1].date_begin + "  —  " + quart[0].weeks[1].date_finish
            v.text_date_3.text =
                quart[0].weeks[2].num_week.toString() + " неделя, " + quart[0].weeks[2].date_begin + "  —  " + quart[0].weeks[2].date_finish
            v.text_date_4.text =
                quart[0].weeks[3].num_week.toString() + " неделя, " + quart[0].weeks[3].date_begin + "  —  " + quart[0].weeks[3].date_finish
            v.text_date_5.text =
                quart[0].weeks[4].num_week.toString() + " неделя, " + quart[0].weeks[4].date_begin + "  —  " + quart[0].weeks[4].date_finish
            v.text_date_6.text =
                quart[0].weeks[5].num_week.toString() + " неделя, " + quart[0].weeks[5].date_begin + "  —  " + quart[0].weeks[5].date_finish
            v.text_date_7.text =
                quart[0].weeks[6].num_week.toString() + " неделя, " + quart[0].weeks[6].date_begin + "  —  " + quart[0].weeks[6].date_finish
            v.text_date_8.text =
                quart[0].weeks[7].num_week.toString() + " неделя, " + quart[0].weeks[7].date_begin + "  —  " + quart[0].weeks[7].date_finish
            v.text_date_9.text =
                quart[0].weeks[8].num_week.toString() + " неделя, " + quart[0].weeks[8].date_begin + "  —  " + quart[0].weeks[8].date_finish
            v.text_date_10.text =
                quart[0].weeks[9].num_week.toString() + " неделя, " + quart[0].weeks[9].date_begin + "  —  " + quart[0].weeks[9].date_finish
            v.text_date_11.text =
                quart[0].weeks[10].num_week.toString() + " неделя, " + quart[0].weeks[10].date_begin + "  —  " + quart[0].weeks[10].date_finish
            v.text_date_12.text =
                quart[0].weeks[11].num_week.toString() + " неделя, " + quart[0].weeks[11].date_begin + "  —  " + quart[0].weeks[11].date_finish
            v.text_date_13.text =
                quart[0].weeks[12].num_week.toString() + " неделя, " + quart[0].weeks[12].date_begin + "  —  " + quart[0].weeks[12].date_finish

            quart[0].weeks.forEachIndexed { index, _ ->
                setTextik(quart, 0, index, index+1, v)
            }

            v.text_link_1.text = quart[0].weeks[0].verse.link_small + " "
            v.text_link_2.text = quart[0].weeks[1].verse.link_small + " "
            v.text_link_3.text = quart[0].weeks[2].verse.link_small + " "
            v.text_link_4.text = quart[0].weeks[3].verse.link_small + " "
            v.text_link_5.text = quart[0].weeks[4].verse.link_small + " "
            v.text_link_6.text = quart[0].weeks[5].verse.link_small + " "
            v.text_link_7.text = quart[0].weeks[6].verse.link_small + " "
            v.text_link_8.text = quart[0].weeks[7].verse.link_small + " "
            v.text_link_9.text = quart[0].weeks[8].verse.link_small + " "
            v.text_link_10.text = quart[0].weeks[9].verse.link_small + " "
            v.text_link_11.text = quart[0].weeks[10].verse.link_small + " "
            v.text_link_12.text = quart[0].weeks[11].verse.link_small + " "
            v.text_link_13.text = quart[0].weeks[12].verse.link_small + " "
        }
        v.text_date_2_q.text = quart[1].date_from + "  —  " + quart[1].date_to
        v.text_year_2_q.text = quart[1].name_quarter
        v.card_2_q.setOnClickListener {
            v.scroll_quarters.visibility = View.GONE
            v.scroll_weeks.visibility = View.VISIBLE
            valNow = "week"
            v.card_mp_1.setCardBackgroundColor(resources.getColor(R.color.color_02))
            v.card_mp_1.setOnClickListener {
                startActivity(this, quart, 1, 0, v)
            }
            v.card_mp_2.setCardBackgroundColor(resources.getColor(R.color.color_02))
            v.card_mp_2.setOnClickListener {
                startActivity(this, quart, 1, 1, v)
            }
            v.card_mp_3.setCardBackgroundColor(resources.getColor(R.color.color_02))
            v.card_mp_3.setOnClickListener {
                startActivity(this, quart, 1, 2, v)
            }
            v.card_mp_4.setCardBackgroundColor(resources.getColor(R.color.color_02))
            v.card_mp_4.setOnClickListener {
                startActivity(this, quart, 1, 3, v)
            }
            v.card_mp_5.setCardBackgroundColor(resources.getColor(R.color.color_02))
            v.card_mp_5.setOnClickListener {
                startActivity(this, quart, 1, 4, v)
            }
            v.card_mp_6.setCardBackgroundColor(resources.getColor(R.color.color_02))
            v.card_mp_6.setOnClickListener {
                startActivity(this, quart, 1, 5, v)
            }
            v.card_mp_7.setCardBackgroundColor(resources.getColor(R.color.color_02))
            v.card_mp_7.setOnClickListener {
                startActivity(this, quart, 1, 6, v)
            }
            v.card_mp_8.setCardBackgroundColor(resources.getColor(R.color.color_02))
            v.card_mp_8.setOnClickListener {
                startActivity(this, quart, 1, 7, v)
            }
            v.card_mp_9.setCardBackgroundColor(resources.getColor(R.color.color_02))
            v.card_mp_9.setOnClickListener {
                startActivity(this, quart, 1, 8, v)
            }
            v.card_mp_10.setCardBackgroundColor(resources.getColor(R.color.color_02))
            v.card_mp_10.setOnClickListener {
                startActivity(this, quart, 1, 9, v)
            }
            v.card_mp_11.setCardBackgroundColor(resources.getColor(R.color.color_02))
            v.card_mp_11.setOnClickListener {
                startActivity(this, quart, 1, 10, v)
            }
            v.card_mp_12.setCardBackgroundColor(resources.getColor(R.color.color_02))
            v.card_mp_12.setOnClickListener {
                startActivity(this, quart, 1, 11, v)
            }
            v.card_mp_13.setCardBackgroundColor(resources.getColor(R.color.color_02))
            v.card_mp_13.setOnClickListener {
                startActivity(this, quart, 1, 12, v)
            }
            v.text_date_1.text =
                quart[1].weeks[0].num_week.toString() + " неделя, " + quart[1].weeks[0].date_begin + "  —  " + quart[1].weeks[0].date_finish
            v.text_date_2.text =
                quart[1].weeks[1].num_week.toString() + " неделя, " + quart[1].weeks[1].date_begin + "  —  " + quart[1].weeks[1].date_finish
            v.text_date_3.text =
                quart[1].weeks[2].num_week.toString() + " неделя, " + quart[1].weeks[2].date_begin + "  —  " + quart[1].weeks[2].date_finish
            v.text_date_4.text =
                quart[1].weeks[3].num_week.toString() + " неделя, " + quart[1].weeks[3].date_begin + "  —  " + quart[1].weeks[3].date_finish
            v.text_date_5.text =
                quart[1].weeks[4].num_week.toString() + " неделя, " + quart[1].weeks[4].date_begin + "  —  " + quart[1].weeks[4].date_finish
            v.text_date_6.text =
                quart[1].weeks[5].num_week.toString() + " неделя, " + quart[1].weeks[5].date_begin + "  —  " + quart[1].weeks[5].date_finish
            v.text_date_7.text =
                quart[1].weeks[6].num_week.toString() + " неделя, " + quart[1].weeks[6].date_begin + "  —  " + quart[1].weeks[6].date_finish
            v.text_date_8.text =
                quart[1].weeks[7].num_week.toString() + " неделя, " + quart[1].weeks[7].date_begin + "  —  " + quart[1].weeks[7].date_finish
            v.text_date_9.text =
                quart[1].weeks[8].num_week.toString() + " неделя, " + quart[1].weeks[8].date_begin + "  —  " + quart[1].weeks[8].date_finish
            v.text_date_10.text =
                quart[1].weeks[9].num_week.toString() + " неделя, " + quart[1].weeks[9].date_begin + "  —  " + quart[1].weeks[9].date_finish
            v.text_date_11.text =
                quart[1].weeks[10].num_week.toString() + " неделя, " + quart[1].weeks[10].date_begin + "  —  " + quart[1].weeks[10].date_finish
            v.text_date_12.text =
                quart[1].weeks[11].num_week.toString() + " неделя, " + quart[1].weeks[11].date_begin + "  —  " + quart[1].weeks[11].date_finish
            v.text_date_13.text =
                quart[1].weeks[12].num_week.toString() + " неделя, " + quart[1].weeks[12].date_begin + "  —  " + quart[1].weeks[12].date_finish

            quart[1].weeks.forEachIndexed { index, _ ->
                setTextik(quart, 1, index, index+1, v)
            }

            v.text_link_1.text = quart[1].weeks[0].verse.link_small + " "
            v.text_link_2.text = quart[1].weeks[1].verse.link_small + " "
            v.text_link_3.text = quart[1].weeks[2].verse.link_small + " "
            v.text_link_4.text = quart[1].weeks[3].verse.link_small + " "
            v.text_link_5.text = quart[1].weeks[4].verse.link_small + " "
            v.text_link_6.text = quart[1].weeks[5].verse.link_small + " "
            v.text_link_7.text = quart[1].weeks[6].verse.link_small + " "
            v.text_link_8.text = quart[1].weeks[7].verse.link_small + " "
            v.text_link_9.text = quart[1].weeks[8].verse.link_small + " "
            v.text_link_10.text = quart[1].weeks[9].verse.link_small + " "
            v.text_link_11.text = quart[1].weeks[10].verse.link_small + " "
            v.text_link_12.text = quart[1].weeks[11].verse.link_small + " "
            v.text_link_13.text = quart[1].weeks[12].verse.link_small + " "
        }
        v.text_date_3_q.text = quart[2].date_from + "  —  " + quart[2].date_to
        v.text_year_3_q.text = quart[2].name_quarter
        v.card_3_q.setOnClickListener {
            valNow = "week"
            v.scroll_quarters.visibility = View.GONE
            v.scroll_weeks.visibility = View.VISIBLE
            v.card_mp_1.setCardBackgroundColor(resources.getColor(R.color.color_03))
            v.card_mp_1.setOnClickListener {
                startActivity(this, quart, 2, 0, v)
            }
            v.card_mp_2.setCardBackgroundColor(resources.getColor(R.color.color_03))
            v.card_mp_2.setOnClickListener {
                startActivity(this, quart, 2, 1, v)
            }
            v.card_mp_3.setCardBackgroundColor(resources.getColor(R.color.color_03))
            v.card_mp_3.setOnClickListener {
                startActivity(this, quart, 2, 2, v)
            }
            v.card_mp_4.setCardBackgroundColor(resources.getColor(R.color.color_03))
            v.card_mp_4.setOnClickListener {
                startActivity(this, quart, 2, 3, v)
            }
            v.card_mp_5.setCardBackgroundColor(resources.getColor(R.color.color_03))
            v.card_mp_5.setOnClickListener {
                startActivity(this, quart, 2, 4, v)
            }
            v.card_mp_6.setCardBackgroundColor(resources.getColor(R.color.color_03))
            v.card_mp_6.setOnClickListener {
                startActivity(this, quart, 2, 5, v)
            }
            v.card_mp_7.setCardBackgroundColor(resources.getColor(R.color.color_03))
            v.card_mp_7.setOnClickListener {
                startActivity(this, quart, 2, 6, v)
            }
            v.card_mp_8.setCardBackgroundColor(resources.getColor(R.color.color_03))
            v.card_mp_8.setOnClickListener {
                startActivity(this, quart, 2, 7, v)
            }
            v.card_mp_9.setCardBackgroundColor(resources.getColor(R.color.color_03))
            v.card_mp_9.setOnClickListener {
                startActivity(this, quart, 2, 8, v)
            }
            v.card_mp_10.setCardBackgroundColor(resources.getColor(R.color.color_03))
            v.card_mp_10.setOnClickListener {
                startActivity(this, quart, 2, 9, v)
            }
            v.card_mp_11.setCardBackgroundColor(resources.getColor(R.color.color_03))
            v.card_mp_11.setOnClickListener {
                startActivity(this, quart, 2, 10, v)
            }
            v.card_mp_12.setCardBackgroundColor(resources.getColor(R.color.color_03))
            v.card_mp_12.setOnClickListener {
                startActivity(this, quart, 2, 11, v)
            }
            v.card_mp_13.setCardBackgroundColor(resources.getColor(R.color.color_03))
            v.card_mp_13.setOnClickListener {
                startActivity(this, quart, 2, 12, v)
            }
            v.text_date_1.text =
                quart[2].weeks[0].num_week.toString() + " неделя, " + quart[2].weeks[0].date_begin + "  —  " + quart[2].weeks[0].date_finish
            v.text_date_2.text =
                quart[2].weeks[1].num_week.toString() + " неделя, " + quart[2].weeks[1].date_begin + "  —  " + quart[2].weeks[1].date_finish
            v.text_date_3.text =
                quart[2].weeks[2].num_week.toString() + " неделя, " + quart[2].weeks[2].date_begin + "  —  " + quart[2].weeks[2].date_finish
            v.text_date_4.text =
                quart[2].weeks[3].num_week.toString() + " неделя, " + quart[2].weeks[3].date_begin + "  —  " + quart[2].weeks[3].date_finish
            v.text_date_5.text =
                quart[2].weeks[4].num_week.toString() + " неделя, " + quart[2].weeks[4].date_begin + "  —  " + quart[2].weeks[4].date_finish
            v.text_date_6.text =
                quart[2].weeks[5].num_week.toString() + " неделя, " + quart[2].weeks[5].date_begin + "  —  " + quart[2].weeks[5].date_finish
            v.text_date_7.text =
                quart[2].weeks[6].num_week.toString() + " неделя, " + quart[2].weeks[6].date_begin + "  —  " + quart[2].weeks[6].date_finish
            v.text_date_8.text =
                quart[2].weeks[7].num_week.toString() + " неделя, " + quart[2].weeks[7].date_begin + "  —  " + quart[2].weeks[7].date_finish
            v.text_date_9.text =
                quart[2].weeks[8].num_week.toString() + " неделя, " + quart[2].weeks[8].date_begin + "  —  " + quart[2].weeks[8].date_finish
            v.text_date_10.text =
                quart[2].weeks[9].num_week.toString() + " неделя, " + quart[2].weeks[9].date_begin + "  —  " + quart[2].weeks[9].date_finish
            v.text_date_11.text =
                quart[2].weeks[10].num_week.toString() + " неделя, " + quart[2].weeks[10].date_begin + "  —  " + quart[2].weeks[10].date_finish
            v.text_date_12.text =
                quart[2].weeks[11].num_week.toString() + " неделя, " + quart[2].weeks[11].date_begin + "  —  " + quart[2].weeks[11].date_finish
            v.text_date_13.text =
                quart[2].weeks[12].num_week.toString() + " неделя, " + quart[2].weeks[12].date_begin + "  —  " + quart[2].weeks[12].date_finish

            quart[2].weeks.forEachIndexed { index, _ ->
                setTextik(quart, 2, index, index+1, v)
            }

            v.text_link_1.text = quart[2].weeks[0].verse.link_small + " "
            v.text_link_2.text = quart[2].weeks[1].verse.link_small + " "
            v.text_link_3.text = quart[2].weeks[2].verse.link_small + " "
            v.text_link_4.text = quart[2].weeks[3].verse.link_small + " "
            v.text_link_5.text = quart[2].weeks[4].verse.link_small + " "
            v.text_link_6.text = quart[2].weeks[5].verse.link_small + " "
            v.text_link_7.text = quart[2].weeks[6].verse.link_small + " "
            v.text_link_8.text = quart[2].weeks[7].verse.link_small + " "
            v.text_link_9.text = quart[2].weeks[8].verse.link_small + " "
            v.text_link_10.text = quart[2].weeks[9].verse.link_small + " "
            v.text_link_11.text = quart[2].weeks[10].verse.link_small + " "
            v.text_link_12.text = quart[2].weeks[11].verse.link_small + " "
            v.text_link_13.text = quart[2].weeks[12].verse.link_small + " "

        }
        v.text_date_4_q.text = quart[3].date_from + "  —  " + quart[3].date_to
        v.text_year_4_q.text = quart[3].name_quarter
        v.card_4_q.setOnClickListener {
            valNow = "week"
            v.scroll_quarters.visibility = View.GONE
            v.scroll_weeks.visibility = View.VISIBLE
            v.card_mp_1.setCardBackgroundColor(resources.getColor(R.color.color_04))
            v.card_mp_1.setOnClickListener {
                startActivity(this, quart, 3, 0, v)
            }
            v.card_mp_2.setCardBackgroundColor(resources.getColor(R.color.color_04))
            v.card_mp_2.setOnClickListener {
                startActivity(this, quart, 3, 1, v)
            }
            v.card_mp_3.setCardBackgroundColor(resources.getColor(R.color.color_04))
            v.card_mp_3.setOnClickListener {
                startActivity(this, quart, 3, 2, v)
            }
            v.card_mp_4.setCardBackgroundColor(resources.getColor(R.color.color_04))
            v.card_mp_4.setOnClickListener {
                startActivity(this, quart, 3, 3, v)
            }
            v.card_mp_5.setCardBackgroundColor(resources.getColor(R.color.color_04))
            v.card_mp_5.setOnClickListener {
                startActivity(this, quart, 3, 4, v)
            }
            v.card_mp_6.setCardBackgroundColor(resources.getColor(R.color.color_04))
            v.card_mp_6.setOnClickListener {
                startActivity(this, quart, 3, 5, v)
            }
            v.card_mp_7.setCardBackgroundColor(resources.getColor(R.color.color_04))
            v.card_mp_7.setOnClickListener {
                startActivity(this, quart, 3, 6, v)
            }
            v.card_mp_8.setCardBackgroundColor(resources.getColor(R.color.color_04))
            v.card_mp_8.setOnClickListener {
                startActivity(this, quart, 3, 7, v)
            }
            v.card_mp_9.setCardBackgroundColor(resources.getColor(R.color.color_04))
            v.card_mp_9.setOnClickListener {
                startActivity(this, quart, 3, 8, v)
            }
            v.card_mp_10.setCardBackgroundColor(resources.getColor(R.color.color_04))
            v.card_mp_10.setOnClickListener {
                startActivity(this, quart, 3, 9, v)
            }
            v.card_mp_11.setCardBackgroundColor(resources.getColor(R.color.color_04))
            v.card_mp_11.setOnClickListener {
                startActivity(this, quart, 3, 10, v)
            }
            v.card_mp_12.setCardBackgroundColor(resources.getColor(R.color.color_04))
            v.card_mp_12.setOnClickListener {
                startActivity(this, quart, 3, 11, v)
            }
            v.card_mp_13.setCardBackgroundColor(resources.getColor(R.color.color_04))
            v.card_mp_13.setOnClickListener {
                startActivity(this, quart, 3, 12, v)
            }
            v.text_date_1.text =
                quart[3].weeks[0].num_week.toString() + " неделя, " + quart[3].weeks[0].date_begin + "  —  " + quart[3].weeks[0].date_finish
            v.text_date_2.text =
                quart[3].weeks[1].num_week.toString() + " неделя, " + quart[3].weeks[1].date_begin + "  —  " + quart[3].weeks[1].date_finish
            v.text_date_3.text =
                quart[3].weeks[2].num_week.toString() + " неделя, " + quart[3].weeks[2].date_begin + "  —  " + quart[3].weeks[2].date_finish
            v.text_date_4.text =
                quart[3].weeks[3].num_week.toString() + " неделя, " + quart[3].weeks[3].date_begin + "  —  " + quart[3].weeks[3].date_finish
            v.text_date_5.text =
                quart[3].weeks[4].num_week.toString() + " неделя, " + quart[3].weeks[4].date_begin + "  —  " + quart[3].weeks[4].date_finish
            v.text_date_6.text =
                quart[3].weeks[5].num_week.toString() + " неделя, " + quart[3].weeks[5].date_begin + "  —  " + quart[3].weeks[5].date_finish
            v.text_date_7.text =
                quart[3].weeks[6].num_week.toString() + " неделя, " + quart[3].weeks[6].date_begin + "  —  " + quart[3].weeks[6].date_finish
            v.text_date_8.text =
                quart[3].weeks[7].num_week.toString() + " неделя, " + quart[3].weeks[7].date_begin + "  —  " + quart[3].weeks[7].date_finish
            v.text_date_9.text =
                quart[3].weeks[8].num_week.toString() + " неделя, " + quart[3].weeks[8].date_begin + "  —  " + quart[3].weeks[8].date_finish
            v.text_date_10.text =
                quart[3].weeks[9].num_week.toString() + " неделя, " + quart[3].weeks[9].date_begin + "  —  " + quart[3].weeks[9].date_finish
            v.text_date_11.text =
                quart[3].weeks[10].num_week.toString() + " неделя, " + quart[3].weeks[10].date_begin + "  —  " + quart[3].weeks[10].date_finish
            v.text_date_12.text =
                quart[3].weeks[11].num_week.toString() + " неделя, " + quart[3].weeks[11].date_begin + "  —  " + quart[3].weeks[11].date_finish
            v.text_date_13.text =
                quart[3].weeks[12].num_week.toString() + " неделя, " + quart[3].weeks[12].date_begin + "  —  " + quart[3].weeks[12].date_finish

            quart[3].weeks.forEachIndexed { index, _ ->
                setTextik(quart, 3, index, index+1, v)
            }

            v.text_link_1.text = quart[3].weeks[0].verse.link_small + " "
            v.text_link_2.text = quart[3].weeks[1].verse.link_small + " "
            v.text_link_3.text = quart[3].weeks[2].verse.link_small + " "
            v.text_link_4.text = quart[3].weeks[3].verse.link_small + " "
            v.text_link_5.text = quart[3].weeks[4].verse.link_small + " "
            v.text_link_6.text = quart[3].weeks[5].verse.link_small + " "
            v.text_link_7.text = quart[3].weeks[6].verse.link_small + " "
            v.text_link_8.text = quart[3].weeks[7].verse.link_small + " "
            v.text_link_9.text = quart[3].weeks[8].verse.link_small + " "
            v.text_link_10.text = quart[3].weeks[9].verse.link_small + " "
            v.text_link_11.text = quart[3].weeks[10].verse.link_small + " "
            v.text_link_12.text = quart[3].weeks[11].verse.link_small + " "
            v.text_link_13.text = quart[3].weeks[12].verse.link_small + " "
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
}


data class year (val name_quarter: String, val date_from: String, val date_to: String, val weeks: Array<week>)

data class week (val num_week: Int, val date_begin: String, val date_finish: String, val verse: verse)

data class verse (val link_full: String, val link_small: String, val RST: String, val BTI: String, val CASS: String, val NRP: String, val CSLAV: String, val SRP: String, val IBL: String)