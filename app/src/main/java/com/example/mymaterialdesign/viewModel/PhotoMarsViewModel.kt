package com.example.mymaterialdesign.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymaterialdesign.appState.AppStateMarsPhoto
import com.example.mymaterialdesign.model.DTOMarsRoverPhotos
import com.example.mymaterialdesign.model.PhotoMars
import com.example.mymaterialdesign.repository.NasaAPIRequestImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PhotoMarsViewModel(private val liveData: MutableLiveData<AppStateMarsPhoto> = MutableLiveData()) :
    ViewModel() {

    private lateinit var toDayDate: String

    private val repositoryNasaAPIRequestImpl: NasaAPIRequestImpl by lazy {
        NasaAPIRequestImpl()
    }

    private fun correctDate() {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.format(Date())
        toDayDate = date
    }

    fun getLiveData() = liveData


    private fun formatError(code: Int, mess: String) = "Ошибка: ${"$code $mess"}"


    fun getFromServerNasaPhotoMars() {
        correctDate()
        repositoryNasaAPIRequestImpl.getPhotoMars(toDayDate, callback)
    }

    fun getFromServerNasaPhotoMarsCalendarDate(date: String) {
        repositoryNasaAPIRequestImpl.getPhotoMars(date, callback)
    }


    private val callback = object : Callback<DTOMarsRoverPhotos> {
        override fun onResponse(
            call: Call<DTOMarsRoverPhotos>,
            response: Response<DTOMarsRoverPhotos>
        ) {
            if (response.isSuccessful) {
                if (response.body() == null) {
                    liveData.postValue(
                        AppStateMarsPhoto.ErrorCode(
                            formatError(
                                response.code(),
                                response.message()
                            )
                        )
                    )
                } else {
                    response.body()?.let {
                        if (it.photos.isNotEmpty()) {
                            liveData.postValue(AppStateMarsPhoto.Success(converterDTO(it)))
                        } else {
                            liveData.postValue(AppStateMarsPhoto.ErrorCode("Сегодня нет фото"))
                        }
                    }
                }
            } else {
                liveData.postValue(
                    AppStateMarsPhoto.ErrorCode(
                        formatError(
                            response.code(),
                            response.message()
                        )
                    )
                )
            }
        }

        override fun onFailure(call: Call<DTOMarsRoverPhotos>, t: Throwable) {

        }

    }

    private fun converterDTO(dtoMarsRoverPhotos: DTOMarsRoverPhotos): MutableList<PhotoMars> {
        val listPhotoMars = mutableListOf<PhotoMars>()
        val sizeDTO = dtoMarsRoverPhotos.photos.size
        for (i in 0 until sizeDTO) {
            listPhotoMars.add(PhotoMars(dtoMarsRoverPhotos.photos[i].imgSrc))
        }
        return listPhotoMars
    }
}