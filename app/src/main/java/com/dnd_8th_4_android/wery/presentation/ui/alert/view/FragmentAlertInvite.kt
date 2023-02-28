package com.dnd_8th_4_android.wery.presentation.ui.alert.view

import android.widget.Toast
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
        binding.vm = viewModel

        viewModel.getInvite()

        alertInviteRecyclerViewAdapter = AlertInviteRecyclerViewAdapter()
        alertInviteRecyclerViewAdapter.apply {
            setOnAcceptClickListener { groupId, notificationId ->
                viewModel.setAccept(groupId, notificationId)
            }
            setOnDenyClickListener { groupId, notificationId ->
                viewModel.setDeny(groupId, notificationId)
            }
        }

        binding.rvInvite.apply {
            adapter = alertInviteRecyclerViewAdapter
            itemAnimator = null
        }
    }

    override fun initDataBinding() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showLoadingDialog()
            else dismissLoadingDialog()
        }

        viewModel.inviteList.observe(viewLifecycleOwner) {
            alertInviteRecyclerViewAdapter.submitList(it)
        }

        viewModel.isToastMessage.observe(viewLifecycleOwner) {
            if (it) Toast.makeText(
                requireContext(),
                R.string.alert_invite_accept_message,
                Toast.LENGTH_SHORT
            ).show()
            else Toast.makeText(
                requireContext(),
                R.string.alert_invite_deny_message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun initAfterBinding() {

    }
}