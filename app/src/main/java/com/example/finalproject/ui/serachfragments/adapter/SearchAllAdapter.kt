package com.example.finalproject.ui.serachfragments.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.finalproject.data.networkdata.models.NameAndImage
import com.example.finalproject.databinding.SearchAllListBinding

class SearchAllAdapter(private val context: Context):RecyclerView.Adapter<SearchAllAdapter.SearchAllViewHolder>() {
   private lateinit var binding:SearchAllListBinding
   private val dataList= mutableListOf<NameAndImage>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAllAdapter.SearchAllViewHolder {
       binding= SearchAllListBinding.inflate(LayoutInflater.from(parent.context)
           ,parent, false)
        return SearchAllViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAllAdapter.SearchAllViewHolder, position: Int) {
        holder.onBind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

  inner  class SearchAllViewHolder(private var binding:SearchAllListBinding):ViewHolder(binding.root) {
        fun onBind(data:NameAndImage){
            Glide.with(context).load(data.image)
                .into(binding.civSearchAll)
            binding.tvSearchAll.text=data.name
        }

    }

    fun addList(newList: List<NameAndImage>){
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

}

