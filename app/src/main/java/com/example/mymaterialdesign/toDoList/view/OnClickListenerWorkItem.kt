package com.example.mymaterialdesign.toDoList.view

import com.example.mymaterialdesign.toDoList.model.ListWork

fun interface OnClickListenerWorkItem {
    fun onItemClick(dataListWork: ListWork)
}