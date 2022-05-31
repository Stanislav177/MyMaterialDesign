package com.example.mymaterialdesign.toDoList.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymaterialdesign.databinding.ItemToDoListBinding
import com.example.mymaterialdesign.databinding.ItemToDoListIcImageBinding
import com.example.mymaterialdesign.toDoList.model.ListWork
import com.example.mymaterialdesign.toDoList.model.TYPE_NO_IMAGE
import com.example.mymaterialdesign.toDoList.repository.OnClickItemUpDownPosition
import com.example.mymaterialdesign.toDoList.repository.OnClickListenerWorkItem

class AdapterToDoListWork(
    private val onClickListenerWorkItem: OnClickListenerWorkItem,
    private val onClickItemUpDownPosition: OnClickItemUpDownPosition,
) :
    RecyclerView.Adapter<AdapterToDoListWork.BaseOnBindViewHolder>() {

    private var dataListWork: MutableList<ListWork> = arrayListOf()

    override fun getItemViewType(position: Int): Int {
        return dataListWork[position].viewType
    }

    fun setDataListWork(data: MutableList<ListWork>) {
        this.dataListWork = data
    }

    fun addItemWork(itemWork: ListWork) {
        dataListWork.add(itemWork)
        notifyItemInserted(itemCount - 1)
    }

    private fun removeItemWork(position: Int) {
        dataListWork.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseOnBindViewHolder {
        return if (TYPE_NO_IMAGE == viewType) {
            val itemBinding: ItemToDoListBinding =
                ItemToDoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            NoImageItemViewHolder(itemBinding.root)
        } else {
            val itemBinding: ItemToDoListIcImageBinding =
                ItemToDoListIcImageBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false)
            ImageItemViewHolder(itemBinding.root)
        }
    }

    override fun onBindViewHolder(holder: BaseOnBindViewHolder, position: Int) {
        holder.onBind(dataListWork[position])
    }

    override fun getItemCount() = dataListWork.size

    private fun downItemList(pos: Int) {
        if (itemCount > pos + 1) {
            dataListWork.removeAt(pos).apply {
                dataListWork.add(pos + 1, this)
                notifyItemMoved(pos, pos + 1)
            }
            onClickItemUpDownPosition.onClick(pos + 1, true)
        } else {
            onClickItemUpDownPosition.onClick(pos + 1, false)
        }
    }

    private fun upItemList(pos: Int) {
        if (pos - 1 > -1) {
            dataListWork.removeAt(pos).apply {
                dataListWork.add(pos - 1, this)
                notifyItemMoved(pos, pos - 1)
            }
            onClickItemUpDownPosition.onClick(pos - 1, true)
        } else {
            onClickItemUpDownPosition.onClick(pos - 1, false)
        }
    }

    inner class NoImageItemViewHolder(view: View) : BaseOnBindViewHolder(view) {
        override fun onBind(listItem: ListWork) {
            ItemToDoListBinding.bind(itemView).apply {
                nameNote.text = listItem.nameWork
                textNote.text = listItem.textWork
                nameNote.setOnClickListener {
                    onClickListenerWorkItem.onItemClick(listItem)
                    textNote.visibility = View.VISIBLE
                }
                btnDeleteNote.setOnClickListener {
                    removeItemWork(layoutPosition)
                }
                btnUpNote.setOnClickListener {
                    upItemList(layoutPosition)
                }

                btnDownNote.setOnClickListener {
                    downItemList(layoutPosition)
                }
            }
        }
    }


    inner class ImageItemViewHolder(view: View) : BaseOnBindViewHolder(view) {
        override fun onBind(listItem: ListWork) {
            ItemToDoListIcImageBinding.bind(itemView).apply {
                nameNote.text = listItem.textWork
                textNote.text = listItem.textWork
                nameNote.setOnClickListener {
                    onClickListenerWorkItem.onItemClick(listItem)
                    textNote.visibility = View.VISIBLE
                }
                btnUpNote.setOnClickListener {
                    upItemList(layoutPosition)
                }

                btnDownNote.setOnClickListener {
                    downItemList(layoutPosition)
                }
            }
        }
    }

    abstract class BaseOnBindViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(listItem: ListWork)
    }
}