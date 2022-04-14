package com.example.mymaterialdesign.retrofit

import com.example.mymaterialdesign.repository.PictureOfTheDayAPI
import com.example.mymaterialdesign.utils.BASE_URL
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitAPI {
    companion object {
        fun getStartRetrofitAPI(): PictureOfTheDayAPI {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory.create(GsonBuilder().setLenient().create())
                ).build()

            return retrofit.create(PictureOfTheDayAPI::class.java)
        }
    }
}