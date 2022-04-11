package com.example.mymaterialdesign.appState

import com.example.mymaterialdesign.model.PDOServerResponse

sealed class AppStatePictureOfTheDay {

    data class Success(val pdoServerResponse: PDOServerResponse) : AppStatePictureOfTheDay()
    data class Error(val error: Throwable) : AppStatePictureOfTheDay()
    data class ErrorCode(val errorCode: Int, val errorMess: String) : AppStatePictureOfTheDay()
    data class Loading(val progress: Int) : AppStatePictureOfTheDay()
}