package com.dnd_8th_4_android.wery.presentation.ui.write.upload.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.databinding.ItemWritingPhotoBinding


class UploadPhotoAdapter(private val onItemDelete: (Uri) -> Unit) :
    ListAdapter<Uri, UploadPhotoAdapter.UploadPhotoViewAdapter>(
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
        val onItemDelete: (Uri) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentPhoto: Uri? = null

        init {
            binding.ivDelete.setOnClickListener {
                currentPhoto?.let {
                    onItemDelete(it)
                }
            }
        }

        fun onBind(imgUri: Uri) {
            currentPhoto = imgUri

            binding.ivPhoto.clipToOutline = true
            Glide.with(binding.root).load(imgUri)
                .placeholder(ColorDrawable(Color.parseColor("#E0E2E5"))).into(binding.ivPhoto)
        }
    }

    companion object {
        private val uploadPhotoDiffUtil = object : DiffUtil.ItemCallback<Uri>() {
            override fun areItemsTheSame(
                oldItem: Uri,
                newItem: Uri
            ): Boolean =
                oldItem === newItem

            override fun areContentsTheSame(
                oldItem: Uri,
                newItem: Uri
            ): Boolean =
                oldItem == newItem
        }
    }
}