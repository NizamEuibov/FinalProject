package com.example.finalproject.ui.homefragment.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.data.localdatabase.ArtistsEntity
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.ui.homefragment.adapters.ParentAdapter
import com.example.finalproject.ui.homefragment.viewmodel.ForArtistsIdViewModel
import com.example.finalproject.ui.homefragment.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ParentAdapter
    private val viewModelForArtistsId: ForArtistsIdViewModel by viewModels()
    private val viewModel: HomeViewModel by viewModels()
    private var selected: ArtistsEntity? = null
    private var userId: Int? = null
    private val PREF_NAME = "SharedPre"
    private var artistsLists: List<DataTypeModel.NameAndImage> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments?.getInt("userId")


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
        Log.d("UserId", "$userId")
        artistsIdFromDatabase()
        sendListToAdapter()

    }


    private fun init() {
        viewModel.list.observe(viewLifecycleOwner) { it ->
            if (it != null) {
                artistsLists = it
            }
        }
    }

    private fun artistsIdFromDatabase() {
        userId?.let {
            viewModelForArtistsId.artistsId(it).observe(viewLifecycleOwner) { list ->
                selected = list
                Log.d("UserId", "$list")

            }
        }
    }
    private fun sendListToAdapter(){
        adapter = ParentAdapter()
        binding.rvHome.adapter = adapter
        binding.rvHome.layoutManager = LinearLayoutManager(context)
        val list =selected?.artistsId
        val list1= artistsLists.filter { list!!.contains(it.id) }
        adapter.addList(list1)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}