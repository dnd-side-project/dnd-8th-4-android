package com.dnd_8th_4_android.wery.presentation.ui.detail.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailCommentData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailImageData
import com.dnd_8th_4_android.wery.databinding.ActivityPostDetailBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.detail.adapter.PostDetailCommentRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.detail.adapter.PostDetailEmotionRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.detail.adapter.PostDetailImageRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.util.MarginItemDecoration

class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>(R.layout.activity_post_detail) {
    private lateinit var postDetailImageRecyclerViewAdapter: PostDetailImageRecyclerViewAdapter
    private lateinit var postDetailEmotionRecyclerViewAdapter: PostDetailEmotionRecyclerViewAdapter
    private lateinit var postDetailCommentRecyclerViewAdapter: PostDetailCommentRecyclerViewAdapter

    private lateinit var imageList: ResponsePostDetailImageData.Data
    private lateinit var emotionList: List<ResponsePostDetailEmotionData.Data>
    private lateinit var commentList: List<ResponsePostDetailCommentData.Data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        }

        // 댓글
        postDetailCommentRecyclerViewAdapter = PostDetailCommentRecyclerViewAdapter(commentList)
        binding.rvComment.adapter = postDetailCommentRecyclerViewAdapter

    }

    private fun initDataBinding() {

    }

    private fun initAfterBinding() {

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
    }
}