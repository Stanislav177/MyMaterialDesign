package com.example.mymaterialdesign.view.materialDesingPictureOfTheDay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import coil.load
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.databinding.FragmentZoomPictureBinding

class ZoomPictureFragment : Fragment() {

    private var url: String? = null

    private var flag = false

    private var _binding: FragmentZoomPictureBinding? = null
    private val binding: FragmentZoomPictureBinding
        get() {
            return _binding!!
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentZoomPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            url = it.getString("URL")
        }

        initImage()

        binding.pictureZoom.setOnClickListener {

            val chaImageTrans = ChangeImageTransform()

            chaImageTrans.duration = 2000
            TransitionManager.beginDelayedTransition(requireActivity().findViewById(R.id.fragmentContainer),
                chaImageTrans)
            flag = !flag
            if (flag) {
                binding.pictureZoom.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                binding.pictureZoom.scaleType = ImageView.ScaleType.FIT_CENTER
            }
        }
    }

    private fun initImage() {
        binding.pictureZoom.load(url)
    }

    companion object {
        fun newInstance(bundle: Bundle) =
            ZoomPictureFragment().apply {
                arguments = bundle

            }
    }
}