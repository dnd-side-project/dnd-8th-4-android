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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapFeedList
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapMissionList
import com.dnd_8th_4_android.wery.data.remote.model.post.ResponseSearchPlace
import com.dnd_8th_4_android.wery.databinding.FragmentMapBinding
import com.dnd_8th_4_android.wery.databinding.ItemMarkerFeedBinding
import com.dnd_8th_4_android.wery.domain.model.DialogInfo
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.map.adapter.MapFeedAdapter
import com.dnd_8th_4_android.wery.presentation.ui.mission.view.MissionDetailActivity
import com.dnd_8th_4_android.wery.presentation.ui.post.place.view.SearchPlaceActivity
import com.dnd_8th_4_android.wery.presentation.util.DialogFragmentUtil
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.MapViewEventListener

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map), MapViewEventListener{

    private lateinit var eventListener: MarkerEventListener

    private val mapViewModel: MapViewModel by viewModels()

    private val mapView: MapView by lazy {  MapView(requireActivity()) }

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
                // ?????? ??????
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

                mapView.setMapCenterPointAndZoomLevel(
                    MapPoint.mapPointWithGeoCoord(
                        mapViewModel.searchResult.value!!.y,
                        mapViewModel.searchResult.value!!.x
                    ),
                    4, true
                )
                /** ????????? ????????? ????????? ???
                 * ????????? ????????? ?????? ????????? ???????????????
                 * ??????????????? x,y ????????? ?????? ????????? ????????????
                 * ??? ?????? ?????? ????????? ???????????? '??????'??? '??????'??? ????????????*/
                mapView.removeAllPOIItems()
                searchPinMarker()
            }
        }

    private val requestUploadActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if (it.resultCode == Activity.RESULT_OK) {
                val upLoadLatitude = it.data?.getDoubleExtra("selectedY", 0.0)
                val upLoadLongitude = it.data?.getDoubleExtra("selectedX", 0.0)

                mapViewModel.myCurrentLatitude.value = upLoadLatitude
                mapViewModel.myCurrentLongitude.value = upLoadLongitude

                mapView.setMapCenterPointAndZoomLevel(
                    MapPoint.mapPointWithGeoCoord(
                        mapViewModel.myCurrentLatitude.value!!,
                        mapViewModel.myCurrentLongitude.value!!
                    ), 4, false
                )

                setXY()
                getSelectedPOItems()
            }
        }

    private fun getSelectedPOItems() {
        if (mapViewModel.filterType.value == 0) {
            setXY()
            mapViewModel.getFeedList()
            mapViewModel.getFeedList()
            mapViewModel.getFeedList()
        } else {
            setMapBoundsPoint()
            mapViewModel.getMissionList(mapViewModel.getCurrentMapBounds())
        }
    }

    private fun searchPinMarker() {
        val marker = MapPOIItem()
        marker.apply {
            itemName = mapViewModel.searchResult.value!!.place_name   // ?????? ??????
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
                "?????? ?????? ??????",
                "?????? ?????? ????????? ???????????????.\n????????? ????????? ?????????????????? ???????????????.",
                "??????",
                "??????"
            )
        ) { doPositiveClick() }
        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
    }

    /** ?????? ?????? ?????? ????????? ?????? ????????? ???????????? ??????
     * */
    @SuppressLint("ClickableViewAccessibility")
    private fun initMapView() {
        binding.layoutMapView.addView(mapView)
        eventListener = MarkerEventListener()
        mapView.setPOIItemEventListener(eventListener)
        mapView.setMapViewEventListener(this)

        getMyCurrentLocation()
    }

    // ?????? ?????? ?????????
    @SuppressLint("MissingPermission")
    private fun getMyCurrentLocation() {
        // ????????? ?????? ON
        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving

        // gps??? ??????????????? ??????
        val lm: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val myCurrentLocation: Location? =
            lm.getLastKnownLocation(LocationManager.GPS_PROVIDER) ?: lm.getLastKnownLocation(
                LocationManager.NETWORK_PROVIDER
            )
        //?????? , ??????
        mapViewModel.myCurrentLatitude.value = myCurrentLocation?.latitude ?: 0.0
        mapViewModel.myCurrentLongitude.value = myCurrentLocation?.longitude ?: 0.0

        mapView.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(
                mapViewModel.myCurrentLatitude.value!!,
                mapViewModel.myCurrentLongitude.value!!
            ), 4, false
        ) // ?????? ???????????? ?????????
        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOff // ????????? ?????? OFF
    }

    override fun initDataBinding() {
        mapViewModel.searchPlaceTxt.value = resources.getString(R.string.map_search_hint)

        //mapViewModel.isLoading.observe(viewLifecycleOwner) {
        //    if (it) showLoadingDialog()
        //    else dismissLoadingDialog()
        //}

        /** [????????????] ?????? ?????? feed ?????? mission ?????? ????????? ??????
         * ?????? ?????? ????????? ??????*/
        mapViewModel.searchPlaceTxt.observe(viewLifecycleOwner) {
            binding.tvSearchHint.text = it
            setDialogEventPop()
        }

        mapViewModel.filterType.observe(viewLifecycleOwner) {
            if (it == 0) {
                binding.ivFilterFeed.isSelected = true
                binding.ivFilterMission.isSelected = false
                binding.standardBottomSheetMission.visibility = View.GONE
            } else {
                binding.ivFilterFeed.isSelected = false
                binding.ivFilterMission.isSelected = true
                binding.vpFeedDialog.visibility = View.GONE
            }
        }

        mapViewModel.feedList.observe(viewLifecycleOwner) {
            showFeedMarkerList(it)
        }

        mapViewModel.missionList.observe(viewLifecycleOwner) {
            showMissionMarkerList(it)
        }

        mapViewModel.feedListData.observe(viewLifecycleOwner) {
            val mapFeedAdapter = MapFeedAdapter()
            mapFeedAdapter.itemList = it.data
            binding.vpFeedDialog.adapter = mapFeedAdapter
        }

        mapViewModel.missionCardData.observe(viewLifecycleOwner) {
            binding.includeLayoutMission.apply {
                when (it.data.missionColor) {
                    0 -> layoutMissionCard.setBackgroundResource(R.drawable.shape_blue_radius_8)
                    1 -> layoutMissionCard.setBackgroundResource(R.drawable.shape_pink_radius_8)
                    2 -> layoutMissionCard.setBackgroundResource(R.drawable.shape_green_radius_8)
                }
            }
            binding.includeLayoutMission.ivGroupImg.clipToOutline = true
            binding.includeLayoutMission.data = it.data

            val intent = Intent(requireContext(), MissionDetailActivity::class.java)
            intent.putExtra("missionId", it.data.groupId)
            binding.includeLayoutMission.root.setOnClickListener {
                startActivity(intent)
            }
        }
    }

    private fun setDialogEventPop() {
        if (mapViewModel.filterType.value == 0) { // ??????
            // ?????? visible
            binding.vpFeedDialog.visibility = View.GONE
        } else { // ??????
            binding.standardBottomSheetMission.visibility = View.GONE
        }
        mapViewModel.setBottomDialogShowingState(false)
        binding.btnFloatingAction.visibility = View.VISIBLE
    }

    override fun initAfterBinding() {
        initViewPager()

        binding.layoutReloadCurrentLocation.setOnClickListener {
            getMyCurrentLocation()
        }

        binding.ivFilterFeed.setOnClickListener {
            if (mapViewModel.filterType.value != 0) {
                mapViewModel.setFilterType(0)
                setXY()
                mapViewModel.getFeedList()
                mapViewModel.getFeedList()
                mapViewModel.getFeedList()
                mapView.removeAllPOIItems()
            }
            if (mapViewModel.searchResult.value?.x != null) {
                if (mapViewModel.searchResult.value?.x != 0.0) searchPinMarker()
            }
        }

        binding.ivFilterMission.setOnClickListener {
            if (mapViewModel.filterType.value != 1) {
                mapViewModel.setFilterType(1)
                setMapBoundsPoint()
                mapViewModel.getMissionList(mapViewModel.getCurrentMapBounds())
                mapView.removeAllPOIItems()
            }
            if (mapViewModel.searchResult.value?.x != null) {
                if (mapViewModel.searchResult.value?.x != 0.0) searchPinMarker()
            }
        }

        binding.tvSearchHint.setOnClickListener {
            val intent = Intent(requireContext(),SearchPlaceActivity::class.java)
            intent.putExtra("fromMapSearch",true)
            requestSearchActivity.launch(intent)
        }

        binding.ivSearchClose.setOnClickListener {
            mapViewModel.searchPlaceTxt.value = resources.getString(R.string.map_search_hint)
        }

        binding.btnFloatingAction.setOnClickListener {
            val intent = Intent(requireContext(), SearchPlaceActivity::class.java)
            intent.putExtra("fromMapBtn", true)
            requestUploadActivity.launch(intent)
        }
    }

    /**
     * ????????? x,y ??????????????? ?????? ???????????? ????????????
     * location??? itemName?????? ?????????
     * ????????? ???????????? ??? ???????????? param?????? contentId??? ????????????
     * */
    private fun showFeedMarkerList(feedList: List<ResponseMapFeedList.ResultMapFeedData>) {
        val distinctFeedList = feedList.distinctBy { it.location }.toList()
        val feedMarkerArr = arrayListOf<MapPOIItem>()

        for (i in distinctFeedList.indices) {
            val view = ItemMarkerFeedBinding.inflate(layoutInflater)

            if (distinctFeedList[i].counts <= 1) view.tvPhotoCnt.visibility = View.GONE
            view.tvPhotoCnt.text =
                getString(R.string.map_content_count).format(distinctFeedList[i].counts)

            Glide.with(requireContext()).load(distinctFeedList[i].contentImageUrl)
                .transform(CenterCrop(), RoundedCorners(12)).override(60, 60)
                .into(view.ivMapGroupImg).waitForLayout()

            val myCustomImageBitmap = createBitMapFromView(view.root)
            view.layoutGroupPhoto.strokeColor = resources.getColor(R.color.color_f47aff, null)
            val mySelectedCustomImageBitmap = createBitMapFromView(view.root)

            val feedMarker = MapPOIItem()
            feedMarker.apply {
                itemName = distinctFeedList[i].location
                isShowCalloutBalloonOnTouch = false
                mapPoint =
                    MapPoint.mapPointWithGeoCoord(distinctFeedList[i].latitude, distinctFeedList[i].longitude)
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


    /**
     * ????????? 4?????? ??????????????? ?????? ???????????? ????????????
     * groupId??? itemName?????? ?????????
     * ????????? ???????????? ??? ???????????? param?????? groupId??? ????????????
     * */
    private fun showMissionMarkerList(missionList: List<ResponseMapMissionList.ResultMapMission>) {
        val missionMarkerArr = arrayListOf<MapPOIItem>()
        for (i in missionList.indices) {
            val missionMarker = MapPOIItem()

            var myCustomImageResourceId = 0
            var myCustomSelectedImageResourceId = 0
            when (missionList[i].missionColor) {
                0 -> {
                    myCustomImageResourceId = R.drawable.img_pin_mission_blue_default
                    myCustomSelectedImageResourceId = R.drawable.img_pin_mission_blue_select
                }
                1 -> {
                    myCustomImageResourceId = R.drawable.img_pin_mission_pink_default
                    myCustomSelectedImageResourceId = R.drawable.img_pin_mission_pin_select
                }
                2 -> {
                    myCustomImageResourceId = R.drawable.img_pin_mission_green_default
                    myCustomSelectedImageResourceId = R.drawable.img_pin_mission_green_select
                }
            }

            missionMarker.apply {
                itemName = missionList[i].missionId.toString()
                isShowCalloutBalloonOnTouch = false
                mapPoint =
                    MapPoint.mapPointWithGeoCoord(missionList[i].latitude, missionList[i].longitude)
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = myCustomImageResourceId
                selectedMarkerType = MapPOIItem.MarkerType.CustomImage
                customSelectedImageResourceId = myCustomSelectedImageResourceId
                isCustomImageAutoscale = false
            }

            missionMarkerArr.add(missionMarker)
        }

        val convertToArrayItem = missionMarkerArr.toArray(arrayOfNulls<MapPOIItem>(missionMarkerArr.size))
        mapView.addPOIItems(convertToArrayItem)
    }

    // ?????????, ????????? ?????? ????????????
    private fun setMapBoundsPoint() {
        mapViewModel.startLatitude.value =
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude // ????????? lat
        mapViewModel.startLongitude.value =
            mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude // ????????? long
        mapViewModel.endLatitude.value =
            mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude // ????????? lat
        mapViewModel.endLongitude.value =
            mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude // ????????? long
    }

    // x, y ?????? ????????????
    private fun setXY() {
        mapViewModel.myCurrentLatitude.value = mapView.mapCenterPoint.mapPointGeoCoord.latitude
        mapViewModel.myCurrentLongitude.value = mapView.mapCenterPoint.mapPointGeoCoord.longitude
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
            binding.root.resources.getDimensionPixelOffset(R.dimen.view_pager_padding_width) // ???????????? padding
        val offsetPx =
            binding.root.resources.getDimensionPixelOffset(R.dimen.view_pager_offset_10)// ????????? ?????? ??????

        binding.vpFeedDialog.setPadding(pagerPadding, 0, pagerPadding, pagerPadding)
        binding.vpFeedDialog.setPageTransformer { page, position ->
            page.translationX = position * offsetPx
        }

        binding.vpFeedDialog.offscreenPageLimit = 2 // ??? ?????? ???????????? ?????? ?????? ???????????????
    }

    private fun getFeedVpData(location: String) {
        mapViewModel.getFeedData(location)
    }

    private fun getMissionCardData(missionId: Int) {
        mapViewModel.getMissionCardData(missionId)
    }

    inner class MarkerEventListener() :
        MapView.POIItemEventListener {

        override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
            // ?????? ?????? ???
            if (!poiItem!!.isShowCalloutBalloonOnTouch) {
                if (mapViewModel.filterType.value == 0) { // ?????? ?????? ??? ???
                    getFeedVpData(poiItem.itemName)
                    binding.vpFeedDialog.visibility = View.VISIBLE
                } else { // ?????? ?????? ??? ???
                    getMissionCardData(poiItem.itemName.toInt())
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

    override fun onMapViewInitialized(p0: MapView?) {}
    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {}
    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {}

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        setDialogEventPop()
        getSelectedPOItems()
    }

}