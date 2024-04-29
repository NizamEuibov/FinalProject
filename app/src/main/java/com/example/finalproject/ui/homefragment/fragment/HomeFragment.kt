package com.example.finalproject.ui.homefragment.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.ui.homefragment.adapters.ParentAdapter
import com.example.finalproject.ui.homefragment.viewmodel.HomeViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ParentAdapter
    private val viewModel: HomeViewModel by viewModels()
    private var selected: List<Int>? = null
    private var name: String? = null
    private val PREF_NAME = "SharedPre"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selected = arguments?.getIntegerArrayList("id")
        name=arguments?.getString("name")
        Log.d("Selected1", "$selected")

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
        Log.d("Selected1", "${savedList()}")


    }


    private fun init() {
        adapter = ParentAdapter(requireContext())
        binding.rvHome.adapter = adapter
        binding.rvHome.layoutManager = LinearLayoutManager(context)
        viewModel.list.observe(viewLifecycleOwner) { it ->
            Log.d("user554", "$it")
            if (it != null) {
                val matchItems = it.filter { selected?.contains(it.id) == true }
                adapter.addList(matchItems)
            }
        }
    }

    private fun selectedList(selectedList: List<Int>?) {
        val sharedPreferences =
            requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(selectedList)
        editor.putString("selected_list", json)
        editor.apply()
    }

    private fun savedList(): List<Int> {
        val sharedPreferences =
            requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val selected = sharedPreferences.getString("selected_list", null)
        return Gson().fromJson(selected, object : TypeToken<List<Int>>() {}.type) ?: emptyList()
    }

    override fun onDestroy() {
        super.onDestroy()
        selectedList(selected)
    }


}