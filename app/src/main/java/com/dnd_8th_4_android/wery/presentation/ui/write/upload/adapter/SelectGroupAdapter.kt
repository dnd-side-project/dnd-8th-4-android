package com.dnd_8th_4_android.wery.presentation.ui.write.upload.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseGroupList
import com.dnd_8th_4_android.wery.databinding.ItemSelectGroupBinding

class SelectGroupAdapter(private val onItemClick: (ResponseGroupList) -> Unit) :
    RecyclerView.Adapter<SelectGroupAdapter.SelectGroupViewHolder>() {

    var itemList = mutableListOf<ResponseGroupList>()

    class SelectGroupViewHolder(
        val binding: ItemSelectGroupBinding,
        val onItemClick: (ResponseGroupList) -> Unit
    ) :
        ViewHolder(binding.root) {
        private var clickedPosition: ResponseGroupList? = null

        init {
            binding.ivGroupImg.clipToOutline = true
            binding.root.setOnClickListener {
                clickedPosition?.let {
                    onItemClick(it)
                }
            }
        }

        fun onBind(data: ResponseGroupList) {
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