package com.example.finalproject.ui.activities

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.finalproject.databinding.FragmentSplashBinding
import com.example.finalproject.ui.`object`.ConstVal
import com.example.finalproject.ui.`object`.SharedPrefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var bindng: FragmentSplashBinding
    private lateinit var animation: ObjectAnimator
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindng = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return bindng.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences =
            requireContext().getSharedPreferences(ConstVal.PREF_NAME, Context.MODE_PRIVATE)
        SharedPrefs.sharedPrefs(sharedPreferences)
        Log.d("Useridspalsh", "${SharedPrefs.getUserId("UserId")}")
        val icon = bindng.icSpotify

        animation = ObjectAnimator.ofFloat(icon, "alpha", 1f, 0.5f)
        animation.duration = 1500
        animation.repeatCount = ObjectAnimator.INFINITE
        animation.repeatMode = ObjectAnimator.REVERSE
        animation.start()

        lifecycleScope.launch {
            delay(1500)
            if (SharedPrefs.checkSignUp("SignedUp") == true) {
                findNavController().
                navigate(SplashFragmentDirections.actionSplashFragmentToMainNavigationGraph())
            } else {
                findNavController().
                navigate(SplashFragmentDirections.actionSplashFragmentToRegistrationNavGraph())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        animation.cancel()
    }
}