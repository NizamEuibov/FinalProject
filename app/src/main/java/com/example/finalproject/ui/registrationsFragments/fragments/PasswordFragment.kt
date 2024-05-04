package com.example.finalproject.ui.registrationsFragments.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentPasswordBinding
import com.example.finalproject.ui.extension.Button.disable
import com.example.finalproject.ui.extension.Button.enable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordFragment : Fragment() {
    private lateinit var binding: FragmentPasswordBinding
    private lateinit var email: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email= arguments?.getString("email").toString()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonNext.setOnClickListener {
            sendPassword()
        }
        binding.cvBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.etRegistration.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = binding.etRegistration.text.toString()
                if (checkPassword(password)) {
                    binding.buttonNext.enable()
                } else {
                    binding.buttonNext.disable()
                    binding.etRegistration.error = "Password must be 8 character and 1 upper letter"
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }

    private fun sendPassword() {
        val password = binding.etRegistration.text.toString()
        if (checkPassword(password)) {
            val bundle = bundleOf(
                "email" to email,
                "password" to password
            )

            findNavController().navigate(R.id.action_passwordFragment_to_genderFragment, bundle)

        } else {
            Toast.makeText(context, "Fill the gaps", Toast.LENGTH_LONG).show()
        }


    }

    private fun checkPassword(text: String): Boolean {

        if (text.length >= 8) {

            for (char in text) {

                if (char.isUpperCase()) {
                    return true

                }
                if (char.isWhitespace()) {
                    return false
                }
            }


        } else {
            return false
        }
        return false
    }

}
fun checkChar(text: String): Boolean {
    if (text.length >= 8) {
        for (char in text) {

            if (char.isUpperCase() ){
                return true

            }
            if (char.isWhitespace()){
                return false}
        }


    } else {
        return false
    }
    return false
}


fun main() {
    val a = checkChar("kgdjgLehch")
    println(a)
}