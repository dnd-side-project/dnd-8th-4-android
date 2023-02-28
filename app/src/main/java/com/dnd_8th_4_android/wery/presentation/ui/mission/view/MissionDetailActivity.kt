package com.dnd_8th_4_android.wery.presentation.ui.mission.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.local.AuthLocalDataSource
import com.dnd_8th_4_android.wery.databinding.ActivityMissionDetailBinding
import com.dnd_8th_4_android.wery.domain.model.DialogInfo
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.view.MissionProgressActivity
import com.dnd_8th_4_android.wery.presentation.ui.mission.viewmodel.MissionDetailViewModel
import com.dnd_8th_4_android.wery.presentation.ui.post.upload.view.UploadPostActivity
import com.dnd_8th_4_android.wery.presentation.util.DialogFragmentUtil
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MissionDetailActivity :
    BaseActivity<ActivityMissionDetailBinding>(R.layout.activity_mission_detail) {
    private val viewModel: MissionDetailViewModel by viewModels()

    private val isMissionCertify = false
    private lateinit var mapView: MapView

    companion object {
        const val GROUP_ID = "groupId"
        const val GROUP_NAME = "groupName"
        const val PLACE_NAME = "placeName"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    private fun initDataBinding() {
        viewModel.missionDetail.observe(this) {
            binding.layoutMissionDelete.isVisible =
                it.createUserId == AuthLocalDataSource(this).userId

            binding.tvMissionName.text = it.missionName

            binding.ivFriendImage.clipToOutline = true
//            Glide.with(this).load(it.작성자프로필)
//                .into(binding.ivFriendImage)

//            binding.tvWriterName.text = it.작성자명
            binding.tvMissionPlaceName.text = it.missionLocationName
//            binding.tvMissionPlaceAdress.text = it.미션주소

            binding.tvMissionDue.text = if (it.existPeriod) {
                resources.getString(
                    R.string.mission_detail_period,
                    it.missionStartDate,
                    it.missionEndDate
                )
            } else {
                resources.getString(R.string.mission_detail_no_period, it.missionStartDate)
            }

            binding.tvGroupName.text = it.groupName

            binding.ivGroupImage.clipToOutline = true
            Glide.with(this).load(it.groupImageUrl)
                .into(binding.ivGroupImage)

            // TODO
//            if (it.userAssignMissionInfoList.locationCheck) {
//                binding.btnMissionDetail.text = resources.getString(R.string.mission_detail_certify)
//            } else {
//                binding.btnMissionDetail.text = resources.getString(R.string.mission_detail_write)
//            }
//            isMissionCertify = it.userAssignMissionInfoList.locationCheck

            binding.btnMissionDetail.setOnClickListener { _ ->
                if (isMissionCertify) {
                    finish()
                    MissionProgressActivity().finish()
                    Intent(this, UploadPostActivity::class.java).apply {
                        putExtra(GROUP_ID, it.groupId)
                        putExtra(GROUP_NAME, it.groupName)
                        putExtra(PLACE_NAME, it.missionLocationName)
                        putExtra(LATITUDE, it.latitude)
                        putExtra(LONGITUDE, it.longitude)
                        startActivity(this)
                    }
                } else {
                    getMyCurrentLocation()
                    viewModel.missionCertify(it.groupId)
                }
            }
        }

        viewModel.isCertify.observe(this) {
            if (it) {
                showToast(resources.getString(R.string.mission_detail_toast_message_success))
                finish()
                MissionProgressActivity().finish()
            } else {
                showToast(resources.getString(R.string.mission_detail_toast_message_failure))
            }
        }
    }

    private fun initStartView() {
        viewModel.isMissionId.value = intent.getIntExtra("missionId", 0)

        viewModel.getMissionDetail()
    }

    private fun initAfterBinding() {
        binding.ivClose.setOnClickListener {
            finish()
        }

        binding.layoutMissionDelete.setOnClickListener {
            val dialog = DialogFragmentUtil(
                DialogInfo(
                    resources.getString(R.string.mission_detail_dialog_title),
                    resources.getString(R.string.mission_detail_dialog_content),
                    "취소",
                    resources.getString(R.string.mission_detail_dialog_confirm)
                )
            ) {
                viewModel.missionDelete()
                showToast(resources.getString(R.string.mission_detail_toast_message_delete))
                finish()
                MissionProgressActivity().finish()
            }
            dialog.show(supportFragmentManager, dialog.tag)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMyCurrentLocation() {
        // 트랙킹 모드 ON
        mapView = MapView(this)
        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving

        // gps가 켜져있는지 확인
        val lm: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val myCurrentLocation: Location? =
            lm.getLastKnownLocation(LocationManager.GPS_PROVIDER) ?: lm.getLastKnownLocation(
                LocationManager.NETWORK_PROVIDER
            )
        //위도 , 경도
        viewModel.myCurrentLatitude.value = myCurrentLocation?.latitude ?: 0.0
        viewModel.myCurrentLongitude.value = myCurrentLocation?.longitude ?: 0.0

        // 맵의 중심좌표 구하기
        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOff // 트랙킹 모드 OFF
    }
}