package com.dnd_8th_4_android.wery.presentation.ui.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyBookmarkData
import com.dnd_8th_4_android.wery.databinding.ItemMypageBookmarkBinding

class MyPageBookmarkRecyclerViewAdapter(private val list: MutableList<ResponseMyBookmarkData.Data.Content>) :
    RecyclerView.Adapter<MyPageBookmarkRecyclerViewAdapter.ViewHolder>() {
    private lateinit var binding: ItemMypageBookmarkBinding

    class ViewHolder(private val binding: ItemMypageBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseMyBookmarkData.Data.Content) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemMypageBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}