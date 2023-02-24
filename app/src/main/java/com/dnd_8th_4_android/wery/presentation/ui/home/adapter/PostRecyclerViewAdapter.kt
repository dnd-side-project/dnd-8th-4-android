package com.dnd_8th_4_android.wery.presentation.ui.home.adapter

import android.content.Intent
import android.graphics.Typeface
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
    ListAdapter<ResponsePostData.Data.Content, PostRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {
    private lateinit var binding: ItemPostBinding
    private lateinit var postImageAdapter: PostImageAdapter
    private lateinit var popupBottomClickListener: PopupBottomClickListener
    private lateinit var popupWindowClickListener: PopupWindowClickListener
    private var viewPagerPosition = 0
    private var emotionDrawable = 0

    inner class ViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponsePostData.Data.Content) {
            binding.ivFriendImage.clipToOutline = true
            Glide.with(binding.ivFriendImage.context).load(item.image)
                .into(binding.ivFriendImage)

            binding.tvFriendName.text = item.name

            if (item.location != null) {
                binding.tvLocation.text = item.location
            } else {
                binding.tvLocation.text =
                    binding.root.resources.getString(R.string.home_item_no_location)
            }

            binding.tvFriendGroup.text = item.groupName
            binding.tvContent.text = item.content

            val pagerPadding =
                binding.root.resources.getDimensionPixelOffset(R.dimen.view_pager_padding_width) // 아이템의 padding
            val offsetPx =
                binding.root.resources.getDimensionPixelOffset(R.dimen.view_pager_offset_8)// 아이템 간의 간격

            binding.vpPostImage.setPadding(pagerPadding, 0, pagerPadding, 0)
            binding.vpPostImage.setPageTransformer { page, position ->
                page.translationX = position * offsetPx
            }

            binding.vpPostImage.offscreenPageLimit = 1 // 몇 개의 페이지를 미리 로드 해둘것인지

            postImageAdapter = PostImageAdapter(item.contentImage)
            postImageAdapter.setPostDetailImageListener {
                goToPostDetail(item, false)
            }
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

                    setEmotionDrawable(item.emotion[0].emotionStatus)
                    Glide.with(binding.ivEmotionLeft.context).load(emotionDrawable)
                        .into(binding.ivEmotionLeft)
                }
                else -> {
                    binding.ivEmotionLeft.isVisible = true
                    binding.ivEmotionRight.isVisible = true
                    binding.tvEmotionCount.isVisible = true

                    setEmotionDrawable(item.emotion[0].emotionStatus)
                    Glide.with(binding.ivEmotionLeft.context).load(emotionDrawable)
                        .into(binding.ivEmotionLeft)

                    setEmotionDrawable(item.emotion[1].emotionStatus)
                    Glide.with(binding.ivEmotionRight.context).load(emotionDrawable)
                        .into(binding.ivEmotionRight)
                }
            }

            binding.tvEmotionCount.text = item.emotion.size.toString()

            if (item.comments != 0) {
                binding.tvComment.isVisible = true
                binding.tvCommentCount.isVisible = true
                binding.tvCommentCount.text = item.comments.toString()
            } else {
                binding.tvComment.isVisible = false
                binding.tvCommentCount.isVisible = false
            }

            binding.tvTime.text = item.time.substring(IntRange(11, 15))
            binding.tvHitCount.text = item.hit

            if (item.emotionStatus != -1) {
                when (item.emotionStatus) {
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
                binding.tvEmotionButton.setTypeface(null, Typeface.BOLD)
                binding.vpPostImage.setCurrentItem(viewPagerPosition, false)
            }

            binding.ivPopup.setOnClickListener {
                popupBottomClickListener.onClicked()
            }

            binding.layoutEmotionButton.setOnClickListener {
                popupWindowClickListener.onClicked(binding.layoutEmotionButton, item.id)
            }

            binding.tvContent.setOnClickListener { goToPostDetail(item, false) }

            binding.layoutCommentWrite.setOnClickListener {
                goToPostDetail(item, true)
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

    fun setEmotionDrawable(position: Int) {
        when (position) {
            PopupWindowType.Type1.emotionPosition -> {
                emotionDrawable = PopupWindowType.Type1.drawable
            }
            PopupWindowType.Type2.emotionPosition -> {
                emotionDrawable = PopupWindowType.Type2.drawable
            }
            PopupWindowType.Type3.emotionPosition -> {
                emotionDrawable = PopupWindowType.Type3.drawable
            }
            PopupWindowType.Type4.emotionPosition -> {
                emotionDrawable = PopupWindowType.Type4.drawable
            }
            PopupWindowType.Type5.emotionPosition -> {
                emotionDrawable = PopupWindowType.Type5.drawable
            }
            PopupWindowType.Type6.emotionPosition -> {
                emotionDrawable = PopupWindowType.Type6.drawable
            }
        }
    }

    fun goToPostDetail(item: ResponsePostData.Data.Content, checkWrite: Boolean) {
        Intent(binding.root.context, PostDetailActivity::class.java).apply {
            putExtra(WRITE_CHECK, checkWrite)
            putExtra(CONTENT_ID, item.id)
            putExtra(GROUP_NAME, item.groupName)
            putExtra(USER_IMAGE, item.image)
            putExtra(NAME, item.name)
            putExtra(TIME, item.time)
            putExtra(LOCATION, item.location)
            putExtra(CONTENT, item.content)
            putExtra(IMAGE, item.contentImage)
            binding.root.context.startActivity(this)
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
            override fun onClicked(view: View, contentId: Int) {
                listener(view, contentId)
            }
        }
    }

    interface PopupBottomClickListener {
        fun onClicked()
    }

    interface PopupWindowClickListener {
        fun onClicked(view: View, contentId: Int)
    }

    companion object {
        const val WRITE_CHECK = "write_check"
        const val CONTENT_ID= "content_id"
        const val GROUP_NAME = "group_name"
        const val USER_IMAGE = "user_image"
        const val NAME = "name"
        const val TIME = "time"
        const val LOCATION = "location"
        const val CONTENT = "content"
        const val IMAGE = "image"

        private val diffUtil = object : DiffUtil.ItemCallback<ResponsePostData.Data.Content>() {
            override fun areItemsTheSame(
                oldItem: ResponsePostData.Data.Content,
                newItem: ResponsePostData.Data.Content,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResponsePostData.Data.Content,
                newItem: ResponsePostData.Data.Content,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}