package com.example.mymaterialdesign.view.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mymaterialdesign.databinding.FragmentSettingBinding
import com.example.mymaterialdesign.utils.KEY_SP
import com.example.mymaterialdesign.utils.KEY_TAB_THEME_COLOR
import com.example.mymaterialdesign.utils.KEY_TAB_THEME_LIGHT_DARK
import com.example.mymaterialdesign.view.MainActivity
import com.google.android.material.tabs.TabLayout

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding
        get() = _binding!!

    private lateinit var parentActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = (context as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTabTheme()
        initTabsColorTheme()
    }

    private fun getClickTabTheme(): Int {
        val sharedPreferences = parentActivity.getSharedPreferences(
            KEY_SP,
            AppCompatActivity.MODE_PRIVATE
        )
        return sharedPreferences.getInt(KEY_TAB_THEME_LIGHT_DARK, 2)
    }

    private fun getClickTabColor(): Int {
        val sharedPreferences = parentActivity.getSharedPreferences(
            KEY_SP,
            AppCompatActivity.MODE_PRIVATE
        )
        return sharedPreferences.getInt(KEY_TAB_THEME_COLOR, 0)
    }

    private fun initTabTheme() {
        binding.tabsTheme.selectTab(binding.tabsColorTheme.getTabAt(getClickTabTheme()))
        binding.tabsTheme.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        parentActivity.setThemeLightDarkSP(tab.position)
                    }
                    1 -> {
                        parentActivity.setThemeLightDarkSP(tab.position)
                    }
                    2 -> {
                        parentActivity.setThemeLightDarkSP(tab.position)
                    }
                }
                parentActivity.recreate()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        parentActivity.setThemeLightDarkSP(tab.position)
                    }
                    1 -> {
                        parentActivity.setThemeLightDarkSP(tab.position)
                    }
                    2 -> {
                        parentActivity.setThemeLightDarkSP(tab.position)
                    }
                }
                parentActivity.recreate()
            }

        })
    }

    private fun initTabsColorTheme() {
        binding.tabsColorTheme.selectTab(binding.tabsColorTheme.getTabAt(getClickTabColor()))
        binding.tabsColorTheme.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        parentActivity.setThemeColorSP(tab.position)

                    }
                    1 -> {
                        parentActivity.setThemeColorSP(tab.position)

                    }
                    2 -> {
                        parentActivity.setThemeColorSP(tab.position)
                    }
                }
                parentActivity.recreate()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        parentActivity.setThemeColorSP(tab.position)
                    }
                    1 -> {
                        parentActivity.setThemeColorSP(tab.position)
                    }
                    2 -> {
                        parentActivity.setThemeColorSP(tab.position)
                    }
                }
                parentActivity.recreate()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = SettingFragment()
    }
}