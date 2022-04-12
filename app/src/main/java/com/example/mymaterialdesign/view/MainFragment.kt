package com.example.mymaterialdesign.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
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

    private var day: Int = 0

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
        requestAPI()
        searchWiki()
        setOptionsMenu()
        initBtnFAB()

        initChips()
    }

    private fun initChips() {
        binding.today.setOnClickListener {
            day = 0
            requestAPI()
        }
        binding.yesterday.setOnClickListener {
            day = 1
            requestAPI()
        }
        binding.beforeYesterday.setOnClickListener {
            day = 2
            requestAPI()
        }
    }

    private fun requestAPI() {
        liveData.modDateDay(day)
        liveData.request()
    }

    private fun initBtnFAB() {
        binding.fab.setOnClickListener {
            Toast.makeText(context, "FAB", Toast.LENGTH_LONG).show()
        }
    }

    private fun setOptionsMenu() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.appBarBottom)
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                Toast.makeText(context, "Setting", Toast.LENGTH_LONG).show()
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
        fun newInstance() = MainFragment()
    }
}