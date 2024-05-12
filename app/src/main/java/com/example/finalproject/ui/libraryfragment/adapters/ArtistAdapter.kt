package com.example.finalproject.ui.libraryfragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.databinding.SearchAllListBinding

class ArtistAdapter () :
    RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    private var dataList = mutableListOf<DataTypeModel.NameAndImage>()
    private  var listener: Listener?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding=SearchAllListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistAdapter.ArtistViewHolder, position: Int) {
        holder.onBind(dataList[position],listener)
    }


    override fun getItemCount(): Int {
        return dataList.size
    }


    inner class ArtistViewHolder(private var binding: SearchAllListBinding) :
        ViewHolder(binding.root) {
        fun onBind(data: DataTypeModel.NameAndImage, listener: Listener?) {
            with(binding) {
                Glide.with(itemView.context).load(data.image).into(civSearchAll)
                binding.tvSearchAll.text = data.name
                itemView.setOnClickListener {
                    listener?.clickListener(data)
                }
            }
        }

    }
    fun addList(newList: List<DataTypeModel.NameAndImage>) {
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun clickListener(data: DataTypeModel.NameAndImage)
    }
}