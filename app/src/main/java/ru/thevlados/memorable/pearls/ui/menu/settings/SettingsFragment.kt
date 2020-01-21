package ru.thevlados.memorable.pearls.ui.menu.settings

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.settings_fragment.view.*
import ru.thevlados.memorable.pearls.R
import ru.thevlados.memorable.pearls.lang


class SettingsFragment : Fragment() {
    private lateinit var viewModel: SettingsViewModel
    lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.settings_fragment, container, false)
        pref = activity!!.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)
        v.edit_name.setText(pref.getString("name", ""))
        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        v.group_lang.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            when (radioGroup.checkedRadioButtonId) {
                R.id.radio_ru -> {
                    v.group_translate_ru.visibility = View.VISIBLE
                    v.group_translate_en.visibility = View.GONE
                    v.group_translate_ua.visibility = View.GONE
                    v.group_translate_by.visibility = View.GONE
                    initLang(returnLang("ru"), v)
                }
                R.id.radio_en -> {
                    v.group_translate_ru.visibility = View.GONE
                    v.group_translate_en.visibility = View.VISIBLE
                    v.group_translate_ua.visibility = View.GONE
                    v.group_translate_by.visibility = View.GONE
                    initLang(returnLang("en"), v)
                }
                R.id.radio_ua -> {
                    v.group_translate_ru.visibility = View.GONE
                    v.group_translate_en.visibility = View.GONE
                    v.group_translate_ua.visibility = View.VISIBLE
                    v.group_translate_by.visibility = View.GONE
                    initLang(returnLang("ua"), v)
                }
                R.id.radio_by -> {
                    v.group_translate_ru.visibility = View.GONE
                    v.group_translate_en.visibility = View.GONE
                    v.group_translate_ua.visibility = View.GONE
                    v.group_translate_by.visibility = View.VISIBLE
                    initLang(returnLang("by"), v)
                }
            }
            pref.edit().putString("lang", resources.getResourceEntryName(radioGroup.checkedRadioButtonId).split("_")[1]).apply()
        }

        var itemTrans = v.findViewById<RadioButton>(resources.getIdentifier("radio_"+pref.getString("translate_ru", ""), "id", context!!.packageName))
        itemTrans.isChecked = true
        itemTrans = v.findViewById(resources.getIdentifier("radio_"+pref.getString("translate_en", ""), "id", context!!.packageName))
        itemTrans.isChecked = true
        itemTrans = v.findViewById(resources.getIdentifier("radio_"+pref.getString("translate_ua", ""), "id", context!!.packageName))
        itemTrans.isChecked = true
        itemTrans = v.findViewById(resources.getIdentifier("radio_"+pref.getString("translate_by", ""), "id", context!!.packageName))
        itemTrans.isChecked = true
        val itemLang = v.findViewById<RadioButton>(resources.getIdentifier("radio_" + pref.getString("lang", ""), "id", context!!.packageName))
        itemLang.isChecked = true

        if (pref.getString("theme","") == "dark") v.switch_dark.isChecked = true

        pref.getString("lang", "")?.let { returnLang(it) }?.let { initLang(it, v) }

        v.switch_dark.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            if(b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                if (Build.VERSION.SDK_INT >= 21) {
                    val window: Window = activity!!.window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = this.resources.getColor(R.color.colorOnSecondary)
                }
                pref.edit().putString("theme", "dark").apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                if (Build.VERSION.SDK_INT >= 21) {
                    val window: Window = activity!!.window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = this.resources.getColor(R.color.colorPrimaryVariant)
                }
                pref.edit().putString("theme", "light").apply()
            }
        }

        v.group_translate_ru.setOnCheckedChangeListener { radioGroup: RadioGroup, _: Int ->
            pref.edit().putString("translate_ru", resources.getResourceEntryName(radioGroup.checkedRadioButtonId).split("_")[1]).apply()
        }
        v.group_translate_en.setOnCheckedChangeListener { radioGroup: RadioGroup, _: Int ->
            pref.edit().putString("translate_en", resources.getResourceEntryName(radioGroup.checkedRadioButtonId).split("_")[1]).apply()
        }
        v.group_translate_ua.setOnCheckedChangeListener { radioGroup: RadioGroup, _: Int ->
            pref.edit().putString("translate_ua", resources.getResourceEntryName(radioGroup.checkedRadioButtonId).split("_")[1]).apply()
        }
        v.group_translate_by.setOnCheckedChangeListener { radioGroup: RadioGroup, _: Int ->
            pref.edit().putString("translate_by", resources.getResourceEntryName(radioGroup.checkedRadioButtonId).split("_")[1]).apply()
        }

        v.edit_name.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                pref.edit().putString("name", editable.toString()).apply()
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
        })

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
    }

    private fun returnLang (string: String): String {
        return activity!!.application.assets.open("lang/$string.json").bufferedReader().use {
            it.readText()
        }
    }

    private fun initLang(str: String, v: View) {
        val lang: lang = Gson().fromJson<lang>(str, lang::class.java)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = lang.settings.text_action_bar
        v.edit_name.hint = lang.start.edit_name
        v.text_translate.text = lang.start.text_translate
        v.radio_rst.text = lang.start.radio_rst
        v.radio_bti.text = lang.start.radio_bti
        v.radio_nrp.text = lang.start.radio_nrp
        v.radio_cslav.text = lang.start.radio_cslav
        v.radio_srp.text = lang.start.radio_srp
        v.radio_cass.text = lang.start.radio_cass
        v.radio_ibl.text = lang.start.radio_ibl
        v.radio_kjv.text = lang.start.radio_kjv
        v.radio_nkjv.text = lang.start.radio_nkjv
        v.radio_nasb.text = lang.start.radio_nasb
        v.radio_csb.text = lang.start.radio_csb
        v.radio_esv.text = lang.start.radio_esv
        v.radio_gnt.text = lang.start.radio_gnt
        v.radio_gw.text = lang.start.radio_gw
        v.radio_nirv.text = lang.start.radio_nirv
        v.radio_niv.text = lang.start.radio_niv
        v.radio_nlt.text = lang.start.radio_nlt
        v.radio_ubio.text = lang.start.radio_ubio
        v.radio_ukrk.text = lang.start.radio_ukrk
        v.radio_utt.text = lang.start.radio_utt
        v.radio_bbl.text = lang.start.radio_bbl
        v.text_lang.text = lang.start.text_lang
        v.radio_ru.text = lang.start.radio_ru
        v.radio_en.text = lang.start.radio_en
        v.radio_ua.text = lang.start.radio_ua
        v.radio_by.text = lang.start.radio_by
        v.switch_dark.text = lang.start.switch_dark
    }

}
