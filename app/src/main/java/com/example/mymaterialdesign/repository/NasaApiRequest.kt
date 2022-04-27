package com.example.mymaterialdesign.repository

import com.example.mymaterialdesign.model.DTOMarsRoverPhotos
import com.example.mymaterialdesign.model.PDOServerResponse
import retrofit2.Callback

interface NasaApiRequest {

    fun getPictureOfTheDay(
        date: String,
        callback: Callback<PDOServerResponse>
    )

    fun getPhotoMars(
        date: String,
        callback: Callback<DTOMarsRoverPhotos>
    )
}