package com.example.finalproject.ui.homefragment.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.data.localdatabase.ArtistsEntity
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.data.networkdata.models.UIState
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.ui.homefragment.adapters.ParentAdapter
import com.example.finalproject.ui.homefragment.viewmodel.ForArtistsIdViewModel
import com.example.finalproject.ui.homefragment.viewmodel.HomeViewModel
import com.example.finalproject.ui.`object`.SharedPrefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ParentAdapter
    private val viewModelForArtistsId: ForArtistsIdViewModel by viewModels()
    private val viewModel: HomeViewModel by viewModels()
    private var selected: ArtistsEntity? = null
    private var id: Int? = null
    private var userId: Int? = null
    private val PREF_NAME = "SharedPre"
    private var artistsLists: List<DataTypeModel.NameAndImage> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("userId")
        userId1()
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
        lifecycleScope.launch {
            artistsIdFromDatabase()
            delay(2000)
            sendListToAdapter()
        }
    }


    private fun init() {
        viewModel.list.observe(viewLifecycleOwner) { data ->
            when (data) {
                is UIState.Loading -> {
                    binding.progressBar.visibility =
                        if (data.isLoading) View.VISIBLE else View.GONE
                }

                is UIState.Data -> {
                    artistsLists = data.data
                }

                is UIState.Error -> {
                    binding.tvError.isVisible = true
                    binding.tvError.text=data.error
                }

                UIState.None -> {

                }
            }
        }
    }

    private fun artistsIdFromDatabase() {

        userId?.let {
            viewModelForArtistsId.artistsId(it).observe(viewLifecycleOwner) { list ->
                selected = list
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
    }

    private fun userId1() {
        val sharedPreferences =
            requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        SharedPrefs.sharedPrefs(sharedPreferences)
        id?.let { SharedPrefs.putUserId("UserId", 22) }
        userId = SharedPrefs.getUserId("UserId")
        Log.d("userid", "$userId")
    }
}