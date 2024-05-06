package com.example.finalproject.ui.homefragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.databinding.HomeListBinding

class ParentAdapter: RecyclerView.Adapter<ParentAdapter.ParentViewHolder>(){
    private lateinit var binding:HomeListBinding
    private val dataList= mutableListOf<DataTypeModel.NameAndImage>()


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

        fun onBind(data: DataTypeModel.NameAndImage){
            with(binding){
                Glide.with(itemView.context).load(data.image)
                    .into(ivHomeImage)
                tvHomeName.text=data.name
           val childAdapter=ChildAdapter(itemView.context)
                rvHomeChild.adapter=childAdapter
                rvHomeChild.layoutManager=LinearLayoutManager(itemView.context,LinearLayoutManager.HORIZONTAL,false)
                childAdapter.addList(data.albums)

            }
        }


    }


    fun addList(newList:List<DataTypeModel.NameAndImage>){
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

}
// single activity, click for child adapter