package com.dnd_8th_4_android.wery.presentation.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapFeed
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapMission
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseSearchPlace
import com.dnd_8th_4_android.wery.databinding.FragmentMapBinding
import com.dnd_8th_4_android.wery.databinding.ItemMarkerFeedBinding
import com.dnd_8th_4_android.wery.domain.model.DialogInfo
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.map.adapter.MapFeedAdapter
import com.dnd_8th_4_android.wery.presentation.ui.write.place.view.SearchPlaceActivity
import com.dnd_8th_4_android.wery.presentation.ui.write.upload.view.WritingActivity
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
                initMapView()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                initMapView()
            }
            else -> {
                // 권한 거부
                permissionDialog()
            }
        }
    }

    private val requestSearchActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if (it.resultCode == Activity.RESULT_OK) {
                val selectedPlace =
                    it.data?.getStringExtra("selectedPlace") ?: getString(R.string.map_search_hint)
                val selectedX = it.data?.getDoubleExtra("selectedX", 0.0)
                val selectedY = it.data?.getDoubleExtra("selectedY", 0.0)

                mapViewModel.searchPlaceTxt.value = selectedPlace

                mapViewModel.setSearchResult(
                    ResponseSearchPlace.Document(
                        selectedPlace,
                        null,
                        selectedX!!,
                        selectedY!!
                    )
                )

                /** 검색한 장소가 존재할 때
                 * 검색결과의 x,y 위치를 지도 마커로 찍어준다  */
                pinSearchMarker()
            }
        }

    private fun pinSearchMarker() {
        val marker = MapPOIItem()
        marker.apply {
            itemName = mapViewModel.searchResult.value!!.place_name   // 마커 이름
            mapPoint = MapPoint.mapPointWithGeoCoord(
                mapViewModel.searchResult.value!!.y,
                mapViewModel.searchResult.value!!.x
            )
            markerType = MapPOIItem.MarkerType.CustomImage
            customImageResourceId = R.drawable.img_current_location_pin
            selectedMarkerType = MapPOIItem.MarkerType.CustomImage
            customSelectedImageResourceId = R.drawable.img_current_location_pin
            isCustomImageAutoscale = false
        }

        mapView.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(
                mapViewModel.searchResult.value!!.y, mapViewModel.searchResult.value!!.x
            ), 5, false
        )
        mapView.addPOIItem(marker)
    }

    override fun initStartView() {
        binding.viewModel = mapViewModel

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
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

        eventListener = MarkerEventListener()
        mapView.setPOIItemEventListener(eventListener)

        getMyCurrentLocation()
        showFeedMarkerList()
        showFeedMarkerList()
        showFeedMarkerList()
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
        val myCurrentLocation: Location? =
            lm.getLastKnownLocation(LocationManager.GPS_PROVIDER) ?: lm.getLastKnownLocation(
                LocationManager.NETWORK_PROVIDER
            )
        //위도 , 경도
        mapViewModel.myCurrentLatitude.value = myCurrentLocation?.latitude ?: 0.0
        mapViewModel.myCurrentLongitude.value = myCurrentLocation?.longitude ?: 0.0

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
        mapViewModel.searchPlaceTxt.value = resources.getString(R.string.map_search_hint)

        mapViewModel.searchPlaceTxt.observe(viewLifecycleOwner) {
            binding.tvSearchHint.text = it
        }

        mapViewModel.filterType.observe(viewLifecycleOwner) {
            if (it == 0) {
                binding.ivFilterFeed.isSelected = true
                binding.ivFilterMission.isSelected = false
            } else {
                binding.ivFilterFeed.isSelected = false
                binding.ivFilterMission.isSelected = true
            }
        }

        mapViewModel.isBottomDialogShowing.observe(viewLifecycleOwner) {
            if (it) {
                mapView.setMapViewEventListener(object : MapView.MapViewEventListener {
                    override fun onMapViewInitialized(p0: MapView?) {}
                    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {}
                    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {}

                    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
                        if (mapViewModel.filterType.value == 0) { // 피드
                            // 피드 visible
                            Toast.makeText(requireContext(), "피드 마커 해제", Toast.LENGTH_SHORT).show()
                            binding.vpFeedDialog.visibility = View.GONE
                        } else { // 미션
                            binding.standardBottomSheetMission.visibility = View.GONE
                            Toast.makeText(requireContext(), "미션 마커 해제", Toast.LENGTH_SHORT).show()
                        }
                        mapViewModel.setBottomDialogShowingState(false)
                        binding.btnFloatingAction.visibility = View.VISIBLE
                    }

                    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {}
                    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {}
                    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {}
                    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {}
                    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {}
                })
            }
        }
    }

    override fun initAfterBinding() {
        initViewPager()

        binding.layoutReloadCurrentLocation.setOnClickListener {
            getMyCurrentLocation()
        }

        binding.ivFilterFeed.setOnClickListener {
            mapViewModel.setFilterType(0)
            mapView.removeAllPOIItems()
            showFeedMarkerList()
        }

        binding.ivFilterMission.setOnClickListener {
            mapViewModel.setFilterType(1)
            mapView.removeAllPOIItems()
            showMissionMarkerList()
        }

        binding.tvSearchHint.setOnClickListener {
            requestSearchActivity.launch(Intent(requireContext(), SearchPlaceActivity::class.java))
        }

        binding.ivSearchClose.setOnClickListener {
            mapViewModel.searchPlaceTxt.value = resources.getString(R.string.map_search_hint)
        }

        binding.btnFloatingAction.setOnClickListener {
            val intent = Intent(requireContext(), WritingActivity::class.java)
            if (mapViewModel.searchPlaceTxt.value != resources.getString(R.string.map_search_hint)) intent.putExtra(
                "placeName", mapViewModel.searchPlaceTxt.value
            )
            startActivity(intent)
        }
    }

    private fun showFeedMarkerList() {

        val imgList = mutableListOf(
            "https://image.dongascience.com/Photo/2022/06/6982fdc1054c503af88bdefeeb7c8fa8.jpg",
            "https://image.dongascience.com/Photo/2022/06/6982fdc1054c503af88bdefeeb7c8fa8.jpg",
        )

        val feedList = mutableListOf<ResponseMapMission>()
        feedList.apply {
            add(ResponseMapMission(33.450936, 126.569477))
            add(ResponseMapMission(33.450879, 126.569940))
        }

        val feedMarkerArr = arrayListOf<MapPOIItem>()

        for (i in feedList.indices) {
            val view = ItemMarkerFeedBinding.inflate(layoutInflater)
            view.ivMapGroupImg.clipToOutline = true
            Glide.with(requireContext()).load(imgList[i])
                .transform(CenterCrop(), RoundedCorners(12)).override(60, 60)
                .into(view.ivMapGroupImg).waitForLayout()

            val myCustomImageBitmap = createBitMapFromView(view.root)

            view.layoutGroupPhoto.foreground = resources.getDrawable(R.drawable.shape_radius_12_f47aff_2, null)

            val mySelectedCustomImageBitmap = createBitMapFromView(view.root)

            val feedMarker = MapPOIItem()
            feedMarker.apply {
                itemName = ""
                isShowCalloutBalloonOnTouch = false
                mapPoint = MapPoint.mapPointWithGeoCoord(feedList[i].x, feedList[i].y)
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageBitmap = myCustomImageBitmap
                selectedMarkerType = MapPOIItem.MarkerType.CustomImage
                customSelectedImageBitmap = mySelectedCustomImageBitmap
                isCustomImageAutoscale = false
            }


            feedMarkerArr.add(feedMarker)

        }

        val convertToArrayItem =
            feedMarkerArr.toArray(arrayOfNulls<MapPOIItem>(feedMarkerArr.size))
        mapView.addPOIItems(convertToArrayItem)
    }

    // TODO 서버 통신 후 미션 들의 위치 좌표 값을 가져온다
    // 미션 리스트 띄우기
    private fun showMissionMarkerList() {
        val missionList = mutableListOf<ResponseMapMission>()
        missionList.apply {
            add(ResponseMapMission(33.450936, 126.569477))
            add(ResponseMapMission(33.450879, 126.569940))
            add(ResponseMapMission(33.450705, 126.570738))
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

    private fun createBitMapFromView(view: View): Bitmap {
        val displayMetrics = DisplayMetrics()
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        val bitmap =
            Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun initViewPager() {
        val pagerPadding =
            binding.root.resources.getDimensionPixelOffset(R.dimen.view_pager_padding_width) // 아이템의 padding
        val offsetPx =
            binding.root.resources.getDimensionPixelOffset(R.dimen.view_pager_offset_10)// 아이템 간의 간격

        binding.vpFeedDialog.setPadding(pagerPadding, 0, pagerPadding, pagerPadding)
        binding.vpFeedDialog.setPageTransformer { page, position ->
            page.translationX = position * offsetPx
        }

        binding.vpFeedDialog.offscreenPageLimit = 2 // 몇 개의 페이지를 미리 로드 해둘것인지

    }

    // TODO 추후 서버통신을 통해 데이터를 받아올 예정
    private fun getFeedVpData() {
        val mapFeedAdapter = MapFeedAdapter()
        mapFeedAdapter.itemList.add(
            ResponseMapFeed(
                "안산 포터블 커피",
                "오늘 지예랑 새로 생긴 카페 갔지롱 인절미 라떼가 진짜 맛있더라 \uD83E\uDD7A 세상엔 왜 이렇게 맛있는 게 많은 걸까 다음엔 너희랑도 가고 싶어",
                "안산 뉴진스", "", "", 4, "2023.02.19"
            )
        )
        mapFeedAdapter.itemList.add(
            ResponseMapFeed(
                "안산 포터블 커피",
                "오늘 지예랑 새로 생긴 카페 갔지롱 인절미 라떼가 진짜 맛있더라 \uD83E\uDD7A 세상엔 왜 이렇게 맛있는 게 많은 걸까 다음엔 너희랑도 가고 싶어",
                "안산 뉴진스", "", "", 4, "2023.02.19"
            )
        )
        binding.vpFeedDialog.adapter = mapFeedAdapter
    }


    inner class MarkerEventListener() :
        MapView.POIItemEventListener {

        override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
            // 마커 클릭 시
            if (!poiItem!!.isShowCalloutBalloonOnTouch) {
                if (mapViewModel.filterType.value == 0) { // 피드 마커 일 때
                    Toast.makeText(
                        requireContext(),
                        "${poiItem?.mapPoint}: 피드 마커 클릭",
                        Toast.LENGTH_SHORT
                    ).show()
                    getFeedVpData()
                    binding.vpFeedDialog.visibility = View.VISIBLE
                } else { // 미션 마커 일 때
                    Toast.makeText(
                        requireContext(),
                        "${poiItem?.mapPoint}: 미션 마커 클릭",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.standardBottomSheetMission.visibility = View.VISIBLE
                }

                mapViewModel.setBottomDialogShowingState(true)
                binding.btnFloatingAction.visibility = View.GONE
            }
        }

        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?) {}

        override fun onCalloutBalloonOfPOIItemTouched(
            mapView: MapView?,
            poiItem: MapPOIItem?,
            buttonType: MapPOIItem.CalloutBalloonButtonType?
        ) {
        }

        override fun onDraggablePOIItemMoved(
            mapView: MapView?,
            poiItem: MapPOIItem?,
            mapPoint: MapPoint?
        ) {
        }
    }

}