package com.example.mymaterialdesign.repository

import com.example.mymaterialdesign.model.PDOServerResponse
import retrofit2.Callback

interface PictureOfTheDayRequest {

    fun getPictureOfTheDay(
        date: String,
        callback: Callback<PDOServerResponse>
    )
}