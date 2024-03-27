package com.example.finalproject.ui.albumsfragment.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.databinding.FragmentAlbumViewBinding
import com.example.finalproject.ui.albumsfragment.adapter.AlbumViewAdapter
import com.example.finalproject.ui.albumsfragment.viewmodel.TrackViewModel
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
    private val viewModel: TrackViewModel by viewModels()
    private var tracksList: List<DataTypeModel.Tracks> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        albumView()

        init()
        binding.bBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.bAlbumViewPoint.setOnClickListener {
            val bundle = bundleOf(
                "id" to id
            )
            findNavController().navigate(
                R.id.action_albumViewFragment_to_albumControlFragment,
                bundle
            )
        }


    }


    private fun albumView() {
        Glide.with(requireContext()).load(albumImage)
            .into(binding.ivAlbumView)
        Glide.with(requireContext()).load(image)
            .into(binding.civAlbumView)
        binding.tvArtistName.text = artistName?.trim('[',']')
        binding.tvAlbumView.text = albumName

    }

    private fun init() {


        binding.rvAlbumView.adapter = adapter
        binding.rvAlbumView.layoutManager = LinearLayoutManager(context)
        viewModel.trackList.observe(viewLifecycleOwner) { it ->
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

                val bundle = bundleOf(
                    "id" to data.id
                )

                findNavController().navigate(
                    R.id.action_albumViewFragment_to_trackControlFragment,
                    bundle
                )

            }

        })
    }


}