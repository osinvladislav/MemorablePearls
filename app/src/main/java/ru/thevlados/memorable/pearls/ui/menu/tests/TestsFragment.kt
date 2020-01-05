package ru.thevlados.memorable.pearls.ui.menu.tests

import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.ContactsContract
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.archive_fragment.*
import kotlinx.android.synthetic.main.tests_fragment.*
import kotlinx.android.synthetic.main.tests_fragment.view.*
import ru.thevlados.memorable.pearls.MainActivity

import ru.thevlados.memorable.pearls.R
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

        when (pref.getString("year", "")) {
            "2017"-> {
                v.spinner_year_trane.setSelection(0)
                v.spinner_year_test.setSelection(0)
            }
            "2018"-> {
                v.spinner_year_trane.setSelection(1)
                v.spinner_year_test.setSelection(1)
            }
            "2019"-> {
                v.spinner_year_trane.setSelection(2)
                v.spinner_year_test.setSelection(2)
            }
            "2020"-> {
                v.spinner_year_trane.setSelection(3)
                v.spinner_year_test.setSelection(3)
            }
            "2021"-> {
                v.spinner_year_trane.setSelection(4)
                v.spinner_year_test.setSelection(4)
            }
        }

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
            pref.edit().putString("whatIs", "trane").apply()
        }
        v.card_test.setOnClickListener {
            (activity as MainActivity?)?.resetActionBar(childAction = true)
            setHasOptionsMenu(true)
            valNow = "detail"
            v.scroll_choose.isVisible = false
            v.scroll_test.isVisible = true
            pref.edit().putString("whatIs", "test").apply()
        }

        v.btn_trane.setOnClickListener {
            val intent = Intent(v.context, TraneActivity::class.java)
            intent.putExtra("year", v.spinner_year_trane.selectedItem.toString())
            intent.putExtra("quart", (v.spinner_quart_trane.selectedItemId+1).toString())
            startActivity(intent)
        }

        v.btn_test.setOnClickListener {
            val intent = Intent(v.context, TestActivity::class.java)
            intent.putExtra("year", v.spinner_year_test.selectedItem.toString())
            intent.putExtra("quart", (v.spinner_quart_test.selectedItemId+1).toString())
            startActivity(intent)
        }

        if (pref.contains("whatIs")) {
            when (pref.getString("whatIs", "")) {
                "trane" -> v.card_trane.performClick()
                "test" -> v.card_test.performClick()
            }
        }

        return v
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
        // TODO: Use the ViewModel
    }

}
