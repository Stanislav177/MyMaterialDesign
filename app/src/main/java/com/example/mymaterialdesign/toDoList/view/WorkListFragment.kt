package com.example.mymaterialdesign.toDoList.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.databinding.FragmentWorkListBinding
import com.example.mymaterialdesign.toDoList.model.ListWork
import com.example.mymaterialdesign.toDoList.model.TYPE_NO_IMAGE
import com.example.mymaterialdesign.toDoList.model.TYPE_YES_IMAGE
import com.example.mymaterialdesign.toDoList.repository.OnClickItemUpDownPosition
import com.example.mymaterialdesign.toDoList.repository.OnClickListenerWorkItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WorkListFragment : Fragment(), OnClickListenerWorkItem, OnClickItemUpDownPosition {

    private var listWorkData: MutableList<ListWork> = arrayListOf()
    private val adapter: AdapterToDoListWork by lazy {
        AdapterToDoListWork(this,this)
    }

    private var _binding: FragmentWorkListBinding? = null
    private val binding: FragmentWorkListBinding
        get() {
            return _binding!!
        }
    private var fabAddListWorkItem: FloatingActionButton? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWorkListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerToDoList.adapter = adapter
        initWorkList()
        adapter.setDataListWork(listWorkData)
        initFabBtnAddItemWork()
    }

    private fun initFabBtnAddItemWork() {
        fabAddListWorkItem = requireActivity().findViewById(R.id.fabBtn)
        fabAddListWorkItem!!.visibility = View.VISIBLE
        fabAddListWorkItem!!.setOnClickListener {
            adapter.addItemWork(ListWork(TYPE_YES_IMAGE,10,"НОВЫЙ ","НОВЫЙ текст"))
            binding.recyclerToDoList.scrollToPosition(adapter.itemCount - 1)
            Toast.makeText(requireContext(), "   ", Toast.LENGTH_LONG).show()

        }
    }

    private fun initWorkList() {
        listWorkData = arrayListOf(
            ListWork(TYPE_NO_IMAGE, 1, "Заметка 1", "Текст заметки 1"),
            ListWork(TYPE_NO_IMAGE, 2, "Заметка 2", "Текст заметки 2"),
            ListWork(TYPE_YES_IMAGE, 3, "Заметка 3", "Текст заметки 3"),
            ListWork(TYPE_YES_IMAGE, 4, "Заметка 4", "Текст заметки 4"),
            ListWork(TYPE_NO_IMAGE, 5, "Заметка 5", "Текст заметки 5"),
            ListWork(TYPE_NO_IMAGE, 6, "Заметка 6", "Текст заметки 6"),
            ListWork(TYPE_NO_IMAGE, 7, "Заметка 7", "Текст заметки 7"),
            ListWork(TYPE_YES_IMAGE, 8, "Заметка 8", "Текст заметки 8"),
            ListWork(TYPE_YES_IMAGE, 9, "Заметка 9", "Текст заметки 9"),
            ListWork(TYPE_YES_IMAGE, 10, "Заметка 10", "Текст заметки 10")
        )
    }

    override fun onItemClick(dataListWork: ListWork) {
        Toast.makeText(requireContext(), dataListWork.nameWork, Toast.LENGTH_LONG).show()
    }

    override fun onClick(pos: Int) {
        binding.recyclerToDoList.scrollToPosition(pos)
    }
}