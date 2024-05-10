package com.example.finalproject.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentHomeNavigationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeNavigationFragment : Fragment() {
    private lateinit var binding:FragmentHomeNavigationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentHomeNavigationBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHost = childFragmentManager
            .findFragmentById(R.id.homeFragmentContainer) as NavHostFragment
        val controller = navHost.navController
       binding.bnvHome.setupWithNavController(controller)
        binding.bnvHome.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                   controller.navigate(R.id.homeFragment)
                    true
                }

                R.id.bottom_search -> {
                   controller.navigate(R.id.searchFragment)
                    true
                }

                else -> {

                    controller.navigate(R.id.yourLibraryFragment)
                    true
                }
            }
        }
    }
}