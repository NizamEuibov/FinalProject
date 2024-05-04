package com.example.finalproject.ui.registrationsFragments.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentEmailBinding
import com.example.finalproject.ui.extension.Button.disable
import com.example.finalproject.ui.extension.Button.enable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailFragment : Fragment() {
    private lateinit var binding: FragmentEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmailBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonNext.disable()

        binding.buttonNext.setOnClickListener {
            sentEmail()
        }

        binding.cvBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


        binding.etRegistration.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val email = binding.etRegistration.text

                if (s != null) {
                    if (Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                        binding.buttonNext.enable()

                    } else {
                        binding.buttonNext.disable()
                        binding.etRegistration.error =
                            "This email is invalid. Make sure it's written like example@email.com"

                    }
                }
                }




        })
    }

    private fun sentEmail() {
        val email = binding.etRegistration.text.toString()


        if (email.isNotEmpty()) {
            val bundle = bundleOf(
                "email" to email
            )

            findNavController().navigate(R.id.action_emailFragment_to_passwordFragment, bundle)
        } else {
            Toast.makeText(context, "Enter right email format", Toast.LENGTH_LONG).show()
        }

    }

}