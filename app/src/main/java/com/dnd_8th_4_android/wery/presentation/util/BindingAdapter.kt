package com.dnd_8th_4_android.wery.presentation.util

import android.widget.ImageView
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @androidx.databinding.BindingAdapter("imageBindFitXY")
    fun setImageFit(imageView: ImageView, imageUrl: String?) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .fitCenter()
            .into(imageView)
    }
}