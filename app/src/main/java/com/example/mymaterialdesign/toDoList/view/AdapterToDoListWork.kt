package com.example.mymaterialdesign.toDoList.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymaterialdesign.databinding.ItemToDoListBinding
import com.example.mymaterialdesign.databinding.ItemToDoListIcImageBinding
import com.example.mymaterialdesign.toDoList.model.ListWork
import com.example.mymaterialdesign.toDoList.model.TYPE_NO_IMAGE
import com.example.mymaterialdesign.toDoList.repository.OnClickListenerWorkItem

class AdapterToDoListWork(val onClickListenerWorkItem: OnClickListenerWorkItem) :
    RecyclerView.Adapter<AdapterToDoListWork.BaseOnBindViewHolder>() {

    private var dataListWork: List<ListWork> = arrayListOf()

    override fun getItemViewType(position: Int): Int {
        return dataListWork[position].viewType
    }

    fun setDataListWork(data: List<ListWork>) {
        this.dataListWork = data
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


    override fun getItemCount() = dataListWork.size


    inner class NoImageItemViewHolder(view: View) : BaseOnBindViewHolder(view) {
        override fun onBind(listItem: ListWork) {
            ItemToDoListBinding.bind(itemView).apply {
                nameNote.text = listItem.nameWork
                textNote2.text = listItem.textWork
                positionItem.setOnClickListener {
                    onClickListenerWorkItem.onItemClick(listItem)
                    textNote2.visibility = View.VISIBLE
                }
            }
        }
    }

    inner class ImageItemViewHolder(view: View) : BaseOnBindViewHolder(view) {
        override fun onBind(listItem: ListWork) {
            ItemToDoListIcImageBinding.bind(itemView).apply {
                nameNote.text = listItem.textWork
                textNote2.text = listItem.textWork
                positionItem.setOnClickListener {
                    onClickListenerWorkItem.onItemClick(listItem)
                    textNote2.visibility = View.VISIBLE
                }

            }

        }
    }


    abstract class BaseOnBindViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(listItem: ListWork)
    }

    override fun onBindViewHolder(holder: BaseOnBindViewHolder, position: Int) {
        holder.onBind(dataListWork[position])
    }
}