package ru.thevlados.memorable.pearls.ui.start

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_rst
import kotlinx.android.synthetic.main.settings_fragment.view.*
import ru.thevlados.memorable.pearls.MainActivity
import ru.thevlados.memorable.pearls.R


class StartActivity : AppCompatActivity() {
    lateinit var pref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        pref = getSharedPreferences("settings", MODE_PRIVATE)

        group_translate.radio_rst.isChecked = true
        radio_ru.isChecked = true
        btn_enter.isEnabled = false

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

        btn_enter.setOnClickListener {
            pref.edit().putString("name", edit_name.text.toString()).putString("translate", resources.getResourceEntryName(group_translate.checkedRadioButtonId)).putString("lang", resources.getResourceEntryName(radio_ru.id)).putString("theme", if (switch_dark.isChecked) "dark" else "light").apply()
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
