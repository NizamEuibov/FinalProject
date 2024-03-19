package com.example.finalproject.ui.podcastfragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.finalproject.data.networkdata.models.NameAndImage
import com.example.finalproject.databinding.PodcastsListBinding


class PodcastAdapter(val context: Context) :
    RecyclerView.Adapter<PodcastAdapter.PodcastViewHolder>() {
    private val dataList = mutableListOf<NameAndImage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = PodcastsListBinding.inflate(inflater, parent, false)
        return PodcastViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: PodcastViewHolder, position: Int) {

    }

    class PodcastViewHolder(private val binding: PodcastsListBinding) : ViewHolder(binding.root) {
        fun onBind(data:NameAndImage) {
//            binding.ivPodcasts.setImageResource(data.image)
            binding.tvPodcasts.text = data.name
        }


    }

//    fun addImage(newList: List<ModelArtistsFragment>) {
//        dataList.clear()
//        dataList.addAll(newList)
//        notifyDataSetChanged()
//    }
}

