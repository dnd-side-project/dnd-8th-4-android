package com.dnd_8th_4_android.wery.presentation.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.databinding.ItemMyGroupBinding

class GroupRecyclerViewAdapter(
    private val list: MutableList<ResponseGroupData.Data.GroupInfo>,
    initItemImage: View,
    initItemText: TextView,
) :
    RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder>() {
    private lateinit var binding: ItemMyGroupBinding

    var selectedItemImage = initItemImage
    var selectedItemText = initItemText

    private lateinit var groupPostCallListener: GroupPostCallListener

    inner class ViewHolder(private val binding: ItemMyGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseGroupData.Data.GroupInfo) {
            binding.ivMyGroup.clipToOutline = true
            Glide.with(binding.ivMyGroup.context).load(item.image)
                .into(binding.ivMyGroup)

            binding.tvGroupName.text = item.name

            binding.layoutMyGroup.setOnClickListener {
                if (selectedItemImage != binding.layoutMyGroupImage) {
                    selectedItemImage.isSelected = false
                    selectedItemText.setTextAppearance(R.style.TextView_Caption_12_R)

                    binding.tvGroupName.setTextAppearance(R.style.TextView_Title_12_Sb)
                    binding.layoutMyGroupImage.isSelected = !binding.layoutMyGroupImage.isSelected

                    selectedItemImage = binding.layoutMyGroupImage
                    selectedItemText = binding.tvGroupName

                    groupPostCallListener.onClicked(item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemMyGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item: MutableList<ResponseGroupData.Data.GroupInfo>) {
        this.list.clear()
        this.list.addAll(item)
        notifyDataSetChanged()
    }

    fun setGroupPostCallListener(listener: (Int) -> Unit) {
        groupPostCallListener = object : GroupPostCallListener {
            override fun onClicked(groupId: Int) {
                listener(groupId)
            }
        }
    }

    interface GroupPostCallListener {
        fun onClicked(groupId: Int)
    }

}