package com.example.mymaterialdesign.view.pictureOfTheDay

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.appState.AppStatePictureOfTheDay
import com.example.mymaterialdesign.databinding.FragmentPictureOfTheDayBinding
import com.example.mymaterialdesign.utils.CLICK_TAB_TODAY
import com.example.mymaterialdesign.utils.CLICK_TAB_YESTERDAY
import com.example.mymaterialdesign.view.settings.SettingFragment
import com.example.mymaterialdesign.viewModel.PictureViewModel
import com.google.android.material.tabs.TabLayout

class PictureOfTheDayFragment : Fragment() {
    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding
        get() {
            return _binding!!
        }

    private var day: Int = 0

    private val liveData: PictureViewModel by lazy {
        ViewModelProvider(this).get(PictureViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveData.getLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })
        requestAPI()
        initTabs()
    }

    private fun requestAPI() {
        liveData.modDateDay(day)
        liveData.request()
    }

    private fun renderData(appStatePictureOfTheDay: AppStatePictureOfTheDay) {
        when (appStatePictureOfTheDay) {
            is AppStatePictureOfTheDay.Error -> {
            }
            is AppStatePictureOfTheDay.ErrorCode -> {
            }
            is AppStatePictureOfTheDay.Loading -> {
            }
            is AppStatePictureOfTheDay.Success -> {
                setInfoDisplay(appStatePictureOfTheDay)
            }
        }
    }

    private fun setInfoDisplay(appStatePictureOfTheDay: AppStatePictureOfTheDay.Success) {

        with(binding) {
            imageToDay.load(appStatePictureOfTheDay.pdoServerResponse.url) {
                placeholder(R.drawable.ic_no_photo_vector)
            }

            titlePictureOfTheDay.text = appStatePictureOfTheDay.pdoServerResponse.title

            includeMainFragment.title.text =
                appStatePictureOfTheDay.pdoServerResponse.title
            includeMainFragment.explanation.text =
                appStatePictureOfTheDay.pdoServerResponse.explanation
        }

    }

    private fun initTabs() {
        binding.tabsPicture.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    CLICK_TAB_TODAY -> {
                        day = tab.position
                        requestAPI()
                    }
                    CLICK_TAB_YESTERDAY -> {
                        day = tab.position
                        requestAPI()
                    }
                    2 -> {
                    }
                }
            }


            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                Toast.makeText(context, "Setting", Toast.LENGTH_LONG).show()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, SettingFragment.newInstance())
                    .addToBackStack(" ").commit()
            }
            android.R.id.home -> {
                Toast.makeText(context, "Home", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }
}