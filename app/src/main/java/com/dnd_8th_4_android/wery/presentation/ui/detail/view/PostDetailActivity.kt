package com.dnd_8th_4_android.wery.presentation.ui.detail.view

import android.os.Bundle
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.activity.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailCommentData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailImageData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailStickerData
import com.dnd_8th_4_android.wery.databinding.ActivityPopupWindowBinding
import com.dnd_8th_4_android.wery.databinding.ActivityPostDetailBinding
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.detail.adapter.PostDetailCommentRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.detail.adapter.PostDetailEmotionRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.detail.adapter.PostDetailImageRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.detail.adapter.PostDetailStickerRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.detail.viewmodel.PostDetailViewModel
import com.dnd_8th_4_android.wery.presentation.util.MarginItemDecoration
import com.dnd_8th_4_android.wery.presentation.util.PopupBottomDialogDialog

class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>(R.layout.activity_post_detail) {
    private val viewModel: PostDetailViewModel by viewModels()
    private var activityPopupWindowBinding: ActivityPopupWindowBinding? = null

    private lateinit var postDetailImageRecyclerViewAdapter: PostDetailImageRecyclerViewAdapter
    private lateinit var postDetailEmotionRecyclerViewAdapter: PostDetailEmotionRecyclerViewAdapter
    private lateinit var postDetailCommentRecyclerViewAdapter: PostDetailCommentRecyclerViewAdapter
    private lateinit var postDetailStickerRecyclerViewAdapter: PostDetailStickerRecyclerViewAdapter

    private lateinit var imageList: ResponsePostDetailImageData.Data
    private lateinit var emotionList: List<ResponsePostDetailEmotionData.Data>
    private lateinit var commentList: List<ResponsePostDetailCommentData.Data>
    private lateinit var stickerList: ResponsePostDetailStickerData.Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityPopupWindowBinding = ActivityPopupWindowBinding.inflate(layoutInflater)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    private fun initStartView() {
        makeList()

        // 게시글 이미지
        postDetailImageRecyclerViewAdapter = PostDetailImageRecyclerViewAdapter(imageList)
        binding.rvPostImage.adapter = postDetailImageRecyclerViewAdapter

        // 감정 이모지
        postDetailEmotionRecyclerViewAdapter = PostDetailEmotionRecyclerViewAdapter()
        postDetailEmotionRecyclerViewAdapter.submitList(emotionList)
        binding.rvEmotion.apply {
            adapter = postDetailEmotionRecyclerViewAdapter
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimension(R.dimen.post_detail_emotion_item_margin).toInt()
                )
            )
            itemAnimator = null
        }
        binding.layoutEmotionPlus.setOnClickListener { getGradePopUp() }

        // 댓글
        postDetailCommentRecyclerViewAdapter = PostDetailCommentRecyclerViewAdapter(commentList)
        binding.rvComment.adapter = postDetailCommentRecyclerViewAdapter

        // 스티커
        postDetailStickerRecyclerViewAdapter = PostDetailStickerRecyclerViewAdapter(stickerList)
        binding.rvSticker.adapter = postDetailStickerRecyclerViewAdapter
    }

    private fun initDataBinding() {
        viewModel.isUpdateList.observe(this) {
            postDetailEmotionRecyclerViewAdapter.submitList(it.toMutableList())
            emotionList = it
        }
    }

    private fun initAfterBinding() {
        binding.ivPopup.setOnClickListener {
            val bottomSheet = PopupBottomDialogDialog()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityPopupWindowBinding = null
    }

    private fun makeList() {
        imageList =
            ResponsePostDetailImageData.Data(
                listOf(
                    R.drawable.img_no_group,
                    R.drawable.img_no_group,
                    R.drawable.img_no_group
                )
            )

        emotionList =
            listOf(
                ResponsePostDetailEmotionData.Data(
                    Pair(
                        R.drawable.img_no_group,
                        R.drawable.img_crying_face
                    )
                ),
                ResponsePostDetailEmotionData.Data(
                    Pair(
                        R.drawable.img_no_group,
                        R.drawable.img_crying_face
                    )
                ),
                ResponsePostDetailEmotionData.Data(
                    Pair(
                        R.drawable.img_no_group,
                        R.drawable.img_crying_face
                    )
                )
            )

        commentList = listOf(
            ResponsePostDetailCommentData.Data(
                R.drawable.img_no_group,
                "User1",
                R.drawable.img_no_group,
                "",
                "11:11"
            ),
            ResponsePostDetailCommentData.Data(
                R.drawable.img_no_group,
                "User1",
                0,
                "댓글은 최대 200자로 제한 합니다.댓글은 최대 200자로 제한 합니다.댓글은 최대 200자로 제한 합니다.댓글은 최대 200자로 제한 합니다.댓글은 최대 200자로 제한 합니다.댓글은 최대 200자로 제한 합니다.댓글은 최대 200자로 제한 합니다.댓글은 최대 200자로 제한 합니다.댓글은 최대 200자로 제한 합니다.댓글은 최대 200자로 제한 합니다.댓글은 최대 200자로 제한 합니다.",
                "22:22"
            ),
        )

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
        // 팝업 생성
        val popupWindow = PopupWindow(
            activityPopupWindowBinding!!.root,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            true
        )
        // 현재 유지시킬 뷰 설정(이 줄을 없애면 키보드가 올라옴)
        popupWindow.contentView = activityPopupWindowBinding!!.root

        // 어떤 레이아웃 밑에 팝업을 달건지 설정
        popupWindow.showAsDropDown(binding.layoutEmotionPlus, -10, -260)

        activityPopupWindowBinding!!.ivEmotionOne.setOnClickListener {
            setEmotion(PopupWindowType.Type1.emotionPosition)
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionTwo.setOnClickListener {
            setEmotion(PopupWindowType.Type2.emotionPosition)
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionThree.setOnClickListener {
            setEmotion(PopupWindowType.Type3.emotionPosition)
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionFour.setOnClickListener {
            setEmotion(PopupWindowType.Type4.emotionPosition)
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionFive.setOnClickListener {
            setEmotion(PopupWindowType.Type5.emotionPosition)
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionSix.setOnClickListener {
            setEmotion(PopupWindowType.Type6.emotionPosition)
            popupWindow.dismiss()
        }
    }

    private fun setEmotion(emotionPosition: Int) {
        // 감정 이모지를 등록하는 사용자 이미지 필요
        viewModel.setUpdateList(emotionPosition, emotionList, R.drawable.img_no_group)
    }
}