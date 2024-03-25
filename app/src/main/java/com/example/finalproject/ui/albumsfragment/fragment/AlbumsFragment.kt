package com.example.finalproject.ui.albumsfragment.fragment

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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.R
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.databinding.FragmentAlbumsBinding
import com.example.finalproject.ui.albumsfragment.adapter.AlbumsAdapter
import com.example.finalproject.ui.albumsfragment.viewmodel.AlbumViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsFragment : Fragment() {
    private lateinit var binding: FragmentAlbumsBinding
    private lateinit var adapter: AlbumsAdapter
    private val viewModel: AlbumViewModel by viewModels()
    private var albumsList: List<DataTypeModel.AlbumList> = emptyList()
    private var artistsList: List<DataTypeModel.NameAndImage> = emptyList()
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
        listenerView()


        binding.svAlbum.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank())
                    filteredAlbum(newText)
                else {
                    adapter.addImage(albumsList)
                }
                return true
            }

        })


    }

    private fun init() {
        adapter = AlbumsAdapter(requireContext())
        binding.rvAlbum.adapter = adapter
        binding.rvAlbum.layoutManager = GridLayoutManager(context, 3)
        viewModel.albumsList.observe(viewLifecycleOwner) { artists ->
            artistsList = artists
            if (artists != null)
                albumsList = artists.map { it.albums }.flatten()
            val artistId = artists.map { it.id }
            Log.d("user555", "$artistId")
            if (artistId.contains(id)) {
                adapter.addImage(artists.filter { it.id == id }.map { it.albums }.flatten())
            } else
                adapter.addImage(albumsList)


        }
    }


    private fun filteredAlbum(query: String) {
        val filteredAlbum = albumsList.filter { it.name?.lowercase()?.contains(query) == true }

        if (filteredAlbum.isEmpty()) {
            binding.vAlbumFragment.visibility = View.VISIBLE
            Toast.makeText(context, "No found data", Toast.LENGTH_SHORT).show()
        } else {
            binding.vAlbumFragment.visibility = View.INVISIBLE
            adapter.addImage(filteredAlbum)
        }
    }


    private fun listenerView() {
        adapter.setOnClickLIstener(object : AlbumsAdapter.Listener {
            override fun onClickListener(data: DataTypeModel.AlbumList) {
                val albumName = data.id

                val bundle = bundleOf(
                    "name" to  artistsList.filter { it.albums.contains(data) }.map { it.name }.toString(),
                    "image" to artistsList.filter { it.albums.contains(data) }.map { it.image }.toString(),
                    "albumName" to data.name,
                    "albumImage" to data.image

                )
                findNavController().navigate(
                    R.id.action_albumsFragment_to_albumViewFragment,
                    bundle
                )
                Log.d(
                    "user666",
                    "${artistsList.filter { it.albums.contains(data) }.map { it.name }}"
                )
            }

        })
    }

}