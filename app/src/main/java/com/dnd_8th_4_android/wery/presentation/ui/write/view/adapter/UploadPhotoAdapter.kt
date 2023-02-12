package com.dnd_8th_4_android.wery.presentation.ui.write.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.databinding.ItemWritingPhotoBinding


class UploadPhotoAdapter : ListAdapter<String, UploadPhotoAdapter.UploadPhotoViewAdapter>(
    uploadPhotoDiffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadPhotoViewAdapter {
        val binding =
            ItemWritingPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UploadPhotoViewAdapter(binding)
    }

    override fun onBindViewHolder(holder: UploadPhotoViewAdapter, position: Int) {
        holder.onBind(getItem(position))
        holder.setDeleteListener()
    }

    class UploadPhotoViewAdapter(val binding: ItemWritingPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(imgUrl: String) {
            binding.ivPhoto.clipToOutline = true
            Glide.with(binding.root).load(imgUrl).into(binding.ivPhoto)
        }

        fun setDeleteListener() {
            binding.ivDelete.setOnClickListener {

            }
        }
    }

    companion object {
        private val uploadPhotoDiffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean =
                oldItem === newItem

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean =
                oldItem == newItem
        }
    }
}