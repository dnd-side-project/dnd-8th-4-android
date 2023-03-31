package com.dnd_8th_4_android.wery.presentation.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.databinding.ItemFinalPostBinding
import com.dnd_8th_4_android.wery.databinding.ItemHomeTopBinding
import com.dnd_8th_4_android.wery.databinding.ItemNoPostBinding
import com.dnd_8th_4_android.wery.databinding.ItemPostBinding
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType
import com.dnd_8th_4_android.wery.presentation.ui.detail.view.PostDetailActivity
import com.dnd_8th_4_android.wery.presentation.ui.home.viewmodel.HomeViewModel
import com.dnd_8th_4_android.wery.presentation.util.dpToPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class HomeRecyclerViewAdapter(
    private val context: Context,
    private val homeViewModel: HomeViewModel,
    private val viewLifecycleOwner: LifecycleOwner,
) : RecyclerView.Adapter<ViewHolder>() {
    private lateinit var groupRecyclerViewAdapter: GroupRecyclerViewAdapter

    var postList = mutableListOf<ResponsePostData.Data.Content>()

    private lateinit var postImageAdapter: PostImageAdapter
    private lateinit var popupBottomClickListener: PopupBottomClickListener
    private lateinit var popupWindowClickListener: PopupWindowClickListener
    private var viewPagerPosition = 0
    private var itemPosition = 0

    private var emotionDrawable = 0

    companion object {
        private const val ITEM_HOME_TOP = 0
        private const val ITEM_HOME_YES_MIDDLE = 1
        private const val ITEM_HOME_NO_MIDDLE = 2
        private const val ITEM_HOME_BOTTOM = 3

        const val WRITE_CHECK = "write_check"
        const val CONTENT_ID = "content_id"
        const val GROUP_NAME = "group_name"
        const val TIME = "time"
        const val USER_IMAGE = "user_image"
        const val CONTENT = "content"
    }

    inner class TopViewHolder(private val binding: ItemHomeTopBinding) : ViewHolder(binding.root) {
        fun bind() {
            binding.ivAllGroup.setOnClickListener {
                if (groupRecyclerViewAdapter.selectedItemImage != binding.ivAllGroup) {
                    with(groupRecyclerViewAdapter) {
                        selectedItemImage.isSelected = false
                        selectedItemText.setTextAppearance(R.style.TextView_Caption_12_R)
                        selectedItemImage = binding.ivAllGroup
                        selectedItemText = binding.tvAllGroup
                    }

                    binding.ivAllGroup.isSelected =
                        !binding.ivAllGroup.isSelected
                    binding.tvAllGroup.setTextAppearance(R.style.TextView_Title_12_Sb)

                    homeViewModel.isSelectGroupId.value = -1
                    homeViewModel.isSelectedEmotion.value = false
                    homeViewModel.pageNumber.value = 1
                    homeViewModel.getGroupPost()
                }
            }
        }
    }

    inner class YesMiddleViewHolder(private val binding: ItemPostBinding) :
        ViewHolder(binding.root) {
        fun bind(item: ResponsePostData.Data.Content) {
            binding.ivFriendImage.clipToOutline = true
            Glide.with(binding.ivFriendImage.context).load(item.image).centerCrop()
                .into(binding.ivFriendImage)

            binding.tvFriendName.text = item.name

            if (item.location != null) {
                binding.tvLocation.text = item.location
                binding.ivLocation.isVisible = true
                binding.tvLocation.isVisible = true
            } else {
                binding.ivLocation.isVisible = false
                binding.tvLocation.isVisible = false
            }

            binding.tvFriendGroup.text = item.groupName
            binding.tvContent.text = item.content

            if (item.contentImage.size > 0) {
                binding.vpPostImage.isVisible = true
                val pagerPadding =
                    binding.root.resources.getDimensionPixelOffset(R.dimen.view_pager_padding_width) // 아이템의 padding
                val offsetPx =
                    binding.root.resources.getDimensionPixelOffset(R.dimen.view_pager_offset_8)// 아이템 간의 간격

                binding.vpPostImage.setPadding(pagerPadding, 0, pagerPadding, 0)
                binding.vpPostImage.setPageTransformer { page, position ->
                    page.translationX = position * offsetPx
                }

                binding.vpPostImage.offscreenPageLimit = 1 // 몇 개의 페이지를 미리 로드 해둘것인지

//                val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
//                    override fun onPageSelected(position: Int) {
//                        super.onPageSelected(position)
//                        val itemCount = postImageAdapter.itemCount
//                        if (position == itemCount - 1) {
//                            // 마지막 페이지가 선택되었을 때 새로운 페이지를 동적으로 로드합니다.
//                            postImageAdapter.insertList(item.contentImage, postImageAdapter.itemCount-1)
//                        }
//                    }
//                }

                postImageAdapter = PostImageAdapter(item.contentImage)
                binding.vpPostImage.adapter = postImageAdapter

//                if (item.contentImage.size > 1) {
//                    binding.vpPostImage.registerOnPageChangeCallback(onPageChangeCallback)
//                }

                postImageAdapter.setPostDetailImageListener {
                    homeViewModel.adapterPosition.value = adapterPosition
                    goToPostDetail(item, false)
                }

                binding.vpPostImage.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        viewPagerPosition = position
                        itemPosition = adapterPosition
                    }
                })
            } else {
                binding.vpPostImage.isVisible = false
            }

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
                        .format(DecodeFormat.PREFER_RGB_565)
                        .into(binding.ivEmotionLeft)

                    val layoutParams =
                        binding.ivEmotionLeft.layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.marginStart = 0
                    binding.ivEmotionLeft.layoutParams = layoutParams
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

                    val layoutParams =
                        binding.ivEmotionLeft.layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.marginStart = 11.dpToPx(binding.root.context)
                    binding.ivEmotionLeft.layoutParams = layoutParams
                }
            }

            binding.tvEmotionCount.text = item.emotions.toString()

            if (item.comments != 0) {
                binding.tvComment.isVisible = true
                binding.tvCommentCount.isVisible = true
                binding.tvCommentCount.text = item.comments.toString()
            } else {
                binding.tvComment.isVisible = false
                binding.tvCommentCount.isVisible = false
            }

            if (LocalDate.now().toString() == item.createAt.substring(IntRange(0, 9))) {
                binding.tvTime.text = item.createAt.substring(IntRange(11, 15)).replace("-", ".")
            } else {
                binding.tvTime.text = item.createAt.substring(IntRange(2, 9)).replace("-", ".")
            }

            binding.tvHitCount.text = item.hit

            if (item.emotionStatus != -1) {
                when (item.emotionStatus) {
                    PopupWindowType.Type1.emotionPosition -> {
                        binding.btnEmotion.setIconResource(PopupWindowType.Type1.drawable)
                        binding.btnEmotion.text = PopupWindowType.Type1.emotionName
                    }
                    PopupWindowType.Type2.emotionPosition -> {
                        binding.btnEmotion.setIconResource(PopupWindowType.Type2.drawable)
                        binding.btnEmotion.text = PopupWindowType.Type2.emotionName
                    }
                    PopupWindowType.Type3.emotionPosition -> {
                        binding.btnEmotion.setIconResource(PopupWindowType.Type3.drawable)
                        binding.btnEmotion.text = PopupWindowType.Type3.emotionName
                    }
                    PopupWindowType.Type4.emotionPosition -> {
                        binding.btnEmotion.setIconResource(PopupWindowType.Type4.drawable)
                        binding.btnEmotion.text = PopupWindowType.Type4.emotionName
                    }
                    PopupWindowType.Type5.emotionPosition -> {
                        binding.btnEmotion.setIconResource(PopupWindowType.Type5.drawable)
                        binding.btnEmotion.text = PopupWindowType.Type5.emotionName
                    }
                    PopupWindowType.Type6.emotionPosition -> {
                        binding.btnEmotion.setIconResource(PopupWindowType.Type6.drawable)
                        binding.btnEmotion.text = PopupWindowType.Type6.emotionName
                    }
                }
                binding.btnEmotion.setTypeface(null, Typeface.BOLD)
            } else {
                binding.btnEmotion.setIconResource(R.drawable.ic_emotion)
                binding.btnEmotion.text =
                    binding.root.resources.getString(R.string.home_item_post_emotion_button)
                binding.btnEmotion.setTypeface(null, Typeface.NORMAL)
            }

            if (adapterPosition != itemPosition) {
                binding.vpPostImage.setCurrentItem(0, false)
            } else {
                binding.vpPostImage.setCurrentItem(viewPagerPosition, false)
            }

            binding.ivPopup.setOnClickListener {
                popupBottomClickListener.onClicked(
                    adapterPosition,
                    item.id,
                    item.userId,
                    item.bookmarkAddStatus
                )
            }

            binding.btnEmotion.setOnClickListener {
                popupWindowClickListener.onClicked(adapterPosition, binding.btnEmotion, item.id)
            }

            binding.tvContent.setOnClickListener {
                homeViewModel.adapterPosition.value = adapterPosition
                goToPostDetail(item, false)
            }

            binding.btnCommentWrite.setOnClickListener {
                homeViewModel.adapterPosition.value = adapterPosition
                goToPostDetail(item, true)
            }
        }
    }

    inner class NoMiddleViewHolder(private val binding: ItemNoPostBinding) :
        ViewHolder(binding.root) {
        fun bind() {
            binding.layoutNoPost.isVisible =
                homeViewModel.pageNumber.value == 1 && homeViewModel.postList.value?.content?.isEmpty() == true
        }
    }

    inner class BottomViewHolder(private val binding: ItemFinalPostBinding) :
        ViewHolder(binding.root) {
        fun bind() {
            if (homeViewModel.pageNumber.value == 1) {
                binding.layoutFinalPost.isVisible = postList.isNotEmpty()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_HOME_TOP -> {
                val binding = ItemHomeTopBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                groupRecyclerViewAdapter = GroupRecyclerViewAdapter(
                    binding.ivAllGroup,
                    binding.tvAllGroup
                )

                binding.rvMyGroup.apply {
                    adapter = groupRecyclerViewAdapter
                    itemAnimator = null
                }

                groupRecyclerViewAdapter.setGroupPostCallListener { groupId ->
                    homeViewModel.isSelectGroupId.value = groupId
                    homeViewModel.isSelectedEmotion.value = false
                    homeViewModel.pageNumber.value = 1
                    homeViewModel.getGroupPost()
                }

                // 1) 스와이프, 스크롤, 재복귀
                homeViewModel.groupList.observe(viewLifecycleOwner) {
                    with(groupRecyclerViewAdapter) {
                        selectedItemImage.isSelected = false
                        selectedItemText.setTextAppearance(R.style.TextView_Caption_12_R)
                        selectedItemImage = binding.ivAllGroup
                        selectedItemText = binding.tvAllGroup
                    }

                    binding.ivAllGroup.isSelected =
                        !binding.ivAllGroup.isSelected
                    binding.tvAllGroup.setTextAppearance(R.style.TextView_Title_12_Sb)

                    groupRecyclerViewAdapter.submitList(it)
                }

                homeViewModel.postList.observe(viewLifecycleOwner) {
                    if (homeViewModel.pageNumber.value == 1) {
                        binding.layoutNoPost.isVisible = it.content.isEmpty()
                    }
                }

                TopViewHolder(binding)
            }

            ITEM_HOME_YES_MIDDLE -> {
                val binding = ItemPostBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                binding.vpPostImage.animation = null
                YesMiddleViewHolder(binding)
            }

            ITEM_HOME_NO_MIDDLE -> {
                val binding = ItemNoPostBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                NoMiddleViewHolder(binding)
            }

            else -> {
                val binding = ItemFinalPostBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                BottomViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        val originSize = postList.size
        return if (originSize == 0) 2 else originSize + 2
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (postList.size != 0) {
            when (holder) {
                is TopViewHolder -> {
                    holder.bind()
                }
                is YesMiddleViewHolder -> {
                    holder.bind(postList[position - 1])
                }
                is BottomViewHolder -> {
                    holder.bind()
                }
            }
        } else {
            when (holder) {
                is TopViewHolder -> {
                    holder.bind()
                }
                is NoMiddleViewHolder -> {
                    holder.bind()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (postList.size != 0) {
            when (position) {
                0 -> ITEM_HOME_TOP
                postList.size + 1 -> ITEM_HOME_BOTTOM
                else -> ITEM_HOME_YES_MIDDLE
            }
        } else {
            if (position == 0) ITEM_HOME_TOP
            else ITEM_HOME_NO_MIDDLE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item: MutableList<ResponsePostData.Data.Content>) {
        CoroutineScope(Dispatchers.IO).launch {
            if (postList != item) {
                postList.clear()
                postList.addAll(item)
                withContext(Dispatchers.Main) {
                    notifyDataSetChanged()
                }
            }
        }
    }

    fun insertList(item: MutableList<ResponsePostData.Data.Content>, position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            postList.addAll(item)
            withContext(Dispatchers.Main) {
                notifyItemInserted(10 * position)
            }
        }
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
        Intent(context, PostDetailActivity::class.java).apply {
            putExtra(WRITE_CHECK, checkWrite)
            putExtra(CONTENT_ID, item.id)
            putExtra(GROUP_NAME, item.groupName)
            putExtra(TIME, item.createAt)
            putExtra(USER_IMAGE, item.image)
            putExtra(CONTENT, item.content)
            context.startActivity(this)
        }
    }

    fun setPopupBottomClickListener(listener: (Int, Int, Int, Boolean) -> Unit) {
        popupBottomClickListener = object : PopupBottomClickListener {
            override fun onClicked(
                position: Int,
                contentId: Int,
                postMine: Int,
                isSelected: Boolean,
            ) {
                listener(position, contentId, postMine, isSelected)
            }
        }
    }

    fun setPopupWindowClickListener(listener: (Int, View, Int) -> Unit) {
        popupWindowClickListener = object : PopupWindowClickListener {
            override fun onClicked(position: Int, view: View, contentId: Int) {
                listener(position, view, contentId)
            }
        }
    }

    interface PopupBottomClickListener {
        fun onClicked(position: Int, contentId: Int, postMine: Int, isSelected: Boolean)
    }

    interface PopupWindowClickListener {
        fun onClicked(position: Int, view: View, contentId: Int)
    }
}