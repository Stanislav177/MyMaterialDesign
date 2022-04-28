package com.example.mymaterialdesign.repository

import com.example.mymaterialdesign.model.DTOMarsRoverPhotos
import com.example.mymaterialdesign.model.PDOServerResponse
import com.example.mymaterialdesign.utils.URL_API_END_POINT_PHOTO
import com.example.mymaterialdesign.utils.URL_API_END_POINT_TO_DAY_PICTURE
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestAPI {

    @GET(URL_API_END_POINT_TO_DAY_PICTURE)
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Call<PDOServerResponse>

    @GET(URL_API_END_POINT_PHOTO)
    fun getPhotoMars(
        @Query("api_key") apiKey: String,
        @Query("earth_date") earthDate: String
    ): Call<DTOMarsRoverPhotos>
}