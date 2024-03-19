package com.example.finalproject.ui.homefragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.finalproject.data.networkdata.models.NameAndImage
import com.example.finalproject.databinding.HomeListBinding

class ParentAdapter(private val context: Context): RecyclerView.Adapter<ParentAdapter.ParentViewHolder>(){
    private lateinit var binding:HomeListBinding
    private val dataList= mutableListOf<NameAndImage>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParentAdapter.ParentViewHolder {
       binding=HomeListBinding.inflate(LayoutInflater.from(parent.context)
       ,parent,false)

        return ParentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentAdapter.ParentViewHolder, position: Int) {
        holder.onBind(dataList[position])
    }

    override fun getItemCount(): Int {
       return dataList.size
    }

  inner  class ParentViewHolder(private var binding: HomeListBinding):ViewHolder(binding.root) {

        fun onBind(data:NameAndImage){
            with(binding){
                Glide.with(context).load(data.image)
                    .into(ivHomeImage)
                tvHomeName.text=data.name
           val childAdapter=ChildAdapter(context)
                rvHomeChild.adapter=childAdapter
                rvHomeChild.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                childAdapter.addList(data.albums)

            }
        }


    }


    fun addList(newList:List<NameAndImage>){
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

}