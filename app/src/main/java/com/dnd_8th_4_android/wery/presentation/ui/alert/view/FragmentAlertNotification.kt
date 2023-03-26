package com.dnd_8th_4_android.wery.presentation.ui.alert.view

import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentAlertNotificationBinding
import com.dnd_8th_4_android.wery.presentation.ui.alert.adapter.AlertNotificationAdapter
import com.dnd_8th_4_android.wery.presentation.ui.alert.viewmodel.AlertNotificationViewModel
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAlertNotification :
    BaseFragment<FragmentAlertNotificationBinding>(R.layout.fragment_alert_notification) {
    private val viewModel: AlertNotificationViewModel by viewModels()
    private lateinit var alertNotificationAdapter: AlertNotificationAdapter

    override fun initStartView() {
        binding.vm = viewModel
        viewModel.getNotificationList()

        alertNotificationAdapter = AlertNotificationAdapter()
        binding.rvAlert.adapter = alertNotificationAdapter
    }

    override fun initDataBinding() {
        viewModel.notificationList.observe(this) {
            alertNotificationAdapter.submitList(it)
        }
    }

    override fun initAfterBinding() {
    }
}