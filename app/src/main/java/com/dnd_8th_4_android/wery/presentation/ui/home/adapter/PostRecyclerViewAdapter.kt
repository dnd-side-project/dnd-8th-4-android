package com.dnd_8th_4_android.wery.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.databinding.ItemPostBinding
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType

class PostRecyclerViewAdapter :
    ListAdapter<ResponsePostData.Data, PostRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {
    private lateinit var binding: ItemPostBinding
    private lateinit var postImageAdapter: PostImageAdapter
    private lateinit var popupBottomClickListener: PopupBottomClickListener
    private lateinit var popupWindowClickListener: PopupWindowClickListener

    inner class ViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponsePostData.Data) {
            binding.ivFriendImage.clipToOutline = true
//            Glide.with(binding.ivFriendImage.context).load(item.image)
//                .into(binding.ivFriendImage)

            binding.tvFriendName.text = item.name
            binding.tvFriendGroup.text = item.groupName
            binding.tvFriendContent.text = item.content

            postImageAdapter = PostImageAdapter(item.contentImage)
            binding.vpPostImage.adapter = postImageAdapter

            when (item.emotion.size) {
                0 -> {
                    binding.ivEmotionLeft.isVisible = false
                    binding.ivEmotionRight.isVisible = false
                    binding.tvEmotionCount.isVisible = false
                }
                1 -> {
                    Glide.with(binding.ivEmotionLeft.context).load(item.emotion[0])
                        .into(binding.ivEmotionLeft)

                    binding.ivEmotionRight.isVisible = false
                }
                else -> {
                    Glide.with(binding.ivEmotionLeft.context).load(item.emotion[0])
                        .into(binding.ivEmotionLeft)

                    Glide.with(binding.ivEmotionRight.context).load(item.emotion[1])
                        .into(binding.ivEmotionRight)
                }
            }

            binding.tvEmotionCount.text = item.emotion.size.toString()
            binding.tvCommentCount.text = item.comment.size.toString()
            binding.tvTime.text = item.time
            binding.tvHitCount.text = item.hit

            if (item.isSelectedEmotion != 0) {
                when (item.isSelectedEmotion) {
                    PopupWindowType.Type1.emotionPosition -> {
                        binding.ivEmotionButton.setImageResource(PopupWindowType.Type1.drawable)
                        binding.tvEmotionButton.text = PopupWindowType.Type1.emotionName
                    }
                    PopupWindowType.Type2.emotionPosition -> {
                        binding.ivEmotionButton.setImageResource(PopupWindowType.Type2.drawable)
                        binding.tvEmotionButton.text = PopupWindowType.Type2.emotionName
                    }
                    PopupWindowType.Type3.emotionPosition -> {
                        binding.ivEmotionButton.setImageResource(PopupWindowType.Type3.drawable)
                        binding.tvEmotionButton.text = PopupWindowType.Type3.emotionName

                    }
                    PopupWindowType.Type4.emotionPosition -> {
                        binding.ivEmotionButton.setImageResource(PopupWindowType.Type4.drawable)
                        binding.tvEmotionButton.text = PopupWindowType.Type4.emotionName

                    }
                    PopupWindowType.Type5.emotionPosition -> {
                        binding.ivEmotionButton.setImageResource(PopupWindowType.Type5.drawable)
                        binding.tvEmotionButton.text = PopupWindowType.Type5.emotionName

                    }
                    PopupWindowType.Type6.emotionPosition -> {
                        binding.ivEmotionButton.setImageResource(PopupWindowType.Type6.drawable)
                        binding.tvEmotionButton.text = PopupWindowType.Type6.emotionName
                    }
                }
            }

            binding.ivPopup.setOnClickListener {
                popupBottomClickListener.onClicked()
            }

            binding.layoutEmotionButton.setOnClickListener {
                popupWindowClickListener.onClicked(binding.layoutEmotionButton, adapterPosition)
            }

            if (adapterPosition == currentList.lastIndex) {
                binding.viewLine.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ResponsePostData.Data>() {
            override fun areItemsTheSame(
                oldItem: ResponsePostData.Data,
                newItem: ResponsePostData.Data,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResponsePostData.Data,
                newItem: ResponsePostData.Data,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun setPopupBottomClickListener(listener: () -> Unit) {
        popupBottomClickListener = object : PopupBottomClickListener {
            override fun onClicked() {
                listener()
            }
        }
    }

    fun setPopupWindowClickListener(listener: (View, Int) -> Unit) {
        popupWindowClickListener = object : PopupWindowClickListener {
            override fun onClicked(view: View, position: Int) {
                listener(view, position)
            }
        }
    }

    interface PopupBottomClickListener {
        fun onClicked()
    }

    interface PopupWindowClickListener {
        fun onClicked(view: View, position: Int)
    }
}