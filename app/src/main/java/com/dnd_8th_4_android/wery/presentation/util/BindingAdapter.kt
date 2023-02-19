package com.dnd_8th_4_android.wery.presentation.util

import android.graphics.Color
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("imageBindFitXY")
    fun setImageFit(imageView: ImageView, imageUrl: String?) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .fitCenter()
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter(
        value = ["dividerHeight", "dividerPadding", "dividerColor"],
        requireAll = false
    )
    fun RecyclerView.setDivider(
        dividerHeight: Float?,
        dividerPadding: Float?,
        @ColorInt dividerColor: Int?,
    ) {
        val decoration = CustomDecoration(
            height = dividerHeight ?: 0f,
            padding = dividerPadding ?: 0f,
            color = dividerColor ?: Color.TRANSPARENT
        )

        addItemDecoration(decoration)
    }
}