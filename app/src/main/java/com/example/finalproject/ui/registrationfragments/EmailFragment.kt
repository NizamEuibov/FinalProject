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
import com.example.finalproject.databinding.FragmentEmailBinding
import com.example.finalproject.viewmodels.forregistrationfragments.RegistrationViewModel


class EmailFragment : Fragment() {
    private var binding: FragmentEmailBinding? = null
    private val viewModel by viewModels<RegistrationViewModel>()
    private val email = binding?.etRegistration?.text

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmailBinding.inflate(layoutInflater, container, false)
        return (binding?.root)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.buttonNext?.setOnClickListener {
            sentEmail()
        }



    }

    private fun sentEmail() {
        if (email?.isNotEmpty() == true) {
            viewModel.email = email.toString()
        } else {
            Toast.makeText(context, "Enter email", Toast.LENGTH_LONG).show()
        }
        findNavController().navigate(R.id.action_emailFragment_to_passwordFragment)

    }

}