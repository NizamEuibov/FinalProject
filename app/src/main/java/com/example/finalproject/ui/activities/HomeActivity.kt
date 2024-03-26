package com.example.finalproject.ui.activities

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityHomeBinding
import com.example.finalproject.ui.activities.adapter.StateFragmentAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    private val viewPager2Adapter = StateFragmentAdapter(supportFragmentManager, lifecycle)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        musicCheck()


        val bottomNav=binding.bnvHome
        val navHost=supportFragmentManager.findFragmentById(R.id.homeFragmentContainer) as NavHostFragment


        val navController = navHost.navController
        setupWithNavController(bottomNav,navController)


        binding.bnvHome.setOnItemSelectedListener{item->
            when(item.itemId){
                R.id.home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.bottom_search-> {
                    navController.navigate(R.id.searchFragment)
                    true
                }
                R.id.bottom_library -> {
                    navController.navigate(R.id.yourLibraryFragment)
                    true
                }

                else -> false
            }
        }
    }



    private fun musicCheck(){
        val mediaPlayer=MediaPlayer.create(this,R.raw.music)

        binding.ibPlay.setOnClickListener {
            if (mediaPlayer.isPlaying)
                mediaPlayer.start()

            else
                mediaPlayer.pause()
        }
    }
        }


