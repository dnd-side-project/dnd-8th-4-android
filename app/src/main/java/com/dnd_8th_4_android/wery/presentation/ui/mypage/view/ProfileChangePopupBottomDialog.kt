package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.InviteBottomDialogPopupBinding
import com.dnd_8th_4_android.wery.databinding.ProfileChangeBottomDialogPopupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseBottomDialogFragment

class ProfileChangePopupBottomDialog :
    BaseBottomDialogFragment<ProfileChangeBottomDialogPopupBinding>(R.layout.profile_change_bottom_dialog_popup) {

    override fun initAfterBinding() {
        binding.layoutAlbum.setOnClickListener {
            // 앨범 갤러리 찾기
        }

        binding.layoutRemove.setOnClickListener {
            // 프로필 삭제
        }
    }
}