package com.example.finalproject.ui.homefragment.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.ui.homefragment.adapters.ParentAdapter
import com.example.finalproject.ui.homefragment.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ParentAdapter
    private val viewModel: HomeViewModel by viewModels()
    private var selected: List<Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selected = arguments?.getIntegerArrayList("selected")
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

    }


    private fun init() {
        adapter = ParentAdapter(requireContext())
        binding.rvHome.adapter = adapter
        binding.rvHome.layoutManager = LinearLayoutManager(context)
        viewModel.list.observe(viewLifecycleOwner) { it ->
            Log.d("user554","$it")
            if (it != null) {
                val matchItems = it.filter { selected?.contains(it.id) == true }
                adapter.addList(matchItems)

            }

        }
    }


//    private fun bottomNavigation(){
//        binding.viewPager2.adapter=adapter
//
//        binding.bnvHome.setOnItemSelectedListener{item ->
//            when(item.itemId){
//                R.id.home -> binding.viewPager2.currentItem=0
//                R.id.bottom_search -> binding.viewPager2.currentItem=1
//                R.id.bottom_library ->binding.viewPager2.currentItem=2
//            }
//            true
//        }
//        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                binding.bnvHome.menu.getItem(position).isChecked=true
//            }
//        })
//
//    }
    }






