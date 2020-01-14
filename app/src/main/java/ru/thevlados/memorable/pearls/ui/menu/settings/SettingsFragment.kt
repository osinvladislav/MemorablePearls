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

        if (pref.getString("theme","") == "dark") v.switch_dark.isChecked = true

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

        v.group_translate.setOnCheckedChangeListener { radioGroup: RadioGroup, _: Int ->
            pref.edit().putString("translate", resources.getResourceEntryName(radioGroup.checkedRadioButtonId)).apply()
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

}
