package com.dnd_8th_4_android.wery.presentation.ui.group.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.ScrollView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.databinding.ActivityPopupWindowBinding
import com.dnd_8th_4_android.wery.databinding.FragmentAccessGroupBinding
import com.dnd_8th_4_android.wery.domain.model.DialogInfo
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.AccessGroupMissionRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupListRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel.AccessGroupViewModel
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.home.view.HomeFragment
import com.dnd_8th_4_android.wery.presentation.ui.mission.create.CreateMissionActivity
import com.dnd_8th_4_android.wery.presentation.ui.mission.view.MissionDetailActivity
import com.dnd_8th_4_android.wery.presentation.ui.post.upload.view.UploadPostActivity
import com.dnd_8th_4_android.wery.presentation.ui.search.view.SearchPostActivity
import com.dnd_8th_4_android.wery.presentation.util.DialogFragmentUtil
import com.dnd_8th_4_android.wery.presentation.util.PostPopupBottomDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccessGroupFragment :
    BaseFragment<FragmentAccessGroupBinding>(R.layout.fragment_access_group) {
    private val viewModel: AccessGroupViewModel by viewModels()
    private lateinit var accessGroupMissionRecyclerViewAdapter: AccessGroupMissionRecyclerViewAdapter
    private lateinit var postRecyclerViewAdapter: PostRecyclerViewAdapter
    private var activityPopupWindowBinding: ActivityPopupWindowBinding? = null

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

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window.apply {
            //상태바
            statusBarColor = Color.WHITE
            //상태바 아이콘(true: 검정 / false: 흰색)
            WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = true
        }
        activityPopupWindowBinding = null
    }

    override fun initStartView() {
        requireActivity().window.apply {
            //상태바
            statusBarColor = Color.BLACK
            //상태바 아이콘(true: 검정 / false: 흰색)
            WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = false
        }

        activityPopupWindowBinding = ActivityPopupWindowBinding.inflate(layoutInflater)

        postRecyclerViewAdapter = PostRecyclerViewAdapter()
        postRecyclerViewAdapter.apply {
            setPopupBottomClickListener { contentId, postMine, isSelected ->
                val bottomSheet = PostPopupBottomDialog(contentId, postMine, isSelected)
                bottomSheet.setOnBookmarkListener {
                    viewModel.getGroupPost()
                }
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }

            setPopupWindowClickListener { view, contentId ->
                getGradePopUp(view, contentId)
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
                        locationPermissionRequest.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                        viewModel.missionId.value = missionId
                    }
                    setOnWriteClickListener {
                        val intent = Intent(requireContext(), UploadPostActivity::class.java)
                        intent.putExtra(MissionDetailActivity.GROUP_ID, it.groupId)
                        intent.putExtra(MissionDetailActivity.GROUP_NAME, it.groupName)
                        intent.putExtra(MissionDetailActivity.PLACE_NAME, it.missionLocationName)
                        intent.putExtra(MissionDetailActivity.LATITUDE, it.latitude)
                        intent.putExtra(MissionDetailActivity.LONGITUDE, it.longitude)
                        intent.putExtra(MissionDetailActivity.MISSION_ID, it.missionId)
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
            binding.layoutNoPost.isVisible = it.content.isEmpty()
            binding.layoutFinal.isVisible = it.content.isNotEmpty()
            binding.tvFinalPageNumber.text = it.totalPages.toString()

            postRecyclerViewAdapter.submitList(it.content)
        }

        viewModel.missionList.observe(viewLifecycleOwner) {
            accessGroupMissionRecyclerViewAdapter.submitList(it)
            binding.tvMissionIngCount.text = it.size.toString()
        }

        viewModel.pageNumber.observe(viewLifecycleOwner) {
            binding.tvPageNumber.text = it.toString()
            binding.scrollView.fullScroll(ScrollView.FOCUS_UP)
        }
    }

    override fun initAfterBinding() {
        binding.appBarContainer.addOnOffsetChangedListener { _, verticalOffset ->
            binding.tvToolbarGroupName.isVisible =
                binding.appBarContainer.totalScrollRange == kotlin.math.abs(
                    verticalOffset
                )
        }

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_accessGroupFragment_to_groupFragment)
        }

        val groupImage = requireArguments().getString(GroupListRecyclerViewAdapter.GROUP_IMAGE)
        if (groupImage != "") {
            Glide.with(binding.ivGroupImage.context)
                .load(groupImage)
                .into(binding.ivGroupImage)
        }

        binding.layoutNoMission.accessGroupCreateMissionBtn.setOnClickListener {
            startActivity(Intent(requireContext(), CreateMissionActivity::class.java))
        }

        binding.tvToolbarGroupName.text =
            requireArguments().getString(GroupListRecyclerViewAdapter.GROUP_NAME)
        binding.tvGroupName.text =
            requireArguments().getString(GroupListRecyclerViewAdapter.GROUP_NAME)
        binding.tvGroupMemberNumber.text =
            requireArguments().getInt(GroupListRecyclerViewAdapter.GROUP_NUMBER).toString()

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
            val bottomSheet = InvitePopupBottomDialog()
            bottomSheet.setOnInviteListener {
                Intent(requireContext(), UserSearchActivity::class.java).apply {
                    putExtra(GroupListRecyclerViewAdapter.GROUP_Id, viewModel.isSelectGroupId.value)
                    startActivity(this)
                }
            }
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }

        binding.btnPrevious.setOnClickListener {
            if (viewModel.pageNumber.value != 1) {
                viewModel.setDownPageNumber()
                viewModel.getGroupPost()
            }
        }

        binding.btnAfter.setOnClickListener {
            if (viewModel.isNoData.value != true) {
                viewModel.setUpPageNumber()
                viewModel.getGroupPost()
            }
        }

        viewModel.isCertify.observe(viewLifecycleOwner) {
            if (it) {
                showToast(resources.getString(R.string.mission_detail_toast_message_success))
                viewModel.getMission()
            } else {
                showToast(resources.getString(R.string.mission_detail_toast_message_failure))
            }
        }

        viewModel.isMissionDday.observe(viewLifecycleOwner) {
            if (it) {
                showToast(resources.getString(R.string.mission_detail_toast_not_mission_dday))
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMyCurrentLocation() {
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

        val missionId = viewModel.missionId.value!!
        viewModel.missionCertify(missionId)
    }

    private fun getGradePopUp(view: View, contentId: Int) {
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
                RequestEmotionStatus(PopupWindowType.Type1.emotionPosition)
            )
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionTwo.setOnClickListener {
            setEmotion(
                contentId,
                RequestEmotionStatus(PopupWindowType.Type2.emotionPosition)
            )
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionThree.setOnClickListener {
            setEmotion(
                contentId,
                RequestEmotionStatus(PopupWindowType.Type3.emotionPosition)
            )
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionFour.setOnClickListener {
            setEmotion(
                contentId,
                RequestEmotionStatus(PopupWindowType.Type4.emotionPosition)
            )
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionFive.setOnClickListener {
            setEmotion(
                contentId,
                RequestEmotionStatus(PopupWindowType.Type5.emotionPosition)
            )
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionSix.setOnClickListener {
            setEmotion(
                contentId,
                RequestEmotionStatus(PopupWindowType.Type6.emotionPosition)
            )
            popupWindow.dismiss()
        }
    }

    private fun setEmotion(contentId: Int, emotionStatus: RequestEmotionStatus) {
        viewModel.setUpdateEmotion(contentId, emotionStatus)
    }
}