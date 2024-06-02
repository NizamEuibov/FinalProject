package com.example.finalproject.ui.homefragment.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.data.localdatabase.ArtistsEntity
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.data.networkdata.models.UIState
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.ui.homefragment.adapters.ParentAdapter
import com.example.finalproject.ui.homefragment.viewmodel.ForArtistsIdViewModel
import com.example.finalproject.ui.homefragment.viewmodel.HomeViewModel
import com.example.finalproject.ui.`object`.SharedPrefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ParentAdapter
    private val viewModelForArtistsId: ForArtistsIdViewModel by viewModels()
    private val viewModel: HomeViewModel by viewModels()
    private var selected: ArtistsEntity? = null
    private var userId: Int? = null
    private var artistsLists: List<DataTypeModel.NameAndImage> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = SharedPrefs.getUserId("UserId")
        Log.d("UserId1", "$userId")
        Log.d("UserId1", "${SharedPrefs.checkSignUp("SignedUp")}")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        Log.d("Signed Up","${SharedPrefs.getUserId("UserId")}")
    }


    private fun init() {
        viewModel.list.observe(viewLifecycleOwner) { data ->
            when (data) {
                is UIState.Loading -> {
                    binding.progressBar.visibility =
                        if (data.isLoading) View.VISIBLE else View.GONE
                }

                is UIState.Data -> {
                    artistsLists = data.data!!
                    artistsIdFromDatabase()
                }

                is UIState.Error -> {
                    binding.tvError.isVisible = true
                    binding.tvError.text = data.error
                }

                UIState.None -> {

                }
            }
        }
        viewModel.getInformation()
    }

    private fun artistsIdFromDatabase() {

        userId?.let {
            viewModelForArtistsId.artistsId(it).observe(viewLifecycleOwner) { list ->
                selected = list
                sendListToAdapter()
            }
        }

    }

    private fun sendListToAdapter() {
        adapter = ParentAdapter()
        binding.rvHome.adapter = adapter
        binding.rvHome.layoutManager = LinearLayoutManager(context)
        val list = selected?.artistsId
        val list1 = artistsLists.filter { list?.contains(it.id) == true }
        adapter.addList(list1)

        adapter.setOnItemClick(object :ParentAdapter.SelectedListener{
            override fun onItemListener(data: DataTypeModel.AlbumList) {
                val bundle= bundleOf(
                    "albumId" to data.id,
                    "albumName" to data.name,
                    "albumImage" to data.image
                )
                findNavController().navigate(R.id.action_homeFragment_to_albumViewFragment,bundle)
            }

        })
    }
}