package com.dnd_8th_4_android.wery.presentation.ui.mission.create

import android.app.Activity
import android.app.DatePickerDialog
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
import com.dnd_8th_4_android.wery.presentation.ui.post.place.view.SearchPlaceActivity
import com.dnd_8th_4_android.wery.presentation.ui.post.upload.view.SelectGroupBottomDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CreateMissionActivity :
    BaseActivity<ActivityCreateMissionBinding>(R.layout.activity_create_mission) {

    private val viewModel: CreateMissionViewModel by viewModels()

    private val requestSearchActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if (it.resultCode == Activity.RESULT_OK) {
                val selectedPlace = it.data?.getStringExtra("selectedPlace")
                    ?: getString(R.string.create_mission_place_hint)

                val lat = it.data?.getDoubleExtra("selectedX", 0.0)!!
                val long = it.data?.getDoubleExtra("selectedY", 0.0)!!

                viewModel.setSelectedPlace(selectedPlace)
                viewModel.setLocationXY(lat, long)
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
        viewModel.setTodayDate()
    }

    private fun initDataBinding() {
        viewModel.selectedPlaceTxt.observe(this) {
            binding.tvVisitPlace.text = it
            val statePlace =
                !viewModel.selectedPlaceTxt.value.equals(resources.getString(R.string.create_mission_place_hint))
            viewModel.missionPlaceState.value = statePlace
        }
        setTxtError(
            binding.etvMissionName,
            binding.tvMissionNameLimit,
            20,
            binding.ivMissionNameClose
        )
        setTxtCancelListener(binding.etvMissionName, binding.ivMissionNameClose)
        viewModel.starDateTxt.observe(this) {
            binding.tvStartDate.text = it
        }
        viewModel.endDateTxt.observe(this) {
            binding.tvEndDate.text = it
        }
        viewModel.missionNameTxt.observe(this) {
            val stateMissionName = viewModel.missionNameTxt.value!!.length in 1..20
            viewModel.missionNameState.value = stateMissionName
        }
        viewModel.selectedGroup.observe(this) {
            val stateGroup =
                !viewModel.selectedGroup.value.equals(resources.getString(R.string.create_mission_select_group))
            viewModel.missionGroupState.value = stateGroup
        }

        viewModel.isLoading.observe(this) {
            if (it) showLoadingDialog()
            else {
                dismissLoadingDialog()
                finish()
            }
        }

        viewModel.selectedGroup.value = getString(R.string.writing_select_group)
        binding.layoutSelectGroup.setOnClickListener {
            viewModel.selectedGroupState.value = true
            viewModel.getGroupList()
            SelectGroupBottomDialog(viewModel, "m").show(supportFragmentManager, null)
        }
    }

    private fun initAfterBinding() {
        binding.missionTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.text!!.contains("있음")) {
                    binding.layoutDate.visibility = View.VISIBLE
                    viewModel.setExistPeriod(true)
                } else {
                    binding.layoutDate.visibility = View.GONE
                    viewModel.setExistPeriod(false)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        binding.layoutMissionPlace.setOnClickListener {
            requestSearchActivity.launch(Intent(this, SearchPlaceActivity::class.java))
        }
        binding.layoutDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                viewModel.setSelectedDate(year, month + 1, day)
            }
            DatePickerDialog(
                this,
                data,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.colorRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_blue -> viewModel.setMissionColor(0)
                R.id.radio_pink -> viewModel.setMissionColor(1)
                R.id.radio_green -> viewModel.setMissionColor(2)
            }
        }

        binding.ivClose.setOnClickListener {
            finish()
        }
        binding.tvRegister.setOnClickListener {
            val data = viewModel.getRequestBodyData()
            viewModel.postMission(data)
            viewModel.setLoadingDialog(true)
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