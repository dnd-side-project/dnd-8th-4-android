package com.dnd_8th_4_android.wery.presentation.ui.create.view

import android.os.Bundle
import android.text.Spannable
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityCreateGroupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.create.viewmodel.CreateGroupViewModel

class CreateGroupActivity :
    BaseActivity<ActivityCreateGroupBinding>(R.layout.activity_create_group) {

    private val createGroupViewModel: CreateGroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    private fun initStartView() {
        binding.viewModel = createGroupViewModel
    }

    private fun initDataBinding() {
        setGroupNameUi()
        setGroupIntroUi()
    }

    private fun setGroupNameUi() {
        createGroupViewModel.groupNameTxt.observe(this) {
            binding.tvGroupNameLimit.text =
                getString(R.string.create_group_name_limit).format(it.length)
        }
    }

    private fun setGroupIntroUi() {
        createGroupViewModel.groupIntroduceTxt.observe(this) {
            binding.tvGroupIntroduceLimit.text =
                getString(R.string.create_group_introduce_limit).format(it.length)
        }
    }

    private fun initAfterBinding() {
        setTxtError(binding.etvGroupName, binding.tvGroupNameLimit, 12)
        setTxtError(binding.etvGroupIntroduce, binding.tvGroupIntroduceLimit, 25)
    }

    private fun setTxtError(etv: EditText, tv: TextView, lenCnt: Int) {
        etv.addTextChangedListener {
            if (it?.length!! > lenCnt) {
                etv.setBackgroundResource(R.drawable.shape_white_radius_8_eb0555)
                (tv.text as Spannable).setSpan(
                    ForegroundColorSpan(resources.getColor(R.color.color_eb0555, null)), 0, 2,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                )
            } else {
                etv.setBackgroundResource(R.drawable.shape_white_radius_8_black)
                var end = 1
                end = if (it.length < 10) 1 else 2

                (tv.text as Spannable).setSpan(
                    ForegroundColorSpan(resources.getColor(R.color.black, null)), 0, end,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                )
            }
        }

        etv.setOnFocusChangeListener { v, hasFocus ->
            if (!v.hasFocus() && etv.text.length <= lenCnt) {
                etv.setBackgroundResource(R.drawable.shape_white_radius_8_gray300)
                var end = 1
                end = if (etv.text.length < 10) 1 else 2
                (tv.text as Spannable).setSpan(
                    ForegroundColorSpan(resources.getColor(R.color.gray600, null)), 0, end,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                )
            }
        }
    }
}