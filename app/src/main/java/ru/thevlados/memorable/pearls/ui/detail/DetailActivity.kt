package ru.thevlados.memorable.pearls.ui.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.activity_trane.*
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import kotlinx.android.synthetic.main.bottom_sheet.view.group_translate_ru
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_bbl
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_bti
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_cass
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_csb
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_cslav
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_esv
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_gnt
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_gw
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_ibl
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_kjv
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_nasb
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_nirv
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_niv
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_nkjv
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_nlt
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_nrp
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_rst
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_srp
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_ubio
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_ukrk
import kotlinx.android.synthetic.main.bottom_sheet.view.radio_utt
import kotlinx.android.synthetic.main.bottom_sheet.view.text_translate
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.text_link
import kotlinx.android.synthetic.main.main_fragment.text_verse
import ru.thevlados.memorable.pearls.R
import ru.thevlados.memorable.pearls.archive
import ru.thevlados.memorable.pearls.lang


class DetailActivity : AppCompatActivity() {
    private var stateNow: String = ""
    private var radioNow: String = ""
    private var textCopy: String = ""
    lateinit var pref: SharedPreferences
    var mp: MediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        pref = getSharedPreferences("settings", MODE_PRIVATE)
        when (pref.getString("theme", "")) {
            "dark" -> {
                if (Build.VERSION.SDK_INT >= 21) {
                    val window: Window = this.window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = this.resources.getColor(R.color.colorOnSecondary)
                }
            }
            "light" -> {
                if (Build.VERSION.SDK_INT >= 21) {
                    val window: Window = this.window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.statusBarColor = this.resources.getColor(R.color.colorPrimaryVariant)
                }
            }
        }

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("text_link_short")
        text_date.text = intent.getStringExtra("text_date")
        when (pref.getString("translate_"+pref.getString("lang", ""), "")) {
            "rst" -> {
                text_verse.text = intent.getStringExtra("text_verse_rst")
                radioNow = "rst"
                textCopy = intent.getStringExtra("text_verse_rst") + " " + intent.getStringExtra("text_link_short")
            }
            "bti" -> {
                text_verse.text = intent.getStringExtra("text_verse_bti")
                radioNow = "bti"
                textCopy = intent.getStringExtra("text_verse_bti") + " " + intent.getStringExtra("text_link_short")
            }
            "nrp" -> {
                text_verse.text = intent.getStringExtra("text_verse_nrp")
                radioNow = "nrp"
                textCopy = intent.getStringExtra("text_verse_nrp") + " " + intent.getStringExtra("text_link_short")
            }
            "cslav" -> {
                text_verse.text = intent.getStringExtra("text_verse_cslav")
                radioNow = "cslav"
                textCopy = intent.getStringExtra("text_verse_cslav") + " " + intent.getStringExtra("text_link_short")
            }
            "srp" -> {
                text_verse.text = intent.getStringExtra("text_verse_srp")
                radioNow = "srp"
                textCopy = intent.getStringExtra("text_verse_srp") + " " + intent.getStringExtra("text_link_short")
            }
            "ibl" -> {
                text_verse.text = intent.getStringExtra("text_verse_ibl")
                radioNow = "ibl"
                textCopy = intent.getStringExtra("text_verse_ibl") + " " + intent.getStringExtra("text_link_short")
            }
            "kjv" -> {
                text_verse.text = intent.getStringExtra("text_verse_kjv")
                radioNow = "kjv"
                textCopy = intent.getStringExtra("text_verse_kjv") + " " + intent.getStringExtra("text_link_short")
            }
            "nkjv" -> {
                text_verse.text = intent.getStringExtra("text_verse_nkjv")
                radioNow = "nkjv"
                textCopy = intent.getStringExtra("text_verse_nkjv") + " " + intent.getStringExtra("text_link_short")
            }
            "nasb" -> {
                text_verse.text = intent.getStringExtra("text_verse_nasb")
                radioNow = "nasb"
                textCopy = intent.getStringExtra("text_verse_nasb") + " " + intent.getStringExtra("text_link_short")
            }
            "csb" -> {
                text_verse.text = intent.getStringExtra("text_verse_csb")
                radioNow = "csb"
                textCopy = intent.getStringExtra("text_verse_csb") + " " + intent.getStringExtra("text_link_short")
            }
            "esv" -> {
                text_verse.text = intent.getStringExtra("text_verse_esv")
                radioNow = "esv"
                textCopy = intent.getStringExtra("text_verse_esv") + " " + intent.getStringExtra("text_link_short")
            }
            "gnt" -> {
                text_verse.text = intent.getStringExtra("text_verse_gnt")
                radioNow = "gnt"
                textCopy = intent.getStringExtra("text_verse_gnt") + " " + intent.getStringExtra("text_link_short")
            }
            "gw" -> {
                text_verse.text = intent.getStringExtra("text_verse_gw")
                radioNow = "gw"
                textCopy = intent.getStringExtra("text_verse_gw") + " " + intent.getStringExtra("text_link_short")
            }
            "nirv" -> {
                text_verse.text = intent.getStringExtra("text_verse_nirv")
                radioNow = "nirv"
                textCopy = intent.getStringExtra("text_verse_nirv") + " " + intent.getStringExtra("text_link_short")
            }
            "niv" -> {
                text_verse.text = intent.getStringExtra("text_verse_niv")
                radioNow = "niv"
                textCopy = intent.getStringExtra("text_verse_niv") + " " + intent.getStringExtra("text_link_short")
            }
            "nlt" -> {
                text_verse.text = intent.getStringExtra("text_verse_nlt")
                radioNow = "nlt"
                textCopy = intent.getStringExtra("text_verse_nlt") + " " + intent.getStringExtra("text_link_short")
            }
            "ubio" -> {
                text_verse.text = intent.getStringExtra("text_verse_ubio")
                radioNow = "ubio"
                textCopy = intent.getStringExtra("text_verse_ubio") + " " + intent.getStringExtra("text_link_short")
            }
            "ukrk" -> {
                text_verse.text = intent.getStringExtra("text_verse_ukrk")
                radioNow = "ukrk"
                textCopy = intent.getStringExtra("text_verse_ukrk") + " " + intent.getStringExtra("text_link_short")
            }
            "utt" -> {
                text_verse.text = intent.getStringExtra("text_verse_utt")
                radioNow = "utt"
                textCopy = intent.getStringExtra("text_verse_utt") + " " + intent.getStringExtra("text_link_short")
            }
            "bbl" -> {
                text_verse.text = intent.getStringExtra("text_verse_bbl")
                radioNow = "bbl"
                textCopy = intent.getStringExtra("text_verse_bbl") + " " + intent.getStringExtra("text_link_short")
            }
            else -> initLang(pref.getString("lang", "")!!).error
        }
        text_link.text = intent.getStringExtra("text_link_full")
        val afd = assets.openFd("audio/"+pref.getString("lang", "")+"/"+pref.getString("season", "")+"/"+pref.getString("q", "")+"/"+pref.getString("v", "")+".mp3")
        mp.setDataSource(afd.fileDescriptor,afd.startOffset,afd.length)
        mp.prepare()
        mp.isLooping = true
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                this.finish()
            }
            R.id.item_music -> {
                if (stateNow == "" || stateNow == "play") {
                    mp.start()
                    menuItem.icon = resources.getDrawable(R.drawable.ic_pause_black_24dp)
                    stateNow = "pause"
                } else if (stateNow == "pause") {
                    mp.pause()
                    menuItem.icon = resources.getDrawable(R.drawable.ic_play_arrow_black_24dp)
                    stateNow = "play"
                }
            }
            R.id.item_copy -> {
                val clipboard =
                    getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText(intent.getStringExtra("text_link_short"), textCopy)
                clipboard.setPrimaryClip(clip)
                val contextView: View = findViewById(R.id.layout_detail)
                Snackbar.make(contextView, initLang(pref.getString("lang", "")!!).copy_notify, Snackbar.LENGTH_LONG)
                    .show()
            }
            R.id.item_translate -> {
                val dialog = BottomSheetDialog(this)
                val bottomSheet = layoutInflater.inflate(R.layout.bottom_sheet, null)
                bottomSheet.btn_close.setOnClickListener { dialog.dismiss() }
                initLangBtm(pref.getString("lang", "")!!, bottomSheet)
                dialog.setContentView(bottomSheet)
                dialog.show()
                when (radioNow) {
                    "rst" -> bottomSheet.radio_rst.isChecked = true
                    "bti" -> bottomSheet.radio_bti.isChecked = true
                    "cass" -> bottomSheet.radio_cass.isChecked = true
                    "nrp" -> bottomSheet.radio_nrp.isChecked = true
                    "cslav" -> bottomSheet.radio_cslav.isChecked = true
                    "srp" -> bottomSheet.radio_srp.isChecked = true
                    "ibl" -> bottomSheet.radio_ibl.isChecked = true
                    "kjv" -> bottomSheet.radio_kjv.isChecked = true
                    "nkjv" -> bottomSheet.radio_nkjv.isChecked = true
                    "nasb" -> bottomSheet.radio_nasb.isChecked = true
                    "csb" -> bottomSheet.radio_csb.isChecked = true
                    "esv" -> bottomSheet.radio_esv.isChecked = true
                    "gnt" -> bottomSheet.radio_gnt.isChecked = true
                    "gw" -> bottomSheet.radio_gw.isChecked = true
                    "nirv" -> bottomSheet.radio_nirv.isChecked = true
                    "niv" -> bottomSheet.radio_niv.isChecked = true
                    "nlt" -> bottomSheet.radio_nlt.isChecked = true
                    "ubio" -> bottomSheet.radio_ubio.isChecked = true
                    "ukrk" -> bottomSheet.radio_ukrk.isChecked = true
                    "utt" -> bottomSheet.radio_utt.isChecked = true
                    "bbl" -> bottomSheet.radio_bbl.isChecked = true
                    else -> bottomSheet.radio_rst.isChecked = true
                }
                when(pref.getString("lang", "")) {
                    "ru" -> {
                        bottomSheet.group_translate_ru.visibility = View.VISIBLE
                        bottomSheet.group_translate_en.visibility = View.GONE
                        bottomSheet.group_translate_ua.visibility = View.GONE
                        bottomSheet.group_translate_by.visibility = View.GONE
                    }
                    "en" -> {
                        bottomSheet.group_translate_ru.visibility = View.GONE
                        bottomSheet.group_translate_en.visibility = View.VISIBLE
                        bottomSheet.group_translate_ua.visibility = View.GONE
                        bottomSheet.group_translate_by.visibility = View.GONE
                    }
                    "ua" -> {
                        bottomSheet.group_translate_ru.visibility = View.GONE
                        bottomSheet.group_translate_en.visibility = View.GONE
                        bottomSheet.group_translate_ua.visibility = View.VISIBLE
                        bottomSheet.group_translate_by.visibility = View.GONE
                    }
                    "by" -> {
                        bottomSheet.group_translate_ru.visibility = View.GONE
                        bottomSheet.group_translate_en.visibility = View.GONE
                        bottomSheet.group_translate_ua.visibility = View.GONE
                        bottomSheet.group_translate_by.visibility = View.VISIBLE
                    }
                }
                bottomSheet.group_translate_ru.setOnCheckedChangeListener { _: RadioGroup, i: Int ->
                    when (i) {
                        R.id.radio_bti ->  {
                            text_verse.text = intent.getStringExtra("text_verse_bti")
                            radioNow = "bti"
                            textCopy = intent.getStringExtra("text_verse_bti") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_rst ->  {
                            text_verse.text = intent.getStringExtra("text_verse_rst")
                            radioNow = "rst"
                            textCopy = intent.getStringExtra("text_verse_rst") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_cass ->  {
                            text_verse.text = intent.getStringExtra("text_verse_cass")
                            radioNow = "cass"
                            textCopy = intent.getStringExtra("text_verse_cass") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_nrp ->  {
                            text_verse.text = intent.getStringExtra("text_verse_nrp")
                            radioNow = "nrp"
                            textCopy = intent.getStringExtra("text_verse_nrp") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_cslav ->  {
                            text_verse.text = intent.getStringExtra("text_verse_cslav")
                            radioNow = "cslav"
                            textCopy = intent.getStringExtra("text_verse_cslav") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_srp ->  {
                            text_verse.text = intent.getStringExtra("text_verse_srp")
                            radioNow = "srp"
                            textCopy = intent.getStringExtra("text_verse_srp") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_ibl ->  {
                            text_verse.text = intent.getStringExtra("text_verse_ibl")
                            radioNow = "ibl"
                            textCopy = intent.getStringExtra("text_verse_ibl") + " " + intent.getStringExtra("text_link_short")
                        }
                    }
                }
                bottomSheet.group_translate_en.setOnCheckedChangeListener { _: RadioGroup, i: Int ->
                    when (i) {
                        R.id.radio_kjv -> {
                            text_verse.text = intent.getStringExtra("text_verse_kjv")
                            radioNow = "kjv"
                            textCopy = intent.getStringExtra("text_verse_kjv") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_nkjv -> {
                            text_verse.text = intent.getStringExtra("text_verse_nkjv")
                            radioNow = "nkjv"
                            textCopy = intent.getStringExtra("text_verse_nkjv") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_nasb -> {
                            text_verse.text = intent.getStringExtra("text_verse_nasb")
                            radioNow = "nasb"
                            textCopy = intent.getStringExtra("text_verse_nasb") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_csb -> {
                            text_verse.text = intent.getStringExtra("text_verse_csb")
                            radioNow = "csb"
                            textCopy = intent.getStringExtra("text_verse_csb") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_esv -> {
                            text_verse.text = intent.getStringExtra("text_verse_esv")
                            radioNow = "esv"
                            textCopy = intent.getStringExtra("text_verse_esv") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_gnt -> {
                            text_verse.text = intent.getStringExtra("text_verse_gnt")
                            radioNow = "gnt"
                            textCopy = intent.getStringExtra("text_verse_gnt") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_gw -> {
                            text_verse.text = intent.getStringExtra("text_verse_gw")
                            radioNow = "gw"
                            textCopy = intent.getStringExtra("text_verse_gw") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_nirv -> {
                            text_verse.text = intent.getStringExtra("text_verse_nirv")
                            radioNow = "nirv"
                            textCopy = intent.getStringExtra("text_verse_nirv") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_niv -> {
                            text_verse.text = intent.getStringExtra("text_verse_niv")
                            radioNow = "niv"
                            textCopy = intent.getStringExtra("text_verse_niv") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_nlt -> {
                            text_verse.text = intent.getStringExtra("text_verse_nlt")
                            radioNow = "nlt"
                            textCopy = intent.getStringExtra("text_verse_nlt") + " " + intent.getStringExtra("text_link_short")
                        }
                    }
                }
                bottomSheet.group_translate_ua.setOnCheckedChangeListener { _: RadioGroup, i: Int ->
                    when (i) {
                        R.id.radio_ubio -> {
                            text_verse.text = intent.getStringExtra("text_verse_ubio")
                            radioNow = "ubio"
                            textCopy = intent.getStringExtra("text_verse_ubio") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_ukrk -> {
                            text_verse.text = intent.getStringExtra("text_verse_ukrk")
                            radioNow = "ukrk"
                            textCopy = intent.getStringExtra("text_verse_ukrk") + " " + intent.getStringExtra("text_link_short")
                        }
                        R.id.radio_utt -> {
                            text_verse.text = intent.getStringExtra("text_verse_utt")
                            radioNow = "utt"
                            textCopy = intent.getStringExtra("text_verse_utt") + " " + intent.getStringExtra("text_link_short")
                        }
                    }
                }
                bottomSheet.group_translate_by.setOnCheckedChangeListener { _: RadioGroup, i: Int ->
                    when (i) {
                        R.id.radio_bbl ->  {
                            text_verse.text = intent.getStringExtra("text_verse_bbl")
                            radioNow = "bbl"
                            textCopy = intent.getStringExtra("text_verse_bbl") + " " + intent.getStringExtra("text_link_short")
                        }
                    }
                }
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        mp.stop()
        super.onDestroy()
    }

    private fun initLang(string: String): archive {
        val str = application.assets.open("lang/$string.json").bufferedReader().use {
            it.readText()
        }
        val lang: lang = Gson().fromJson<lang>(str, lang::class.java)
        return lang.archive
    }

    private fun initLangBtm(string: String, v: View) {
        val str = application.assets.open("lang/$string.json").bufferedReader().use {
            it.readText()
        }
        val lang: lang = Gson().fromJson<lang>(str, lang::class.java)
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
        v.btn_close.text = lang.finish.btn_end
    }

}
