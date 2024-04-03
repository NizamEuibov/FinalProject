package com.example.finalproject.ui.artistsfragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.databinding.AlbumViewListBinding
import javax.inject.Inject

class ArtistAlbumViewAdapter @Inject constructor(private val context: Context) :
    RecyclerView.Adapter<ArtistAlbumViewAdapter.ArtistAlbumViewHolder>() {
    private lateinit var binding: AlbumViewListBinding
    private var dataList = mutableListOf<DataTypeModel.AlbumList>()
    private var listener:Listener?=null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistAlbumViewAdapter.ArtistAlbumViewHolder {
        binding = AlbumViewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistAlbumViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ArtistAlbumViewAdapter.ArtistAlbumViewHolder,
        position: Int
    ) {
        holder.onBind(dataList[position],listener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ArtistAlbumViewHolder(private var binding: AlbumViewListBinding) :
        ViewHolder(binding.root) {
        fun onBind(data: DataTypeModel.AlbumList,listener: Listener?) {
            with(binding) {
                Glide.with(context).load(data.image).into(civSearchAll)
                tvSearchAll.text = data.name

                itemView.setOnClickListener {
                    listener?.clickListener(data)
                }
            }
        }
    }

    fun addList(newList:List<DataTypeModel.AlbumList>){
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: Listener){
        this.listener=listener
    }

    interface Listener{
        fun clickListener(data:DataTypeModel.AlbumList)
    }
}