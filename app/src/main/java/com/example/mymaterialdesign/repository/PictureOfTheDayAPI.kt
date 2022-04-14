package com.example.mymaterialdesign.repository

import com.example.mymaterialdesign.model.PDOServerResponse
import com.example.mymaterialdesign.utils.URL_API_END_POINT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {

    @GET(URL_API_END_POINT)
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Call<PDOServerResponse>

}