package com.example.finalproject.ui.libraryfragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.finalproject.data.localdatabase.TrackEntity
import com.example.finalproject.databinding.SongsListBinding
import javax.inject.Inject

class LikedSongsAdapter @Inject constructor() :
    RecyclerView.Adapter<LikedSongsAdapter.LikedSongsViewHolder>() {
    private lateinit var binding: SongsListBinding
    private val dataList = mutableListOf<TrackEntity>()
    private var listener: Listener? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LikedSongsViewHolder {
        binding = SongsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LikedSongsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LikedSongsViewHolder, position: Int) {
        holder.onBind(dataList[position], listener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class LikedSongsViewHolder(private val binding: SongsListBinding) :
        ViewHolder(binding.root) {
        fun onBind(data: TrackEntity, listener: Listener?) {
            with(binding) {
                Glide.with(itemView.context).load(data.trackImage).into(civSong)
                tvSong.text = data.trackName
                itemView.setOnClickListener {
                    listener?.clickListener(data)
                }
            }
        }
    }

    fun addList(newList: List<TrackEntity>) {
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }


    fun setOnClickListener(listener: Listener) {
        this.listener = listener
    }


    interface Listener {
        fun clickListener(data: TrackEntity)
    }

}