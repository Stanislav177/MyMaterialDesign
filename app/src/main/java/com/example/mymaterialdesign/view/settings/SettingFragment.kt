package com.example.mymaterialdesign.view.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mymaterialdesign.databinding.FragmentSettingBinding
import com.example.mymaterialdesign.utils.KEY_SP
import com.example.mymaterialdesign.utils.KEY_TAB_THEME
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
        setOptionsMenu()

        super.onViewCreated(view, savedInstanceState)

        initTabTheme()
        initTabsColorTheme()
    }

    private fun getClickTab(): Int {
        val sharedPreferences = parentActivity.getSharedPreferences(
            KEY_SP,
            AppCompatActivity.MODE_PRIVATE
        )
        return sharedPreferences.getInt(KEY_TAB_THEME, 0)
    }

    private fun initTabTheme() {
        binding.tabsTheme.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        Toast.makeText(context, "tab0", Toast.LENGTH_LONG).show()
                    }
                    1 -> {
                        Toast.makeText(context, "tab1", Toast.LENGTH_LONG).show()
                    }
                    2 -> {
                        Toast.makeText(context, "tab2", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun initTabsColorTheme() {
        binding.tabsColorTheme.selectTab(binding.tabsColorTheme.getTabAt(getClickTab()))
        binding.tabsColorTheme.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        parentActivity.setThemeSP(tab.position)

                    }
                    1 -> {
                        parentActivity.setThemeSP(tab.position)

                    }
                    2 -> {
                        parentActivity.setThemeSP(tab.position)
                    }
                }
                parentActivity.recreate()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        parentActivity.setThemeSP(tab.position)
                    }
                    1 -> {
                        parentActivity.setThemeSP(tab.position)
                    }
                    2 -> {
                        parentActivity.setThemeSP(tab.position)
                    }
                }
                parentActivity.recreate()
            }
        })
    }

    private fun setOptionsMenu() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appBarBottom)
        setHasOptionsMenu(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = SettingFragment()
    }
}