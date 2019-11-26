package ru.thevlados.memorable.pearls.ui.detail

import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import kotlinx.android.synthetic.main.main_fragment.*
import ru.thevlados.memorable.pearls.R


class DetailActivity : AppCompatActivity() {
    private var stateNow: String = ""
    private var radioNow: String = ""
    private val m = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("text_link_short")
        text_date.text = intent.getStringExtra("text_date")
        text_verse.text = intent.getStringExtra("text_verse_rst")
        text_link.text = intent.getStringExtra("text_link_full")
        val descriptor = assets.openFd("message.mp3")
        m.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
        descriptor.close()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                this.finish()
            }
            R.id.item_music -> {
                if (stateNow == "" || stateNow == "play") {
                    menuItem.icon = resources.getDrawable(R.drawable.ic_pause_black_24dp)
                    m.prepare()
                    m.setVolume(1f, 1f)
                    m.isLooping = true
                    m.start()
                    stateNow = "pause"
                } else if (stateNow == "pause") {
                    menuItem.icon = resources.getDrawable(R.drawable.ic_play_arrow_black_24dp)
                    stateNow = "play"
                    m.stop()
                }
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
                    else -> bottomSheet.radio_rst.isChecked = true
                }
                bottomSheet.radio_group.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
                    when (i) {
                        R.id.radio_bti ->  {
                            text_verse.text = intent.getStringExtra("text_verse_bti")
                            radioNow = "bti"
                        }
                        R.id.radio_rst ->  {
                            text_verse.text = intent.getStringExtra("text_verse_rst")
                            radioNow = "rst"
                        }                    }
                }
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
