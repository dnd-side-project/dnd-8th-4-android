package com.dnd_8th_4_android.wery.presentation.ui.write.place.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseSearchPlace.Document
import com.dnd_8th_4_android.wery.databinding.ItemSearchBinding

class SearchAdapter() :
    ListAdapter<Document, SearchAdapter.SearchViewAdapter>(
        searchPlaceDiffUtil
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewAdapter {
        val binding =
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewAdapter(binding)
    }

    override fun onBindViewHolder(holder: SearchViewAdapter, position: Int) {
        holder.onBind(getItem(position))
    }

    class SearchViewAdapter(
        val binding: ItemSearchBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: Document) {
            binding.data = data
        }
    }

    companion object {
        private val searchPlaceDiffUtil = object : DiffUtil.ItemCallback<Document>() {
            override fun areItemsTheSame(
                oldItem: Document,
                newItem: Document
            ): Boolean =
                oldItem.x == newItem.x

            override fun areContentsTheSame(
                oldItem: Document,
                newItem: Document
            ): Boolean =
                oldItem == newItem
        }
    }
}