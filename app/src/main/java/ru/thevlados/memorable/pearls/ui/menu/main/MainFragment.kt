package ru.thevlados.memorable.pearls.ui.menu.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.main_fragment, container, false)

        val sdfh = SimpleDateFormat("HH")
        val currentHour = sdfh.format(Date())
        when {
            currentHour.toInt() < 6 -> {
                v.text_headline.text = "Доброй ночи, Владислав!"
            }
            currentHour.toInt() in 7..10 -> {
                v.text_headline.text = "Доброе утро, Владислав!"
            }
            currentHour.toInt() in 11..16 -> {
                v.text_headline.text = "Добрый день, Владислав!"
            }
            currentHour.toInt() in 17..22 -> {
                v.text_headline.text = "Добрый вечер, Владислав!"
            }
            currentHour.toInt() > 22 -> {
                v.text_headline.text = "Доброй ночи, Владислав!"
            }
        }

        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val currentDate = sdf.format(Date())
        println(currentDate)

        val jsonString = activity!!.application.assets.open("2019-ru.json").bufferedReader().use{
            it.readText()
        }
        val quart: Array<year> = Gson().fromJson<Array<year>>(jsonString, Array<year>::class.java)

        quart.forEachIndexed {index, it ->
            if (sdf.parse(currentDate) in sdf.parse(it.date_from)..sdf.parse(it.date_to)) {
                when (index) {
                    0 -> {
                        v.card_mp_today.setCardBackgroundColor(resources.getColor(R.color.color_01))
                    }
                    1 -> {
                        v.card_mp_today.setCardBackgroundColor(resources.getColor(R.color.color_02))
                    }
                    2 -> {
                        v.card_mp_today.setCardBackgroundColor(resources.getColor(R.color.color_03))
                    }
                    3 -> {
                        v.card_mp_today.setCardBackgroundColor(resources.getColor(R.color.color_04))
                    }
                }
                it.weeks.forEachIndexed {indexik, item ->
                    if (sdf.parse(currentDate) in sdf.parse(item.date_begin)..sdf.parse(item.date_finish)) {
                        v.text_date.text = item.num_week.toString() + " неделя, " + item.date_begin  + "  —  " + item.date_finish
                        v.text_verse.text = item.verse.RST
                        v.text_link.text = item.verse.link_full
                        v.card_mp_today.setOnClickListener {
                            val intent = Intent(context, DetailActivity::class.java)
                            intent.putExtra("text_date", v.text_date.text as String)
                            intent.putExtra("text_verse_rst", item.verse.RST)
                            intent.putExtra("text_verse_bti", item.verse.BTI)
                            intent.putExtra("text_link", item.verse.link_full)
                            ContextCompat.startActivity(v.context, intent, null)
                        }

                        v.text_open_now.setOnClickListener {
                            val navController = findNavController()
                            val args = Bundle()
                            args.putBoolean("isQuart", true)
                            when {
                                SimpleDateFormat("yyyy").format(Date()) == "2019" -> {
                                    args.putInt("int", 3)
                                }
                                SimpleDateFormat("yyyy").format(Date()) == "2020" -> {
                                    args.putInt("int", 4)
                                }
                                SimpleDateFormat("yyyy").format(Date()) == "2021" -> {
                                    args.putInt("int", 5)
                                }
                            }
                            args.putInt("int1", index+1)
                            navController.navigate(R.id.navigation_archive, args)
                        }
                    }
                }
            }
        }

        v.text_trans_now.setOnClickListener {
            val navController = findNavController()
            val args = Bundle()
            args.putBoolean("isTrain", true)
            args.putBoolean("isCheck", false)
            navController.navigate(R.id.navigation_tests, args)
        }

        v.text_check_know.setOnClickListener {
            val navController = findNavController()
            val args = Bundle()
            args.putBoolean("isTrain", false)
            args.putBoolean("isCheck", true)
            navController.navigate(R.id.navigation_tests, args)
        }

        v.text_switch_translate.setOnClickListener {
            val navController = findNavController()
            val args = Bundle()
            args.putBoolean("isTrans", true)
            navController.navigate(R.id.navigation_settings, args)
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

}
