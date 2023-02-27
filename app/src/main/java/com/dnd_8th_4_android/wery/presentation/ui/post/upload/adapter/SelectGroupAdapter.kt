package com.dnd_8th_4_android.wery.presentation.ui.post.upload.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dnd_8th_4_android.wery.data.remote.model.post.ResponseGroupList
import com.dnd_8th_4_android.wery.databinding.ItemSelectGroupBinding

class SelectGroupAdapter(private val onItemClick: (ResponseGroupList.ResultGroupList) -> Unit) :
    RecyclerView.Adapter<SelectGroupAdapter.SelectGroupViewHolder>() {

    var itemList = listOf<ResponseGroupList.ResultGroupList>()

    class SelectGroupViewHolder(
        val binding: ItemSelectGroupBinding,
        val onItemClick: (ResponseGroupList.ResultGroupList) -> Unit
    ) :
        ViewHolder(binding.root) {
        private var clickedPosition: ResponseGroupList.ResultGroupList? = null

        init {
            binding.ivGroupImg.clipToOutline = true
            binding.root.setOnClickListener {
                clickedPosition?.let {
                    onItemClick(it)
                }
            }
        }

        fun onBind(data: ResponseGroupList.ResultGroupList) {
            clickedPosition = data
            binding.data = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectGroupViewHolder {
        val binding =
            ItemSelectGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectGroupViewHolder(binding, onItemClick)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: SelectGroupViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }
}