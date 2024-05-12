package com.example.finalproject.ui.artistsfragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.databinding.ArtistViewListBinding
import javax.inject.Inject

class ArtistViewAdapter @Inject constructor() :
    RecyclerView.Adapter<ArtistViewAdapter.ForArtistViewHolder>() {
  private lateinit var binding:ArtistViewListBinding
  private var dataList= mutableListOf<DataTypeModel.NameAndImage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForArtistViewHolder {
      binding=ArtistViewListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ForArtistViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return dataList.size
    }

    override fun onBindViewHolder(holder: ForArtistViewHolder, position: Int) {
       holder.onBind(dataList[position])
    }

    inner class ForArtistViewHolder (private var binding:ArtistViewListBinding):ViewHolder(binding.root) {
        fun onBind(data:DataTypeModel.NameAndImage){
            with(binding){
                Glide.with(itemView.context).load(data.image).into(civArtistImage)
                tvArtistName.text=data.name
                val artistAlbumViewAdapter=ArtistAlbumViewAdapter()
                rvAlbumView.adapter=artistAlbumViewAdapter
                rvAlbumView.layoutManager=LinearLayoutManager(itemView.context)
                artistAlbumViewAdapter.addList(data.albums)
            }
        }

    }

    fun addList(newList:List<DataTypeModel.NameAndImage>){
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

}