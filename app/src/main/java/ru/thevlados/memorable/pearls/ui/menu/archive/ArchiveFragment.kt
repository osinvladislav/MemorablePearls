package ru.thevlados.memorable.pearls.ui.menu.archive

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import com.google.gson.Gson
import kotlinx.android.synthetic.main.archive_fragment.view.*

import ru.thevlados.memorable.pearls.R
import java.io.BufferedReader

class ArchiveFragment : Fragment() {

    private lateinit var viewModel: ArchiveViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.archive_fragment, container, false)
        v.card_third.setOnClickListener{
            val json_string = activity!!.application.assets.open("2019-ru.json").bufferedReader().use{
                it.readText()
            }
            val quart: Array<year> = Gson().fromJson<Array<year>>(json_string, Array<year>::class.java)
            v.scroll_quarters.visibility = View.VISIBLE
            v.scroll_years.visibility = View.GONE
            v.text_date_1_q.text = quart[0].date_from + "  —  " + quart[0].date_to
            v.text_date_2_q.text = quart[1].date_from + "  —  " + quart[1].date_to
            v.text_date_3_q.text = quart[2].date_from + "  —  " + quart[2].date_to
            v.text_date_4_q.text = quart[3].date_from + "  —  " + quart[3].date_to

            v.text_year_1_q.text = quart[0].name_quarter
            v.text_year_2_q.text = quart[1].name_quarter
            v.text_year_3_q.text = quart[2].name_quarter
            v.text_year_4_q.text = quart[3].name_quarter
        }
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ArchiveViewModel::class.java)
    }

}

data class year (val name_quarter: String, val date_from: String, val date_to: String, val weeks: Array<week>)

data class week (val num_week: Int, val date_begin: String, val date_finish: String, val verse: verse)

data class verse (val link_full: String, val link_small: String, val RST: String, val BTI: String)