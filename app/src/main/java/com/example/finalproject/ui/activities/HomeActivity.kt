package com.example.finalproject.ui.activities

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(){
    private lateinit var binding: ActivityHomeBinding
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav = binding.bnvHome
        val navHost =
            supportFragmentManager.findFragmentById(R.id.homeFragmentContainer) as NavHostFragment


        val navController = navHost.navController
        setupWithNavController(bottomNav, navController)


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

                else-> {
                    navController.navigate(R.id.yourLibraryFragment)
                    true
                }


            }
        }


        }


    fun musicCheck(audioIdPair: Pair<String, Int>) {
        Log.d("Miri", audioIdPair.toString())
        audioIdPair.first.let {
            Log.d("Audio", it)
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        audioIdPair.first.let {mediaPlayer.setDataSource(it)  }
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener{
            mediaPlayer.start()
        }}

        binding.ibMotion.setOnClickListener {
            if (mediaPlayer?.isPlaying == true)
                mediaPlayer?.stop()
            else
                mediaPlayer?.start()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }




}




