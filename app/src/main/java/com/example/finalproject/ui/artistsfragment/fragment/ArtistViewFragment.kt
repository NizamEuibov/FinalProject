package com.example.finalproject.ui.artistsfragment.fragment

import android.os.Bundle
import android.util.Log
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
import com.example.finalproject.databinding.FragmentArtistViewBinding
import com.example.finalproject.ui.artistsfragment.adapters.ArtistViewAdapter
import com.example.finalproject.ui.artistsfragment.adapters.SimpleArtistAdapter
import com.example.finalproject.ui.artistsfragment.viewmodel.ArtistsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtistViewFragment : Fragment() {
    private lateinit var binding: FragmentArtistViewBinding
    private var id: Int? = null
    private val viewModel: ArtistsViewModel by viewModels()
    private var adapter: ArtistViewAdapter? = null
    private var simpleArtistAdapter: SimpleArtistAdapter? = null
    private var artistsList: List<DataTypeModel.NameAndImage> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id")
        Log.d("artistId", "$id")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtistViewBinding.inflate(layoutInflater, container, false)
        Log.d("Lifecycle", "start")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Lifecycle", "${init()}")
        init()

        binding.cvBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.svArtist.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    searchArtist(newText)
                } else {
                    simpleArtistAdapter?.addList(artistsList)
                    adapter?.addList(artistsList)
                }
                return true
            }
        })
    }


    private fun init() {

        viewModel.artistsLiveData.observe(viewLifecycleOwner) { data ->
            when (data) {
                is UIState.Loading -> {
                    binding.progressBar.visibility =
                        if (data.isLoading) View.VISIBLE else View.GONE
                }

                is UIState.Data -> {
                    artistsList = data.data!!
                    Log.d("artistId", "$data")
                    if (id == null) {
                        simpleGetArtist()
                    } else {
                        getArtistWithId()
                    }
                }

                is UIState.Error -> {
                }

                UIState.None -> {
                }
            }
        }
        viewModel.fetchArtists()
    }

    private fun getArtistWithId() {
        adapter = ArtistViewAdapter()
        binding.rvArtists.adapter = adapter
        binding.rvArtists.layoutManager = LinearLayoutManager(context)
        val artistList = artistsList.filter { it.id == id }
        adapter?.addList(artistList)
    }

    private fun simpleGetArtist() {
        simpleArtistAdapter = SimpleArtistAdapter()
        binding.rvArtists.adapter = simpleArtistAdapter
        binding.rvArtists.layoutManager = LinearLayoutManager(context)
        simpleArtistAdapter?.addList(artistsList)
        simpleArtistAdapter?.setOnClickListener(object : SimpleArtistAdapter.Listener {
            override fun clickListener(data: DataTypeModel.NameAndImage) {
                val bundle = bundleOf(
                    "id" to data.id
                )
                findNavController().navigate(
                    R.id.action_artistViewFragment_to_albumsFragment,
                    bundle
                )
            }

        })
    }

    private fun searchArtist(query: String) {

        val searchList = artistsList.filter { it.name.lowercase().contains(query) }
        if (searchList.isEmpty()) {
            binding.vArtist.visibility = View.VISIBLE
            Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
        } else {
            binding.vArtist.visibility = View.INVISIBLE
            simpleArtistAdapter?.addList(searchList)
            adapter?.addList(searchList)
        }
    }
}