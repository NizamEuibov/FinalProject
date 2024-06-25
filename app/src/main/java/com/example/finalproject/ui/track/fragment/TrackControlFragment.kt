package com.example.finalproject.ui.track.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.data.localdatabase.TrackEntity
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.data.networkdata.models.UIState
import com.example.finalproject.databinding.FragmentTrackControlBinding
import com.example.finalproject.ui.`object`.ConstVal.ERROR
import com.example.finalproject.ui.`object`.SharedPrefs
import com.example.finalproject.ui.track.viewmodel.SendTrackToRepo
import com.example.finalproject.ui.track.viewmodel.TrackViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackControlFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTrackControlBinding
    private val viewModel: TrackViewModel by viewModels()
    private val sendTrackToRepo: SendTrackToRepo by viewModels()
    private var trackList: List<DataTypeModel.Tracks> = emptyList()
    private var id: Int? = null
    private var userId: Int? = null
    private var artistName: String? = null
    private var albumId: Int? = null
    private var artistId: Int? = null
    private var isButton = false
    private var list: List<DataTypeModel.NameAndImage> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id")
        albumId = arguments?.getInt("albumId")
        userId = SharedPrefs.getUserId("UserId")
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
        init()
        iconVisibility()

        binding.tbClose.setOnClickListener {
            dismiss()
        }

        binding.ibShare.setOnClickListener {
            val bundle = bundleOf("id" to id)
            val trackControlFragment = TrackShareFragment().apply {
                arguments = bundle
            }
            trackControlFragment.show(childFragmentManager, "")
        }


        binding.ibViewAlbum.setOnClickListener {
            val bundle = bundleOf("albumId" to albumId)
            findNavController().navigate(R.id.albumViewFragment, bundle)
            dismiss()
        }
        binding.ibViewArtist.setOnClickListener {
            val bundle = bundleOf(
                "id" to artistId
            )
            findNavController().navigate(R.id.artistViewFragment, bundle)
            dismiss()
        }

        binding.ibLike.setOnClickListener {
            isButton = !isButton

            if (isButton) {
                binding.ibLike.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
                sendTrack()
            } else {
                binding.ibLike.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                deleteMusicFromDatabase()
            }
        }
    }

    private fun init() {
        viewModel.trackList.observe(viewLifecycleOwner) { data ->
            when (data) {
                is UIState.Loading -> {
                    binding.progressBar.isVisible = data.isLoading
                    binding.controlGroup.visibility =
                        if (data.isLoading) View.INVISIBLE else View.VISIBLE
                }

                is UIState.Data -> {
                    list = data.data!!
                    trackImage()
                }

                else -> {
                    UIState.Error(ERROR)
                }
            }

        }
        viewModel.fetchTracks()
    }

    private fun trackImage() {
        trackList = list.map { it.tracks.filter { it.id == id } }.flatten()
        artistId =
            list.filter { it.tracks.map { it.id }.contains(id) }.map { it.id }.toString()
                .trim('[', ']').toInt()
        val trackImage =
            list.map { it.tracks.filter { it.id == id } }.map { it.map { it.image } }
                .flatten().toString().trim('[', ']')
        val trackName =
            list.map { it.tracks.filter { it.id == id } }.map { it.map { it.name } }.flatten()
        artistName =
            list.filter { it.tracks.map { it.id }.contains(id) }.map { it.name }.toString()

        with(binding) {
            Glide.with(requireContext()).load(trackImage).into(ivTrackImage)
            tvTrackName.text = trackName.toString().trim('[', ']')
            tvArtistsName.text = artistName?.trim('[', ']')
        }

        Log.d("user5552", "$trackName $artistName $trackImage")
    }


    private fun sendTrack() {
        val id = trackList.map { it.id }.toString().trim('[', ']').toInt()
        val name = trackList.map { it.name }.toString().trim('[', ']')
        val audio = trackList.map { it.audio }.toString().trim('[', ']')
        val image = trackList.map { it.image }.toString().trim('[', ']')
        val track = TrackEntity(id, userId, name, image, audio)
        sendTrackToRepo.sendTrackToRepo(track)
    }


    private fun deleteMusicFromDatabase() {
        userId?.let { sendTrackToRepo.deleteTrackFromDatabase(it) }
    }

    private fun iconVisibility() {
        userId?.let {
            sendTrackToRepo.getLikedTracks(it).observe(viewLifecycleOwner) { list ->
                val userIdList = list?.map { userId -> userId.userId }
                val tracksIdList = list?.map { id -> id.number }

                if (userIdList?.contains(userId) == true &&
                    tracksIdList?.contains(id) == true
                ) {
                    binding.ibLike.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
                    isButton = true
                } else {
                    binding.ibLike.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                    isButton = false
                }
            }
        }
    }
}
