package com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMyMissionCard
import com.dnd_8th_4_android.wery.databinding.ItemMyMissionBinding
import com.dnd_8th_4_android.wery.presentation.ui.mission.view.MissionDetailActivity
import com.dnd_8th_4_android.wery.presentation.util.dpToPx

class MyMissionAdapter() :
    ListAdapter<ResponseMyMissionCard, MyMissionAdapter.MyMissionCardViewAdapter>(
        myMissionCardDiffUtil
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMissionCardViewAdapter {
        val binding =
            ItemMyMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyMissionCardViewAdapter(binding)
    }

    override fun onBindViewHolder(holder: MyMissionCardViewAdapter, position: Int) {
        holder.onBind(getItem(position))
        holder.onItemClick(getItem(position))
        val layoutParams = (holder.binding.root.layoutParams as ViewGroup.MarginLayoutParams)
        layoutParams.bottomMargin = if (position == (itemCount - 1)) 0 else 16.dpToPx(holder.binding.root.context)
        holder.binding.root.layoutParams = layoutParams
    }

    class MyMissionCardViewAdapter(
        val binding: ItemMyMissionBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: ResponseMyMissionCard) {
            binding.ivMissionGroupImg.clipToOutline = true
            binding.data = data
        }

        fun onItemClick(data: ResponseMyMissionCard) {
            binding.root.setOnClickListener {
                val intent = Intent(it.context, MissionDetailActivity::class.java)
                intent.putExtra("missionId", data.missionId)
                intent.putExtra("groupId",data.groupId)
                (it.context as Activity).startActivity(intent)
            }
        }
    }

    companion object {
        private val myMissionCardDiffUtil = object : DiffUtil.ItemCallback<ResponseMyMissionCard>() {
            override fun areItemsTheSame(
                oldItem: ResponseMyMissionCard,
                newItem: ResponseMyMissionCard
            ): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: ResponseMyMissionCard,
                newItem: ResponseMyMissionCard
            ): Boolean =
                oldItem == newItem
        }
    }
}