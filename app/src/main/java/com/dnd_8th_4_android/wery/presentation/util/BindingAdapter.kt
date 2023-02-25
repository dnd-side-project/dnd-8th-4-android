package com.dnd_8th_4_android.wery.presentation.util

import android.graphics.Color
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R

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

    @JvmStatic
    @BindingAdapter("missionColor")
    fun setMissionColor(cardView: CardView, colorValue: Int?) {
        when (colorValue) {
            0 -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.color_3f75ff))
            1 -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.color_f47aff))
            2 -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.color_34c18e))
        }
    }
}