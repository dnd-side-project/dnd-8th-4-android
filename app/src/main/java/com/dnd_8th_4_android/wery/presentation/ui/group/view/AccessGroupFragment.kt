package com.dnd_8th_4_android.wery.presentation.ui.group.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.ScrollView
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.databinding.ActivityPopupWindowBinding
import com.dnd_8th_4_android.wery.databinding.FragmentAccessGroupBinding
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.AccessGroupMissionRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupListRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel.AccessGroupViewModel
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.home.view.HomeFragment
import com.dnd_8th_4_android.wery.presentation.ui.mission.view.MissionDetailActivity
import com.dnd_8th_4_android.wery.presentation.ui.post.upload.view.UploadPostActivity
import com.dnd_8th_4_android.wery.presentation.ui.search.view.SearchPostActivity
import com.dnd_8th_4_android.wery.presentation.util.PopupBottomDialog
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class AccessGroupFragment :
    BaseFragment<FragmentAccessGroupBinding>(R.layout.fragment_access_group) {
    private val viewModel: AccessGroupViewModel by viewModels()
    private lateinit var accessGroupMissionRecyclerViewAdapter: AccessGroupMissionRecyclerViewAdapter
    private lateinit var postRecyclerViewAdapter: PostRecyclerViewAdapter
    private var activityPopupWindowBinding: ActivityPopupWindowBinding? = null

    private lateinit var mapView: MapView

    override fun onResume() {
        super.onResume()
        binding.vm = viewModel

        viewModel.isSelectGroupId.value =
            requireArguments().getString(GroupListRecyclerViewAdapter.GROUP_Id)
        viewModel.setPageNumber(1)
        viewModel.initBookmark(requireArguments().getBoolean(GroupListRecyclerViewAdapter.GROUP_BOOKMARK))
        viewModel.getGroupPost()
        viewModel.getMission()
    }

    override fun initStartView() {
        activityPopupWindowBinding = ActivityPopupWindowBinding.inflate(layoutInflater)

        postRecyclerViewAdapter = PostRecyclerViewAdapter()
        postRecyclerViewAdapter.apply {
            setPopupBottomClickListener { contentId, postMine, isSelected ->
                val bottomSheet = PopupBottomDialog(contentId, postMine, isSelected)
                bottomSheet.setOnBookmarkListener {
                    viewModel.getGroupPost()
                }
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }

            setPopupWindowClickListener { view, position, contentId ->
                getGradePopUp(view, position, contentId)
            }
        }

        binding.rvMyGroupPost.apply {
            adapter = postRecyclerViewAdapter
            itemAnimator = null
            isNestedScrollingEnabled = false
        }
    }

    override fun initDataBinding() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showLoadingDialog()
            else dismissLoadingDialog()
        }

        viewModel.isSelectedBookmark.observe(viewLifecycleOwner) {
            binding.ivBookmark.isSelected = it
        }

        viewModel.isExistMission.observe(viewLifecycleOwner) { isExistMission ->
            if (isExistMission) {
                binding.tvMissionIngCount.isVisible = true
                val pagerWidth =
                    resources.getDimensionPixelOffset(R.dimen.accessGroup_viewPager_width)
                val screenWidth = resources.displayMetrics.widthPixels
                val pagerPadding = ((screenWidth - pagerWidth) * 0.5).toInt()

                binding.layoutYesMission.vpYesMission.clipToPadding = false
                binding.layoutYesMission.vpYesMission.clipChildren = false
                binding.layoutYesMission.vpYesMission.setPadding(
                    14,
                    0,
                    pagerPadding,
                    0
                )
                binding.layoutYesMission.vpYesMission.setPageTransformer { page, _ ->
                    page.translationX =
                        resources.getDimensionPixelOffset(R.dimen.view_pager_offset_12).toFloat()
                }
                binding.layoutYesMission.vpYesMission.offscreenPageLimit = 1

                accessGroupMissionRecyclerViewAdapter = AccessGroupMissionRecyclerViewAdapter()
                accessGroupMissionRecyclerViewAdapter.apply {
                    setOnCertifyClickListener { missionId ->
                        getMyCurrentLocation()
                        viewModel.missionCertify(missionId)
                    }
                    setOnWriteClickListener {
                        val intent = Intent(requireContext(), UploadPostActivity::class.java)
                        intent.putExtra(MissionDetailActivity.GROUP_ID, it.groupId)
                        intent.putExtra(MissionDetailActivity.GROUP_NAME, it.groupName)
                        intent.putExtra(MissionDetailActivity.PLACE_NAME, it.missionLocationName)
                        intent.putExtra(MissionDetailActivity.LATITUDE, it.latitude)
                        intent.putExtra(MissionDetailActivity.LONGITUDE, it.longitude)
                        startActivity(intent)
                    }
                }

                binding.layoutYesMission.vpYesMission.adapter =
                    accessGroupMissionRecyclerViewAdapter
                binding.layoutYesMission.vpIndicator.attachTo(binding.layoutYesMission.vpYesMission)
            } else {
                binding.tvMissionIngCount.isVisible = false
            }
        }

        viewModel.postList.observe(viewLifecycleOwner) {
            if (it.content.isNotEmpty()) {
                binding.layoutNoPost.isVisible = false
                binding.layoutFinalPost.isVisible = true
            } else {
                binding.layoutNoPost.isVisible = true
                binding.layoutFinalPost.isVisible = false
            }

            postRecyclerViewAdapter.submitList(it.content)
        }

        viewModel.missionList.observe(viewLifecycleOwner) {
            accessGroupMissionRecyclerViewAdapter.submitList(it)
            binding.tvMissionIngCount.text = it.size.toString()
        }

        viewModel.pageNumber.observe(viewLifecycleOwner) {
            binding.scrollView.fullScroll(ScrollView.FOCUS_UP)
        }
    }

    override fun initAfterBinding() {
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_accessGroupFragment_to_groupFragment)
        }

        val groupImage = requireArguments().getString(GroupListRecyclerViewAdapter.GROUP_IMAGE)
        if (groupImage != "") {
            Glide.with(binding.ivGroupImage.context)
                .load(groupImage)
                .into(binding.ivGroupImage)
        }

        binding.tvGroupName.text =
            requireArguments().getString(GroupListRecyclerViewAdapter.GROUP_NAME)
        binding.tvGroupMemberNumber.text =
            requireArguments().getString(GroupListRecyclerViewAdapter.GROUP_NUMBER)

        binding.ivSearch.setOnClickListener {
            Intent(requireContext(), SearchPostActivity::class.java).apply {
                putExtra(HomeFragment.GROUP_ALL_LIST, viewModel.isSelectGroupId.value)
                startActivity(this)
            }
        }

        binding.ivBookmark.setOnClickListener {
            viewModel.setBookmark()
        }

        binding.layerGroupName.setOnClickListener {
            Intent(requireContext(), GroupInformationActivity::class.java).apply {
                putExtra(GroupListRecyclerViewAdapter.GROUP_Id, viewModel.isSelectGroupId.value)
                startActivity(this)
            }
        }

        binding.btnInviteSearch.setOnClickListener {
            Intent(requireContext(), UserSearchActivity::class.java).apply {
                putExtra(GroupListRecyclerViewAdapter.GROUP_Id, viewModel.isSelectGroupId.value)
                startActivity(this)
            }
        }

        binding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, _, _, _ ->
            if (viewModel.isLoading.value == false && viewModel.isNoData.value != true && !v.canScrollVertically(
                    1
                )
            ) {
                viewModel.setUpPageNumber()
                viewModel.getGroupPost()
            }
        })

        viewModel.isCertify.observe(viewLifecycleOwner) {
            if (it) {
                showToast(resources.getString(R.string.mission_detail_toast_message_success))
                viewModel.getMission()
            } else {
                showToast(resources.getString(R.string.mission_detail_toast_message_failure))
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMyCurrentLocation() {
        // 트랙킹 모드 ON
        mapView = MapView(requireActivity())
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
        viewModel.myCurrentLatitude.value = myCurrentLocation?.latitude ?: 0.0
        viewModel.myCurrentLongitude.value = myCurrentLocation?.longitude ?: 0.0

        // 맵의 중심좌표 구하기
        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOff // 트랙킹 모드 OFF
    }

    private fun getGradePopUp(view: View, position: Int, contentId: Int) {
        // 팝업 생성
        val popupWindow = PopupWindow(
            activityPopupWindowBinding!!.root,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            true
        )
        // 현재 유지시킬 뷰 설정(이 줄을 없애면 키보드가 올라옴)
        popupWindow.contentView = activityPopupWindowBinding!!.root

        // 어떤 레이아웃 밑에 팝업을 달건지 설정
        popupWindow.showAsDropDown(view, 50, -250)

        activityPopupWindowBinding!!.ivEmotionOne.setOnClickListener {
            setEmotion(
                contentId,
                position,
                RequestEmotionStatus(PopupWindowType.Type1.emotionPosition)
            )
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionTwo.setOnClickListener {
            setEmotion(
                contentId,
                position,
                RequestEmotionStatus(PopupWindowType.Type2.emotionPosition)
            )
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionThree.setOnClickListener {
            setEmotion(
                contentId,
                position,
                RequestEmotionStatus(PopupWindowType.Type3.emotionPosition)
            )
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionFour.setOnClickListener {
            setEmotion(
                contentId,
                position,
                RequestEmotionStatus(PopupWindowType.Type4.emotionPosition)
            )
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionFive.setOnClickListener {
            setEmotion(
                contentId,
                position,
                RequestEmotionStatus(PopupWindowType.Type5.emotionPosition)
            )
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionSix.setOnClickListener {
            setEmotion(
                contentId,
                position,
                RequestEmotionStatus(PopupWindowType.Type6.emotionPosition)
            )
            popupWindow.dismiss()
        }
    }

    private fun setEmotion(contentId: Int, position: Int, emotionStatus: RequestEmotionStatus) {
        viewModel.setUpdateEmotion(contentId, position, emotionStatus)
    }
}