package com.example.mymaterialdesign.view.photoMars

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mymaterialdesign.appState.AppStateMarsPhoto
import com.example.mymaterialdesign.databinding.FragmentPhotoMarsBinding
import com.example.mymaterialdesign.viewModel.PhotoMarsViewModel
import kotlinx.android.synthetic.main.fragment_photo_mars.*
import java.text.SimpleDateFormat
import java.util.*

class PhotoMarsFragment : Fragment() {

    private var _binding: FragmentPhotoMarsBinding? = null
    private val binding: FragmentPhotoMarsBinding
        get() {
            return _binding!!
        }

    private val liveData: PhotoMarsViewModel by lazy {
        ViewModelProvider(this).get(PhotoMarsViewModel::class.java)
    }

    private val adapter: PhotoMarsAdapter by lazy {
        PhotoMarsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoMarsRecycler.adapter = adapter

        liveData.getLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })
        liveData.getFromServerNasaPhotoMars()

        initCalendar()
    }

    private fun initCalendar() {
        binding.tvBtnDate.setOnClickListener {
            calendarDate()
        }
        binding.tvBtnDate.text = correctDate()
    }

    private fun correctDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date())
    }

    private fun renderData(appStateMarsPhoto: AppStateMarsPhoto) {
        when (appStateMarsPhoto) {
            is AppStateMarsPhoto.Error -> {
            }
            is AppStateMarsPhoto.ErrorCode -> {
                Toast.makeText(context, appStateMarsPhoto.errorMess, Toast.LENGTH_LONG).show()
            }
            is AppStateMarsPhoto.Loading -> {
            }
            is AppStateMarsPhoto.Success -> {
                val listURL = appStateMarsPhoto.photoMars
                adapter.setListURL(listURL)
            }
        }
    }

    private fun calendarDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(), { _, year, monthOfYear, dayOfMonth ->
                val mon = monthOfYear + 1
                liveData.getFromServerNasaPhotoMarsCalendarDate("$year-$mon-$dayOfMonth")
                binding.tvBtnDate.text = "$year-$mon-$dayOfMonth"
            }, year, month, day
        )
        datePickerDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}