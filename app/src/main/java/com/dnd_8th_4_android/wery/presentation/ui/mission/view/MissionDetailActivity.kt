package com.dnd_8th_4_android.wery.presentation.ui.mission.view

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.local.AuthLocalDataSource
import com.dnd_8th_4_android.wery.databinding.ActivityMissionDetailBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.view.MissionProgressActivity
import com.dnd_8th_4_android.wery.presentation.ui.mission.viewmodel.MissionDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MissionDetailActivity :
    BaseActivity<ActivityMissionDetailBinding>(R.layout.activity_mission_detail) {
    private val viewModel: MissionDetailViewModel by viewModels()
    private val isMissionCertify = false
    private lateinit var mapView: MapView

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
        }
    }

    private fun initStartView() {
        viewModel.isMissionId.value = intent.getIntExtra("missionId", 0)

        viewModel.getMissionDetail()
    }

    private fun initAfterBinding() {
        if (isMissionCertify) {
            //
        } else {
            getMyCurrentLocation()
            showToast(R.string.mission_toast_message.toString())
            finish()
            MissionProgressActivity().finish()
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