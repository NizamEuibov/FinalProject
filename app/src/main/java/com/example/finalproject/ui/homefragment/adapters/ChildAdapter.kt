package com.example.finalproject.ui.homefragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.finalproject.data.networkdata.models.DataTypeModel

import com.example.finalproject.databinding.ChildHomeListBinding

class ChildAdapter():RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {
    private lateinit var binding:ChildHomeListBinding
    private val dataList= mutableListOf<DataTypeModel.AlbumList>()
    private var selectedListener:ParentAdapter.SelectedListener?=null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChildAdapter.ChildViewHolder {
        binding= ChildHomeListBinding.
        inflate(LayoutInflater.from(parent.context)
            ,parent,false)
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildAdapter.ChildViewHolder, position: Int) {
       holder.onBind(dataList[position], selectedListener )
    }

    override fun getItemCount(): Int {
       return dataList.size
    }

    inner class ChildViewHolder(private val binding:ChildHomeListBinding):ViewHolder(binding.root) {

        fun onBind(data: DataTypeModel.AlbumList, selectedListener: ParentAdapter.SelectedListener?){
            with(binding){
                Glide.with(itemView.context).load(data.image)
                    .into(ivHomeImage)
                tvHomeName.text=data.name
                itemView.setOnClickListener{
                  selectedListener?.onItemListener(data)
                }
            }

        }

    }

    fun addList(newList:List<DataTypeModel.AlbumList>){
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

}