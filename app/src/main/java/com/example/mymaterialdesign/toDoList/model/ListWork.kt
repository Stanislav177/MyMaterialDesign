package com.example.mymaterialdesign.toDoList.model

const val TYPE_NO_IMAGE = 1
const val TYPE_YES_IMAGE = 2
const val OPEN_ITEM = true
const val CLOSE_ITEM = false

data class ListWork(val viewType: Int, val id: Int, val nameWork: String, val textWork: String)