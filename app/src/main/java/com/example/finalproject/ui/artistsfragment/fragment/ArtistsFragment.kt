package com.example.finalproject.ui.artistsfragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentArtistsBinding
import com.example.finalproject.ui.artistsfragment.adapters.ArtistsAdapter
import com.example.finalproject.ui.artistsfragment.model.ModelArtistsFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
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
        binding.cvBack.setOnClickListener {
            findNavController().navigate(R.id.action_artistsFragment_to_logInFragment)
        }

        binding.svArtist.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filteredList(newText)

                }
                return true
            }

        })

    }

    private fun init1() {
        adapater = ArtistsAdapter(requireContext(), this)
        binding.rvArtists.adapter = adapater
        binding.rvArtists.layoutManager = GridLayoutManager(context, 3)
        adapater.addNotes(imageList)

    }


    private val imageList = mutableListOf(
        ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljmj"),
        ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljmh"),
        ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljmk"),
        ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm"),
        ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljmk"),
        ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm1"),
        ModelArtistsFragment(R.drawable.ic_fashion, "jhfuilj5m"),
        ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm8"),
        ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm6"),
        ModelArtistsFragment(R.drawable.ic_fashion, "jhfuiljm6"),
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


    override fun onClick(click: ModelArtistsFragment) {
        findNavController().navigate(R.id.action_artistsFragment_to_podcatsFragment)
    }

    private fun filteredList(query: String) {
        val filteredList = mutableListOf<ModelArtistsFragment>()
        for (i in imageList) {
            if (i.name.lowercase().contains(query)) {
                filteredList.add(i)
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show()
        }
        else{
            adapater.addNotes(filteredList)
        }


    }
}