package com.example.finalproject.ui.serachfragments.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.finalproject.databinding.SearchCardListBinding
import com.example.finalproject.ui.serachfragments.model.CardListModel

class SearchAdapter:RecyclerView.Adapter<SearchAdapter.CardListViewHolder>() {
    private val dataList= mutableListOf<CardListModel>()
    private var listener:Listener?=null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.CardListViewHolder {
        val binding=SearchCardListBinding.inflate(LayoutInflater.from(parent.context)
            ,parent,false)
        return CardListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.CardListViewHolder, position: Int) {
        holder.onBind(dataList[position], listener)
        holder.itemView.setBackgroundColor(setRandomBackground())
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    inner class CardListViewHolder(private val binding:SearchCardListBinding):ViewHolder(binding.root) {

        fun onBind(data: CardListModel, listener: Listener?){
            binding.tvCardList.text=data.text
            listener?.onClickListener(data)
        }
    }

    fun setOnClickListener(listener: Listener){
        this.listener=listener
    }

    interface Listener{
        fun onClickListener(data:CardListModel)
    }

    fun addLists(newList:List<CardListModel>){
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }
    private fun setRandomBackground():Int{
        val random=java.util.Random()
        return Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256))
    }

}