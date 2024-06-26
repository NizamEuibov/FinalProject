package com.example.finalproject.ui.libraryfragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.data.networkdata.models.UIState
import com.example.finalproject.databinding.FragmentYourLibraryBinding
import com.example.finalproject.ui.artistsfragment.viewmodel.ArtistsViewModel
import com.example.finalproject.ui.libraryfragment.adapters.AlbumAdapter
import com.example.finalproject.ui.libraryfragment.adapters.ArtistAdapter
import com.example.finalproject.ui.libraryfragment.adapters.SongAdapter
import com.example.finalproject.ui.`object`.ConstVal.ERROR
import com.example.finalproject.ui.track.viewmodel.TrackViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YourLibraryFragment : Fragment() {
    private lateinit var binding: FragmentYourLibraryBinding
    private val viewModel: ArtistsViewModel by viewModels()
    private val trackViewModel: TrackViewModel by viewModels()
    private lateinit var adapter: ArtistAdapter
    private var albumAdapter = AlbumAdapter()
    private lateinit var songAdapter: SongAdapter
    private var name: String? = null
    private var albumList: List<DataTypeModel.AlbumList> = emptyList()
    private var artistsList: List<DataTypeModel.NameAndImage> = emptyList()
    private var songList: List<DataTypeModel.NameAndImage> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = arguments?.getString("name")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentYourLibraryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModels()
    }

    private fun setupUI() {
        binding.bLibraryArtist.setOnClickListener { libraryArtist() }
        binding.bLibraryAlbum.setOnClickListener { libraryAlbum() }
        binding.bLibraryPlaylists.setOnClickListener { librarySong() }

        binding.civUserImage.setOnClickListener {
            val bundle = bundleOf("name" to name)
            findNavController().navigate(R.id.action_yourLibraryFragment_to_library, bundle)
        }

        binding.ivLiked.setOnClickListener {
            findNavController().navigate(R.id.action_yourLibraryFragment_to_likedSongsFragment)
        }
    }

    private fun observeViewModels() {
        viewModel.artistsLiveData.observe(viewLifecycleOwner) { data ->
            when (data) {
                is UIState.Loading -> binding.progressBar.visibility = if (data.isLoading) View.VISIBLE else View.GONE
                is UIState.Data -> {
                    artistsList = data.data?: emptyList()
                    albumList = artistsList.flatMap { it.albums }
                    libraryArtist()
                }
                else ->{ UIState.Error(ERROR)}
            }
        }
        viewModel.fetchArtists()

        trackViewModel.trackList.observe(viewLifecycleOwner) { data ->
            when (data) {
                is UIState.Loading -> binding.progressBar.visibility = if (data.isLoading) View.VISIBLE else View.GONE
                is UIState.Data -> {
                    songList =data.data?: emptyList()
                }
                else ->{ UIState.Error(ERROR)}
            }
        }
        trackViewModel.fetchTracks()
    }

    private fun libraryArtist() {
        adapter = ArtistAdapter()
        binding.rvLibrary.adapter = adapter
        binding.rvLibrary.layoutManager = LinearLayoutManager(requireContext())
        adapter.addList(artistsList)
        adapter.setOnClickListener(object : ArtistAdapter.Listener {
            override fun clickListener(data: DataTypeModel.NameAndImage) {
                val bundle = bundleOf("id" to data.id)
                findNavController().navigate(R.id.action_yourLibraryFragment_to_albumsFragment, bundle)
            }
        })
    }

    private fun libraryAlbum() {
        binding.rvLibrary.adapter = albumAdapter
        binding.rvLibrary.layoutManager = LinearLayoutManager(requireContext())
        albumAdapter.addList(albumList)
        albumAdapter.setOnClickListener(object : AlbumAdapter.Listener {
            override fun clickListener(data: DataTypeModel.AlbumList) {
                val bundle = bundleOf("albumId" to data.id)
                findNavController().navigate(R.id.action_yourLibraryFragment_to_albumViewFragment, bundle)
            }
        })
    }

    private fun librarySong() {
        songAdapter = SongAdapter()
        binding.rvLibrary.adapter = songAdapter
        binding.rvLibrary.layoutManager = LinearLayoutManager(requireContext())

        songAdapter.addList(songList.flatMap { it.tracks })
        songAdapter.setOnClickListener(object : SongAdapter.Listener {
            override fun clickListener(data: DataTypeModel.Tracks) {
                val bundle = bundleOf("id" to data.id, "albumName" to data.albumName)
                findNavController().navigate(R.id.action_yourLibraryFragment_to_playTrackFragment, bundle)
            }
        })
    }
}
