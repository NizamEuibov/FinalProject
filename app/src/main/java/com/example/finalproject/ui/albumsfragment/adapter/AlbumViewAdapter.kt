package com.example.finalproject.ui.albumsfragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.databinding.MusicViewListBinding

class AlbumViewAdapter  :
    RecyclerView.Adapter<AlbumViewAdapter.AlbumsViewHolder>() {
    private lateinit var binding: MusicViewListBinding
    private val dataList = mutableListOf<DataTypeModel.Tracks>()
    private var listener:Listener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        binding = MusicViewListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AlbumsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        holder.onBind(dataList[position], listener)
    }


    class AlbumsViewHolder(private val binding: MusicViewListBinding) : ViewHolder(binding.root) {

        fun onBind(data: DataTypeModel.Tracks, listener: Listener?) {
            binding.tvMusicList.text = data.name
            binding.tvMusicArtist.text = data.albumName
            binding.ibMusicListEnd.setOnClickListener {
                listener?.onClickListener(data)
            }
        }

    }


    fun addTrack(newList:List<DataTypeModel.Tracks>){
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: Listener){
        this.listener=listener
    }

    interface Listener{
        fun onClickListener(data:DataTypeModel.Tracks)
    }
}