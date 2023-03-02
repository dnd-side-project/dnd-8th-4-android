package com.dnd_8th_4_android.wery.presentation.util

import android.content.Intent
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.local.AuthLocalDataSource
import com.dnd_8th_4_android.wery.databinding.BottomDialogPopupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseBottomDialogFragment
import com.dnd_8th_4_android.wery.presentation.ui.post.upload.view.UploadPostActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopupBottomDialog(
    private val contentId: Int,
    private val postMine: Int,
    private val selected: Boolean,
) :
    BaseBottomDialogFragment<BottomDialogPopupBinding>(R.layout.bottom_dialog_popup) {
    private val viewModel: PopupBottomViewModel by viewModels()
    private lateinit var onBookmarkListener: OnBookMarkListener

    override fun initAfterBinding() {
        viewModel.setOnBookmark(selected)

        if (postMine != AuthLocalDataSource(requireContext()).userId) {
            binding.viewLine2.isVisible = false
            binding.layoutModify.isVisible = false
            binding.layoutDelete.isVisible = false
        } else {
            binding.viewLine2.isVisible = true
            binding.layoutModify.isVisible = true
            binding.layoutDelete.isVisible = true
        }

        viewModel.isSelectedBookmark.observe(viewLifecycleOwner) {
            if (it) {
                binding.tvBookmark.text = resources.getString(R.string.bottom_sheet_bookmark_cancel)
                showToast(resources.getString(R.string.bottom_sheet_on_bookmark_toast))
            } else {
                binding.tvBookmark.text = resources.getString(R.string.bottom_sheet_bookmark)
                showToast(resources.getString(R.string.bottom_sheet_off_bookmark_toast))
            }
            binding.ivBookmark.isSelected = it
        }

        binding.layoutBookmark.setOnClickListener {
            viewModel.setBookmark(contentId)
        }

        binding.layoutClip.setOnClickListener {
            showToast(resources.getString(R.string.bottom_sheet_clip_toast))
        }

        binding.layoutDelete.setOnClickListener {
            val postRemoveDialog = PostRemoveDialog()
            postRemoveDialog.setOnPostDeleteListener {
                viewModel.setPostDelete(contentId)
                showToast(resources.getString(R.string.bottom_sheet_remove_toast))
                dialog!!.dismiss()
            }
            postRemoveDialog.show(childFragmentManager, null)
        }

        binding.layoutModify.setOnClickListener {
            val intent = Intent(requireContext(), UploadPostActivity::class.java)
            intent.putExtra("contentId", contentId)
            startActivity(intent)
            dismiss()
        }
    }

    override fun onDestroyView() {
        onBookmarkListener.onClicked()
        super.onDestroyView()
    }

    fun setOnBookmarkListener(listener: () -> Unit) {
        onBookmarkListener = object : OnBookMarkListener {
            override fun onClicked() {
                listener()
            }
        }
    }

    interface OnBookMarkListener {
        fun onClicked()
    }
}