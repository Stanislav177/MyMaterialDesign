package com.example.mymaterialdesign.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.utils.*
import com.example.mymaterialdesign.view.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLightDarkTheme(getLightDarkThemeSP())
        setTheme(startTheme(getColorThemeSP()))
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MainFragment.newInstance()).commit()
        }
    }

    fun setThemeColorSP(numberTheme: Int) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_THEME_COLOR, numberTheme)
        editor.putInt(KEY_TAB_THEME_COLOR, numberTheme)
        editor.apply()
    }

    fun setThemeLightDarkSP(numberTheme: Int) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_THEME_LIGHT_DARK, numberTheme)
        editor.putInt(KEY_TAB_THEME_LIGHT_DARK, numberTheme)
        editor.apply()
    }

    private fun getColorThemeSP(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_THEME_COLOR, -1)
    }

    private fun getLightDarkThemeSP(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_TAB_THEME_LIGHT_DARK, 1)
    }

    private fun setLightDarkTheme(numTheme: Int) {
        when (numTheme) {
            THEME_LIGHT -> {
                setDefaultNightMode(MODE_NIGHT_NO)
            }
            THEME_NIGHT -> {
                setDefaultNightMode(MODE_NIGHT_YES)
            }
            THEME_SYSTEM_DEFAULT -> {
                setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
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