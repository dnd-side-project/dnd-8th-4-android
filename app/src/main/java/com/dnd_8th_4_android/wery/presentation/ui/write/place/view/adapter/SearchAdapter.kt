package com.dnd_8th_4_android.wery.presentation.ui.write.place.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseSearchPlace
import com.dnd_8th_4_android.wery.databinding.ItemWritingPhotoBinding

class SearchAdapter() :
    ListAdapter<ResponseSearchPlace, SearchAdapter.SearchViewAdapter>(
        searchPlaceDiffUtil
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewAdapter {
        val binding =
            ItemWritingPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewAdapter(binding)
    }

    override fun onBindViewHolder(holder: SearchViewAdapter, position: Int) {
        holder.onBind(getItem(position))
    }

    class SearchViewAdapter(
        val binding: ItemWritingPhotoBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentPhoto: String? = null

        init {
            binding.ivDelete.setOnClickListener {
                currentPhoto?.let {
                    //onItemDelete(it)
                }
            }
        }

        fun onBind(data: ResponseSearchPlace) {
            // currentPhoto = imgUrl
        }
    }

    companion object {
        private val searchPlaceDiffUtil = object : DiffUtil.ItemCallback<ResponseSearchPlace>() {
            override fun areItemsTheSame(
                oldItem: ResponseSearchPlace,
                newItem: ResponseSearchPlace
            ): Boolean =
                oldItem.documents === newItem.documents

            override fun areContentsTheSame(
                oldItem: ResponseSearchPlace,
                newItem: ResponseSearchPlace
            ): Boolean =
                oldItem == newItem
        }
    }
}