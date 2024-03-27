package com.example.finalproject.ui.track.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.FragmentTrackControlBinding
import com.example.finalproject.ui.track.viewmodel.TrackViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackControlFragment : Fragment() {
    private lateinit var binding: FragmentTrackControlBinding
    private val viewModel: TrackViewModel by viewModels()
    private var id: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrackControlBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trackImage()
        binding.tbClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }

    private fun trackImage() {
        viewModel.trackList.observe(viewLifecycleOwner) { it ->
            if (it != null) {
                val trackImage=it.map { it.tracks.filter { it.id ==id}}.map {it.map { it.image }}.flatten()
                val trackName = it.map { it.tracks.filter { it.id ==id}}.map {it.map { it.name }}.flatten()
                val artistName = it.filter { it.tracks.map { it.id }.contains(id) }.map { it.name }.toString()

                with(binding){
                    Glide.with(requireContext()).load(trackImage).into(ivTrackImage)
                    tvTrackName.text= trackName.toString().trim('[',']')
                    tvArtistsName.text=artistName.trim('[',']')

                }

                Log.d("user5552","$trackName $artistName $trackImage")
            }

        }
    }


}