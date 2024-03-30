package com.example.finalproject.ui.track.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentTrackControlBinding
import com.example.finalproject.ui.track.viewmodel.TrackViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackControlFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTrackControlBinding
    private val viewModel: TrackViewModel by viewModels()
    private var id: Int? = null
    private var artistName: String? = null
    private var albumId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id")
        albumId = arguments?.getInt("albumId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrackControlBinding.inflate(layoutInflater, container, false)
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

        binding.tbClose.setOnClickListener {
            dismiss()
        }

        binding.tvAlbumControlShare.setOnClickListener {
            val bundle = bundleOf("id" to id)
            val trackControlFragment = TrackShareFragment().apply {
                arguments = bundle
            }
            trackControlFragment.show(childFragmentManager, "")
        }


        binding.tvAlbumControlAlbum.setOnClickListener {
            val bundle = bundleOf("albumId" to albumId)
            dismiss()
            findNavController().navigate(
                R.id.action_trackControlFragment_to_albumViewFragment,
                bundle
            )
        }


        trackImage()
    }

    private fun trackImage() {
        viewModel.trackList.observe(viewLifecycleOwner) { it ->
            if (it != null) {
                // Retrieve track image, name, and artist name
                val trackImage =
                    it.map { it.tracks.filter { it.id == id } }.map { it.map { it.image } }
                        .flatten().toString().trim('[', ']')
                val trackName =
                    it.map { it.tracks.filter { it.id == id } }.map { it.map { it.name } }.flatten()
                artistName =
                    it.filter { it.tracks.map { it.id }.contains(id) }.map { it.name }.toString()

                with(binding) {
                    Glide.with(requireContext()).load(trackImage).into(ivTrackImage)
                    tvTrackName.text = trackName.toString().trim('[', ']')
                    tvArtistsName.text = artistName?.trim('[', ']')
                }

                Log.d("user5552", "$trackName $artistName $trackImage")
            }
        }
    }
}
