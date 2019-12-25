package ru.thevlados.memorable.pearls.ui.menu.tests

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.ContactsContract
import android.view.*
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.tests_fragment, container, false)

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
            intent.putExtra("diff", v.spinner_diff.selectedItem.toString())
            startActivity(intent)
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
