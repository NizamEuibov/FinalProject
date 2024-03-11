package com.example.finalproject.ui.podcastfragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager

import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentPodcatsBinding
import com.example.finalproject.ui.podcastfragment.adapter.PodcastAdapter
import com.example.finalproject.ui.artistsfragment.model.ModelArtistsFragment

class PodcatsFragment : Fragment() {
    private lateinit var binding: FragmentPodcatsBinding
   private lateinit var  adapter: PodcastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPodcatsBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        data()

    }

    private fun init() {
        adapter= PodcastAdapter(requireContext())
        binding.rvPodcast.adapter = adapter
        binding.rvPodcast.layoutManager = GridLayoutManager(context, 3)
    }

    private fun data() {
        val imageList = mutableListOf(
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm")
        )

        adapter.addImage(imageList)

    }
}