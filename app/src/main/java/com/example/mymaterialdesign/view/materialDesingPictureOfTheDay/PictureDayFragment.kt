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
import com.example.mymaterialdesign.databinding.PictureOfTheDayBinding
import com.example.mymaterialdesign.viewModel.PictureViewModel

class PictureDayFragment : Fragment() {

    private var _binding: PictureOfTheDayBinding? = null
    private val binding: PictureOfTheDayBinding
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
        _binding = PictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveData.getLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })
        liveData.modDateDay(0)
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
                binding.pictureCustom.load(appStatePictureOfTheDay.pdoServerResponse.url) {
                    placeholder(R.drawable.ic_no_photo_vector)
                }
                binding.explanation.text = appStatePictureOfTheDay.pdoServerResponse.explanation
                binding.title.text = appStatePictureOfTheDay.pdoServerResponse.title
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}