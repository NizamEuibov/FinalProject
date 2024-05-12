package com.example.finalproject.ui.track.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
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
import com.example.finalproject.data.localdatabase.TrackEntity
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.data.networkdata.models.UIState
import com.example.finalproject.databinding.FragmentPlayTrackBinding
import com.example.finalproject.services.MusicPlayerService
import com.example.finalproject.ui.`object`.ConstVal.ERROR
import com.example.finalproject.ui.`object`.SharedPrefs
import com.example.finalproject.ui.track.viewmodel.SendTrackToRepo
import com.example.finalproject.ui.track.viewmodel.TrackViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayTrackFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPlayTrackBinding
    private var id: Int? = null
    private var albumName: String? = null
    private val sendTrackToRepo: SendTrackToRepo by viewModels()
    private val viewModel: TrackViewModel by viewModels()
    private val mediaPlayer: MediaPlayer by lazy { MediaPlayer() }
    private var index: Int? = null
    private var isShuffleEnabled = false
    private var isRepeatEnabled = false
    private var job: Job? = null
    private var isLikeEnabled = false
    private var trackList: List<Int?> = emptyList()
    private var trackIdList: List<Int?> = emptyList()
    private var list: List<DataTypeModel.NameAndImage> = emptyList()
    private var userId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id")
        albumName = arguments?.getString("albumName")
        userId = SharedPrefs.getUserId("UserId")
        Log.d("id12", "$id")
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
        init()
        iconVisibility()
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
            intent.action = MusicPlayerService.Actions.PAUSE.toString()
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
            toggleShuffle()
        }

        binding.ibRepeat.setOnClickListener {
            repeatTracks()
        }


        binding.ibLike.setOnClickListener {
            isLikeEnabled = !isLikeEnabled
            if (isLikeEnabled) {
                binding.ibLike.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
                likeTrack()
            } else {
                binding.ibLike.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                deleteLikedTrack()
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

        binding.tvMotion2.setOnClickListener {
            findNavController().navigate(R.id.action_playTrackFragment_to_forBluetoothFragment)
        }
    }


    private fun init() {
        viewModel.trackList.observe(viewLifecycleOwner) { data ->
            when (data) {
                is UIState.Loading -> {
                    binding.progressBar.visibility =
                        if (data.isLoading) View.VISIBLE else View.GONE
                }

                is UIState.Data -> {
                    list = data.data!!
                    trackList =
                        list.map { album -> album.tracks.filter { it.albumName == albumName } }
                            .map { tracks -> tracks.map { id -> id.id } }.flatten()
                    id?.let { setTexts(it) }
                    id?.let { playMusic(it) }
                }

                else -> {
                    UIState.Error(ERROR)
                }
            }
        }
        viewModel.fetchTracks()
    }

    private fun setTexts(id: Int) {
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

    private fun playMusic(id: Int) {
        val audio =
            list.flatMap { tracks -> tracks.tracks.filter { tracksId -> tracksId.id == id } }
                .map { audio -> audio.audio }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.reset()
        mediaPlayer.setDataSource(audio.first())
        mediaPlayer.isLooping = true
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            it.start()
            binding.skbTrack.max = mediaPlayer.duration
            seekBarConnect()
            displayMusicTime(mediaPlayer.duration)
        }
        mediaPlayer.setOnCompletionListener {
            nextMusic()
        }
//        audio.first()?.let { sendIntentToService(it) }
//        Log.d("AUDIO1", audio.toString())
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
        trackIdList = if (isShuffleEnabled) {
            trackList.shuffled()
        } else {
            trackList
        }

        if (index!! < trackIdList.size - 1) {
            index = index!! + 1
            trackIdList[index!!]?.let { playMusic(it) }
            trackIdList[index!!]?.let { setTexts(it) }
            Log.d("trackList", "$trackIdList")
        } else if (isRepeatEnabled) {
            trackIdList[0]?.let {
                playMusic(it)
                setTexts(it)
            }
            index = 0
        }

    }


    private fun previousMusic() {
        mediaPlayer.stop()
        trackIdList = if (isShuffleEnabled) {
            trackList.shuffled()
        } else {
            trackList
        }
        if (index!! > 0) {
            index = index!! - 1
            trackIdList[index!!]?.let {
                playMusic(it)
                setTexts(it)
            }

        } else if (index == 0) {
            trackIdList[0]?.let {
                playMusic(it)
                setTexts(it)
            }
            index = trackIdList.size
            Log.d("id121", "$index")
        }

    }

    private fun shuffleMusic() {
        isShuffleEnabled = if (isShuffleEnabled) {
            binding.ibShuffle.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP)
            true
        } else {
            binding.ibShuffle.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            false
        }
    }

    private fun seekBarConnect() {
        job?.cancel()
        job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                updateSeekBar()
                delay(1000)
            }
        }
    }

    private fun updateSeekBar() {
        try {
            if (mediaPlayer.isPlaying) {
                binding.skbTrack.progress = mediaPlayer.currentPosition
                currentMusicTime(mediaPlayer.currentPosition)
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    private fun displayMusicTime(totalDuration: Int) {
        val totalMinutes = totalDuration / 1000 / 60
        val totalSeconds = (totalDuration / 1000) % 60
        val totalTimeString = String.format("%02d:%02d", totalMinutes, totalSeconds)
        binding.tvEndDuration.text = totalTimeString
    }

    private fun currentMusicTime(currentTime: Int) {
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

    private fun sendIntentToService(audio: String) {
        val intent = Intent(requireContext(), MusicPlayerService::class.java)
        intent.putExtra("audio", audio)
        intent.action = MusicPlayerService.Actions.PLAY.toString()
        requireContext().startService(intent)
    }


    private fun toggleShuffle() {
        isShuffleEnabled = !isShuffleEnabled
        shuffleMusic()
    }

    private fun repeatTracks() {
        isRepeatEnabled = !isRepeatEnabled

        if (isRepeatEnabled) {
            binding.ibRepeat.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP)
        } else {
            binding.ibRepeat.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        }
    }

    private fun likeTrack() {
        val listTracks = list.map { it.tracks.filter { it.id == id } }.flatten()
        val id = listTracks.map { it.id }.toString().trim('[', ']').toInt()
        val name = listTracks.map { it.name }.toString().trim('[', ']')
        val audio = listTracks.map { it.audio }.toString().trim('[', ']')
        val image = listTracks.map { it.image }.toString().trim('[', ']')
        val track = TrackEntity(id,userId, name, image, audio)
        Log.d("Tracks5", "$listTracks")
        sendTrackToRepo.sendTrackToRepo(track)
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
                    isLikeEnabled = true
                } else {
                    binding.ibLike.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                    isLikeEnabled = false
                }
            }
        }
    }


    private fun deleteLikedTrack() {
        userId?.let {  sendTrackToRepo.deleteTrackFromDatabase(it)}
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }


}