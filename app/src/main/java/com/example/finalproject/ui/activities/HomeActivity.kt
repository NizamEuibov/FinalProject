package com.example.finalproject.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityHomeBinding
import com.example.finalproject.ui.activities.oject.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var selectedArtist:ArrayList<Int>?=null
    private val sharedViewModel:SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedArtist = intent.getIntegerArrayListExtra("selected")
        Log.d("Selected", "$selectedArtist")
        val bundle=Bundle()
        bundle.putIntegerArrayList("id", selectedArtist)




        val bottomNav = binding.bnvHome
        val navHost =
            supportFragmentManager.findFragmentById(R.id.homeFragmentContainer) as NavHostFragment

        val navController = navHost.navController
        setupWithNavController(bottomNav, navController)
        navController.navigate(R.id.homeFragment,bundle)


        binding.bnvHome.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.bottom_search -> {
                    navController.navigate(R.id.searchFragment)
                    true
                }

                else -> {
                    navController.navigate(R.id.yourLibraryFragment)
                    true
                }


            }
        }

    }


    private fun musicCheck() {
       sharedViewModel.data.observe(this) {
           Log.d("Audio1", "$it")
       }

    }

}