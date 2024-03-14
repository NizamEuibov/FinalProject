package com.example.finalproject.ui.registrationfragments.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.data.localdatabase.RegistrationEntity
import com.example.finalproject.databinding.FragmentNameBinding
import com.example.finalproject.ui.extension.Button.disable
import com.example.finalproject.ui.extension.Button.enable
import com.example.finalproject.ui.registrationfragments.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NameFragment : Fragment() {
    private val viewModel by viewModels<RegistrationViewModel>()
    private lateinit var binding: FragmentNameBinding
    private lateinit var email: String

    private lateinit var password: String

    private lateinit var gender: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = arguments?.getString("email").toString()
        password = arguments?.getString("password").toString()
        gender = arguments?.getString("gender").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNameBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAccount.setOnClickListener {
            sendName()
        }
        binding.cvBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.etRegistration.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (!checkName(s.toString())) {
                    binding.buttonAccount.disable()
                } else {
                    binding.buttonAccount.enable()
                }
            }

        })
//problem var kecidlerle bagli

    }

    private fun sendName() {

        val name = binding.etRegistration.text.toString()
        if (checkName(name)) {
            val user = RegistrationEntity(0, email, password, gender, name)
            viewModel.sendDataToRepository(user)
            findNavController().navigate(R.id.action_nameFragment_to_artistsFragment)
        }


    }

    private fun checkName(text: String): Boolean {
        val radioButton1 = binding.rb1Name
        val radioButton2 = binding.rb2Name
        return !(text.isNotEmpty() && radioButton2.isChecked && radioButton1.isChecked)
    }
}