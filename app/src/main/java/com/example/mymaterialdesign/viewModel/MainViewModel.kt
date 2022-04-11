package com.example.mymaterialdesign.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymaterialdesign.BuildConfig
import com.example.mymaterialdesign.appState.AppStatePictureOfTheDay
import com.example.mymaterialdesign.model.PDOServerResponse
import com.example.mymaterialdesign.repository.PictureOfTheDayImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val liveData: MutableLiveData<AppStatePictureOfTheDay> = MutableLiveData(),
    private val pictureOfTheDayImpl: PictureOfTheDayImpl = PictureOfTheDayImpl()
) : ViewModel() {


    fun getLiveData(): LiveData<AppStatePictureOfTheDay> {
        return liveData
    }

    fun request() {
        liveData.postValue(AppStatePictureOfTheDay.Loading(0))

        pictureOfTheDayImpl.getRetrofitAPI().getPictureOfTheDay(BuildConfig.API_KEY_NASA).enqueue(
            object : Callback<PDOServerResponse> {
                override fun onResponse(
                    call: Call<PDOServerResponse>,
                    response: Response<PDOServerResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            liveData.postValue(AppStatePictureOfTheDay.Success(it))
                        }
                    } else {
                        val messError: String = response.message()
                        val codeError: Int = response.code()
                        liveData.postValue(AppStatePictureOfTheDay.ErrorCode(codeError, messError))
                    }
                }

                override fun onFailure(call: Call<PDOServerResponse>, t: Throwable) {
                    liveData.postValue(AppStatePictureOfTheDay.Error(t))
                }
            }
        )
    }
}