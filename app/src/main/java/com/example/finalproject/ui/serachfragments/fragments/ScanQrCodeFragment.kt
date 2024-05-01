package com.example.finalproject.ui.serachfragments.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.finalproject.databinding.FragmentScanQrCodeBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ScanQrCodeFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentScanQrCodeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanQrCodeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(
            requireContext(), com.google.android.material.R.style.Theme_Design_BottomSheetDialog
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
    }


}