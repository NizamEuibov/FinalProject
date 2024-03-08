package com.example.finalproject.ui.registrationfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentGenderBinding
import com.example.finalproject.databinding.FragmentNameBinding
import com.example.finalproject.databinding.FragmentPasswordBinding
import com.example.finalproject.databinding.FragmentPasswordBinding.*
import com.example.finalproject.viewmodels.forregistrationfragments.RegistrationViewModel


class PasswordFragment : Fragment() {
    private var binding: FragmentGenderBinding? = null
    private val viewModel by viewModels<RegistrationViewModel>()
    private val password = binding?.etRegistration?.text

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenderBinding.inflate(layoutInflater,container,false)
        return (binding?.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.buttonNext?.setOnClickListener {
            sendPassword()
        }
        binding?.mbBack?.setOnClickListener {
            findNavController().navigate(R.id.action_passwordFragment_to_emailFragment)
        }
    }

    private fun sendPassword() {
        if (password?.isNotEmpty() == true ) {
            viewModel.password = password.toString()
        } else {
            Toast.makeText(context, "Fill the gaps", Toast.LENGTH_LONG).show()
        }
        findNavController().navigate(R.id.action_passwordFragment_to_genderFragment)
    }

}