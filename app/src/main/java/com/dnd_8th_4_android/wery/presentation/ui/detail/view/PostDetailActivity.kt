package com.dnd_8th_4_android.wery.presentation.ui.detail.view

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.ScrollView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.detail.RequestPostDetailCommentNote
import com.dnd_8th_4_android.wery.data.remote.model.detail.RequestPostDetailStickerId
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.databinding.ActivityPopupWindowBinding
import com.dnd_8th_4_android.wery.databinding.ActivityPostDetailBinding
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.detail.adapter.PostDetailCommentRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.detail.adapter.PostDetailEmotionRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.detail.adapter.PostDetailImageRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.detail.adapter.PostDetailStickerRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.detail.viewmodel.PostDetailViewModel
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.util.PostPopupBottomDialog
import com.dnd_8th_4_android.wery.presentation.util.hideKeyboard
import com.dnd_8th_4_android.wery.presentation.util.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>(R.layout.activity_post_detail) {
    private val viewModel: PostDetailViewModel by viewModels()
    private var activityPopupWindowBinding: ActivityPopupWindowBinding? = null
    private var writeButton = false

    private lateinit var postDetailImageRecyclerViewAdapter: PostDetailImageRecyclerViewAdapter
    private lateinit var postDetailEmotionRecyclerViewAdapter: PostDetailEmotionRecyclerViewAdapter
    private lateinit var postDetailCommentRecyclerViewAdapter: PostDetailCommentRecyclerViewAdapter
    private lateinit var postDetailStickerRecyclerViewAdapter: PostDetailStickerRecyclerViewAdapter

    private var userId = 0
    private var contentId = 0
    private var bookmarkAddStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        activityPopupWindowBinding = ActivityPopupWindowBinding.inflate(layoutInflater)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    private fun initStartView() {
        initData()

        // 게시글 이미지
        postDetailImageRecyclerViewAdapter = PostDetailImageRecyclerViewAdapter()
        binding.rvPostImage.adapter = postDetailImageRecyclerViewAdapter
        binding.rvPostImage.isNestedScrollingEnabled = false

        // 감정 이모지
        postDetailEmotionRecyclerViewAdapter = PostDetailEmotionRecyclerViewAdapter()
        binding.rvEmotion.apply {
            adapter = postDetailEmotionRecyclerViewAdapter
            itemAnimator = null
        }
        viewModel.getEmotion(contentId)

        // 공감 이모지 등록
        binding.layoutEmotionPlus.setOnClickListener { getGradePopUp() }

        // 댓글
        postDetailCommentRecyclerViewAdapter = PostDetailCommentRecyclerViewAdapter()
        binding.rvComment.apply {
            adapter = postDetailCommentRecyclerViewAdapter
            itemAnimator = null
            isNestedScrollingEnabled = false
        }
        viewModel.getComment(contentId)

        // 스티커
        viewModel.getSticker()
    }

    private fun initDataBinding() {
        viewModel.isLoading.observe(this) {
            if (it) showLoadingDialog()
            else dismissLoadingDialog()
        }

        viewModel.postDetailList.observe(this) {
            postDetailImageRecyclerViewAdapter.submitList(it.imageList)
            binding.tvHitCount.text = it.views.toString()

            if (it.location != null) {
                binding.tvLocation.text = it.location
            } else {
                binding.tvLocation.text = resources.getString(R.string.home_item_no_location)
            }

            binding.tvFriendName.text = it.userName
            userId = it.userId
            bookmarkAddStatus = it.bookmarkAddStatus
        }

        viewModel.emotionList.observe(this) {
            postDetailEmotionRecyclerViewAdapter.submitList(it.toMutableList())
        }

        viewModel.commentList.observe(this) {
            postDetailCommentRecyclerViewAdapter.submitList(it.toMutableList())
        }

        viewModel.stickerList.observe(this) {
            postDetailStickerRecyclerViewAdapter = PostDetailStickerRecyclerViewAdapter(it)
            postDetailStickerRecyclerViewAdapter.setStickerClickListener { stickerId ->
                viewModel.setUpdateSticker(contentId, RequestPostDetailStickerId(stickerId))
                viewModel.setSelected()
//                binding.rvComment.smoothScrollToPosition(0)
            }
            binding.rvSticker.adapter = postDetailStickerRecyclerViewAdapter
        }

        viewModel.isSelected.observe(this) {
            binding.rvSticker.isVisible = it

            if (it) {
                if (postDetailStickerRecyclerViewAdapter.itemCount == 0) {
                    binding.rvSticker.isVisible = false
                    binding.layoutNoSticker.isVisible = true
                } else {
                    binding.rvSticker.isVisible = true
                    binding.layoutNoSticker.isVisible = false
                }
            }
        }

        viewModel.isEnabled.observe(this) {
            binding.ivSticker.isEnabled = !it
        }
    }

    private fun initAfterBinding() {
        if (writeButton) {
            binding.scrollView.post {
                binding.scrollView.fullScroll(View.FOCUS_DOWN)
                binding.etComment.requestFocus()
                binding.etComment.showKeyboard()
            }
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.ivPopup.setOnClickListener {
            val bottomSheet = PostPopupBottomDialog(
                contentId,
                userId,
                bookmarkAddStatus
            )
            bottomSheet.setOnBookmarkListener {
                viewModel.getPostDetail(contentId)
            }
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        binding.etComment.setOnFocusChangeListener { _, gainFocus ->
            if (gainFocus) {
                viewModel.setUnSelected()
                binding.ivSend.isSelected = true
                binding.etComment.hint = resources.getString(R.string.post_detail_hint)
                binding.ivSticker.isSelected = false
            }
        }

        binding.ivSticker.setOnClickListener {
            binding.ivSend.isSelected = false
            viewModel.setSelected()
            binding.etComment.hideKeyboard()
            binding.ivSticker.isSelected = !binding.ivSticker.isSelected

            binding.scrollView.post {
                binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN)
            }

            if (it.isSelected) {
                binding.etComment.hint = resources.getString(R.string.post_detail_hint_sticker)
            } else {
                binding.etComment.hint = resources.getString(R.string.post_detail_hint)
            }
        }

        binding.etComment.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(200))

        binding.ivSend.setOnClickListener {
            if (binding.etComment.text.toString() != "") {
                viewModel.setUpdateComment(
                    contentId,
                    RequestPostDetailCommentNote(binding.etComment.text.toString())
                )
                binding.etComment.setText("")
                binding.etComment.hideKeyboard()
            }
        }

        binding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, _, _, _ ->
            if (viewModel.isLoading.value == false && viewModel.isNoData.value != true && !v.canScrollVertically(
                    1
                )
            ) {
                viewModel.setUpPageNumber()
                viewModel.getComment(contentId)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        activityPopupWindowBinding = null
    }

    private fun initData() {
        with(binding) {
            writeButton = intent.getBooleanExtra(PostRecyclerViewAdapter.WRITE_CHECK, false)
            contentId = intent.getIntExtra(PostRecyclerViewAdapter.CONTENT_ID, 0)

            tvContent.text = intent.getStringExtra(PostRecyclerViewAdapter.CONTENT).toString()

            binding.ivFriendImage.clipToOutline = true
            Glide.with(applicationContext)
                .load(intent.getStringExtra(PostRecyclerViewAdapter.USER_IMAGE))
                .into(binding.ivFriendImage)

            tvGroupName.text = intent.getStringExtra(PostRecyclerViewAdapter.GROUP_NAME).toString()

            val time = intent.getStringExtra(PostRecyclerViewAdapter.TIME).toString()
            if (time.length > 9) {
                if (LocalDate.now().toString() == time.substring(IntRange(0, 10))) {
                    binding.tvTime.text = time.substring(IntRange(11, 15)).replace("-", ".")
                } else {
                    binding.tvTime.text = time.substring(IntRange(2, 9)).replace("-", ".")
                }
            } else {
                binding.tvTime.text = time
            }
        }

        viewModel.getPostDetail(contentId)

        viewModel.setPageNumber(1)
    }

    private fun getGradePopUp() {
        val popupWindow = PopupWindow(
            activityPopupWindowBinding!!.root,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.contentView = activityPopupWindowBinding!!.root

        popupWindow.showAsDropDown(binding.layoutEmotionPlus, -10, -260)

        activityPopupWindowBinding!!.ivEmotionOne.setOnClickListener {
            setEmotion(RequestEmotionStatus(PopupWindowType.Type1.emotionPosition))
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionTwo.setOnClickListener {
            setEmotion(RequestEmotionStatus(PopupWindowType.Type2.emotionPosition))
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionThree.setOnClickListener {
            setEmotion(RequestEmotionStatus(PopupWindowType.Type3.emotionPosition))
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionFour.setOnClickListener {
            setEmotion(RequestEmotionStatus(PopupWindowType.Type4.emotionPosition))
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionFive.setOnClickListener {
            setEmotion(RequestEmotionStatus(PopupWindowType.Type5.emotionPosition))
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionSix.setOnClickListener {
            setEmotion(RequestEmotionStatus(PopupWindowType.Type6.emotionPosition))
            popupWindow.dismiss()
        }
    }

    private fun setEmotion(emotionStatus: RequestEmotionStatus) {
        viewModel.setUpdateEmotion(contentId, emotionStatus)
    }
}