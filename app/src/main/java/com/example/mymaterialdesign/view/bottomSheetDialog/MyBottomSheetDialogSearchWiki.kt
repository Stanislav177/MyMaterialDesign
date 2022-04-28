package com.example.mymaterialdesign.view.bottomSheetDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mymaterialdesign.databinding.FragmentDialogSearchWikiBinding
import com.example.mymaterialdesign.viewModel.PictureViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MyBottomSheetDialogSearchWiki : BottomSheetDialogFragment() {

    private var _binding: FragmentDialogSearchWikiBinding? = null
    private val binding: FragmentDialogSearchWikiBinding
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
        _binding = FragmentDialogSearchWikiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutSearchWiki.setEndIconOnClickListener {
            dismiss()
            liveData.getStartIntent(binding.textSearchWiki.text.toString(), requireContext())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}