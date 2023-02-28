package com.dnd_8th_4_android.wery.presentation.ui.alert.view

import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentAlertInviteBinding
import com.dnd_8th_4_android.wery.presentation.ui.alert.adapter.AlertInviteRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.alert.viewmodel.AlertInviteViewModel
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAlertInvite :
    BaseFragment<FragmentAlertInviteBinding>(R.layout.fragment_alert_invite) {
    private val viewModel: AlertInviteViewModel by viewModels()
    private lateinit var alertInviteRecyclerViewAdapter: AlertInviteRecyclerViewAdapter

    override fun initStartView() {
        viewModel.getInvite()

        alertInviteRecyclerViewAdapter = AlertInviteRecyclerViewAdapter()
        binding.rvInvite.apply {
            adapter = alertInviteRecyclerViewAdapter
            itemAnimator = null
        }
    }

    override fun initDataBinding() {
        viewModel.inviteList.observe(viewLifecycleOwner) {
            alertInviteRecyclerViewAdapter.submitList(it)
        }
    }

    override fun initAfterBinding() {

    }
}