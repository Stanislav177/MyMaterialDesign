package com.example.mymaterialdesign.view.toDoList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mymaterialdesign.databinding.FragmentToDayBinding

class ToDoListFragment : Fragment() {

    private var _binding: FragmentToDayBinding? = null
    private val binding: FragmentToDayBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentToDayBinding.inflate(inflater, container, false)
        return binding.root
    }
}