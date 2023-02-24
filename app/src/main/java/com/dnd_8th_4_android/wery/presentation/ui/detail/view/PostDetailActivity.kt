package com.dnd_8th_4_android.wery.presentation.ui.detail.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.ScrollView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.detail.RequestPostDetailCommentNote
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailStickerData
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
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
import com.dnd_8th_4_android.wery.presentation.util.PopupBottomDialogDialog
import com.dnd_8th_4_android.wery.presentation.util.hideKeyboard
import com.dnd_8th_4_android.wery.presentation.util.showKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>(R.layout.activity_post_detail) {
    private val viewModel: PostDetailViewModel by viewModels()
    private var activityPopupWindowBinding: ActivityPopupWindowBinding? = null
    private var writeButton = false

    private lateinit var postDetailImageRecyclerViewAdapter: PostDetailImageRecyclerViewAdapter
    private lateinit var postDetailEmotionRecyclerViewAdapter: PostDetailEmotionRecyclerViewAdapter
    private lateinit var postDetailCommentRecyclerViewAdapter: PostDetailCommentRecyclerViewAdapter
    private lateinit var postDetailStickerRecyclerViewAdapter: PostDetailStickerRecyclerViewAdapter

    private lateinit var imageList: MutableList<ResponsePostData.Data.Content.Images>
    private lateinit var stickerList: ResponsePostDetailStickerData.Data

    private var contentId = 0

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
        postDetailImageRecyclerViewAdapter = PostDetailImageRecyclerViewAdapter(imageList)
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
        viewModel.getComment(contentId, 1)

        // 스티커
//        postDetailStickerRecyclerViewAdapter = PostDetailStickerRecyclerViewAdapter(stickerList)
//        postDetailStickerRecyclerViewAdapter.setStickerClickListener { sticker ->
//            viewModel.setUpdateComment(commentList, "", sticker)
//            viewModel.setSelected()
//        }
//        binding.rvSticker.adapter = postDetailStickerRecyclerViewAdapter
    }

    private fun initDataBinding() {
        viewModel.emotionCount.observe(this) {
            binding.layoutEmotionCount.visibility = if (it != 0) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        viewModel.emotionList.observe(this) {
            postDetailEmotionRecyclerViewAdapter.submitList(it)
        }

        viewModel.commentList.observe(this) {
            postDetailCommentRecyclerViewAdapter.submitList(it)
        }


//        viewModel.isUpdateComment.observe(this) {
//            postDetailCommentRecyclerViewAdapter.submitList(it.toMutableList())
//            commentList = it
//            viewModel.setCommentCount(it.size)
//
//            Handler(Looper.getMainLooper())
//                .postDelayed({
//                    binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN)
//                }, 100)
//            binding.etComment.hint = resources.getString(R.string.post_detail_hint)
//            binding.ivSticker.isSelected = false
//            binding.ivSend.isSelected = false
//        }

        viewModel.commentCount.observe(this) {
            binding.layoutCommentCount.visibility = if (it != 0) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        viewModel.isSelected.observe(this) {
            binding.rvSticker.isVisible = it
        }

        viewModel.isEnabled.observe(this) {
            binding.ivSticker.isEnabled = !it
        }
    }

    private fun initAfterBinding() {
        if (writeButton) {
            Handler(Looper.getMainLooper())
                .postDelayed({
                    binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN)
                }, 500)
            binding.etComment.requestFocus()
            binding.etComment.showKeyboard()
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.ivPopup.setOnClickListener {
            val bottomSheet = PopupBottomDialogDialog()
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
            Handler(Looper.getMainLooper())
                .postDelayed({
                    binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN)
                }, 100)

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
    }

    override fun onDestroy() {
        super.onDestroy()
        activityPopupWindowBinding = null
    }

    private fun initData() {
        with(binding) {
            writeButton = intent.getBooleanExtra(PostRecyclerViewAdapter.WRITE_CHECK, false)
            contentId = intent.getIntExtra(PostRecyclerViewAdapter.CONTENT_ID, 0)

            tvGroupName.text = intent.getStringExtra(PostRecyclerViewAdapter.GROUP_NAME).toString()

            Glide.with(applicationContext)
                .load(intent.getStringExtra(PostRecyclerViewAdapter.USER_IMAGE))
                .into(binding.ivFriendImage)

            tvFriendName.text = intent.getStringExtra(PostRecyclerViewAdapter.NAME).toString()
            tvTime.text = intent.getStringExtra(PostRecyclerViewAdapter.TIME).toString()
            tvLocation.text = intent.getStringExtra(PostRecyclerViewAdapter.LOCATION).toString()
            tvContent.text = intent.getStringExtra(PostRecyclerViewAdapter.CONTENT).toString()
        }

        imageList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(
                PostRecyclerViewAdapter.IMAGE,
                ResponsePostData.Data.Content.Images::class.java
            ) as MutableList<ResponsePostData.Data.Content.Images>
        } else {
            intent.getSerializableExtra(PostRecyclerViewAdapter.IMAGE) as MutableList<ResponsePostData.Data.Content.Images>
        }




        stickerList = ResponsePostDetailStickerData.Data(
            listOf(
                R.drawable.img_no_group,
                R.drawable.img_crying_face,
                R.drawable.img_checkbox_checked,
                R.drawable.img_checkbox_default,
                R.drawable.img_photo_delete,
            )
        )
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