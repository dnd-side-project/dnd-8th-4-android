package com.dnd_8th_4_android.wery.presentation.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.databinding.ItemPostBinding
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType
import com.dnd_8th_4_android.wery.presentation.ui.detail.view.PostDetailActivity

class PostRecyclerViewAdapter :
    ListAdapter<ResponsePostData.Data, PostRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {
    private lateinit var binding: ItemPostBinding
    private lateinit var postImageAdapter: PostImageAdapter
    private lateinit var popupBottomClickListener: PopupBottomClickListener
    private lateinit var popupWindowClickListener: PopupWindowClickListener
    private var viewPagerPosition = 0

    inner class ViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponsePostData.Data) {
            binding.ivFriendImage.clipToOutline = true
            Glide.with(binding.ivFriendImage.context).load(item.image)
                .into(binding.ivFriendImage)

            binding.tvFriendName.text = item.name
            binding.tvLocation.text = item.location
            binding.tvFriendGroup.text = item.groupName
            binding.tvContent.text = item.content

            // ViewPager Padding 설정
            val pagerPadding = binding.root.resources.getDimension(R.dimen.view_pager_padding_width)
            val offsetPx = binding.root.resources.getDimension(R.dimen.view_pager_padding_width)
            binding.vpPostImage.clipChildren = false
            binding.vpPostImage.setPadding(pagerPadding.toInt(), 0, pagerPadding.toInt(), 0)
            binding.vpPostImage.setPageTransformer { page, position ->
                page.translationX = position * offsetPx
            }

            postImageAdapter = PostImageAdapter(item.contentImage)
            binding.vpPostImage.adapter = postImageAdapter
            binding.vpPostImage.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewPagerPosition = position
                }
            })

            when (item.emotion.size) {
                0 -> {
                    binding.tvEmotionCount.isVisible = false
                    binding.ivEmotionLeft.isVisible = false
                    binding.ivEmotionRight.isVisible = false
                }
                1 -> {
                    binding.tvEmotionCount.isVisible = true
                    binding.ivEmotionLeft.isVisible = true
                    binding.ivEmotionRight.isVisible = false

                    Glide.with(binding.ivEmotionLeft.context).load(item.emotion[0])
                        .into(binding.ivEmotionLeft)
                }
                else -> {
                    binding.ivEmotionLeft.isVisible = true
                    binding.ivEmotionRight.isVisible = true
                    binding.tvEmotionCount.isVisible = true

                    Glide.with(binding.ivEmotionLeft.context).load(item.emotion[0])
                        .into(binding.ivEmotionLeft)

                    Glide.with(binding.ivEmotionRight.context).load(item.emotion[1])
                        .into(binding.ivEmotionRight)
                }
            }

            binding.tvEmotionCount.text = item.emotion.size.toString()

            if (item.comment.isNotEmpty()) {
                binding.tvCommentCount.text = item.comment.size.toString()
            } else {
                binding.tvComment.isVisible = false
                binding.tvCommentCount.isVisible = false
            }

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
                binding.vpPostImage.setCurrentItem(viewPagerPosition, false)
            }

            binding.ivPopup.setOnClickListener {
                popupBottomClickListener.onClicked()
            }

            binding.layoutEmotionButton.setOnClickListener {
                popupWindowClickListener.onClicked(binding.layoutEmotionButton, adapterPosition)
            }

            binding.layoutCommentWrite.setOnClickListener {
                val intent = Intent(binding.root.context, PostDetailActivity::class.java)
                intent.putExtra(GROUP_NAME, item.groupName)
                intent.putExtra(NAME, item.name)
                intent.putExtra(TIME, item.time)
                intent.putExtra(LOCATION, item.location)
                intent.putExtra(CONTENT, item.content)
                intent.putIntegerArrayListExtra(IMAGE, item.contentImage)
                binding.root.context.startActivity(intent)
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

    companion object {
        // TODO 보류 : Activity에서 API 호출 필요
        const val GROUP_NAME = "group_name"
        const val NAME = "name"
        const val TIME = "time"
        const val LOCATION = "location"
        const val CONTENT = "content"
        const val IMAGE = "image"

        private val diffUtil = object : DiffUtil.ItemCallback<ResponsePostData.Data>() {
            override fun areItemsTheSame(
                oldItem: ResponsePostData.Data,
                newItem: ResponsePostData.Data,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ResponsePostData.Data,
                newItem: ResponsePostData.Data,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}