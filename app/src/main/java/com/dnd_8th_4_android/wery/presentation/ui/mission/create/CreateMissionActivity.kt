package com.dnd_8th_4_android.wery.presentation.ui.mission.create

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityCreateMissionBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.write.place.view.SearchPlaceActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class CreateMissionActivity :
    BaseActivity<ActivityCreateMissionBinding>(R.layout.activity_create_mission) {

    private val viewModel: CreateMissionViewModel by viewModels()

    private val requestSearchActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if (it.resultCode == Activity.RESULT_OK) {
                val selectedPlace = it.data?.getStringExtra("selectedPlace")
                    ?: getString(R.string.create_mission_place_hint)
                viewModel.setSelectedPlace(selectedPlace)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    private fun initStartView() {
        binding.vm = viewModel
        binding.missionTabLayout.apply {
            addTab(this.newTab().setText(resources.getString(R.string.create_mission_due_exist)))
            addTab(this.newTab().setText(resources.getString(R.string.create_mission_due_no_exist)))
        }
        viewModel.setSelectedPlace(resources.getString(R.string.create_mission_place_hint))
    }

    private fun initDataBinding() {
        viewModel.selectedPlaceTxt.observe(this) {
            binding.tvVisitPlace.text = it
        }
        setTxtError(binding.etvMissionName,binding.tvMissionNameLimit,20,binding.ivMissionNameClose)
        setTxtCancelListener(binding.etvMissionName,binding.ivMissionNameClose)
    }

    private fun initAfterBinding() {
        binding.missionTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.text!!.contains("있음")) binding.layoutDate.visibility = View.VISIBLE
                else binding.layoutDate.visibility = View.GONE
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        binding.layoutMissionPlace.setOnClickListener {
            requestSearchActivity.launch(Intent(this, SearchPlaceActivity::class.java))
        }
    }

    private fun setTxtError(etv: EditText, tv: TextView, lenCnt: Int, ivClose: ImageView) {
        etv.addTextChangedListener {
            if (it?.length!! > lenCnt) {
                etv.setBackgroundResource(R.drawable.shape_white_radius_8_eb0555)
                tv.setTextColor(resources.getColor(R.color.color_eb0555, null))
            } else {
                etv.setBackgroundResource(R.drawable.shape_white_radius_8_black)
                tv.setTextColor(resources.getColor(R.color.black, null))
            }
        }

        etv.setOnFocusChangeListener { v, hasFocus ->
            if (!v.hasFocus() && etv.text.length <= lenCnt) {
                etv.setBackgroundResource(R.drawable.shape_white_radius_8_gray300)
                tv.setTextColor(resources.getColor(R.color.gray600, null))
            }

            if (!v.hasFocus()) ivClose.visibility = View.GONE else ivClose.visibility = View.VISIBLE
        }
    }

    private fun setTxtCancelListener(etv: EditText, ivClose: ImageView) {
        etv.addTextChangedListener {
            if (it!!.isNotEmpty()) ivClose.visibility = View.VISIBLE
            else ivClose.visibility = View.GONE
        }
        ivClose.setOnClickListener {
            etv.text.clear()
        }
    }

}