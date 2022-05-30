package com.example.mymaterialdesign.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.databinding.FragmentIntermBinding
import com.example.mymaterialdesign.toDoList.view.ToDoListFragment
import com.example.mymaterialdesign.view.animationPictureToDay.AnimationPictureFragment
import com.example.mymaterialdesign.view.materialDesingPictureOfTheDay.PictureDayFragment

class IntermFragment : Fragment() {

    private var _binding: FragmentIntermBinding? = null
    private val binding: FragmentIntermBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentIntermBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            pictureOfTheDay.setOnClickListener {
                toFragment(PictureDayFragment())
            }
            pictureOfTheDayAnimation.setOnClickListener {
                toFragment(AnimationPictureFragment())
            }
            listWork.setOnClickListener {
                toFragment(ToDoListFragment())
            }
        }

    }

    private fun toFragment(f: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, f)
            .addToBackStack("")
            .commit()
    }
}