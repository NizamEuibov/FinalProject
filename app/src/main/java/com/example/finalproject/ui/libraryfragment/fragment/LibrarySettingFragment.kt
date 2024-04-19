package com.example.finalproject.ui.libraryfragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentLibrarySettingBinding

class LibrarySettingFragment : Fragment() {
    private lateinit var binding: FragmentLibrarySettingBinding
    private var name: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = arguments?.getString("name")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibrarySettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.civSettings.setOnClickListener {
            findNavController().navigate(R.id.action_librarySettingFragment_to_libraryUserFragment)
        }

        binding.ibBackSettings.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.tvSettingName.text = name

    }

    override fun onDestroy() {
        super.onDestroy()

    }
}

