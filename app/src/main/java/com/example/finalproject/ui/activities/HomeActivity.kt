package com.example.finalproject.ui.activities

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() ,AudioListener {
    private lateinit var binding: ActivityHomeBinding
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        musicCheck()


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

                R.id.bottom_library -> {
                    navController.navigate(R.id.yourLibraryFragment)
                    true
                }

                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.albumControlFragment) {
                binding.bnvHome.visibility = View.INVISIBLE
            } else bottomNav.visibility = View.VISIBLE


        }
    }


    private fun musicCheck() {
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setDataSource("https://prod-1.storage.jamendo.com/?trackid=241&format=mp31&from=w3d4v2X9TLHG%2BTVkKZe%2FTw%3D%3D%7CTpCSTgyZebQhLbYNer928g%3D%3D")
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener{
            mediaPlayer.start()
        }
        binding.ibMotion.setOnClickListener {
            if (mediaPlayer.isPlaying)
                mediaPlayer.stop()
            else
                mediaPlayer.start()
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun sentAudioLink(audio: String, id: Int) {

    }


}
interface AudioListener {
    fun sentAudioLink(audio: String, id: Int)
}






