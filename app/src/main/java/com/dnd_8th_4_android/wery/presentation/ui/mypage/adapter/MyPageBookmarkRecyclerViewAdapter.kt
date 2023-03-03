package com.dnd_8th_4_android.wery.presentation.ui.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyBookmarkData
import com.dnd_8th_4_android.wery.databinding.ItemMypageBookmarkBinding
import java.time.LocalDate

class MyPageBookmarkRecyclerViewAdapter(private val list: MutableList<ResponseMyBookmarkData.Data.Content>) :
    RecyclerView.Adapter<MyPageBookmarkRecyclerViewAdapter.ViewHolder>() {
    private lateinit var binding: ItemMypageBookmarkBinding
    private lateinit var onGoPostClickListener: OnGoPostClickListener

    inner class ViewHolder(private val binding: ItemMypageBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseMyBookmarkData.Data.Content) {
            binding.tvPostContent.text = item.content

            binding.ivPostImage.clipToOutline = true
            Glide.with(binding.root.context).load(item.images[0].imageUrl)
                .into(binding.ivPostImage)

            if (item.imageSize != 1) {
                binding.tvImageCount.isVisible = true
                binding.tvImageCount.text = item.imageSize.toString()
            } else {
                binding.tvImageCount.isVisible = false
            }

            if (LocalDate.now().toString() == item.createAt.substring(IntRange(0, 9))) {
                binding.tvTime.text = item.createAt.substring(IntRange(11, 15)).replace("-", ".")
            } else {
                binding.tvTime.text = item.createAt.substring(IntRange(2, 9)).replace("-", ".")
            }

            binding.tvHitCont.text = item.views.toString()
            binding.tvComment.text = item.comments.toString()

            binding.ivGroupImage.clipToOutline = true
            Glide.with(binding.root.context).load(item.groupImage)
                .into(binding.ivGroupImage)

            binding.tvName.text = item.groupName

            binding.layoutPostBookmark.setOnClickListener {
                onGoPostClickListener.onClicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemMypageBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setGoPostClickListener(listener: (Int) -> Unit) {
        onGoPostClickListener = object : OnGoPostClickListener {
            override fun onClicked(position: Int) {
                listener(position)
            }
        }
    }

    interface OnGoPostClickListener {
        fun onClicked(position: Int)
    }
}