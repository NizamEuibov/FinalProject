package com.example.finalproject.ui.artistsfragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.finalproject.databinding.ArtistsListBinding
import com.example.finalproject.ui.artistsfragment.model.ModelArtistsFragment

class ArtistsAdapter(val context: Context, private val onActionListener: OnActionListener):RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder>() {
    private val dataList= mutableListOf<ModelArtistsFragment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistsViewHolder {
       val inflater=LayoutInflater.from(context)
        val binding=ArtistsListBinding.inflate(inflater,parent,false)
         return ArtistsViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return dataList.size
    }

    override fun onBindViewHolder(holder: ArtistsViewHolder, position: Int) {
        holder.onBind(dataList[position],onActionListener)
    }

    class ArtistsViewHolder(private var binding: ArtistsListBinding) : ViewHolder(binding.root) {
        fun onBind(data: ModelArtistsFragment, onActionListener: OnActionListener) {
            with(binding) {
                civArtist.setImageResource(data.image)
                tvArtistList.text = data.name

                root.setOnClickListener {
                    onActionListener.onClick(data)
                }
            }
        }
    }

    fun addNotes(newList: List<ModelArtistsFragment>){
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnActionListener{
        fun onClick( click: ModelArtistsFragment)
    }
}