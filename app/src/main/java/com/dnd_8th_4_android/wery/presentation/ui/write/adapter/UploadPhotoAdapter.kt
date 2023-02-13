package com.dnd_8th_4_android.wery.presentation.ui.write.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.databinding.ItemWritingPhotoBinding


class UploadPhotoAdapter(private val onItemDelete: (String) -> Unit) :
    ListAdapter<String, UploadPhotoAdapter.UploadPhotoViewAdapter>(
        uploadPhotoDiffUtil
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadPhotoViewAdapter {
        val binding =
            ItemWritingPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UploadPhotoViewAdapter(binding, onItemDelete)
    }

    override fun onBindViewHolder(holder: UploadPhotoViewAdapter, position: Int) {
        holder.onBind(getItem(position))
    }

    class UploadPhotoViewAdapter(
        val binding: ItemWritingPhotoBinding,
        val onItemDelete: (String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentPhoto: String? = null

        init {
            binding.ivDelete.setOnClickListener {
                currentPhoto?.let {
                    onItemDelete(it)
                }
            }
        }

        fun onBind(imgUrl: String) {
            currentPhoto = imgUrl

            binding.photoCardView.clipToOutline = true
            binding.ivPhoto.clipToOutline = true
            Glide.with(binding.root).load(imgUrl)
                .placeholder(ColorDrawable(Color.parseColor("#E0E2E5"))).into(binding.ivPhoto)
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