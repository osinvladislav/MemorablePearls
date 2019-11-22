package ru.thevlados.memorable.pearls.ui.detail

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import kotlinx.android.synthetic.main.main_fragment.*
import ru.thevlados.memorable.pearls.R


class DetailActivity : AppCompatActivity() {
    private var stateNow: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("text_link")
        text_date.text = intent.getStringExtra("text_date")
        text_verse.text = intent.getStringExtra("text_verse")
        text_link.text = intent.getStringExtra("text_link")
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                this.finish()
            }
            R.id.item_music -> {
                if (stateNow == "" || stateNow == "play") {
                    menuItem.icon = resources.getDrawable(R.drawable.ic_pause_black_24dp)
                    stateNow = "pause"
                } else if (stateNow == "pause") {
                    menuItem.icon = resources.getDrawable(R.drawable.ic_play_arrow_black_24dp)
                    stateNow = "play"
                }
            }
            R.id.item_translate -> {
                val dialog = BottomSheetDialog(this)
                val bottomSheet = layoutInflater.inflate(R.layout.bottom_sheet, null)
                bottomSheet.btn_close.setOnClickListener { dialog.dismiss() }
                dialog.setContentView(bottomSheet)
                dialog.show()
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
