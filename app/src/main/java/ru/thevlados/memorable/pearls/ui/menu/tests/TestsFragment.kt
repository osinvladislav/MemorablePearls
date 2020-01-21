package ru.thevlados.memorable.pearls.ui.menu.tests

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import kotlinx.android.synthetic.main.tests_fragment.*
import kotlinx.android.synthetic.main.tests_fragment.view.*
import ru.thevlados.memorable.pearls.MainActivity
import ru.thevlados.memorable.pearls.R
import ru.thevlados.memorable.pearls.lang
import ru.thevlados.memorable.pearls.ui.test.TestActivity
import ru.thevlados.memorable.pearls.ui.trane.TraneActivity


class TestsFragment : Fragment() {

    private lateinit var viewModel: TestsViewModel
    private var valNow = "choose"
    private lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.tests_fragment, container, false)
        pref = activity!!.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)

        initLang(returnLang(pref.getString("lang", "")!!), v)

        setHasOptionsMenu(true)
        v.isFocusableInTouchMode = true
        v.requestFocus()
        v.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    when (valNow) {
                        "detail" -> {
                            valNow = "choose"
                            v.scroll_choose.isVisible = true
                            v.scroll_trane.isVisible = false
                            v.scroll_test.isVisible = false
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
        val dayNames = arrayOf(pref.getString("1y", "").toString(),
            pref.getString("2y", "").toString(),
            pref.getString("3y", "").toString(),
            pref.getString("4y", "").toString(),
            pref.getString("5y", "").toString()
        )

        val adapter = ArrayAdapter<String>(
            v.context,
            android.R.layout.simple_spinner_item, dayNames
        )
        (adapter as ArrayAdapter<*>).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        v.spinner_year_trane.adapter = adapter
        v.spinner_year_test.adapter = adapter

        v.spinner_year_trane.setSelection(2)
        v.spinner_year_test.setSelection(2)

        when (pref.getString("q", "")) {
            "1q"-> {
                v.spinner_quart_trane.setSelection(0)
                v.spinner_quart_test.setSelection(0)
            }
            "2q"-> {
                v.spinner_quart_trane.setSelection(1)
                v.spinner_quart_test.setSelection(1)
            }
            "3q"-> {
                v.spinner_quart_trane.setSelection(2)
                v.spinner_quart_test.setSelection(2)
            }
            "4q"-> {
                v.spinner_quart_trane.setSelection(3)
                v.spinner_quart_test.setSelection(3)
            }
        }

        v.card_trane.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(childAction = true)
            setHasOptionsMenu(true)
            valNow = "detail"
            v.scroll_choose.isVisible = false
            v.scroll_trane.isVisible = true
        }
        v.card_test.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(childAction = true)
            setHasOptionsMenu(true)
            valNow = "detail"
            v.scroll_choose.isVisible = false
            v.scroll_test.isVisible = true
        }
        if (arguments != null) {
            if (arguments!!.getString("whatIs", "") == "trane") {
                v.card_trane.performClick()
            } else if (arguments!!.getString("whatIs", "") == "test") {
                v.card_test.performClick()
            }
        }

        v.btn_trane.setOnClickListener {
            val intent = Intent(v.context, TraneActivity::class.java)
            intent.putExtra("year", v.spinner_year_trane.selectedItem.toString())
            intent.putExtra("season", returnSeason((v.spinner_year_trane.selectedItemId+1).toInt()))
            intent.putExtra("quart", (v.spinner_quart_trane.selectedItemId+1).toString())
            startActivity(intent)
        }

        v.btn_test.setOnClickListener {
            val intent = Intent(v.context, TestActivity::class.java)
            intent.putExtra("year", v.spinner_year_test.selectedItem.toString())
            intent.putExtra("season", returnSeason((v.spinner_year_test.selectedItemId+1).toInt()))
            intent.putExtra("quart", (v.spinner_quart_test.selectedItemId+1).toString())
            startActivity(intent)
        }
        return v
    }

    private fun returnSeason(item: Int): String {
        var str = ""
        when(pref.getString("seas", "")) {
            "1s" -> {
                when (item) {
                    1 -> str = "4s"
                    2 -> str = "5s"
                    3 -> str = "1s"
                    4 -> str = "2s"
                    5 -> str = "3s"
                }
            }
            "2s" -> {
                when (item) {
                    1 -> str = "5s"
                    2 -> str = "1s"
                    3 -> str = "2s"
                    4 -> str = "3s"
                    5 -> str = "4s"
                }
            }
            "3s" -> {
                when (item) {
                    1 -> str = "1s"
                    2 -> str = "2s"
                    3 -> str = "3s"
                    4 -> str = "4s"
                    5 -> str = "5s"
                }
            }
            "4s" -> {
                when (item) {
                    1 -> str = "2s"
                    2 -> str = "3s"
                    3 -> str = "4s"
                    4 -> str = "5s"
                    5 -> str = "1s"
                }
            }
            "5s" -> {
                when (item) {
                    1 -> str = "3s"
                    2 -> str = "4s"
                    3 -> str = "5s"
                    4 -> str = "1s"
                    5 -> str = "2s"
                }
            }
        }
        return str
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                when (valNow) {
                    "detail" -> {
                        valNow = "choose"
                        scroll_choose.isVisible = true
                        scroll_trane.isVisible = false
                        scroll_test.isVisible = false
                        (activity as MainActivity?)?.resetActionBar(false)
                    }
                    else -> {
                        activity?.onBackPressed()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TestsViewModel::class.java)
    }

    private fun returnLang (string: String): String {
        return activity!!.application.assets.open("lang/$string.json").bufferedReader().use {
            it.readText()
        }
    }

    private fun initLang(str: String, v: View) {
        val lang = Gson().fromJson<lang>(str, lang::class.java)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = lang.tests.text_action_bar
        v.text_title.text = lang.tests.text_title
        v.text_trane.text = lang.tests.text_trane
        v.text_trane_desc.text = lang.tests.text_trane_desc
        v.text_test.text = lang.tests.text_test
        v.text_test_desc.text = lang.tests.text_test_desc
        v.text_title_trane.text = lang.tests.text_title_trane
        v.text_desc_trane.text = lang.tests.text_desc_trane
        v.text_select_year_trane.text = lang.tests.text_select_year_trane
        v.text_select_quart_trane.text = lang.tests.text_select_quart_trane
        v.btn_trane.text = lang.tests.btn_trane
        v.text_title_test.text = lang.tests.text_title_test
        v.text_desc_test.text = lang.tests.text_desc_test
        v.text_select_year_test.text = lang.tests.text_select_year_test
        v.text_select_quart_test.text = lang.tests.text_select_quart_test
        v.btn_test.text = lang.tests.btn_test
    }
}
