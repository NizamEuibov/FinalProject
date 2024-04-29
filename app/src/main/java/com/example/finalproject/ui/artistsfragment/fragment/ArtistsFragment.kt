package com.example.finalproject.ui.artistsfragment.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.R
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.databinding.FragmentArtistsBinding
import com.example.finalproject.ui.activities.HomeActivity
import com.example.finalproject.ui.artistsfragment.adapters.ArtistsAdapter
import com.example.finalproject.ui.artistsfragment.viewmodel.ArtistsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArtistsFragment : Fragment() {
    private var name:String?=null
    private lateinit var binding: FragmentArtistsBinding
    private lateinit var adapter: ArtistsAdapter
    private val viewModel: ArtistsViewModel by viewModels()
    private var artistsList: List<DataTypeModel.NameAndImage> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name=arguments?.getString("name")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtistsBinding.inflate(layoutInflater, container, false)
        return binding.root

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
                if (!newText.isNullOrBlank()) {
                    filteredList(newText)
                } else {
                    adapter.addNotes(artistsList)
                }
                return true
            }
        })


    }

    private fun init1() {
        adapter = ArtistsAdapter(requireContext())
        binding.rvArtists.adapter = adapter
        binding.rvArtists.layoutManager = GridLayoutManager(context, 3)
        viewModel.artistsLiveData.observe(viewLifecycleOwner) { artists ->
            artists?.let {
                artistsList = it
                adapter.addNotes(artistsList)

            }
        }
        adapter.setSelectedListener(object : ArtistsAdapter.SelectedListener {
            override fun onSelected(selectedItems: List<DataTypeModel.NameAndImage>) {


                if (selectedItems.size < 3) {
                    binding.artistsButton.visibility = View.INVISIBLE
                } else {
                    binding.artistsButton.visibility = View.VISIBLE

                    binding.artistsButton.setOnClickListener {
                        val isSelected=selectedItems.map { it.id }
                        //findNavController().navigate(R.id.action_artistsFragment_to_homeFragment,bundle)
                        val navigateIntent = Intent(requireActivity(),HomeActivity::class.java)
                        navigateIntent.putExtra("selected", ArrayList(isSelected))
                        navigateIntent.putExtra("name", name)
                        startActivity(navigateIntent)
                    }
                }
            }

        })

    }

    private fun filteredList(query: String) {
        val filteredList = artistsList.filter { it.name.lowercase().contains(query) }
        if (filteredList.isEmpty()) {
            Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show()
        } else {
            adapter.addNotes(filteredList)
        }
    }


}


