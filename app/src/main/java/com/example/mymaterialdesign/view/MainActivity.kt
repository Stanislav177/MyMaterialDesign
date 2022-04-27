package com.example.mymaterialdesign.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.fragment.app.Fragment
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.databinding.ActivityMainBinding
import com.example.mymaterialdesign.utils.*
import com.example.mymaterialdesign.view.photoMars.PhotoMarsFragment
import com.example.mymaterialdesign.view.pictureOfTheDay.PictureOfTheDayFragment
import com.example.mymaterialdesign.view.viewpager.EarthFragment
import com.example.mymaterialdesign.view.viewpager.MarsFragment
import com.example.mymaterialdesign.view.viewpager.SystemFragment
import com.example.mymaterialdesign.view.viewpager.ViewPagerFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLightDarkTheme(getLightDarkThemeSP())
        setTheme(startTheme(getColorThemeSP()))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, PictureOfTheDayFragment.newInstance())
                .addToBackStack(" ").commit()
        }
        initNavigationBottom()
    }

    private fun toFragment(f: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, f).addToBackStack(" ").commit()
    }

    private fun initNavigationBottom() {
        with(binding) {
            bottomNavView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.imageDayMenuBottom -> {
                        toFragment(PictureOfTheDayFragment.newInstance())
                        true
                    }
                    R.id.weatherMenuBottom -> {
                        toFragment(ViewPagerFragment())
                        true
                    }
                    R.id.earthMenuBottom -> {
                        toFragment(EarthFragment())
                        true
                    }
                    R.id.marsMenuBottom -> {
                        toFragment(PhotoMarsFragment())
                        true
                    }
                    R.id.searchMenuBottom -> {
                        true
                    }
                    else -> true
                }
            }
            bottomNavView.selectedItemId = R.id.imageDayMenuBottom
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