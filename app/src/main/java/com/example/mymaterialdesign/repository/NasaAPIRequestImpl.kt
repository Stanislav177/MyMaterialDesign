package com.example.mymaterialdesign.repository

import com.example.mymaterialdesign.BuildConfig
import com.example.mymaterialdesign.model.DTOMarsRoverPhotos
import com.example.mymaterialdesign.model.PDOServerResponse
import com.example.mymaterialdesign.retrofit.RetrofitAPI
import retrofit2.Callback

class NasaAPIRequestImpl : NasaApiRequest {

    private val retrofitAPI by lazy { RetrofitAPI.getStartRetrofitAPI() }

    override fun getPictureOfTheDay(date: String, callback: Callback<PDOServerResponse>) {
        retrofitAPI.getPictureOfTheDay(BuildConfig.API_KEY_NASA, date).enqueue(callback)
    }

    override fun getPhotoMars(date: String, callback: Callback<DTOMarsRoverPhotos>) {
        retrofitAPI.getPhotoMars(BuildConfig.API_KEY_NASA, date).enqueue(callback)
    }
}