package com.example.finalproject.ui.albumsfragment.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.data.networkdata.models.UIState
import com.example.finalproject.databinding.FragmentAlbumViewBinding
import com.example.finalproject.ui.albumsfragment.adapter.AlbumViewAdapter
import com.example.finalproject.ui.albumsfragment.viewmodel.TrackViewModel
import com.example.finalproject.ui.`object`.ConstVal.ERROR
import com.example.finalproject.ui.track.fragment.TrackControlFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumViewFragment : Fragment() {
    private lateinit var binding: FragmentAlbumViewBinding
    private var adapter = AlbumViewAdapter()
    private val viewModel: TrackViewModel by activityViewModels()
    private var albumId: Int? = null
    private var albumName:String?=null
    private var albumImage:String?=null
    private var tracksList: List<DataTypeModel.Tracks> = emptyList()
    private var list: List<DataTypeModel.NameAndImage> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumId = arguments?.getInt("albumId")
        albumName = arguments?.getString("albumName")
        albumImage = arguments?.getString("albumImage")


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

//        albumUIState()
        init()
        binding.bBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.bAlbumViewPoint.setOnClickListener {
            val bundle = bundleOf(
                "id" to albumId
            )
            val albumControlFragment = AlbumControlFragment().apply {
                arguments = bundle
            }
            albumControlFragment.show(childFragmentManager, "")
        }


    }

//    private fun albumView() {
//        Glide.with(requireContext()).load(albumImage)
//            .into(binding.ivAlbumView)
//        Glide.with(requireContext()).load(image?.trim('[', ']'))
//            .into(binding.civAlbumView)
//        binding.tvArtistName.text = artistName?.trim('[', ']')
//        binding.tvAlbumView.text = albumName
//
//    }

    private fun init() {
        viewModel.trackList.observe(viewLifecycleOwner) { data ->
            when (data) {
                is UIState.Loading -> {
                    binding.progressBar.visibility =
                        if (data.isLoading) View.VISIBLE else View.GONE
                }

                is UIState.Data -> {
                    list = data.data
                    albumInformation()
                    adapterClick()
                }
                else -> {
                    UIState.Error(ERROR)
                }
            }
        }

    }



//    private fun albumUIState() {
//        albumViewModel.albumsList.observe(viewLifecycleOwner) { data ->
//            when (data) {
//                is UIState.Data -> {
//                    list = data.data
//                    if (id != null) {
//                        albumView()
//                    } else {
//                        albumInformation()
//                    }
//                }
//
//                else -> {
//                    UIState.Error(ConstVal.ERROR)
//                }
//            }
//
//        }
//
//
//    }

    private fun albumInformation() {
        val artistName =
            list.filter { it.tracks.map { it.albumId }.contains(albumId) }
                .map { it.name }.toString().trim('[', ']')
        Log.d("user666", "$artistName ")
        val artistImage =
            list.filter { it.tracks.map { it.albumId }.contains(albumId) }.map { it.image }
                .toString()
                .trim('[', ']')

        binding.tvArtistName.text = artistName
        binding.tvAlbumView.text = albumName
        Glide.with(requireContext()).load(albumImage).into(binding.ivAlbumView)
        Glide.with(requireContext()).load(artistImage).into(binding.civAlbumView)
        addList()

//        viewModel.trackList.observe(viewLifecycleOwner)
//        { it ->
//
//            if (it != null) {
//                val trackList =
//                    it.filter { it.tracks.map { it.albumId }.contains(albumId) }.map { it.tracks }
//                        .flatten()
//                adapter.addTrack(trackList)
//                adapter.setOnClickListener(object : AlbumViewAdapter.Listener {
//                    override fun onClickListener(data: DataTypeModel.Tracks) {
//
//                        val bundle = bundleOf(
//                            "id" to data.id,
//                            "albumId" to id
//                        )
//
//                        val trackControlFragment = TrackControlFragment().apply {
//                            arguments = bundle
//                        }
//                        trackControlFragment.show(childFragmentManager, "")
//                    }
//
//                    override fun onClickAudioListener(data: DataTypeModel.Tracks) {
//
//                    }
//                })
//            }
//        }
    }

    private fun addList() {
        binding.rvAlbumView.adapter = adapter
        binding.rvAlbumView.layoutManager = LinearLayoutManager(context)
        tracksList = list.map { it.tracks.filter { it.albumId == albumId } }.flatten()
        adapter.addTrack(tracksList)
    }


    private fun adapterClick() {
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
                Log.d("id121", "${data.albumName}")
                findNavController().navigate(
                    R.id.action_albumViewFragment_to_playTrackFragment,
                    bundle
                )
            }


        })
    }
}

