package com.example.finalproject.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var bindng:ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindng= ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(bindng.root)

        bindng.icSpotify.alpha= 0f
        bindng.icSpotify.animate().setDuration(1500).alpha(1f).withEndAction{
            val intent =Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}