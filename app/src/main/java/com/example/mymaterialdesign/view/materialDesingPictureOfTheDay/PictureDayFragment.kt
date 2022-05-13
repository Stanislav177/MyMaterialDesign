package com.example.mymaterialdesign.view.materialDesingPictureOfTheDay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.appState.AppStatePictureOfTheDay
import com.example.mymaterialdesign.databinding.FragmentPictureOfTheDayCoordinatorBinding
import com.example.mymaterialdesign.view.viewpager.TO_DAY
import com.example.mymaterialdesign.view.viewpager.YESTERDAY
import com.example.mymaterialdesign.viewModel.PictureViewModel

class PictureDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayCoordinatorBinding? = null
    private val binding: FragmentPictureOfTheDayCoordinatorBinding
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
        _binding = FragmentPictureOfTheDayCoordinatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveData.getLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })
        requestPictureLiveData(TO_DAY)
        initBtn()

    }

    private fun requestPictureLiveData(i: Int) {
        liveData.modDateDay(i)
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
                with(binding) {
                    pictureCustom.load(appStatePictureOfTheDay.pdoServerResponse.url) {
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                    explanation.text = appStatePictureOfTheDay.pdoServerResponse.explanation
                    title.text = appStatePictureOfTheDay.pdoServerResponse.title
                }
            }
        }
    }

    private fun initBtn() {
        with(binding) {
            btnYesterday.setOnClickListener {
                requestPictureLiveData(YESTERDAY)
            }
            btnToDay.setOnClickListener {
                requestPictureLiveData(TO_DAY)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}