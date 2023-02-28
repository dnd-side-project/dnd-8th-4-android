package com.dnd_8th_4_android.wery.presentation.ui.alert.view

import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentAlertInviteBinding
import com.dnd_8th_4_android.wery.presentation.ui.alert.adapter.AlertInviteRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment

class FragmentAlertInvite :
    BaseFragment<FragmentAlertInviteBinding>(R.layout.fragment_alert_invite) {
    private lateinit var alertInviteRecyclerViewAdapter: AlertInviteRecyclerViewAdapter

    override fun initStartView() {
        alertInviteRecyclerViewAdapter = AlertInviteRecyclerViewAdapter()
        binding.rvInvite.apply {
            adapter = alertInviteRecyclerViewAdapter
            itemAnimator = null
        }
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}