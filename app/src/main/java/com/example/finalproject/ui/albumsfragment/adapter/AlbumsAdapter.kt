package com.example.finalproject.ui.albumsfragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.databinding.AlbumsListBinding
import javax.inject.Inject


class AlbumsAdapter @Inject constructor(val context: Context) :
    RecyclerView.Adapter<AlbumsAdapter.PodcastViewHolder>() {
    private val dataList = mutableListOf<DataTypeModel.AlbumList>()
    private var listener:Listener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AlbumsListBinding.inflate(inflater, parent, false)
        return PodcastViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: PodcastViewHolder, position: Int) {
        holder.onBind(dataList[position],listener)

    }

    inner class PodcastViewHolder(private val binding: AlbumsListBinding) :
        ViewHolder(binding.root) {
        fun onBind(data: DataTypeModel.AlbumList, listener: Listener?) {
            Glide.with(context).load(data.image)
                .into(binding.ivAlbum)
            binding.tvAlbums.text = data.name
            itemView.setOnClickListener{
                listener?.onClickListener(data)
            }

        }


    }

    fun addImage(newList: List<DataTypeModel.AlbumList>) {
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }


    interface Listener{
        fun onClickListener(data:DataTypeModel.AlbumList)
    }
      fun setOnClickLIstener(listener: Listener){
          this.listener=listener
      }
}

