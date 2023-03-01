package com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMainMissionCard
import com.dnd_8th_4_android.wery.databinding.ItemMissionCardBinding
import com.dnd_8th_4_android.wery.presentation.ui.mission.view.MissionDetailActivity
import com.dnd_8th_4_android.wery.presentation.util.dpToPx

class MissionCardAdapter() :
    ListAdapter<ResponseMainMissionCard.ResultMissionCard, MissionCardAdapter.MissionCardViewAdapter>(
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
        layoutParams.marginEnd = if (position == (itemCount - 1)) 0 else 12.dpToPx(holder.binding.root.context)
        holder.binding.root.layoutParams = layoutParams
    }

    class MissionCardViewAdapter(
        val binding: ItemMissionCardBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: ResponseMainMissionCard.ResultMissionCard) {
            binding.ivGroupImg.clipToOutline = true
            binding.data = data
            binding.root.setOnClickListener {
                val intent = Intent(it.context, MissionDetailActivity::class.java)
                intent.putExtra("missionId",data.missionId.toInt())
                intent.putExtra("groupId",data.groupId)
                (it.context).startActivity(intent)
            }
        }
    }

    companion object {
        private val missionCardDiffUtil = object : DiffUtil.ItemCallback<ResponseMainMissionCard.ResultMissionCard>() {
            override fun areItemsTheSame(
                oldItem: ResponseMainMissionCard.ResultMissionCard,
                newItem: ResponseMainMissionCard.ResultMissionCard
            ): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: ResponseMainMissionCard.ResultMissionCard,
                newItem: ResponseMainMissionCard.ResultMissionCard
            ): Boolean =
                oldItem == newItem
        }
    }
}