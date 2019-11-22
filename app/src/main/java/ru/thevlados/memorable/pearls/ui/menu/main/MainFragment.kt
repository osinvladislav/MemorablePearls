package ru.thevlados.memorable.pearls.ui.menu.main

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.main_fragment.view.*

import ru.thevlados.memorable.pearls.R
import ru.thevlados.memorable.pearls.ui.detail.DetailActivity

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.main_fragment, container, false)

        v.card_mp_today.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("text_date", v.text_date.text)
            intent.putExtra("text_verse", v.text_verse.text)
            intent.putExtra("text_link", v.text_link.text)
            ContextCompat.startActivity(v.context, intent, null)
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

}
