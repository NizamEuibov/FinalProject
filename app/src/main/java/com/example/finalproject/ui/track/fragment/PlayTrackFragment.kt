package com.example.finalproject.ui.track.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentPlayTrackBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PlayTrackFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPlayTrackBinding
    private var id: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(),
            com.google.android.material.R.style.Theme_Design_BottomSheetDialog)
        dialog.setOnShowListener{
            val d =it as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentPlayTrackBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ib3Point.setOnClickListener {
            findNavController().navigate(R.id.action_playTrackFragment_to_trackControlFragment)
        }
        binding.ibCancel.setOnClickListener { dismiss() }
        binding.ibShare.setOnClickListener {
            findNavController().navigate(R.id.action_playTrackFragment_to_trackShareFragment)
        }

    }


    private fun playMusic(){

    }


}