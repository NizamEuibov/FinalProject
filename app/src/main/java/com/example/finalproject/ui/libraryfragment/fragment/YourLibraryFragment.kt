package com.example.finalproject.ui.libraryfragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentYourLibraryBinding
import com.example.finalproject.ui.artistsfragment.viewmodel.ArtistsViewModel
import com.example.finalproject.ui.libraryfragment.adapters.AlbumAdapter
import com.example.finalproject.ui.libraryfragment.adapters.ArtistAdapter
import com.example.finalproject.ui.libraryfragment.adapters.SongAdapter
import com.example.finalproject.ui.track.viewmodel.TrackViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YourLibraryFragment : Fragment() {
    private lateinit var binding: FragmentYourLibraryBinding
    private val viewModel: ArtistsViewModel by viewModels()
    private val trackViewModel: TrackViewModel by viewModels()
    private lateinit var adapter: ArtistAdapter
    private lateinit var albumAdapter:AlbumAdapter
    private lateinit var songAdapter: SongAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentYourLibraryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bLibraryArtist.setOnClickListener {
            libraryArtist()
        }
        binding.bLibraryAlbum.setOnClickListener {
            libraryAlbum()
        }
        binding.bLibraryPlaylists.setOnClickListener {
            librarySong()
        }
    }

    private fun libraryArtist() {
        adapter = ArtistAdapter(requireContext())
        binding.rvLibrary.adapter = adapter
        binding.rvLibrary.layoutManager = LinearLayoutManager(context)
        viewModel.artistsLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.addList(it)
            }
        }
    }

    private fun libraryAlbum() {
        albumAdapter = AlbumAdapter(requireContext())
        binding.rvLibrary.adapter = albumAdapter
        binding.rvLibrary.layoutManager = LinearLayoutManager(context)
        viewModel.artistsLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                albumAdapter.addList(it.map { it.albums }.flatten())
            }
        }

    }

    private fun librarySong() {
        songAdapter = SongAdapter(requireContext())
        binding.rvLibrary.adapter = songAdapter
        binding.rvLibrary.layoutManager = LinearLayoutManager(context)
        trackViewModel.trackList.observe(viewLifecycleOwner) {
            if (it != null) {
                val songList = it.map { it.tracks }.flatten()
                songAdapter.addList(songList)
            }
        }
    }

}