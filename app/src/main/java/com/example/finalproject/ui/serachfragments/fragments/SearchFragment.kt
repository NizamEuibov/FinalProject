package com.example.finalproject.ui.serachfragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentSearchBinding
import com.example.finalproject.ui.serachfragments.adapter.SearchAdapter
import com.example.finalproject.ui.serachfragments.model.CardListModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter:SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.svSearch.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_searchAllFragment)
        }
        init()


    }

    private fun init(){
        adapter=SearchAdapter()
        binding.rvSearch.adapter=adapter
        binding.rvSearch.layoutManager=GridLayoutManager(context,2)
        adapter.addLists(cardList)

    }



    private val cardList= listOf(
        CardListModel("Music"),
        CardListModel("Albums"),
        CardListModel("Pop"),
        CardListModel("Comedy"),
        CardListModel("Podcasts"),
        CardListModel("Charts"),
        CardListModel("Made for you"),
        CardListModel("News"),
        CardListModel("Mood"),
        CardListModel("Educational"),
        CardListModel("Merch"),
        CardListModel("Trending")
    )

    }