package com.example.mymaterialdesign.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.utils.*
import com.example.mymaterialdesign.view.main.MainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(startTheme(getThemeSP()))
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MainFragment.newInstance()).commit()
        }
    }

    fun setThemeSP(numberTheme: Int) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_THEME, numberTheme)
        editor.putInt(KEY_TAB_THEME,numberTheme)
        editor.apply()
    }

    private fun getThemeSP(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_THEME, -1)
    }

    private fun startTheme(numberTheme: Int): Int {
        return when (numberTheme) {
            THEME_GREY -> R.style.MyGrey
            THEME_TEAL -> R.style.MyTeal
            THEME_ORANGE -> R.style.MyOrange
            else -> 0
        }
    }
}