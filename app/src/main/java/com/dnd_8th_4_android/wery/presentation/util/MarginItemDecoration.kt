package com.dnd_8th_4_android.wery.presentation.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val spaceSize: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
       outRect: Rect, view: View,
       parent: RecyclerView,
       state: RecyclerView.State,
    ) {
        outRect.right = spaceSize
    }
}