package com.example.finalproject.ui.serachfragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.data.networkdata.models.UIState
import com.example.finalproject.databinding.FragmentSearchAllBinding
import com.example.finalproject.ui.`object`.ConstVal.ERROR
import com.example.finalproject.ui.serachfragments.adapter.SearchAllAdapter
import com.example.finalproject.ui.serachfragments.viewmodel.SearchAllViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAllFragment : Fragment() {
    private lateinit var binding: FragmentSearchAllBinding
    private lateinit var adapter: SearchAllAdapter
    private var listArtists: List<DataTypeModel> = emptyList()
    private val viewModel: SearchAllViewModel by viewModels()
    private var id: Int? = null
    private var artistsList: List<DataTypeModel.NameAndImage> = emptyList()

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

        binding.svCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


    }

    private fun init() {
        viewModel.artistsList.observe(viewLifecycleOwner) { data ->
            when (data) {
                is UIState.Loading -> {
                    binding.progressBar.visibility =
                        if (data.isLoading) View.VISIBLE else View.GONE
                }

                is UIState.Data -> {
                    artistsList = data.data!!
                    searchList()
                    adapterClick()
                }

                else -> {
                    UIState.Error(ERROR)
                }
            }

        }


    }

    private fun searchList() {
        adapter = SearchAllAdapter(requireContext())
        binding.rvSearchAll.adapter = adapter
        binding.rvSearchAll.layoutManager = LinearLayoutManager(context)
        val listAlbums = artistsList.map { it.albums }.flatten()
        listArtists = artistsList + listAlbums
        adapter.addList(listArtists)
    }


    private fun searchList(query: String) {
        val searchList = listArtists.filter { item ->
            when (item) {
                is DataTypeModel.NameAndImage -> item.name.lowercase().contains(query)
                is DataTypeModel.AlbumList -> item.name?.lowercase()?.contains(query) == true
                else -> {
                    false
                }
            }
        }
        if (searchList.isEmpty()) {
            binding.vSearchBackground.visibility = View.VISIBLE
            Toast.makeText(context, "No found data", Toast.LENGTH_SHORT).show()
        } else {
            binding.vSearchBackground.visibility = View.INVISIBLE
            adapter.addList(searchList)
        }


    }


    private fun adapterClick() {
        adapter.setOnClicikListener(object : SearchAllAdapter.Listener {
            override fun onClickListener(data: DataTypeModel) {
                when (data) {
                    is DataTypeModel.NameAndImage -> {
                        val bundle = bundleOf(
                            "id" to data.id
                        )
                        findNavController().navigate(
                            R.id.action_searchAllFragment_to_albumsFragment,
                            bundle
                        )
                    }

                    is DataTypeModel.AlbumList -> {
                        val bundle = bundleOf(
                            "albumId" to data.id,
                            "albumName" to data.name,
                            "albumImage" to data.image
                        )
                        findNavController().navigate(
                            R.id.action_searchAllFragment_to_albumViewFragment,
                            bundle
                        )
                    }

                    else -> {
                        error("Invalid")
                    }
                }
            }

        })
    }
}