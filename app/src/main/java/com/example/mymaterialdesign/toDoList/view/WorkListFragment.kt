package com.example.mymaterialdesign.toDoList.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.databinding.FragmentWorkListBinding
import com.example.mymaterialdesign.toDoList.model.CLOSE_ITEM
import com.example.mymaterialdesign.toDoList.model.ListWork
import com.example.mymaterialdesign.toDoList.model.TYPE_NO_IMAGE
import com.example.mymaterialdesign.toDoList.model.TYPE_YES_IMAGE
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WorkListFragment : Fragment(), OnClickItemUpDownPosition {

    private var _binding: FragmentWorkListBinding? = null
    private val binding: FragmentWorkListBinding
        get() {
            return _binding!!
        }

    private var listWorkData: MutableList<Pair<Boolean, ListWork>> = arrayListOf()

    private lateinit var adapter: AdapterToDoListWork

    private var fabAddListWorkItem: FloatingActionButton? = null
    lateinit var itemTouch: ItemTouchHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWorkListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AdapterToDoListWork(this)
        binding.recyclerToDoList.adapter = adapter
        initWorkList()
        adapter.setDataListWork(listWorkData)
        initFabBtnAddItemWork()

        itemTouch = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouch.attachToRecyclerView(binding.recyclerToDoList)
    }

    private fun initFabBtnAddItemWork() {
        fabAddListWorkItem = requireActivity().findViewById(R.id.fabBtn)
        fabAddListWorkItem!!.visibility = View.VISIBLE
        fabAddListWorkItem!!.setOnClickListener {
            adapter.addItemWork(Pair(CLOSE_ITEM,
                ListWork(TYPE_YES_IMAGE, 10, "НОВЫЙ ", "НОВЫЙ текст")))
            binding.recyclerToDoList.scrollToPosition(adapter.itemCount - 1)
            Toast.makeText(requireContext(), "   ", Toast.LENGTH_LONG).show()

        }
    }

    private fun initWorkList() {
        listWorkData = arrayListOf(
            Pair(CLOSE_ITEM, ListWork(TYPE_NO_IMAGE, 1, "Заметка 1", "Текст заметки 1")),
            Pair(CLOSE_ITEM, ListWork(TYPE_NO_IMAGE, 2, "Заметка 2", "Текст заметки 2")),
            Pair(CLOSE_ITEM, ListWork(TYPE_YES_IMAGE, 3, "Заметка 3", "Текст заметки 3")),
            Pair(CLOSE_ITEM, ListWork(TYPE_YES_IMAGE, 4, "Заметка 4", "Текст заметки 4")),
            Pair(CLOSE_ITEM, ListWork(TYPE_NO_IMAGE, 5, "Заметка 5", "Текст заметки 5")),
            Pair(CLOSE_ITEM, ListWork(TYPE_NO_IMAGE, 6, "Заметка 6", "Текст заметки 6")),
            Pair(CLOSE_ITEM, ListWork(TYPE_NO_IMAGE, 7, "Заметка 7", "Текст заметки 7")),
            Pair(CLOSE_ITEM, ListWork(TYPE_YES_IMAGE, 8, "Заметка 8", "Текст заметки 8")),
            Pair(CLOSE_ITEM, ListWork(TYPE_YES_IMAGE, 9, "Заметка 9", "Текст заметки 9")),
            Pair(CLOSE_ITEM, ListWork(TYPE_YES_IMAGE, 10, "Заметка 10", "Текст заметки 10"))
        )
    }

    override fun onClick(pos: Int, moving: Boolean) {
        if (moving) {
            binding.recyclerToDoList.scrollToPosition(pos)
            Toast.makeText(requireContext(), "Заметка на позиции - $pos", Toast.LENGTH_LONG)
                .show()
        } else {
            if (pos > 0) {
                Toast.makeText(requireContext(), "Это самая крайняя заметка", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Это самая первая заметка", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    class ItemTouchHelperCallback(private val adapterItem: AdapterToDoListWork) :
        ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
        ): Int {
            /*  Два дня потратил из-за свой невнимательности...
            уже новое приложение написал с этой функцией и только потом увидел где проблема
            val swipe = ItemTouchHelper.DOWN or ItemTouchHelper.UP
            val drag = ItemTouchHelper.START or ItemTouchHelper.END */

            val swipe = ItemTouchHelper.START or ItemTouchHelper.END
            val drag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            return makeMovementFlags(drag, swipe)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapterItem.onItemDismiss(viewHolder.adapterPosition)
        }
    }
}