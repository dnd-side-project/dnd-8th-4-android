package com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMissionCard
import com.dnd_8th_4_android.wery.databinding.ItemMissionCardBinding
import com.dnd_8th_4_android.wery.presentation.util.dpToPx

class MissionCardAdapter() :
    ListAdapter<ResponseMissionCard, MissionCardAdapter.MissionCardViewAdapter>(
        missionCardDiffUtil
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionCardViewAdapter {
        val binding =
            ItemMissionCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MissionCardViewAdapter(binding)
    }

    override fun onBindViewHolder(holder: MissionCardViewAdapter, position: Int) {
        holder.onBind(getItem(position))
        val layoutParams = (holder.binding.root.layoutParams as ViewGroup.MarginLayoutParams)
        layoutParams.marginEnd =
            if (position == (itemCount - 1)) 0 else 12.dpToPx(holder.binding.root.context)
        holder.binding.root.layoutParams = layoutParams
    }

    class MissionCardViewAdapter(
        val binding: ItemMissionCardBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: ResponseMissionCard) {
            binding.data = data
        }
    }

    companion object {
        private val missionCardDiffUtil = object : DiffUtil.ItemCallback<ResponseMissionCard>() {
            override fun areItemsTheSame(
                oldItem: ResponseMissionCard,
                newItem: ResponseMissionCard
            ): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: ResponseMissionCard,
                newItem: ResponseMissionCard
            ): Boolean =
                oldItem == newItem
        }
    }
}