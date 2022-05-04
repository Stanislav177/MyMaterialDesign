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
import com.example.mymaterialdesign.databinding.FragmentToDayBinding
import com.example.mymaterialdesign.utils.CLICK_TODAY
import com.example.mymaterialdesign.viewModel.PictureViewModel

class PictureToDay : Fragment() {
    private var _binding: FragmentToDayBinding? = null
    private val binding: FragmentToDayBinding
        get() {
            return _binding!!
        }

    private val liveData: PictureViewModel by lazy {
        ViewModelProvider(this).get(PictureViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToDayBinding.inflate(inflater, container, false)
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
        liveData.modDateDay(CLICK_TODAY)
        liveData.request()
    }

    private fun setInfoDisplay(appStatePictureOfTheDay: AppStatePictureOfTheDay.Success) {

        with(binding) {
            imageToDayPicturePager.load(appStatePictureOfTheDay.pdoServerResponse.url) {
                placeholder(R.drawable.ic_no_photo_vector)
            }

            titlePictureOfTheDayPicturePager.text = appStatePictureOfTheDay.pdoServerResponse.title

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