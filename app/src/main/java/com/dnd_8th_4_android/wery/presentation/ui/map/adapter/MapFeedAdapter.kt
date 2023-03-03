package com.dnd_8th_4_android.wery.presentation.ui.map.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapFeedData
import com.dnd_8th_4_android.wery.databinding.ItemMapFeedBinding
import com.dnd_8th_4_android.wery.presentation.ui.detail.view.PostDetailActivity
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter

class MapFeedAdapter :
    RecyclerView.Adapter<MapFeedAdapter.MapFeedViewHolder>() {
    var itemList = listOf<ResponseMapFeedData.ResultMapFeedData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapFeedViewHolder {
        val binding =
            ItemMapFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MapFeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MapFeedViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    class MapFeedViewHolder(var binding: ItemMapFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ResponseMapFeedData.ResultMapFeedData) {
            binding.data = data

            binding.root.setOnClickListener {
                Intent(it.context, PostDetailActivity::class.java).apply {
                    putExtra(PostRecyclerViewAdapter.CONTENT_ID, data.contentId)
                    putExtra(PostRecyclerViewAdapter.CONTENT, data.content)
                    putExtra(PostRecyclerViewAdapter.USER_IMAGE, data.userProfileImage)
                    putExtra(PostRecyclerViewAdapter.GROUP_NAME, data.groupName)
                    putExtra(PostRecyclerViewAdapter.TIME, data.createAt)
                    it.context.startActivity(this)
                }
            }
        }
    }
}