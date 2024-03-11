package com.example.finalproject.ui.artistsfragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentArtistsBinding
import com.example.finalproject.ui.artistsfragment.adapters.ArtistsAdapter
import com.example.finalproject.ui.artistsfragment.model.ModelArtistsFragment


class ArtistsFragment : Fragment(), ArtistsAdapter.OnActionListener {
    private lateinit var binding: FragmentArtistsBinding
    private lateinit var adapater: ArtistsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtistsBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init1()
        data()
    }

    private fun init1() {
        adapater = ArtistsAdapter(requireContext(), this)
        binding.rvArtists.adapter = adapater
        binding.rvArtists.layoutManager = GridLayoutManager(context, 3)

    }

    private fun data() {
        val imageList = mutableListOf(
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
            ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm")
        )

        adapater.addNotes(imageList)

    }

    override fun onClick(click: ModelArtistsFragment) {
        findNavController().navigate(R.id.action_artistsFragment_to_podcatsFragment)
    }
}