package com.example.finalproject.ui.albumfragments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentAlbumBinding
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
//class AlbumFragment : Fragment() {
//    private lateinit var binding:FragmentAlbumBinding
//    private lateinit var adapter:List<Model>
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding=FragmentAlbumBinding.inflate(layoutInflater,container,false)
//        return (binding.root)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }
//
//    fun init(){
//        binding.rvAlbums.adapter=adapter
//        binding.rvAlbums.layoutManager=GridLayoutManager(requireContext(),3)
//    }
//
//}