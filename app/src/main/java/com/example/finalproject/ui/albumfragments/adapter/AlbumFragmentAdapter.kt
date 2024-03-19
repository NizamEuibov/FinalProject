package com.example.finalproject.ui.albumfragments.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.AlbumsListBinding
//
//class AlbumFragmentAdapter(val context: Context) :
//    RecyclerView.Adapter<AlbumFragmentAdapter.AlbumsViewHolder>() {
//    private lateinit var binding: AlbumsListBinding
//
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): AlbumFragmentAdapter.AlbumsViewHolder {
//        binding = AlbumsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return AlbumsViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: AlbumFragmentAdapter.AlbumsViewHolder, position: Int) {
//        holder.onBind(dataList[position])
//    }
//
//    override fun getItemCount(): Int {
//        return dataList.size
//    }
//
//    inner class AlbumsViewHolder(private val binding: AlbumsListBinding) :
//        ViewHolder(binding.root) {
//
//        fun onBind(data: AlbumList) {
//            with(binding) {
//                Glide.with(context).load(data.image)
//                    .into(ivAlbum)
//                tvAlbums.text = data.name
//            }
//        }
//    }
//
//
//    fun addList(newList:List<AlbumList>){
//        dataList.clear()
//        dataList.addAll(newList)
//        notifyDataSetChanged()
//    }
//}