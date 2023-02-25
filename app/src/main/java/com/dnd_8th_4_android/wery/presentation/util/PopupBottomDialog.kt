package com.dnd_8th_4_android.wery.presentation.util

import android.util.Log
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.BottomDialogPopupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseBottomDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopupBottomDialog(private val homeSelect: Boolean, private val contentId: Int, private val selected: Boolean) :
    BaseBottomDialogFragment<BottomDialogPopupBinding>(R.layout.bottom_dialog_popup) {
    private val viewModel: PopupBottomViewModel by viewModels()
    private lateinit var onBookmarkListener: OnBookMarkListener

    override fun initAfterBinding() {
        viewModel.setOnBookmark(selected)
        viewModel.setIsHomeSelect(homeSelect)

        viewModel.isSelectedBookmark.observe(viewLifecycleOwner) {
            if (it) {
                binding.tvBookmark.text = resources.getString(R.string.bottom_sheet_bookmark_cancel)
            } else {
                binding.tvBookmark.text = resources.getString(R.string.bottom_sheet_bookmark)
            }
            binding.ivBookmark.isSelected = it
        }

        binding.layerBookmark.setOnClickListener {
            viewModel.setBookmark(contentId)
        }

        binding.layerRemove.setOnClickListener {
            val postRemoveDialog = PostRemoveDialog()
            postRemoveDialog.setOnPostDeleteListener {
                viewModel.isHomeSelect.value = true
                viewModel.setPostDelete(contentId)
                dialog!!.dismiss()
            }
            postRemoveDialog.show(childFragmentManager, null)
        }
    }

    override fun onDestroyView() {
        if (viewModel.isHomeSelect.value == true) onBookmarkListener.onClicked()
        super.onDestroyView()
    }

    fun setOnBookmarkListener(listener: () -> Unit) {
        onBookmarkListener = object : OnBookMarkListener{
            override fun onClicked() {
                listener()
            }
        }
    }

    interface OnBookMarkListener {
        fun onClicked()
    }
}