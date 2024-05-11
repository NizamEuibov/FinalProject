package com.example.finalproject.ui.libraryfragment.fragment

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentEditProfileBinding
import com.example.finalproject.ui.extension.TextView.disable
import com.example.finalproject.ui.extension.TextView.enable
import com.example.finalproject.ui.libraryfragment.viewmodel.UserNameViewModel
import com.example.finalproject.ui.`object`.SharedPrefs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel: UserNameViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(
            requireContext(),
            com.google.android.material.R.style.Theme_Design_BottomSheetDialog
        )

        dialog.setOnShowListener {
            val d = it as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ibCancel.setOnClickListener {
            dismiss()
        }


        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    binding.tvSave.enable()
                } else {
                    binding.tvSave.disable()
                    binding.etName.error = "Enter name"

                }

            }


            override fun afterTextChanged(s: Editable?) {
            }


        })
        binding.tvSave.setOnClickListener {
            changeName()
        }

    }

    private fun changeName() {
        val name = binding.etName.text.toString()
        val bundle = bundleOf(
            "name" to name
        )
        val id = SharedPrefs.getUserId("UserId")
        if (id != null) {
            viewModel.updateUserName(id, name)
        }
        findNavController().navigate(
            R.id.action_editProfileFragment_to_librarySettingFragment,
            bundle
        )
    }
}