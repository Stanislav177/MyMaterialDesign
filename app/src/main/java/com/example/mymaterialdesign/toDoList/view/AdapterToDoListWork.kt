package com.example.mymaterialdesign.toDoList.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymaterialdesign.databinding.ItemToDoListBinding
import com.example.mymaterialdesign.databinding.ItemToDoListIcImageBinding
import com.example.mymaterialdesign.toDoList.model.CLOSE_ITEM
import com.example.mymaterialdesign.toDoList.model.ListWork
import com.example.mymaterialdesign.toDoList.model.OPEN_ITEM
import com.example.mymaterialdesign.toDoList.model.TYPE_NO_IMAGE
import com.example.mymaterialdesign.toDoList.touchHelper.ItemTouchHelperAdapter
import com.example.mymaterialdesign.toDoList.touchHelper.ItemTouchHelperViewAdapter

class AdapterToDoListWork(
    private val onClickItemUpDownPosition: OnClickItemUpDownPosition,
) : RecyclerView.Adapter<AdapterToDoListWork.BaseOnBindViewHolder>(), ItemTouchHelperAdapter {

    private var dataListWork: MutableList<Pair<Boolean, ListWork>> = arrayListOf()

    override fun getItemViewType(position: Int): Int {
        return dataListWork[position].second.viewType
    }

    fun setDataListWork(data: MutableList<Pair<Boolean, ListWork>>) {
        this.dataListWork = data
    }

    fun addItemWork(itemWork: Pair<Boolean, ListWork>) {
        dataListWork.add(itemWork)
        notifyItemInserted(itemCount - 1)
    }

    private fun removeItemWork(position: Int) {
        dataListWork.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseOnBindViewHolder {
        if (TYPE_NO_IMAGE == viewType) {
            val itemBinding: ItemToDoListBinding =
                ItemToDoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return NoImageItemViewHolder(itemBinding.root)
        } else {
            val itemBinding: ItemToDoListIcImageBinding =
                ItemToDoListIcImageBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false)
            return ImageItemViewHolder(itemBinding.root)
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

    inner class NoImageItemViewHolder(view: View) : BaseOnBindViewHolder(view),
        ItemTouchHelperViewAdapter {
        override fun onBind(listItem: Pair<Boolean, ListWork>) {
            ItemToDoListBinding.bind(itemView).apply {
                nameNote.text = listItem.second.nameWork
                textNote.text = listItem.second.textWork

                nameNote.setOnClickListener {
                    dataListWork[layoutPosition] = dataListWork[layoutPosition].let {
                        Pair(if (it.first == CLOSE_ITEM) OPEN_ITEM else CLOSE_ITEM, it.second)

                    }
                    notifyItemChanged(layoutPosition)
                }
                textNote.visibility =
                    if (dataListWork[layoutPosition].first == CLOSE_ITEM) View.GONE else View.VISIBLE

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

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }


    inner class ImageItemViewHolder(view: View) : BaseOnBindViewHolder(view),
        ItemTouchHelperViewAdapter {
        override fun onBind(listItem: Pair<Boolean, ListWork>) {
            ItemToDoListIcImageBinding.bind(itemView).apply {
                nameNote.text = listItem.second.nameWork
                textNote.text = listItem.second.textWork
                nameNote.setOnClickListener {
                    dataListWork[layoutPosition] = dataListWork[layoutPosition].let {
                        Pair(if (it.first == CLOSE_ITEM) OPEN_ITEM else CLOSE_ITEM, it.second)

                    }
                    notifyItemChanged(layoutPosition)
                }
                textNote.visibility =
                    if (dataListWork[layoutPosition].first == CLOSE_ITEM) View.GONE else View.VISIBLE
                btnUpNote.setOnClickListener {
                    upItemList(layoutPosition)
                }

                btnDownNote.setOnClickListener {
                    downItemList(layoutPosition)
                }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    abstract class BaseOnBindViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(listItem: Pair<Boolean, ListWork>)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        dataListWork.removeAt(fromPosition).apply {
            dataListWork.add(toPosition, this)
            notifyItemMoved(fromPosition, toPosition)
        }
    }

    override fun onItemDismiss(position: Int) {
        dataListWork.removeAt(position)
        notifyItemRemoved(position)
    }


}