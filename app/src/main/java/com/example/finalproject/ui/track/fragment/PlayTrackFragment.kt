package com.example.finalproject.ui.track.fragment

import android.app.Dialog
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SeekBar
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentPlayTrackBinding
import com.example.finalproject.services.MusicPlayerService
import com.example.finalproject.ui.extension.ImageButton.disable
import com.example.finalproject.ui.extension.ImageButton.enable
import com.example.finalproject.ui.track.viewmodel.TrackViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayTrackFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPlayTrackBinding
    private var id: Int? = null
    private var albumName: String? = null
    private val viewModel: TrackViewModel by viewModels()
    private val mediaPlayer: MediaPlayer by lazy { MediaPlayer() }
    private var index: Int? = null
    private var isShuffleEnabled=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id")
        albumName = arguments?.getString("albumName")
        Log.d("IDid", "$id")
        Log.d("id121", "$albumName")
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
        id?.let { setTexts(it) }
        id?.let { playMusic(it) }
        binding.ib3Point.setOnClickListener {
            val bundle = bundleOf(
                "id" to id
            )
            findNavController().navigate(
                R.id.action_playTrackFragment_to_trackControlFragment, bundle
            )

        }
        binding.ibCancel.setOnClickListener {
            dismiss()
        }

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
            val intent = Intent(requireContext(), MusicPlayerService::class.java)
            intent.action= MusicPlayerService.Actions.PAUSE.toString()
           requireContext().startService(intent)
        }

        binding.ibPlay.setOnClickListener {
            id?.let { it1 -> playMusic(it1) }
            binding.ibPause.visibility = View.VISIBLE
            binding.ibPlay.visibility = View.INVISIBLE
        }
        binding.ibNext.setOnClickListener {
            binding.ibPause.visibility = View.VISIBLE
            binding.ibPlay.visibility = View.INVISIBLE
            nextMusic()
        }

        binding.ibPrevious.setOnClickListener {
            Log.d("id121", "$index")
            binding.ibPause.visibility = View.VISIBLE
            binding.ibPlay.visibility = View.INVISIBLE
            previousMusic()

        }

        binding.ibShuffle.setOnClickListener {
            isShuffleEnabled = !isShuffleEnabled
            Log.d("Shuffle","$isShuffleEnabled")
            if (isShuffleEnabled) {
                binding.ibShuffle.enable()
            } else {
                binding.ibShuffle.disable()
            }
        }




        binding.skbTrack.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }


    private fun setTexts(id: Int) {
        Log.d("IDid", "$id")
        viewModel.trackList.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                val artistName =
                    list.filter { tracks ->
                        tracks.tracks.map { tracksId ->
                            tracksId.id
                        }.contains(id)
                    }.map { name ->
                        name.name
                    }.toString()
                        .trim('[', ']')


                val albumName =
                    list.map { tracks ->
                        tracks.tracks.filter { tracksId ->
                            tracksId.id == id
                        }
                    }.map { tracks ->
                        tracks.map { albumName -> albumName.albumName }
                    }
                        .flatten().toString().trim('[', ']')


                val trackName =
                    list.map { tracks -> tracks.tracks.filter { tracksId -> tracksId.id == id } }
                        .map { tracks -> tracks.map { albumName -> albumName.name } }
                        .flatten().toString().trim('[', ']')


                val image =
                    list.map { tracks -> tracks.tracks.filter { tracksId -> tracksId.id == id } }
                        .map { tracks -> tracks.map { image -> image.image } }
                        .flatten().toString().trim('[', ']')

                val trackList =
                    list.map { album -> album.tracks.filter { it.albumName == albumName } }
                        .map { tracks -> tracks.map { id -> id.id } }.flatten()



                index = trackList.indexOf(id)
                with(binding) {
                    Glide.with(requireContext()).load(image).into(ivTracks)
                    tvArtistName.text = artistName
                    tvAlbumName.text = albumName
                    tvTrackName.text = trackName
                }
            }
        }
    }

    private fun playMusic(id: Int) {
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
                binding.skbTrack.max = mediaPlayer.duration
                seekBarConnect()
                displayMusicTime( mediaPlayer.duration)
            }
            mediaPlayer.setOnCompletionListener {
                nextMusic()
            }
            audio?.first()?.let { sendIntentToService(it) }
            Log.d("AUDIO1", audio.toString())
        }

    }

    private fun pauseMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            binding.ibPause.visibility = View.INVISIBLE
            binding.ibPlay.visibility = View.VISIBLE
        }

    }


    private fun nextMusic() {
        mediaPlayer.stop()
        viewModel.trackList.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                val trackList =
                    list.map { album -> album.tracks.filter { it.albumName == albumName } }
                        .map { tracks -> tracks.map { id -> id.id } }.flatten()
                if (index!! < trackList.size - 1) {
                    index = index!! + 1
                    trackList[index!!]?.let { playMusic(it) }
                    trackList[index!!]?.let { setTexts(it) }
                    Log.d("trackList", "$index")
                } else if (index!! == trackList.size - 1) {
                    trackList[0]?.let {
                        playMusic(it)
                        setTexts(it)
                    }
                    index = 0
                }

            }
        }
    }


    private fun previousMusic() {
        mediaPlayer.stop()
        viewModel.trackList.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                val trackList =
                    list.map { album -> album.tracks.filter { it.albumName == albumName } }
                        .map { tracks -> tracks.map { id -> id.id } }.flatten()
                Log.d("id121", "$trackList")
                if (index!! > 0) {
                    index = index!! - 1
                    trackList[index!!]?.let {
                        playMusic(it)
                        setTexts(it)
                    }

                } else if (index == 0) {
                    trackList[0]?.let {
                        playMusic(it)
                        setTexts(it)
                    }
                    index = trackList.size
                    Log.d("id121", "$index")
                }

            }
        }
    }


    private fun shuffleMusic() {
        viewModel.trackList.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                val trackList =
                    list.filter { album ->
                        album.tracks.map { it.albumName }.contains(albumName)
                    }
                        .map { it.tracks.map { id -> id.id } }.flatten()
                trackList.shuffled()
            }
        }
    }

    private fun seekBarConnect() {
        val handler = android.os.Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                binding.skbTrack.progress = mediaPlayer.currentPosition
                currentMusicTime(mediaPlayer.currentPosition)
                handler.postDelayed(this, 1000)
            }
        }, 1000)

    }

    private fun displayMusicTime( totalDuration: Int) {
        val totalMinutes = totalDuration / 1000 / 60
        val totalSeconds = (totalDuration / 1000) % 60
        val totalTimeString = String.format("%02d:%02d", totalMinutes, totalSeconds)
        binding.tvEndDuration.text = totalTimeString
    }

    private fun currentMusicTime(currentTime:Int){
        val currentMinutes = currentTime / 1000 / 60
        val currentSeconds = (currentTime / 1000) % 60
        val currentTimeString = String.format("%02d:%02d", currentMinutes, currentSeconds)
        binding.tvStartDuration.text = currentTimeString


    }


    private fun repeat() {
        mediaPlayer.setOnCompletionListener {
            index = index!! + 1
            nextMusic()
        }
    }

    private fun sendIntentToService(audio:String){
        val intent = Intent(requireContext(), MusicPlayerService::class.java)
        intent.putExtra("audio",audio)
        intent.action= MusicPlayerService.Actions.PLAY.toString()
        requireContext().startService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}



