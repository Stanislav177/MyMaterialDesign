package com.example.mymaterialdesign.view.photoMars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.model.PhotoMars

class PhotoMarsAdapter : RecyclerView.Adapter<PhotoMarsAdapter.MyPhotoMarsViewHolder>() {

    private var localListURLPhotoMars: MutableList<PhotoMars> = mutableListOf()

    fun setListURL(data: MutableList<PhotoMars>) {
        this.localListURLPhotoMars = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPhotoMarsViewHolder {
        return MyPhotoMarsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo_mars, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyPhotoMarsViewHolder, position: Int) {
        holder.bind(localListURLPhotoMars[position])
    }

    override fun getItemCount() = localListURLPhotoMars.size


    inner class MyPhotoMarsViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bind(photoMars: PhotoMars) {
            itemView.findViewById<ImageView>(R.id.photoMarsItemCardView).load(photoMars.url)
                // TODO не проходим по высоте.
        }

    }
}