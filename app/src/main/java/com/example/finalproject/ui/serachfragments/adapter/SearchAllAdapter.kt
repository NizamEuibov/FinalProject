package com.example.finalproject.ui.serachfragments.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.databinding.SearchAlbumListBinding
import com.example.finalproject.databinding.SearchAllListBinding
import javax.inject.Inject

class SearchAllAdapter @Inject constructor(val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {
    private lateinit var binding: SearchAllListBinding
    private val dataList = mutableListOf<DataTypeModel>()
    private var listener: Listener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return when (viewType) {
            ARTISTS_TYPE -> {
                binding = SearchAllListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                SearchAllViewHolder(binding)
            }

            else -> {
                val binding =
                    SearchAlbumListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                SearchAllAlbumViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is SearchAllViewHolder -> holder.onBind(
                dataList[position] as DataTypeModel.NameAndImage,
                listener
            )

            is SearchAllAlbumViewHolder -> holder.onBind(
                dataList[position] as DataTypeModel.AlbumList,
                listener
            )
        }


    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position]) {
            is DataTypeModel.NameAndImage -> ARTISTS_TYPE
            else -> ALBUM_TYPE
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class SearchAllViewHolder(private var binding: SearchAllListBinding) :
        ViewHolder(binding.root) {
        fun onBind(data: DataTypeModel.NameAndImage, listener: Listener?) {
            Glide.with(context).load(data.image)
                .into(binding.civSearchAll)
            binding.tvSearchAll.text = data.name
            itemView.setOnClickListener {
                listener?.onClickListener(data)
            }
        }

    }

    inner class SearchAllAlbumViewHolder(private val binding: SearchAlbumListBinding) :
        ViewHolder(binding.root) {
        fun onBind(data: DataTypeModel.AlbumList, listener: Listener?) {
            Glide.with(context).load(data.image)
                .into(binding.civSearchAll)
            binding.tvSearchAll.text = data.name
            itemView.setOnClickListener {
                listener?.onClickListener(data)

            }
        }

    }

    fun addList(newList: List<DataTypeModel>) {
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()
    }


    companion object {
        private const val ARTISTS_TYPE = 0
        private const val ALBUM_TYPE = 1
    }

    interface Listener {
        fun onClickListener(data: DataTypeModel)
    }

    fun setOnClicikListener(listener: Listener) {
        this.listener = listener
    }

}



