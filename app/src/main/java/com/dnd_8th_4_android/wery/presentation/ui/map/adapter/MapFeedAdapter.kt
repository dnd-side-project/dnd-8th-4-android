package com.dnd_8th_4_android.wery.presentation.ui.map.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapFeedData
import com.dnd_8th_4_android.wery.databinding.ItemMapFeedBinding

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
            binding.ivGroupImg.clipToOutline = true
            binding.ivGroupPhoto.clipToOutline = true
        }
    }
}