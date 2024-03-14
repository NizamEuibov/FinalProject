package com.example.finalproject.ui.loginfragments.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentLoginBinding
import com.example.finalproject.ui.extension.Button.enable
import com.example.finalproject.ui.loginfragments.model.LoginModel
import com.example.finalproject.ui.loginfragments.viewmodel.LogInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginnFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LogInViewModel by viewModels<LogInViewModel>()
    lateinit var email: String
    lateinit var password: String
    lateinit var data: LoginModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogInn.setOnClickListener {
            getLogin()
        }


        binding.etForLogin.addTextChangedListener(object : TextWatcher {
            val email = binding.etForLogin.text.toString()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isEmpty() == true && !(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
                    binding.etForLogin.error = "Please enter your Spotify email address."
                } else {
                    binding.buttonLogInn.enable()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.cvBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }


    private fun getLogin() {
        email = binding.etForLogin.text.toString()
        password = binding.etForPassword.text.toString()
        data = LoginModel(email, password)


        if (viewModel.checkData(data) == true) {
            findNavController().navigate(R.id.action_loginnFragment_to_homeFragment)
        } else {
            Toast.makeText(
                requireContext(),
                "Oops! Something went wrong, please try again or check your email or password",
                Toast.LENGTH_LONG
            ).show()
        }

    }

}


