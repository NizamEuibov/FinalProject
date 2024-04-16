package com.example.finalproject.ui.track.fragment

import android.app.Dialog
import android.media.AudioManager
import android.media.MediaPlayer
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
import com.example.finalproject.databinding.FragmentPlayTrackBinding
import com.example.finalproject.ui.track.viewmodel.TrackViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayTrackFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPlayTrackBinding
    private var id: Int? = null
    private val viewModel: TrackViewModel by viewModels()
    private lateinit var artistName: String
    private lateinit var albumName: String
    private lateinit var trackName: String
    private lateinit var image: String
    private val mediaPlayer: MediaPlayer by lazy { MediaPlayer() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id")
        Log.d("IDid", "$id")
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayTrackBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTexts()
        playMusic()
        binding.ib3Point.setOnClickListener {
            val bundle = bundleOf(
                "id" to id
            )
            findNavController().navigate(
                R.id.action_playTrackFragment_to_trackControlFragment, bundle
            )

        }
        binding.ibCancel.setOnClickListener { dismiss() }
        binding.ibShare.setOnClickListener {
            val bundle = bundleOf(
                "id" to id
            )
            findNavController().navigate(
                R.id.action_playTrackFragment_to_trackShareFragment,
                bundle
            )
        }
        binding.ibPause.setOnClickListener {
            pauseMusic()
        }

        binding.ibPlay.setOnClickListener {
            mediaPlayer.start()
            binding.ibPause.visibility=View.VISIBLE
            binding.ibPlay.visibility=View.INVISIBLE
        }

    }


    private fun setTexts() {
        viewModel.trackList.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                artistName =
                    list.filter { tracks ->
                        tracks.tracks.map { tracksId ->
                            tracksId.id
                        }.contains(id)
                    }.map { name ->
                        name.name
                    }.toString()
                        .trim('[', ']')


                albumName =
                    list.map { tracks ->
                        tracks.tracks.filter { tracksId ->
                            tracksId.id == id
                        }
                    }.map { tracks ->
                        tracks.map { albumName -> albumName.albumName }
                    }
                        .flatten().toString().trim('[', ']')


                trackName =
                    list.map { tracks -> tracks.tracks.filter { tracksId -> tracksId.id == id } }
                        .map { tracks -> tracks.map { albumName -> albumName.albumName } }
                        .flatten().toString().trim('[', ']')


                image =
                    list.map { tracks -> tracks.tracks.filter { tracksId -> tracksId.id == id } }
                        .map { tracks -> tracks.map { image -> image.image } }
                        .flatten().toString().trim('[', ']')







                with(binding) {
                    Glide.with(requireContext()).load(image).into(ivTracks)
                    tvArtistName.text = artistName
                    tvAlbumName.text = albumName
                    tvTrackName.text = trackName
                }
            }
        }
    }

    private fun playMusic() {
        viewModel.trackList.observe(viewLifecycleOwner) { list ->
            val audio =
                list?.flatMap { tracks -> tracks.tracks.filter { tracksId -> tracksId.id == id } }
                    ?.map { audio -> audio.audio }
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.reset()
            mediaPlayer.setDataSource(audio?.first())
            mediaPlayer.isLooping = true
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                it.start()
            }
            Log.d("AUDIO1", audio.toString())
        }
    }

    private fun pauseMusic(){
        if (mediaPlayer.isPlaying){
            mediaPlayer.stop()
            binding.ibPause.visibility=View.INVISIBLE
            binding.ibPlay.visibility=View.VISIBLE
        }
    }


}
