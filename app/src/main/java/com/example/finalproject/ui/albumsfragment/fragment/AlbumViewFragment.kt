package com.example.finalproject.ui.albumsfragment.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.databinding.FragmentAlbumViewBinding
import com.example.finalproject.ui.activities.oject.SharedViewModel
import com.example.finalproject.ui.albumsfragment.adapter.AlbumViewAdapter
import com.example.finalproject.ui.albumsfragment.viewmodel.AlbumViewModel
import com.example.finalproject.ui.albumsfragment.viewmodel.TrackViewModel
import com.example.finalproject.ui.track.fragment.TrackControlFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumViewFragment : Fragment() {
    private lateinit var binding: FragmentAlbumViewBinding
    private var artistName: String? = null
    private var image: String? = null
    private var albumName: String? = null
    private var albumImage: String? = null
    private var id: Int? = null
    private var adapter = AlbumViewAdapter()
    private val viewModel: TrackViewModel by activityViewModels()
    private var albumId: Int? = null
    private val albumViewModel: AlbumViewModel by viewModels()
    private var tracksList: List<DataTypeModel.Tracks> = emptyList()
    private val sharedViewModel:SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumId = arguments?.getInt("albumId")
        id = arguments?.getInt("id")
        artistName = arguments?.getString("name")
        image = arguments?.getString("image")
        albumName = arguments?.getString("albumName")
        albumImage = arguments?.getString("albumImage")
        Log.d("user666", "$artistName $albumName $image $id ")

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
        binding.rvAlbumView.adapter = adapter
        binding.rvAlbumView.layoutManager = LinearLayoutManager(context)
        if (id != 0) {
            albumView()
        } else {
            albumInformation()
        }
        init()
        binding.bBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.bAlbumViewPoint.setOnClickListener {
            val bundle = bundleOf(
                "id" to id
            )

            val albumControlFragment = AlbumControlFragment().apply {
                arguments = bundle
            }

            albumControlFragment.show(childFragmentManager, "")
        }


    }

    private fun albumView() {
        Glide.with(requireContext()).load(albumImage)
            .into(binding.ivAlbumView)
        Glide.with(requireContext()).load(image?.trim('[', ']'))
            .into(binding.civAlbumView)
        binding.tvArtistName.text = artistName?.trim('[', ']')
        binding.tvAlbumView.text = albumName

    }

    private fun init() {


        viewModel.trackList.observe(viewLifecycleOwner) { it ->
            Log.d("IT12", "$it")
            if (it != null)
                tracksList = it.map { it.tracks }.flatten()

            if (tracksList.map { it.albumName }.contains(albumName))
                adapter.addTrack(tracksList.filter {
                    albumName?.let { it1 ->
                        it.albumName?.contains(
                            it1
                        )
                    } == true
                })
            Log.d("userList", "$it")
        }

        adapter.setOnClickListener(object : AlbumViewAdapter.Listener {
            override fun onClickListener(data: DataTypeModel.Tracks) {
                Log.d("Data", "${data.audio}")


                val bundle = bundleOf(
                    "id" to data.id,
                    "albumId" to id
                )

                val trackControlFragment = TrackControlFragment().apply {
                    arguments = bundle
                }

                trackControlFragment.show(childFragmentManager, "")

            }

            override fun onClickAudioListener(data: DataTypeModel.Tracks) {
               val bundle = bundleOf(
                   "id" to data.id,
                   "albumName" to data.albumName
               )
                findNavController().navigate(R.id.action_albumViewFragment_to_playTrackFragment, bundle)
            }


        })
    }


    private fun albumInformation() {
        Log.d("id121", "$albumId")

        albumViewModel.albumsList.observe(viewLifecycleOwner) {

            val albumName = it.map { it.albums.filter { it.id == albumId } }
                .map { it.map { it.name } }.flatten()
            val artistName =
                it.filter { it.albums.map { it.id }.contains(albumId) }.map { it.name }
            val albumImage = it.map { it.albums.filter { it.id == albumId } }
                .map { it.map { it.image } }.flatten().toString().trim('[', ']')
            val artistImage =
                it.filter { it.albums.map { it.id }.contains(albumId) }.map { it.image }.toString()
                    .trim('[', ']')
            binding.tvArtistName.text = artistName.toString().trim('[', ']')
            binding.tvAlbumView.text = albumName.toString().trim('[', ']')
            Glide.with(requireContext()).load(albumImage).into(binding.ivAlbumView)
            Glide.with(requireContext()).load(artistImage).into(binding.civAlbumView)
        }

        viewModel.trackList.observe(viewLifecycleOwner) { it ->

            if (it != null) {
                val trackList =
                    it.filter { it.tracks.map { it.albumId }.contains(albumId) }.map { it.tracks }
                        .flatten()
                adapter.addTrack(trackList)
                adapter.setOnClickListener(object : AlbumViewAdapter.Listener {
                    override fun onClickListener(data: DataTypeModel.Tracks) {

                        val bundle = bundleOf(
                            "id" to data.id,
                            "albumId" to id
                        )

                        val trackControlFragment = TrackControlFragment().apply {
                            arguments = bundle
                        }
                        trackControlFragment.show(childFragmentManager, "")
                    }

                    override fun onClickAudioListener(data: DataTypeModel.Tracks) {

                    }
                })
            }
        }
    }



}