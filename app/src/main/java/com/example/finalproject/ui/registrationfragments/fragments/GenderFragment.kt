package com.example.finalproject.ui.registrationfragments.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentGenderBinding
import com.example.finalproject.ui.extension.Button.disable
import com.example.finalproject.ui.extension.Button.enable

class GenderFragment : Fragment() {
    private var binding: FragmentGenderBinding? = null

    private val email: String? by lazy {
        arguments?.getString("email")
    }

    private val password: String? by lazy {
        arguments?.getString("password")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenderBinding.inflate(layoutInflater, container, false)
        return (binding?.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         autoComplete()


        binding?.buttonNext?.setOnClickListener {
            sendGender()
        }
        binding?.cvBack?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


        binding?.autoComplete?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString().isNotEmpty()) {
                    binding?.buttonNext?.enable()
                } else {
                    binding?.buttonNext?.disable()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun sendGender() {
        val gender = binding?.autoComplete?.text.toString()

        if (checkText(gender)) {
            val bundle = bundleOf(
                "email" to email, "password" to password, "gender" to gender
            )

            findNavController().navigate(R.id.action_genderFragment_to_nameFragment, bundle)
        } else {
            Toast.makeText(context, "Enter gender", Toast.LENGTH_LONG).show()
        }

    }

    private fun autoComplete(){
        val list = arrayOf("man", "woman", "none")

        binding?.autoComplete?.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                list
            )
        )
    }

    private fun checkText(text:String): Boolean {
        val gender= binding?.autoComplete?.text.toString()
        return gender.isNotEmpty()

    }

}