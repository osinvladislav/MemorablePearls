package ru.thevlados.memorable.pearls.ui.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import kotlinx.android.synthetic.main.main_fragment.*
import ru.thevlados.memorable.pearls.R


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
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("text_link_short")
        text_date.text = intent.getStringExtra("text_date")
        when (pref.getString("translate", "")) {
            "radio_rst" -> {
                text_verse.text = intent.getStringExtra("text_verse_rst")
                radioNow = "rst"
                textCopy = intent.getStringExtra("text_verse_rst") + " " + intent.getStringExtra("text_link_short")
            }
            "radio_bti" -> {
                text_verse.text = intent.getStringExtra("text_verse_bti")
                radioNow = "bti"
                textCopy = intent.getStringExtra("text_verse_bti") + " " + intent.getStringExtra("text_link_short")
            }
            "radio_nrp" -> {
                text_verse.text = intent.getStringExtra("text_verse_nrp")
                radioNow = "nrp"
                textCopy = intent.getStringExtra("text_verse_nrp") + " " + intent.getStringExtra("text_link_short")
            }
            "radio_cslav" -> {
                text_verse.text = intent.getStringExtra("text_verse_cslav")
                radioNow = "cslav"
                textCopy = intent.getStringExtra("text_verse_cslav") + " " + intent.getStringExtra("text_link_short")
            }
            "radio_srp" -> {
                text_verse.text = intent.getStringExtra("text_verse_srp")
                radioNow = "srp"
                textCopy = intent.getStringExtra("text_verse_srp") + " " + intent.getStringExtra("text_link_short")
            }
            "radio_ibl" -> {
                text_verse.text = intent.getStringExtra("text_verse_ibl")
                radioNow = "ibl"
                textCopy = intent.getStringExtra("text_verse_ibl") + " " + intent.getStringExtra("text_link_short")
            }
        }
        text_link.text = intent.getStringExtra("text_link_full")
        val afd = assets.openFd("audio/"+pref.getString("year", "")+"/"+pref.getString("q", "")+"/"+pref.getString("v", "")+".mp3")
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
                Snackbar.make(contextView, "Стих скопирован в буфер обмена", Snackbar.LENGTH_LONG)
                    .show()
            }
            R.id.item_translate -> {
                val dialog = BottomSheetDialog(this)
                val bottomSheet = layoutInflater.inflate(R.layout.bottom_sheet, null)
                bottomSheet.btn_close.setOnClickListener { dialog.dismiss() }
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
                    else -> bottomSheet.radio_rst.isChecked = true
                }
                bottomSheet.radio_group.setOnCheckedChangeListener { _: RadioGroup, i: Int ->
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

}
