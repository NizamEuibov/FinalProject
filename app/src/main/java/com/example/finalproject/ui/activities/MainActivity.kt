package com.example.finalproject.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.ui.activities.stateadapter.HomeFragmentStateAdapter
import com.example.finalproject.ui.homefragment.fragment.HomeFragment
import com.example.finalproject.ui.libraryfragment.YourLibraryFragment
import com.example.finalproject.ui.serachfragments.fragments.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


    }
}