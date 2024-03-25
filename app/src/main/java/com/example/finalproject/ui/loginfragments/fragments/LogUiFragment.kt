package com.example.finalproject.ui.loginfragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentLogUiBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogUiFragment : Fragment() {
    private lateinit var binding: FragmentLogUiBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentLogUiBinding.inflate(layoutInflater,container,false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.butLogin.setOnClickListener {
            findNavController().navigate(R.id.action_logUiFragment_to_emailFragment)
        }

        binding.butLogin1.setOnClickListener {
            findNavController().navigate(R.id.action_logUiFragment_to_loginnFragment)
        }
    }

}