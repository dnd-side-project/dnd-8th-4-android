package com.dnd_8th_4_android.wery.presentation.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.databinding.ItemMyGroupBinding

class GroupRecyclerViewAdapter(
    private val list: MutableList<ResponseGroupData.Data>,
    initItem: View,
) :
    RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder>() {
    private lateinit var binding: ItemMyGroupBinding
    var selectedItem = initItem

    inner class ViewHolder(private val binding: ItemMyGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseGroupData.Data) {
            binding.ivMyGroup.clipToOutline = true
            binding.tvGroupName.text = item.name
            binding.layoutMyGroup.setOnClickListener {
                isSelected(itemView)
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

    private fun isSelected(itemView: View) {
        if (selectedItem != itemView) {
            selectedItem.isSelected = false
            itemView.isSelected = !itemView.isSelected
            selectedItem = itemView
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item: MutableList<ResponseGroupData.Data>) {
        this.list.clear()
        this.list.addAll(item)
        notifyDataSetChanged()
    }
}