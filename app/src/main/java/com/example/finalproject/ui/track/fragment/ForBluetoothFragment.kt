package com.example.finalproject.ui.track.fragment

import android.app.Dialog
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.finalproject.databinding.FragmentForBluetoothBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ForBluetoothFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentForBluetoothBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForBluetoothBinding.inflate(layoutInflater, container, false)
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
            connectSlider()
            dismiss()
        }
    }


    private fun connectSlider() {
        val audioManager = requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)


        binding.skbVolume.valueTo = maxVolume.toFloat()
        binding.skbVolume.value = currentVolume.toFloat()

        binding.skbVolume.addOnChangeListener { _, value, _ ->
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, value.toInt(), AudioManager.FLAG_SHOW_UI)
        }
    }

}