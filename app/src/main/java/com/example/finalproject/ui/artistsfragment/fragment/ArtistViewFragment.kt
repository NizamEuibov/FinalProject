package com.example.finalproject.ui.artistsfragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentArtistViewBinding
import com.example.finalproject.ui.artistsfragment.adapters.ArtistViewAdapter
import com.example.finalproject.ui.artistsfragment.adapters.SimpleArtistAdapter
import com.example.finalproject.ui.artistsfragment.viewmodel.ArtistsViewModel


class ArtistViewFragment : Fragment() {
    private lateinit var binding:FragmentArtistViewBinding
    private var id:Int?=null
    private val viewModel: ArtistsViewModel by viewModels()
    private lateinit var adapter:ArtistViewAdapter
    private lateinit var simpleArtistAdapter: SimpleArtistAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id=arguments?.getInt("id")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding= FragmentArtistViewBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (id !=0){
            getArtistWithId()
        }
        else{
            simpleGetArtist()
        }
    }

    private fun getArtistWithId(){
        adapter= ArtistViewAdapter(requireContext())
        binding.rvArtists.adapter=adapter
        binding.rvArtists.layoutManager=LinearLayoutManager(context)
        viewModel.artistsLiveData.observe(viewLifecycleOwner){
            if (it != null) {
                val artistList = it.filter { it.id==id }
                adapter.addList(artistList)
            }

        }

    }

    private fun simpleGetArtist(){
        simpleArtistAdapter= SimpleArtistAdapter(requireContext())
        binding.rvArtists.adapter=simpleArtistAdapter
        binding.rvArtists.layoutManager=LinearLayoutManager(context)
        viewModel.artistsLiveData.observe(viewLifecycleOwner){
            if (it != null) {
                simpleArtistAdapter.addList(it)
            }
        }
    }

}