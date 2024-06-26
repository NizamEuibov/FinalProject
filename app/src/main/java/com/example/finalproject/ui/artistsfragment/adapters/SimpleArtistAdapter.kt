package com.example.finalproject.ui.artistsfragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.databinding.SearchAllListBinding
import javax.inject.Inject

class SimpleArtistAdapter @Inject constructor() :
    RecyclerView.Adapter<SimpleArtistAdapter.SimpleViewHolder>() {
    private lateinit var binding: SearchAllListBinding
    private var dataList = mutableListOf<DataTypeModel.NameAndImage>()
    private var listener: Listener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimpleArtistAdapter.SimpleViewHolder {
        binding = SearchAllListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimpleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimpleArtistAdapter.SimpleViewHolder, position: Int) {
        holder.onBind(dataList[position], listener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class SimpleViewHolder(private var binding: SearchAllListBinding) :
        ViewHolder(binding.root) {
        fun onBind(data: DataTypeModel.NameAndImage, listener: Listener?) {
            with(binding) {
                Glide.with(itemView.context).load(data.image).into(civSearchAll)
                tvSearchAll.text = data.name
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