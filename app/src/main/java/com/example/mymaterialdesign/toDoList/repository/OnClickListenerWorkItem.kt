package com.example.mymaterialdesign.toDoList.repository

import com.example.mymaterialdesign.toDoList.model.ListWork

fun interface OnClickListenerWorkItem {
    fun onItemClick(dataListWork: ListWork)
}