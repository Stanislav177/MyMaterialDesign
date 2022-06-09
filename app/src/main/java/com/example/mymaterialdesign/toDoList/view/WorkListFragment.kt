package com.example.mymaterialdesign.toDoList.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
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
import com.example.mymaterialdesign.toDoList.touchHelper.ItemTouchHelperViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WorkListFragment : Fragment(), OnClickItemUpDownPosition {

    private var _binding: FragmentWorkListBinding? = null
    private val binding: FragmentWorkListBinding
        get() {
            return _binding!!
        }

    private var flag = false

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

        binding.workMenu.alpha = 0f
        offClickable()

    }

    private fun offClickable() {
        binding.addWorkList.isClickable = false
        binding.sortedDate.isClickable = false
        binding.sortedName.isClickable = false
        binding.sortedLabel.isClickable = false
    }

    private fun onClickable() {
        binding.addWorkList.isClickable = true
        binding.sortedDate.isClickable = true
        binding.sortedName.isClickable = true
        binding.sortedLabel.isClickable = true
    }

    private fun initFabBtnAddItemWork() {
        fabAddListWorkItem = requireActivity().findViewById(R.id.fabBtn)
        fabAddListWorkItem!!.visibility = View.VISIBLE
        fabAddListWorkItem!!.setOnClickListener {

            flag = !flag

            if (flag) {
                binding.recyclerToDoList.animate().alpha(0.8f).setDuration(2500L)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            binding.recyclerToDoList.layoutManager.apply {
                                it.isScrollContainer = false
                            }
                            super.onAnimationEnd(animation)
                        }
                    })
                ObjectAnimator.ofFloat(fabAddListWorkItem, View.ROTATION, 0f, 200f)
                    .setDuration(2000L).start()
                ObjectAnimator.ofFloat(binding.workMenu, View.TRANSLATION_Y, 0f, -300f)
                    .setDuration(2500L).start()
                binding.workMenu
                    .animate()
                    .alpha(1f)
                    .setDuration(2000L)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            onClickable()
                            initMenuWork()

                            super.onAnimationEnd(animation)
                        }

                        private fun initMenuWork() {
                            binding.addWorkList.setOnClickListener {
                                adapter.addItemWork(Pair(CLOSE_ITEM,
                                    ListWork(TYPE_YES_IMAGE, 10, "НОВЫЙ ", "НОВЫЙ текст")))
                                binding.recyclerToDoList.scrollToPosition(adapter.itemCount - 1)

                            }
                            binding.sortedDate.setOnClickListener { }
                            binding.sortedName.setOnClickListener { }
                            binding.sortedLabel.setOnClickListener { }
                        }
                    })

            } else {
                binding.recyclerToDoList.animate().alpha(1f).setDuration(2500L)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            binding.recyclerToDoList.layoutManager.apply {
                                it.canScrollVertically(1)
                            }
                            super.onAnimationEnd(animation)
                        }
                    })
                ObjectAnimator.ofFloat(fabAddListWorkItem, View.ROTATION, 180f, 0f)
                    .setDuration(2000L).start()
                ObjectAnimator.ofFloat(binding.workMenu, View.TRANSLATION_Y, -300f, 0f)
                    .setDuration(2500L).start()
                binding.workMenu
                    .animate()
                    .alpha(0f)
                    .setDuration(2000L)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            offClickable()
                            super.onAnimationEnd(animation)
                        }
                    })
            }
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
            adapterItem.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapterItem.onItemDismiss(viewHolder.adapterPosition)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                (viewHolder as ItemTouchHelperViewAdapter).onItemSelected()
            }
            super.onSelectedChanged(viewHolder, actionState)
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            (viewHolder as ItemTouchHelperViewAdapter).onItemClear()
            super.clearView(recyclerView, viewHolder)
        }
    }
}