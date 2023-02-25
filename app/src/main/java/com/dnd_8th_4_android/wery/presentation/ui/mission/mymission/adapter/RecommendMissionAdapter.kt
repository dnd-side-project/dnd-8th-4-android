package com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.databinding.ItemMissionRecommendBinding
import com.dnd_8th_4_android.wery.domain.model.RecommendMission
import com.dnd_8th_4_android.wery.presentation.util.dpToPx

class RecommendMissionAdapter :
    RecyclerView.Adapter<RecommendMissionAdapter.RecommendMissionHolder>() {
    var itemList = mutableListOf<RecommendMission>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendMissionHolder {
        val binding =
            ItemMissionRecommendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendMissionHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendMissionHolder, position: Int) {
        holder.bind(itemList[position])
        val layoutParams = (holder.binding.root.layoutParams as ViewGroup.MarginLayoutParams)
        layoutParams.bottomMargin = if (position == (itemCount - 1)) 0 else 16.dpToPx(holder.binding.root.context)
        holder.binding.root.layoutParams = layoutParams
    }

    override fun getItemCount(): Int = itemList.size

    class RecommendMissionHolder(var binding: ItemMissionRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RecommendMission) {
            binding.data = data
        }
    }
}