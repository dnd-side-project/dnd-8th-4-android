package com.dnd_8th_4_android.wery.presentation.ui.search.adapter

import android.content.Intent
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.search.ResponsePostSearchData
import com.dnd_8th_4_android.wery.databinding.ItemPostSearchBinding
import com.dnd_8th_4_android.wery.presentation.ui.detail.view.PostDetailActivity
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter
import java.time.LocalDate

class PostSearchAdapter :
    ListAdapter<ResponsePostSearchData.Data.Content, PostSearchAdapter.ViewHolder>(diffUtil) {
    private lateinit var binding: ItemPostSearchBinding
    var wordText = ""

    inner class ViewHolder(private val binding: ItemPostSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponsePostSearchData.Data.Content) {
            binding.ivPostImage.clipToOutline = true
            binding.ivFriendImage.clipToOutline = true

            val builder = SpannableStringBuilder(item.content)
            builder.setSpan(
                StyleSpan(Typeface.BOLD),
                item.content.indexOf(wordText),
                item.content.indexOf(wordText) + wordText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.tvPostContent.text = builder

            if (item.contentImageList.isNotEmpty()) {
                binding.layoutPostImage.isVisible = true
                binding.tvImageCount.isVisible = true

                Glide.with(binding.ivPostImage.context).load(item.contentImageList[0].imageUrl)
                    .into(binding.ivPostImage)

                binding.tvImageCount.text = item.contentImageListSize.toString()
            } else {
                binding.layoutPostImage.isVisible = false
                binding.tvImageCount.isVisible = false
            }

            Glide.with(binding.ivFriendImage.context).load(item.groupImage)
                .into(binding.ivFriendImage)

            binding.tvName.text = item.groupName

            if (LocalDate.now().toString() == item.createAt.substring(IntRange(0, 10))) {
                binding.tvTime.text = item.createAt.substring(IntRange(11, 15)).replace("-", ".")
            } else {
                binding.tvTime.text = item.createAt.substring(IntRange(2, 9)).replace("-", ".")
            }

            binding.layoutPostSearch.setOnClickListener {
                goToPostDetail(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPostSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun goToPostDetail(item: ResponsePostSearchData.Data.Content) {
        Intent(binding.root.context, PostDetailActivity::class.java).apply {
            putExtra(PostRecyclerViewAdapter.WRITE_CHECK, false)
            putExtra(PostRecyclerViewAdapter.CONTENT_ID, item.contentId)
            putExtra(PostRecyclerViewAdapter.CONTENT, item.content)
            putExtra(PostRecyclerViewAdapter.USER_IMAGE, item.groupImage)
            putExtra(PostRecyclerViewAdapter.GROUP_NAME, item.groupName)
            putExtra(PostRecyclerViewAdapter.TIME, item.createAt)
            binding.root.context.startActivity(this)
        }
    }

    companion object {
        private val diffUtil =
            object : DiffUtil.ItemCallback<ResponsePostSearchData.Data.Content>() {
                override fun areItemsTheSame(
                    oldItem: ResponsePostSearchData.Data.Content,
                    newItem: ResponsePostSearchData.Data.Content,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: ResponsePostSearchData.Data.Content,
                    newItem: ResponsePostSearchData.Data.Content,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}