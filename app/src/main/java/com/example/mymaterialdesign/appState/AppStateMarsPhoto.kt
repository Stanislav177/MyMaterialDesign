package com.example.mymaterialdesign.appState

import com.example.mymaterialdesign.model.PhotoMars

sealed class AppStateMarsPhoto {
    data class Success(val photoMars: MutableList<PhotoMars>) : AppStateMarsPhoto()
    data class Error(val error: Throwable) : AppStateMarsPhoto()
    data class ErrorCode(val errorMess: String) : AppStateMarsPhoto()
    data class Loading(val progress: Int) : AppStateMarsPhoto()
}
