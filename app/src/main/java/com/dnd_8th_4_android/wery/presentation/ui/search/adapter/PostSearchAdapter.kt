package com.dnd_8th_4_android.wery.presentation.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.search.ResponsePostSearchData
import com.dnd_8th_4_android.wery.databinding.ItemPostSearchBinding
import java.time.LocalDate

class PostSearchAdapter :
    ListAdapter<ResponsePostSearchData.Data.Content, PostSearchAdapter.ViewHolder>(diffUtil) {
    private lateinit var binding: ItemPostSearchBinding

    class ViewHolder(private val binding: ItemPostSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponsePostSearchData.Data.Content) {
            binding.ivPostImage.clipToOutline = true
            binding.ivFriendImage.clipToOutline = true

            binding.tvPostContent.text = item.content

            if (item.contentImageList.isNotEmpty()) {
                Glide.with(binding.ivPostImage.context).load(item.contentImageList[0].imageUrl)
                    .into(binding.ivPostImage)
            }

            binding.tvImageCount.text = item.contentImageListSize.toString()

            Glide.with(binding.ivFriendImage.context).load(item.groupImage)
                .into(binding.ivFriendImage)

            binding.tvName.text = item.groupName

            if (LocalDate.now().toString() == item.createAt.substring(IntRange(0, 10))) {
                binding.tvTime.text = item.createAt.substring(IntRange(11, 15)).replace("-", ".")
            } else {
                binding.tvTime.text = item.createAt.substring(IntRange(2, 9)).replace("-", ".")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPostSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    companion object {
        private val diffUtil =
            object : DiffUtil.ItemCallback<ResponsePostSearchData.Data.Content>() {
                override fun areItemsTheSame(
                    oldItem: ResponsePostSearchData.Data.Content,
                    newItem: ResponsePostSearchData.Data.Content,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: ResponsePostSearchData.Data.Content,
                    newItem: ResponsePostSearchData.Data.Content,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}