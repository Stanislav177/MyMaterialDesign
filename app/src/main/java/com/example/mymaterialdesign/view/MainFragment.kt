package com.example.mymaterialdesign.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.appState.AppStatePictureOfTheDay
import com.example.mymaterialdesign.databinding.FragmentMainBinding
import com.example.mymaterialdesign.viewModel.MainViewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    private val liveData: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        liveData.getLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })
        liveData.request()

        searchWiki()
    }

    private fun searchWiki() {
        binding.layoutSearchWiki.setEndIconOnClickListener {
            liveData.getStartIntent(binding.textSearchWiki.text.toString(), requireContext())
//            startActivity(Intent(Intent.ACTION_VIEW).apply {
//                data =
//                    Uri.parse("https://ru.wikipedia.org/wiki/${binding.textSearchWiki.text.toString()}")
//            })
        }
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
        binding.imageToDay.load(appStatePictureOfTheDay.pdoServerResponse.url) {
            placeholder(R.drawable.ic_no_photo_vector)
        }

        binding.includeMainFragment.title.text =
            appStatePictureOfTheDay.pdoServerResponse.title
        binding.includeMainFragment.explanation.text =
            appStatePictureOfTheDay.pdoServerResponse.explanation
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}