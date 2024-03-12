package com.example.finalproject.ui.podcastfragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentPodcatsBinding
import com.example.finalproject.ui.artistsfragment.model.ModelArtistsFragment
import com.example.finalproject.ui.podcastfragment.adapter.PodcastAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PodcastsFragment : Fragment() {
    private lateinit var binding: FragmentPodcatsBinding
    private lateinit var adapter: PodcastAdapter

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


        binding.svPodcast.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filteredPodcast(newText)
                }
                return true
            }

        })
    }

    private fun init() {
        adapter = PodcastAdapter(requireContext())
        binding.rvPodcast.adapter = adapter
        adapter.addImage(imageList)
        binding.rvPodcast.layoutManager = GridLayoutManager(context, 3)
    }


    private val imageList = mutableListOf(
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

    private fun filteredPodcast(query: String) {
        val filteredPodcast = mutableListOf<ModelArtistsFragment>()
        for (i in imageList) {
            if (i.name.lowercase().contains(query)) {
                filteredPodcast.add(i)
            }
        }
        if (query.isEmpty()) {
            Toast.makeText(requireContext(), "No data Found", Toast.LENGTH_SHORT).show()
        } else {
            adapter.addImage(filteredPodcast)
        }
    }

}