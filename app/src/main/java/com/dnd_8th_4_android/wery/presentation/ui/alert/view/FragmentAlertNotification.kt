package com.dnd_8th_4_android.wery.presentation.ui.alert.view

import android.content.Intent
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertNotificationData
import com.dnd_8th_4_android.wery.databinding.FragmentAlertNotificationBinding
import com.dnd_8th_4_android.wery.presentation.ui.alert.adapter.AlertNotificationAdapter
import com.dnd_8th_4_android.wery.presentation.ui.alert.viewmodel.AlertNotificationViewModel
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.detail.view.PostDetailActivity
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAlertNotification :
    BaseFragment<FragmentAlertNotificationBinding>(R.layout.fragment_alert_notification) {
    private val viewModel: AlertNotificationViewModel by viewModels()
    private lateinit var alertNotificationAdapter: AlertNotificationAdapter

    override fun initStartView() {
        binding.vm = viewModel

        alertNotificationAdapter = AlertNotificationAdapter { data -> moveToPostDetail(data) }
        binding.rvAlert.apply {
            adapter = alertNotificationAdapter
            itemAnimator = null
        }
    }

    override fun initDataBinding() {
        viewModel.notificationList.observe(this) {
            alertNotificationAdapter.submitList(it)
        }
        viewModel.isRead.observe(this) {
            viewModel.getAlertPostInfo(viewModel.getContentId())
        }
        viewModel.notificationPostInfo.observe(this) {
            if (it == null) {
                showToast("해당 게시글이 존재하지 않습니다.")
            } else {
                Intent(requireContext(), PostDetailActivity::class.java).apply {
                    putExtra(PostRecyclerViewAdapter.CONTENT_ID, viewModel.contentId.value!!)
                    putExtra(PostRecyclerViewAdapter.CONTENT, it.content ?: "")
                    putExtra(PostRecyclerViewAdapter.USER_IMAGE, it.profileImageUrl)
                    putExtra(PostRecyclerViewAdapter.GROUP_NAME, it.groupName)
                    putExtra(PostRecyclerViewAdapter.TIME, it.createAt)
                    startActivity(this)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotificationList()
    }

    override fun initAfterBinding() {
    }

    private fun moveToPostDetail(data: ResponseAlertNotificationData.Data.NotificationInfo) {
        viewModel.readNotification(data.notificationId)
        viewModel.setContentId(data.contentId)
    }
}