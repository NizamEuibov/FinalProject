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
import com.example.finalproject.databinding.FragmentGenderBinding
import com.example.finalproject.viewmodels.forregistrationfragments.RegistrationViewModel

class GenderFragment : Fragment() {
    private var binding: FragmentGenderBinding? = null
    private val viewModel by viewModels<RegistrationViewModel>()
    private val gender = binding?.etRegistration?.text

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentGenderBinding.inflate(layoutInflater,container,false)
        return (binding?.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.buttonNext?.setOnClickListener {
            sendGender()
        }
        binding?.mbBack?.setOnClickListener {
            findNavController().navigate(R.id.action_genderFragment_to_passwordFragment)
        }
    }

    private fun sendGender(){
        if (gender?.isNotEmpty()==true){
            viewModel.gender=gender.toString()
        }
        else{
            Toast.makeText(context,"Enter gender",Toast.LENGTH_LONG).show()
        }
        findNavController().navigate(R.id.action_genderFragment_to_nameFragment)
    }

}