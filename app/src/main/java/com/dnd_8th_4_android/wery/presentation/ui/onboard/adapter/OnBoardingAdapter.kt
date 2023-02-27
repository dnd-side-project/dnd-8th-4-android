package com.dnd_8th_4_android.wery.presentation.ui.onboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.databinding.ItemOnboardBinding
import com.dnd_8th_4_android.wery.domain.model.OnBoardData

class OnBoardingAdapter :
    RecyclerView.Adapter<OnBoardingAdapter.OnBoardingHolder>() {
    var itemList = mutableListOf<OnBoardData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingHolder {
        val binding = ItemOnboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnBoardingHolder(binding)
    }

    override fun onBindViewHolder(holder: OnBoardingHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    class OnBoardingHolder(var binding: ItemOnboardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: OnBoardData) {
            binding.data = data
            Glide.with(binding.ivOnBoardImg.context).load(data.imgDrawable)
                .into(binding.ivOnBoardImg)
        }
    }
}