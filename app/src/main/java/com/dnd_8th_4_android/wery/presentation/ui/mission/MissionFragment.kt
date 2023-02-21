package com.dnd_8th_4_android.wery.presentation.ui.mission

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentMissionBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment

class MissionFragment : BaseFragment<FragmentMissionBinding>(R.layout.fragment_mission) {

    override fun initStartView() {
    }

    override fun initDataBinding() {

    }

    @SuppressLint("MissingPermission")
    override fun initAfterBinding() {
        binding.btnLocation.setOnClickListener {
            val locatioNManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager?
            val locatioNProvider = LocationManager.GPS_PROVIDER
            val currentLatLng = locatioNManager?.getLastKnownLocation(locatioNProvider)
            Toast.makeText(requireContext(), currentLatLng.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}