package com.example.mymaterialdesign.view.photoMars

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.appState.AppStateMarsPhoto
import com.example.mymaterialdesign.databinding.FragmentPhotoMarsBinding
import com.example.mymaterialdesign.viewModel.PhotoMarsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_photo_mars.*
import java.text.SimpleDateFormat
import java.util.*

class PhotoMarsFragment : Fragment() {

    private var _binding: FragmentPhotoMarsBinding? = null
    private val binding: FragmentPhotoMarsBinding
        get() {
            return _binding!!
        }

    private var flag = false

    private val duration = 1000L

    private val liveData: PhotoMarsViewModel by lazy {
        ViewModelProvider(this).get(PhotoMarsViewModel::class.java)
    }

    private val adapter: PhotoMarsAdapter by lazy {
        PhotoMarsAdapter()
    }
    private var fabSettingMarsPhoto: FloatingActionButton? = null

    private var sdfYear = SimpleDateFormat("yyyy").format(Date())
    private var sdfMonth = SimpleDateFormat("MM").format(Date())
    private var sdfDay = SimpleDateFormat("dd").format(Date())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
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

        fabSettingMarsPhoto = requireActivity().findViewById(R.id.fabBtn)
        fabSettingMarsPhoto!!.visibility = View.VISIBLE


        binding.calendarDatePict.alpha = 0f
        binding.calendarDatePict.isClickable = false

        binding.photoMarsRecycler.setOnScrollChangeListener {
                v, scrollX, scrollY,
                oldScrollX, oldScrollY,
            ->
            binding.header.isSelected = binding.photoMarsRecycler.canScrollVertically(-1)

        }

        fabSettingMarsPhoto!!.setOnClickListener {
            flag = !flag

            if (flag) {
                ObjectAnimator.ofFloat(fabSettingMarsPhoto, View.ROTATION, 0f, 200f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.calendarDatePict, View.TRANSLATION_Y, 0f, -200f)
                    .setDuration(duration).start()


                binding.calendarDatePict
                    .animate()
                    .alpha(1f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            binding.calendarDatePict.isClickable = true
                            binding.calendarDatePict.setOnClickListener {
                                calendarDate(sdfYear.toInt(), sdfMonth.toInt(), sdfDay.toInt())

                            }
                            super.onAnimationEnd(animation)
                        }
                    })

                binding.transparentBackground
                    .animate()
                    .alpha(0.8f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            binding.transparentBackground.isClickable = false
                            super.onAnimationEnd(animation)
                        }
                    })

            } else {
                ObjectAnimator.ofFloat(fabSettingMarsPhoto, View.ROTATION, 180f, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.calendarDatePict, View.TRANSLATION_Y, -180f, 0f)
                    .setDuration(duration).start()

                binding.calendarDatePict.animate()
                    .alpha(0f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.calendarDatePict.isClickable = false
                        }
                    })

                binding.transparentBackground.animate().alpha(1f).setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            binding.transparentBackground.isClickable = true
                            super.onAnimationEnd(animation)

                        }
                    })
            }
//            calendarDate(sdfYear.toInt(), sdfMonth.toInt(), sdfDay.toInt())

        }

        // binding.tvBtnDate.text = "${sdfYear}-${sdfMonth}-${sdfDay}"
    }

    private fun initCalendar() {
        //        binding.tvBtnDate.setOnClickListener {
        //            calendarDate(sdfYear.toInt(), sdfMonth.toInt(), sdfDay.toInt())
        //        }
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

    private fun calendarDate(setYear: Int, setMonth: Int, setDay: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(setYear, setMonth - 1, setDay)
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(), { _, year, monthOfYear, dayOfMonth ->
                val mon = monthOfYear + 1
                sdfYear = year.toString()
                sdfMonth = mon.toString()
                sdfDay = dayOfMonth.toString()
                liveData.getFromServerNasaPhotoMarsCalendarDate("$year-$mon-$dayOfMonth")
                // binding.tvBtnDate.text = "$year-$mon-$dayOfMonth"
            }, year, month, day
        )
        datePickerDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        fabSettingMarsPhoto!!.visibility = View.GONE
        _binding = null
    }
}