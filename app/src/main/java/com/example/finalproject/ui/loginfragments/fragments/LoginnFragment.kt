package com.example.finalproject.ui.loginfragments.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.finalproject.databinding.FragmentLoginBinding
import com.example.finalproject.ui.extension.Button.disable
import com.example.finalproject.ui.extension.Button.enable
import com.example.finalproject.ui.loginfragments.model.LoginModel
import com.example.finalproject.ui.loginfragments.viewmodel.LogInViewModel
import com.example.finalproject.ui.`object`.ConstVal.PREF_NAME
import com.example.finalproject.ui.`object`.SharedPrefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginnFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LogInViewModel by viewModels<LogInViewModel>()
    lateinit var email: String
    lateinit var password: String
    lateinit var data: LoginModel
    private var id: Int? = null

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

        textWatcher()

        binding.cvBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }


    private fun getLogin() {
        email = binding.etForLogin.text.toString()
        password = binding.etRegistration.text.toString()
        data = LoginModel(email, password)

        if (viewModel.checkData(data) == true) {
            lifecycleScope.launch {
                getUserId(email)
                delay(1000)
                sharedPreferences()
                findNavController().navigate(LoginnFragmentDirections.actionLoginnFragmentToMainNavigationGraph())
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Oops! Something went wrong, please try again or check your email or password",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    private fun textWatcher() {
        binding.etForLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()
                val password = binding.etRegistration.text.toString().isNotEmpty()
                if (email && password) {
                    binding.buttonLogInn.enable()
                } else {
                    binding.buttonLogInn.disable()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })


        binding.etRegistration.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString().length >= 8
                val email =
                    Patterns.EMAIL_ADDRESS.matcher(binding.etForLogin.text.toString()).matches()
                if (password && email) {
                    binding.buttonLogInn.enable()
                } else {
                    binding.etRegistration.error = "Email or password is incorrect."
                    binding.buttonLogInn.disable()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun getUserId(email: String) {
        viewModel.userId(email).observe(viewLifecycleOwner) {
            if (it != null) {
                id = it
                Log.d("UserId", "$id")
                val sharedPreferences =
                    requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                SharedPrefs.sharedPrefs(sharedPreferences)
                SharedPrefs.SignUp("SignedUp", true)
                SharedPrefs.putUserId("UserId", it)
            }
        }
    }

    private fun sharedPreferences() {
        val sharedPreferences =
            requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        SharedPrefs.sharedPrefs(sharedPreferences)
        SharedPrefs.SignUp("SignedUp", true)
    }
}
