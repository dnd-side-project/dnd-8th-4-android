package com.dnd_8th_4_android.wery.presentation.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMission
import com.dnd_8th_4_android.wery.databinding.FragmentMapBinding
import com.dnd_8th_4_android.wery.domain.model.DialogInfo
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.util.DialogFragmentUtil
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {

    private lateinit var mapView: MapView
    private lateinit var eventListener: MarkerEventListener

    private val mapViewModel: MapViewModel by viewModels()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                getMyCurrentLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                getMyCurrentLocation()
            }
            else -> {
                // 권한 거부
                permissionDialog()
            }
        }
    }

    override fun initStartView() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        initMapView()
    }

    private fun permissionDialog() {
        fun doPositiveClick() {
            startActivity(
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context?.packageName, null)
                )
            )
        }

        val dialog = DialogFragmentUtil(
            DialogInfo(
                "위치 접근 권한",
                "위치 접근 권한이 필요합니다.\n확인을 누르면 설정화면으로 이동합니다.",
                "닫기",
                "확인"
            )
        ) { doPositiveClick() }
        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
    }

    /** 최초 시작 점은 사용자 현재 위치를 기반으로 한다
     * */
    private fun initMapView() {
        mapView = MapView(requireActivity())
        binding.layoutMapView.addView(mapView)

        eventListener = MarkerEventListener(requireContext())
        mapView.setPOIItemEventListener(eventListener)
    }

    // 현재 위치 구하기
    @SuppressLint("MissingPermission")
    private fun getMyCurrentLocation() {
        // 트랙킹 모드 ON
        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving

        // gps가 켜져있는지 확인
        val lm: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val myCurrentLocation: Location? = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        //위도 , 경도
        mapViewModel.myCurrentLatitude.value = myCurrentLocation?.latitude!!
        mapViewModel.myCurrentLongitude.value = myCurrentLocation.longitude

        mapView.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(
                mapViewModel.myCurrentLatitude.value!!,
                mapViewModel.myCurrentLongitude.value!!
            ), 5, false
        ) // 맵의 중심좌표 구하기
        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOff // 트랙킹 모드 OFF
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        binding.layoutReloadCurrentLocation.setOnClickListener {
            getMyCurrentLocation()
            showMissionPinList()
        }
    }

    // TODO 서버 통신 후 미션 들의 위치 좌표 값을 가져온다
    // 미션 리스트 띄우기
    private fun showMissionPinList() {
        val missionList = mutableListOf<ResponseMission>()
        missionList.apply {
            add(ResponseMission(33.4507057,126.570677))
            add(ResponseMission(33.450936,126.569477))
            add(ResponseMission(33.450879,126.569940))
            add(ResponseMission(33.450705,126.570738))
        }

        val missionMarkerArr = arrayListOf<MapPOIItem>()
        for (i in missionList.indices) {
            val missionMarker = MapPOIItem()
            missionMarker.apply {
                itemName = ""
                isShowCalloutBalloonOnTouch = false
                mapPoint = MapPoint.mapPointWithGeoCoord(missionList[i].x, missionList[i].y)
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = R.drawable.img_pin_mission_pink_default
                selectedMarkerType = MapPOIItem.MarkerType.CustomImage
                customSelectedImageResourceId = R.drawable.img_pin_mission_pin_select
                isCustomImageAutoscale = false
            }
            missionMarkerArr.add(missionMarker)
        }

        val convertToArrayItem =
            missionMarkerArr.toArray(arrayOfNulls<MapPOIItem>(missionMarkerArr.size))
        mapView.addPOIItems(convertToArrayItem)
    }
}

