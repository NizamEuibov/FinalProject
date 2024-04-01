package com.example.finalproject.ui.libraryfragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.databinding.SongsListBinding
import javax.inject.Inject

class SongAdapter @Inject constructor(val context: Context) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
    private lateinit var binding: SongsListBinding
    private var dataList = mutableListOf<DataTypeModel.Tracks>()
    private var listener: Listener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SongAdapter.SongViewHolder {
        binding = SongsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongAdapter.SongViewHolder, position: Int) {
        holder.onBind(dataList[position], listener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class SongViewHolder(private var binding: SongsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: DataTypeModel.Tracks, listener: Listener?) {
            with(binding) {
                Glide.with(context).load(data.image).into(civSong)
                tvSong.text = data.name
                itemView.setOnClickListener {
                    listener?.clickListener(data)
                }
            }
        }

    }

    fun addList(newList: List<DataTypeModel.Tracks>) {
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun clickListener(data: DataTypeModel.Tracks)
    }
}
