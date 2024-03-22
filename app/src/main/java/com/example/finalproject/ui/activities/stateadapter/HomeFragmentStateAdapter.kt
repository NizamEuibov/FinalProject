package com.example.finalproject.ui.activities.stateadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finalproject.ui.homefragment.fragment.HomeFragment
import com.example.finalproject.ui.libraryfragment.YourLibraryFragment
import com.example.finalproject.ui.serachfragments.fragments.SearchFragment

class HomeFragmentStateAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> HomeFragment()
           1 -> SearchFragment()
           else -> YourLibraryFragment()
       }
    }




}