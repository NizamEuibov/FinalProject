package com.example.finalproject.ui.activities.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finalproject.ui.homefragment.fragment.HomeFragment
import com.example.finalproject.ui.libraryfragment.fragment.YourLibraryFragment
import com.example.finalproject.ui.serachfragments.fragments.SearchFragment

class StateFragmentAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments = listOf(HomeFragment(), SearchFragment(), YourLibraryFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}