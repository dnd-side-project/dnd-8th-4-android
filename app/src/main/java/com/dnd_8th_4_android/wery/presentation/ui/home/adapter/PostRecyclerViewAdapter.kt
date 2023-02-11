package com.dnd_8th_4_android.wery.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.databinding.ItemPostBinding

class PostRecyclerViewAdapter :
    ListAdapter<ResponsePostData.Data, PostRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {
    private lateinit var binding: ItemPostBinding
    private lateinit var postImageAdapter: PostImageAdapter

    inner class ViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponsePostData.Data) {
//            Glide.with(binding.ivFriendImage.context).load(item.image)
//                .into(binding.ivFriendImage)

            binding.tvFriendName.text = item.name
            binding.tvFriendGroup.text = item.groupName
            binding.tvFriendContent.text = item.content

            postImageAdapter = PostImageAdapter(item.contentImage)
            binding.vpPostImage.adapter = postImageAdapter

            // TODO 감정 표현 개수에 따른 표시
//            Glide.with(binding.ivEmotionLeft.context).load(item.emotion[0])
//                .into(binding.ivEmotionLeft)

//            Glide.with(binding.ivEmotionRight.context).load(item.emotion[1])
//                .into(binding.ivEmotionRight)

//            binding.tvCommentCount.text = item.comment.size.toString()

            binding.tvTime.text = item.time
            binding.tvHitCount.text = item.hit

            if(adapterPosition == currentList.lastIndex) {
                binding.viewLine.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object diffUtil : DiffUtil.ItemCallback<ResponsePostData.Data>() {
        override fun areItemsTheSame(
            oldItem: ResponsePostData.Data,
            newItem: ResponsePostData.Data,
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: ResponsePostData.Data,
            newItem: ResponsePostData.Data,
        ): Boolean {
            return oldItem == newItem
        }
    }
}