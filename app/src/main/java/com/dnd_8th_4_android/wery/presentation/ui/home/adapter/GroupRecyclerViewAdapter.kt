package com.dnd_8th_4_android.wery.presentation.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.databinding.ItemMyGroupBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupRecyclerViewAdapter(
    var selectedItemImage: ImageView,
    var selectedItemText: TextView,
) :
    RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder>() {
    private lateinit var binding: ItemMyGroupBinding
    var groupList = mutableListOf<ResponseGroupData.Data.GroupInfo>()

    private lateinit var groupPostCallListener: GroupPostCallListener

    inner class ViewHolder(private val binding: ItemMyGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseGroupData.Data.GroupInfo) {
            binding.ivMyGroup.clipToOutline = true
            Glide.with(binding.ivMyGroup.context).load(item.image).centerCrop()
                .into(binding.ivMyGroup)

            binding.tvGroupName.text = item.name

            itemView.setOnClickListener {
                if (selectedItemImage != binding.ivBtnState) {
                    selectedItemImage.isSelected = false
                    selectedItemText.setTextAppearance(R.style.TextView_Caption_12_R)

                    binding.tvGroupName.setTextAppearance(R.style.TextView_Title_12_Sb)
                    binding.ivBtnState.isSelected = !binding.ivBtnState.isSelected

                    selectedItemImage = binding.ivBtnState
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

    override fun getItemCount() = groupList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(groupList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item: MutableList<ResponseGroupData.Data.GroupInfo>) {
        CoroutineScope(Dispatchers.IO).launch {
            if (groupList != item) {
                groupList.clear()
                groupList.addAll(item)
                withContext(Dispatchers.Main) {
                    notifyDataSetChanged()
                }
            }
        }
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