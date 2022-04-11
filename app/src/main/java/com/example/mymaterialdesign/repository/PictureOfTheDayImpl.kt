package com.example.mymaterialdesign.repository

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PictureOfTheDayImpl {

    private val baseURL = "https://api.nasa.gov/"

    fun getRetrofitAPI(): PictureOfTheDayAPI {
        val podRetrofit = Retrofit.Builder().baseUrl(baseURL)
            .addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().setLenient().create())
            ).build()

        return podRetrofit.create(PictureOfTheDayAPI::class.java)
    }
}