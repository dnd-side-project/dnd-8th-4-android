//package com.dnd_8th_4_android.wery.presentation.ui.group.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
//import com.dnd_8th_4_android.wery.databinding.ItemMyGroupBinding
//
//class GroupBookmarkRecyclerViewAdapter :
//    ListAdapter<ResponseGroupData.Data, GroupBookmarkRecyclerViewAdapter.ViewHolder>(diffUtil) {
//    private lateinit var binding: ItemMyGroupBinding
//
//    class ViewHolder(private val binding: ItemMyGroupBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: ResponseGroupData.Data) {
//            binding.ivMyGroup.clipToOutline = true
//            Glide.with(binding.ivMyGroup.context).load(item.image)
//                .into(binding.ivMyGroup)
//
//            binding.tvGroupName.text = item.name
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        binding = ItemMyGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(currentList[position])
//    }
//
//    companion object {
//        private val diffUtil = object : DiffUtil.ItemCallback<ResponseGroupData.Data>() {
//            override fun areItemsTheSame(
//                oldItem: ResponseGroupData.Data,
//                newItem: ResponseGroupData.Data,
//            ): Boolean {
//                return oldItem.id == newItem.id
//            }
//
//            override fun areContentsTheSame(
//                oldItem: ResponseGroupData.Data,
//                newItem: ResponseGroupData.Data,
//            ): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
//}