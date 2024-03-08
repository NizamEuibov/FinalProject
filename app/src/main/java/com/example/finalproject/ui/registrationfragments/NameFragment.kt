package com.example.finalproject.ui.registrationfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentNameBinding
import com.example.finalproject.viewmodels.forregistrationfragments.RegistrationViewModel


class NameFragment : Fragment() {
    private var binding: FragmentNameBinding? = null
    private val viewModel by viewModels<RegistrationViewModel>()
    private val name = binding?.etRegistration?.text
    private val radioButton1 = binding?.rb1Name
    private val radioButton2 = binding?.rb2Name

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNameBinding.inflate(layoutInflater, container, false)
        return (binding?.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.buttonAccount?.setOnClickListener {
            sendName()
        }
        binding?.mbBack?.setOnClickListener {
            findNavController().navigate(R.id.action_nameFragment_to_genderFragment)
        }
    }

    private fun sendName() {
        if (name?.isNotEmpty() == true &&
            radioButton1?.isChecked == true &&
            radioButton2?.isChecked == true) {
            viewModel.gender = name.toString()
        } else {
            Toast.makeText(context, "Fill the gaps", Toast.LENGTH_LONG).show()
        }
    }


}