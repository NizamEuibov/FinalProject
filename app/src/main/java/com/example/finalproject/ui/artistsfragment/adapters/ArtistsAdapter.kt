package com.example.finalproject.ui.artistsfragment.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.databinding.ArtistsListBinding

class ArtistsAdapter() :
    RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder>() {
    private var selectedList = mutableListOf<DataTypeModel.NameAndImage>()
    private  var selectedListener: SelectedListener?=null
    private val dataList = mutableListOf<DataTypeModel.NameAndImage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ArtistsListBinding.inflate(inflater, parent, false)
        return ArtistsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ArtistsViewHolder, position: Int) {
        holder.onBind(dataList[position], selectedList.contains(dataList[position]))


    }


    inner class ArtistsViewHolder(private val binding: ArtistsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.civArtist.setOnClickListener {
                val item = dataList[position]
                if (selectedList.contains(item)) {
                    selectedList.remove(item)
                    Log.d("user542", "$selectedList")
                } else {
                    selectedList.add(item)

                }
                notifyItemChanged(position)
                selectedListener?.onSelected(selectedList.toList())
            }

        }


        fun onBind(data: DataTypeModel.NameAndImage, isSelected: Boolean) {
            with(binding) {
                Glide.with(itemView.context).load(data.image).into(civArtist)
                tvArtistList.text = data.name
                if (isSelected) {
                    itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.bbColor))
                } else {
                    itemView.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            android.R.color.transparent
                        )
                    )
                }
            }


        }
    }

    fun addNotes(newList: List<DataTypeModel.NameAndImage>) {
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()

    }

    fun setSelectedListener(listener: SelectedListener) {
        this.selectedListener = listener
    }


    interface SelectedListener {
        fun onSelected(selectedItems: List<DataTypeModel.NameAndImage>)
    }
}