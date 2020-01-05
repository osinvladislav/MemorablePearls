package ru.thevlados.memorable.pearls.ui.menu.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.settings_fragment.view.*
import ru.thevlados.memorable.pearls.R


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
        val itemTrans = v.findViewById<RadioButton>(resources.getIdentifier(pref.getString("translate", ""), "id", context!!.packageName))
        itemTrans.isChecked = true
        val itemLang = v.findViewById<RadioButton>(resources.getIdentifier(pref.getString("lang", ""), "id", context!!.packageName))
        itemLang.isChecked = true
        val itemTheme = v.findViewById<RadioButton>(resources.getIdentifier(pref.getString("theme", ""), "id", context!!.packageName))
        itemTheme.isChecked = true

        v.btn_save.setOnClickListener {
            pref.edit().putString("name", edit_name.text.toString()).putString("translate", resources.getResourceEntryName(group_translate.checkedRadioButtonId)).putString("lang", resources.getResourceEntryName(radio_ru.id)).putString("theme", resources.getResourceEntryName(group_theme.checkedRadioButtonId)).apply()
            when (v.group_theme.checkedRadioButtonId) {
                R.id.radio_light -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                R.id.radio_dark -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                R.id.radio_energy -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            }
            Toast.makeText(context, "Сохранено!", Toast.LENGTH_SHORT).show()
        }

        v.edit_name.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                v.btn_save.isEnabled = count > 0
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

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
    }

}
