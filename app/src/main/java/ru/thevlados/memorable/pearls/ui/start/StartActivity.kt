package ru.thevlados.memorable.pearls.ui.start

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_rst
import ru.thevlados.memorable.pearls.MainActivity
import ru.thevlados.memorable.pearls.R
import ru.thevlados.memorable.pearls.lang


class StartActivity : AppCompatActivity() {
    lateinit var pref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        pref = getSharedPreferences("settings", MODE_PRIVATE)

        btn_enter.isEnabled = false

        initLang(returnLang("ru"))

        edit_name.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                btn_enter.isEnabled = count > 0
            }

            override fun afterTextChanged(editable: Editable) {
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
        })

        group_lang.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            when (radioGroup.checkedRadioButtonId) {
                R.id.radio_ru -> {
                    group_translate_ru.visibility = View.VISIBLE
                    group_translate_en.visibility = View.GONE
                    group_translate_ua.visibility = View.GONE
                    group_translate_by.visibility = View.GONE
                    initLang(returnLang("ru"))
                }
                R.id.radio_en -> {
                    group_translate_ru.visibility = View.GONE
                    group_translate_en.visibility = View.VISIBLE
                    group_translate_ua.visibility = View.GONE
                    group_translate_by.visibility = View.GONE
                    initLang(returnLang("en"))
                }
                R.id.radio_ua -> {
                    group_translate_ru.visibility = View.GONE
                    group_translate_en.visibility = View.GONE
                    group_translate_ua.visibility = View.VISIBLE
                    group_translate_by.visibility = View.GONE
                    initLang(returnLang("ua"))
                }
                R.id.radio_by -> {
                    group_translate_ru.visibility = View.GONE
                    group_translate_en.visibility = View.GONE
                    group_translate_ua.visibility = View.GONE
                    group_translate_by.visibility = View.VISIBLE
                    initLang(returnLang("by"))
                }
            }
        }

        btn_enter.setOnClickListener {
            pref.edit().putString("name", edit_name.text.toString())
                .putString("translate_ru", resources.getResourceEntryName(group_translate_ru.checkedRadioButtonId).split("_")[1])
                .putString("translate_en", resources.getResourceEntryName(group_translate_en.checkedRadioButtonId).split("_")[1])
                .putString("translate_ua", resources.getResourceEntryName(group_translate_ua.checkedRadioButtonId).split("_")[1])
                .putString("translate_by", resources.getResourceEntryName(group_translate_by.checkedRadioButtonId).split("_")[1])
                .putString("lang", resources.getResourceEntryName(group_lang.checkedRadioButtonId).split("_")[1])
                .putString("theme", if (switch_dark.isChecked) "dark" else "light")
                .apply()
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun returnLang (string: String): String {
        return application.assets.open("lang/$string.json").bufferedReader().use {
            it.readText()
        }
    }

    private fun initLang(str: String) {
        val lang: lang = Gson().fromJson<lang>(str, lang::class.java)
        supportActionBar?.title = lang.app_name
        text_hello.text = lang.start.text_hello
        edit_name.hint = lang.start.edit_name
        text_translate.text = lang.start.text_translate
        radio_rst.text = lang.start.radio_rst
        radio_bti.text = lang.start.radio_bti
        radio_nrp.text = lang.start.radio_nrp
        radio_cslav.text = lang.start.radio_cslav
        radio_srp.text = lang.start.radio_srp
        radio_cass.text = lang.start.radio_cass
        radio_ibl.text = lang.start.radio_ibl
        radio_kjv.text = lang.start.radio_kjv
        radio_nkjv.text = lang.start.radio_nkjv
        radio_nasb.text = lang.start.radio_nasb
        radio_csb.text = lang.start.radio_csb
        radio_esv.text = lang.start.radio_esv
        radio_gnt.text = lang.start.radio_gnt
        radio_gw.text = lang.start.radio_gw
        radio_nirv.text = lang.start.radio_nirv
        radio_niv.text = lang.start.radio_niv
        radio_nlt.text = lang.start.radio_nlt
        radio_ubio.text = lang.start.radio_ubio
        radio_ukrk.text = lang.start.radio_ukrk
        radio_utt.text = lang.start.radio_utt
        radio_bbl.text = lang.start.radio_bbl
        text_lang.text = lang.start.text_lang
        radio_ru.text = lang.start.radio_ru
        radio_en.text = lang.start.radio_en
        radio_ua.text = lang.start.radio_ua
        radio_by.text = lang.start.radio_by
        switch_dark.text = lang.start.switch_dark
        btn_enter.text = lang.start.btn_enter
    }
}
