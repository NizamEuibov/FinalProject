package com.example.finalproject.ui.libraryfragment.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentLibrarySettingBinding
import com.example.finalproject.ui.activities.MainActivity
import com.example.finalproject.ui.libraryfragment.viewmodel.UserNameViewModel
import com.example.finalproject.ui.`object`.SharedPrefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LibrarySettingFragment : Fragment() {
    private lateinit var binding: FragmentLibrarySettingBinding
    private val viewModel: UserNameViewModel by viewModels()
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = SharedPrefs.getUserId("UserId")
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
        userName()
        binding.civSettings.setOnClickListener {
            findNavController().navigate(R.id.action_librarySettingFragment_to_libraryUserFragment)
        }

        binding.ibBackSettings.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.bLogOut.setOnClickListener {
            logOutAlertDialog()
        }
    }

    private fun userName() {

        id?.let {
            viewModel.getUserName(it).observe(viewLifecycleOwner) { name ->
                binding.tvSettingName.text = name

            }
        }
    }

    private fun logOutAlertDialog() {
        AlertDialog.Builder(context)
            .setMessage("Do you want to exit")
            .setPositiveButton("Yes") { _, _ ->
                SharedPrefs.removeUserId("UserId")
                SharedPrefs.removeSignUp("SignedUp")
                val intent = Intent(requireActivity(),MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}


