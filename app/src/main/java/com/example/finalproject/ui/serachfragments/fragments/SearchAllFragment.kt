package com.example.finalproject.ui.serachfragments.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.data.networkdata.models.AlbumList
import com.example.finalproject.data.networkdata.models.NameAndImage
import com.example.finalproject.databinding.FragmentSearchAllBinding
import com.example.finalproject.ui.serachfragments.adapter.SearchAllAdapter
import com.example.finalproject.ui.serachfragments.viewmodel.SearchAllViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class SearchAllFragment : Fragment() {
    private lateinit var binding: FragmentSearchAllBinding
    private lateinit var adapter: SearchAllAdapter
    private var listArtists: List<NameAndImage> = emptyList()
    private var listAlbums: List<List<AlbumList>> = emptyList()
    private val viewModel: SearchAllViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchAllBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            init()

            binding.svSearchAll.setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (!newText.isNullOrBlank()) {
                        searchList(newText)
                    } else {
                        adapter.addList(listArtists)
                    }
                    return true
                }

            })

    }

    private fun init() {

            adapter = SearchAllAdapter(requireContext())
            binding.rvSearchAll.adapter = adapter
            binding.rvSearchAll.layoutManager = LinearLayoutManager(context)
            viewModel.artistsList.observe(viewLifecycleOwner) {
                listArtists = it
                Log.d("user555","$it")
                adapter.addList(it)
            }
        }


    private fun searchList(query: String) {
        val searchList = listArtists.filter { it.name.lowercase().contains(query) }
        if (searchList.isEmpty()) {
            Toast.makeText(context, "No found data", Toast.LENGTH_SHORT).show()
        } else {
            adapter.addList(searchList)
        }


    }


}