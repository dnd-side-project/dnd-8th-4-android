package com.dnd_8th_4_android.wery.presentation.ui.write.upload.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseGroupList
import com.dnd_8th_4_android.wery.databinding.ItemSelectGroupBinding

class SelectGroupAdapter : RecyclerView.Adapter<SelectGroupAdapter.SelectGroupViewHolder>() {

    var itemList = mutableListOf<ResponseGroupList>()

    class SelectGroupViewHolder(val binding: ItemSelectGroupBinding) :
        ViewHolder(binding.root) {

        init {
            binding.ivGroupImg.clipToOutline = true
        }

        fun onBind(data: ResponseGroupList) {
            binding.data = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectGroupViewHolder {
        val binding =
            ItemSelectGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectGroupViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: SelectGroupViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }
}