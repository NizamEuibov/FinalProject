package com.example.finalproject.ui.artistsfragment.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.finalproject.data.localdatabase.ArtistsEntity
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.data.networkdata.models.UIState
import com.example.finalproject.databinding.FragmentArtistsBinding
import com.example.finalproject.ui.artistsfragment.adapters.ArtistsAdapter
import com.example.finalproject.ui.artistsfragment.viewmodel.ArtistsIdViewModel
import com.example.finalproject.ui.artistsfragment.viewmodel.ArtistsViewModel
import com.example.finalproject.ui.artistsfragment.viewmodel.UserIdViewModel
import com.example.finalproject.ui.`object`.ConstVal.ERROR
import com.example.finalproject.ui.`object`.ConstVal.PREF_NAME
import com.example.finalproject.ui.`object`.SharedPrefs
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates


@AndroidEntryPoint
class ArtistsFragment : Fragment() {
    private lateinit var binding: FragmentArtistsBinding
    private lateinit var adapter: ArtistsAdapter
    private val userIdViewModel: UserIdViewModel by viewModels()
    private val artistsIdViewModel: ArtistsIdViewModel by viewModels()
    private val viewModel: ArtistsViewModel by viewModels()
    private var artistsList: List<DataTypeModel.NameAndImage> = emptyList()
    private var userId by Delegates.notNull<Int>()
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = arguments?.getString("email").toString()
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
        startFirst()
        init()
        binding.cvBack.setOnClickListener {
            findNavController().navigate(R.id.action_artistsFragment_to_loginnFragment)
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


    private fun init() {

        viewModel.artistsLiveData.observe(viewLifecycleOwner) { data ->
            when (data) {
                is UIState.Loading -> {
                    binding.progressBar.visibility =
                        if (data.isLoading) View.VISIBLE else View.GONE
                }

                is UIState.Data -> {
                    artistsList = data.data!!
                    artistsInformation()
                }

                else -> {
                    UIState.Error(ERROR)
                }
            }
        }
        viewModel.fetchArtists()
    }

    private fun artistsInformation() {
        adapter = ArtistsAdapter()
        binding.rvArtists.adapter = adapter
        binding.rvArtists.layoutManager = GridLayoutManager(context, 3)
        adapter.addNotes(artistsList)
        adapterClick()
    }

    private fun adapterClick() {
        adapter.setSelectedListener(object : ArtistsAdapter.SelectedListener {
            override fun onSelected(selectedItems: List<DataTypeModel.NameAndImage>) {


                if (selectedItems.size < 3) {
                    binding.artistsButton.visibility = View.INVISIBLE
                } else {
                    binding.artistsButton.visibility = View.VISIBLE

                    binding.artistsButton.setOnClickListener {
                        val isSelected = selectedItems.map { it.id }
                        val artist = ArtistsEntity(0, userId, isSelected)
                        artistsIdViewModel.sendArtistsIdToRepository(artist)
                        findNavController().navigate(ArtistsFragmentDirections.actionArtistsFragmentToMainNavigationGraph())
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


    private fun sharedPreferences() {
        val sharedPreferences =
            requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        SharedPrefs.sharedPrefs(sharedPreferences)
        SharedPrefs.putUserId("UserId", userId)
        SharedPrefs.SignUp("SignedUp", true)
    }


    private fun startFirst() {
        userIdViewModel.getUserId(email).observe(viewLifecycleOwner) {
            if (it != null) {
                userId = it
                Log.d("UserId", "$userId")
                sharedPreferences()
            }
        }
    }

}


