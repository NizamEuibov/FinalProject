package com.example.finalproject.ui.libraryfragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.databinding.SearchAlbumListBinding
import javax.inject.Inject

class AlbumAdapter @Inject constructor() :
    RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {
    private lateinit var binding: SearchAlbumListBinding
    private var dataList = mutableListOf<DataTypeModel.AlbumList>()
    private  var listener: Listener?=null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumAdapter.AlbumViewHolder {
        binding = SearchAlbumListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumAdapter.AlbumViewHolder, position: Int) {
        holder.onBind(dataList[position], listener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class AlbumViewHolder(private var binding: SearchAlbumListBinding) :
        ViewHolder(binding.root) {
        fun onBind(data: DataTypeModel.AlbumList, listener: Listener?) {
            with(binding) {
                Glide.with(itemView.context).load(data.image).into(civSearchAll)
                tvSearchAll.text = data.name
                itemView.setOnClickListener {
                    listener?.clickListener(data)
                }
            }
        }

    }

    fun addList(newList: List<DataTypeModel.AlbumList>) {
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun clickListener(data: DataTypeModel.AlbumList)
    }
}