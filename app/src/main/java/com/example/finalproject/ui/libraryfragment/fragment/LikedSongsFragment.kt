package com.example.finalproject.ui.libraryfragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentLikedSongsBinding
import com.example.finalproject.ui.libraryfragment.adapter.LikedSongsAdapter
import com.example.finalproject.ui.libraryfragment.viewmodel.LikedTracksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikedSongsFragment : Fragment() {
    private lateinit var binding:FragmentLikedSongsBinding
    private val viewModel:LikedTracksViewModel by viewModels()
    private lateinit var adapter:LikedSongsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentLikedSongsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ibBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        likedSongs()
    }


    private fun likedSongs(){
        adapter= LikedSongsAdapter(requireContext())
        binding.rvLikedSongs.adapter=adapter
        binding.rvLikedSongs.layoutManager=LinearLayoutManager(context)
        viewModel.likedTracks.observe(viewLifecycleOwner){list->
               list?.let { adapter.addList(it)}
        }
    }

}



