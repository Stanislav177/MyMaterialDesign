package com.example.mymaterialdesign.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DTOMarsRoverPhotos(
    val photos: List<Photo>
) : Parcelable

@Parcelize
data class Photo(
    val id: Long,
    val sol: Long,
    val camera: Camera,

    @SerializedName("img_src")
    val imgSrc: String,

    @SerializedName("earth_date")
    val earthDate: String,

    val rover: Rover
) : Parcelable

@Parcelize
data class Camera(
    val id: Long,
    val name: String,

    @SerializedName("rover_id")
    val roverID: Long,

    @SerializedName("full_name")
    val fullName: String
) : Parcelable

@Parcelize
data class Rover(
    val id: Long,
    val name: String,

    @SerializedName("landing_date")
    val landingDate: String,

    @SerializedName("launch_date")
    val launchDate: String,

    val status: String
) : Parcelable
