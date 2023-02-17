package com.dnd_8th_4_android.wery.presentation.ui.onboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ItemOnboardBinding

class OnBoardingAdapter :
    RecyclerView.Adapter<OnBoardingAdapter.OnBoardingHolder>() {
    private val itemList =
        mutableListOf(R.drawable.img_crying_face, R.drawable.img_no_group, R.drawable.ic_group_on)

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
        fun bind(imgValue: Int) {
            Glide.with(binding.ivOnBoardImg.context).load(imgValue)
                .into(binding.ivOnBoardImg)
        }
    }
}