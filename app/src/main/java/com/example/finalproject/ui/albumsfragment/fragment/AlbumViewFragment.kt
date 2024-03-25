package com.example.finalproject.ui.albumsfragment.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.FragmentAlbumViewBinding

class AlbumViewFragment : Fragment() {
    private lateinit var binding: FragmentAlbumViewBinding
    private var artistName: String? = null
    private var image: String? = null
    private var albumName: String? = null
    private var albumImage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artistName = arguments?.getString("name")
        image = arguments?.getString("image")
        albumName = arguments?.getString("albumName")
        albumImage = arguments?.getString("albumImage")
        Log.d("user666", "$artistName $albumName $image")

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumViewBinding.inflate(layoutInflater, container, false)
        return (binding.root)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumView()
    }


    private fun albumView() {
        Glide.with(requireContext()).load(albumImage)
            .into(binding.ivAlbumView)
        Glide.with(requireContext()).load(image)
            .into(binding.civAlbumView)
        binding.tvArtistName.text = artistName
        binding.tvAlbumView.text = albumName

    }


}