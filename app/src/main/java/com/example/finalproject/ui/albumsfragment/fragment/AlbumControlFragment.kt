package com.example.finalproject.ui.albumsfragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.FragmentAlbumControlBinding
import com.example.finalproject.ui.albumsfragment.viewmodel.AlbumViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumControlFragment : Fragment() {
    private lateinit var binding: FragmentAlbumControlBinding
    private val viewModel: AlbumViewModel by viewModels()
    private var id: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumControlBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumInformation()
        binding.tbClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }


    private fun albumInformation() {
        viewModel.albumsList.observe(viewLifecycleOwner) {
            if (it != null) {
                val albumImage =
                    it.map { it.albums.filter { it.id == id } }.map { it.map { it.image } }
                        .flatten()
                val artistName =
                    it.filter { it.albums.map { it.id }.contains(id) }.map { it.name }.toString()
                val albumName =
                    it.map { it.albums.filter { it.id == id } }.map { it.map { it.name } }.flatten()

                with(binding) {
                    tvAlbumName.text = albumName.toString().trim('[', ']')
                    tvArtistsName.text = artistName.trim('[', ']')
                    Glide.with(requireContext()).load(albumImage.toString())
                        .into(ivAlbumControl)
                }

            }
        }
    }

}