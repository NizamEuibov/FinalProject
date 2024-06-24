package com.example.finalproject.ui.albumsfragment.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.R
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.data.networkdata.models.UIState
import com.example.finalproject.databinding.FragmentAlbumsBinding
import com.example.finalproject.ui.albumsfragment.adapter.AlbumsAdapter
import com.example.finalproject.ui.albumsfragment.viewmodel.AlbumViewModel
import com.example.finalproject.ui.`object`.ConstVal.ERROR
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsFragment : Fragment() {
    private lateinit var binding: FragmentAlbumsBinding
    private lateinit var adapter: AlbumsAdapter
    private val viewModel: AlbumViewModel by viewModels()
    private var albumsList: List<DataTypeModel.AlbumList> = emptyList()
    private var albumsListAll: MutableList<DataTypeModel.AlbumList> = mutableListOf()
    private val artistsList: MutableList<DataTypeModel.NameAndImage> = mutableListOf()
    private var id: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumsBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()


        binding.cvBack.setOnClickListener {
           parentFragmentManager.popBackStack()
        }

        binding.svAlbum.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()){
                    filteredAlbum(newText)
                }
                else{
                    adapter.addImage(albumsListAll)
                }
                return true
            }

        })


    }

    private fun init() {
        adapter = AlbumsAdapter()
        binding.rvAlbum.adapter = adapter
        binding.rvAlbum.layoutManager = GridLayoutManager(context, 3)
        viewModel.albumsList.observe(viewLifecycleOwner) { data ->
            when (data) {
                is UIState.Loading -> {
                    binding.progressBar.visibility =
                        if (data.isLoading) View.VISIBLE else View.GONE
                }

                is UIState.Data -> {
                    data.data?.let {
                        artistsList.addAll(it)
                    }

                    if (id != null) {
                        albumList()
                    } else {
                        addList()
                    }
                    listenerView()


                }

                else -> {
                    UIState.Error(ERROR)
                }
            }
        }
        viewModel.fetchArtist()
    }

    private fun albumList() {
        albumsList = artistsList.map { it.albums }.flatten()
        val artistId = artistsList.map { it.id }
        Log.d("user555", "$artistId")
        if (artistId.contains(id)) {
            adapter.addImage(artistsList.filter { it.id == id }.map { it.albums }.flatten())
        } else
            adapter.addImage(albumsList)

    }


    private fun filteredAlbum(query: String) {
        val filteredAlbum = albumsListAll.filter { it.name?.lowercase()?.contains(query) == true }

        if (filteredAlbum.isEmpty()) {
            binding.vAlbumFragment.visibility = View.VISIBLE
        } else {
            binding.vAlbumFragment.visibility = View.INVISIBLE
            adapter.addImage(filteredAlbum)
        }
    }


    private fun listenerView() {
        adapter.setOnClickLIstener(object : AlbumsAdapter.Listener {
            override fun onClickListener(data: DataTypeModel.AlbumList) {

                val bundle = bundleOf(
                    "albumId" to data.id,
                    "albumName" to data.name,
                    "albumImage" to data.image

                )
                findNavController().navigate(
                    R.id.action_albumsFragment_to_albumViewFragment,
                    bundle
                )
            }

        })
    }

    private fun addList() {
        adapter = AlbumsAdapter()
        binding.rvAlbum.adapter = adapter
        binding.rvAlbum.layoutManager = GridLayoutManager(context, 3)
        albumsListAll = artistsList.map { it.albums }.flatten().toMutableList()

        adapter.addImage(albumsListAll)
    }
}
//back edende melumat itir
