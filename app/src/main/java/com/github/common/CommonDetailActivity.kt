package com.github.common

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.github.settings.Themer

abstract class CommonDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Themer.applyProperTheme(this, true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {// 左上角返回键，supportActionBar.setDisplayHomeAsUpEnabled(true)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item);
    }
}