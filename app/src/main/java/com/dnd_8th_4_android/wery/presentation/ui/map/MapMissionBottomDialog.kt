package com.dnd_8th_4_android.wery.presentation.ui.map

import android.view.WindowManager
import android.widget.Toast
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.DialogMapMissionBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseBottomDialogFragment

class MapMissionBottomDialog :
    BaseBottomDialogFragment<DialogMapMissionBinding>(R.layout.dialog_map_mission) {

    override fun onStart() {
        super.onStart()
        dialog?.window?.attributes?.windowAnimations = androidx.appcompat.R.anim.abc_fade_in
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    override fun initAfterBinding() {
        binding.root.setOnClickListener {
            // TODO 추후 해당 미션화면으로 이동하게 할 예정
            Toast.makeText(requireContext(), "미션화면 이동!", Toast.LENGTH_SHORT).show()
        }
    }
}