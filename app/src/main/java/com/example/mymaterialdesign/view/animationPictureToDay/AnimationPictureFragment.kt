package com.example.mymaterialdesign.view.animationPictureToDay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import coil.load
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.appState.AppStatePictureOfTheDay
import com.example.mymaterialdesign.databinding.FragmentAnimationPictureBinding
import com.example.mymaterialdesign.view.viewpager.TO_DAY
import com.example.mymaterialdesign.viewModel.PictureViewModel


class AnimationPictureFragment : Fragment() {

    private var _binding: FragmentAnimationPictureBinding? = null
    private val binding: FragmentAnimationPictureBinding
        get() {
            return _binding!!
        }

    private var flag = false
    private val duration = 1000L
    private val mTension = 8f

    private val liveData: PictureViewModel by lazy {
        ViewModelProvider(this).get(PictureViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAnimationPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        liveData.getLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })
        requestPictureLiveData(TO_DAY)

        initClickPicture()

    }

    private fun initClickPicture() {
        binding.pictureCustom.setOnClickListener {
            flag = !flag
            startAnimation()
        }
    }

    private fun startAnimation() {
        val constraintSet = ConstraintSet()
        val changeBounds = ChangeBounds()

        if (flag) {
            constraintSet.clone(requireContext(), R.layout.fragment_animation_picture)
            changeBounds.duration = duration
            changeBounds.interpolator = AnticipateOvershootInterpolator(mTension)

            TransitionManager.beginDelayedTransition(binding.constraintCont, changeBounds)

            constraintSet.connect(R.id.titlePicture,
                ConstraintSet.START,
                R.id.constraintCont,
                ConstraintSet.START)
            constraintSet.connect(R.id.titlePicture,
                ConstraintSet.END,
                R.id.constraintCont,
                ConstraintSet.END)
            constraintSet.connect(R.id.titlePicture,
                ConstraintSet.BOTTOM,
                R.id.pictureCustom,
                ConstraintSet.TOP)

            constraintSet.connect(R.id.nestedScrollDesc,
                ConstraintSet.START,
                R.id.constraintCont,
                ConstraintSet.START)
            constraintSet.connect(R.id.nestedScrollDesc,
                ConstraintSet.END,
                R.id.constraintCont,
                ConstraintSet.END)
            constraintSet.connect(R.id.nestedScrollDesc,
                ConstraintSet.TOP,
                R.id.pictureCustom,
                ConstraintSet.BOTTOM)

            constraintSet.applyTo(binding.constraintCont)

        } else {
            constraintSet.clone(requireContext(), R.layout.fragment_animation_picture)
            changeBounds.duration = duration
            changeBounds.interpolator = AnticipateOvershootInterpolator(mTension)

            TransitionManager.beginDelayedTransition(binding.constraintCont, changeBounds)

            constraintSet.applyTo(binding.constraintCont)
        }
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
                    descriptionPicture.text = appStatePictureOfTheDay.pdoServerResponse.explanation
                    titlePicture.text = appStatePictureOfTheDay.pdoServerResponse.title
                }
            }
        }
    }

}