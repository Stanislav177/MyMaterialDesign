package com.example.mymaterialdesign.view.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.appState.AppStatePictureOfTheDay
import com.example.mymaterialdesign.databinding.FragmentSystemBinding
import com.example.mymaterialdesign.databinding.FragmentToDayBinding
import com.example.mymaterialdesign.databinding.FragmentYesterdayBinding
import com.example.mymaterialdesign.viewModel.PictureViewModel

class PictureYesterday : Fragment() {
    private var _binding: FragmentYesterdayBinding? = null
    private val binding: FragmentYesterdayBinding
        get() {
        return _binding!!
    }
    private var day: Int = 1

    private val liveData: PictureViewModel by lazy {
        ViewModelProvider(this).get(PictureViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentYesterdayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveData.getLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })
        requestAPI()
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
    private fun requestAPI() {
        liveData.modDateDay(day)
        liveData.request()
    }

    private fun setInfoDisplay(appStatePictureOfTheDay: AppStatePictureOfTheDay.Success) {

        with(binding) {
            imageYesterdayPicturePager.load(appStatePictureOfTheDay.pdoServerResponse.url) {
                placeholder(R.drawable.ic_no_photo_vector)
            }

            titlePictureYesterdayPicturePager.text = appStatePictureOfTheDay.pdoServerResponse.title

            includeMainFragmentPicturePager.title.text =
                appStatePictureOfTheDay.pdoServerResponse.title
            includeMainFragmentPicturePager.explanation.text =
                appStatePictureOfTheDay.pdoServerResponse.explanation
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}