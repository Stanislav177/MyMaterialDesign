package com.example.mymaterialdesign.viewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*

class MainViewModel(
    private val liveData: MutableLiveData<AppStatePictureOfTheDay> = MutableLiveData(),
    private val pictureOfTheDayImpl: PictureOfTheDayImpl = PictureOfTheDayImpl()
) : ViewModel() {

    private lateinit var dayDate: String

    fun getLiveData(): LiveData<AppStatePictureOfTheDay> {
        return liveData
    }

    fun getStartIntent(textSearch: String, context: Context) {
        context.startActivity(Intent(Intent.ACTION_VIEW).apply {
            data =
                Uri.parse("https://ru.wikipedia.org/wiki/${textSearch}")
        })
    }

    fun request() {
        liveData.postValue(AppStatePictureOfTheDay.Loading(0))
        pictureOfTheDayImpl.getRetrofitAPI()
            .getPictureOfTheDay(BuildConfig.API_KEY_NASA, dayDate).enqueue(
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
                            liveData.postValue(
                                AppStatePictureOfTheDay.ErrorCode(
                                    codeError,
                                    messError
                                )
                            )
                        }
                    }

                    override fun onFailure(call: Call<PDOServerResponse>, t: Throwable) {
                        liveData.postValue(AppStatePictureOfTheDay.Error(t))
                    }
                }
            )
    }

    private fun correctDate() {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.format(Date())
        dayDate = date
    }

    fun modDateDay(number: Int) {
        correctDate()
        val localDate = LocalDate.parse(dayDate)
        val period = Period.of(0, 0, number)
        dayDate = localDate.minus(period).toString()
        //request()
    }
}