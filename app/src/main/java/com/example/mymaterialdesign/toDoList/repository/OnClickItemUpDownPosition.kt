package com.example.mymaterialdesign.toDoList.repository

fun interface OnClickItemUpDownPosition {
    fun onClick(pos: Int, moving:Boolean)
}