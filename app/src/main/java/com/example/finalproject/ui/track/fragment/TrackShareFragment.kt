package com.example.finalproject.ui.track.fragment

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.data.networkdata.models.UIState
import com.example.finalproject.databinding.FragmentTrackShareBinding
import com.example.finalproject.ui.`object`.ConstVal.ERROR
import com.example.finalproject.ui.track.viewmodel.TrackViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackShareFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTrackShareBinding
    private val viewModel: TrackViewModel by viewModels()
    private var id: Int? = null
    private var idAlbum: Int? = null
    private var sendTrackAudio: String? = null
    private var shareAlbum: String? = null
    private var list: List<DataTypeModel.NameAndImage> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id")
        idAlbum = arguments?.getInt("idAlbum")
        Log.d("Album", "$id")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrackShareBinding.inflate(layoutInflater, container, false)
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
        binding.fabExit.setOnClickListener {
            dismiss()
        }

        binding.ibWhatsapp.setOnClickListener {
            if (id != 0) {
                sendTrackWithWhatsapp(sendTrackAudio!!)
            } else {
                sendTrackWithWhatsapp(shareAlbum!!)
            }
        }

        binding.ibTwitter.setOnClickListener {
            if (id != 0) {
                sendTrackWithTwitter(sendTrackAudio!!)
            } else {
                sendTrackWithTwitter(shareAlbum!!)
            }

        }
        binding.ibMessage.setOnClickListener {
            if (id != 0) {
                sendTrackWithMessage(sendTrackAudio!!)
            } else {
                sendTrackWithMessage(shareAlbum!!)
            }

        }

        binding.ibMore.setOnClickListener {
            if (id != 0) {
                sendTrackWithVia(sendTrackAudio!!)
            } else {
                sendTrackWithVia(shareAlbum!!)
            }
        }

        binding.ibLink.setOnClickListener {
            if (id != 0) {
                copyLink(requireContext(), sendTrackAudio!!)
            } else {
                copyLink(requireContext(), shareAlbum!!)
            }
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
                    if (id != 0) {
                        trackInformation()
                    } else {
                        albumInformation()
                    }
                }

                else -> {
                    UIState.Error(ERROR)
                }
            }
        }
        viewModel.fetchTracks()
    }


    private fun trackInformation() {
        val artistName =
            list.filter { it.tracks.map { it.id }.contains(id) }.map { it.name }.toString()
        Log.d("id121", artistName)
        val trackName =
            list.map { it.tracks.filter { it.id == id } }.map { it.map { it.name } }.flatten()
                .toString()
        val trackImage =
            list.map { it.tracks.filter { it.id == id } }.map { it.map { it.image } }
                .flatten().toString().trim('[', ']')

        sendTrackAudio =
            list.map { it.tracks.filter { it.id == id } }.map { it.map { it.audio } }
                .flatten()
                .toString()

        with(binding) {
            Glide.with(requireContext()).load(trackImage).into(ivTrack)
            tvArtistName.text = artistName.trim('[', ']')
            tvTrackName.text = trackName.trim('[', ']')
        }
    }


    private fun albumInformation() {

        val artistName =
            list.filter { it.tracks.map { it.albumId }.contains(idAlbum) }.map { it.name }
                .toString()
        val albumName =
            list.map { it.tracks.filter { it.albumId == idAlbum } }.map { it.map { it.albumName } }
                .flatten()

        val albumImage =
            list.map { it.tracks.filter { it.albumId == idAlbum } }.map { it.map { it.image } }
                .flatten().toString().trim('[', ']')
        Log.d("Album", albumImage)

        shareAlbum = albumImage


        with(binding) {
            Glide.with(requireContext()).load(albumImage).into(ivTrack)
            tvArtistName.text = artistName.trim('[', ']')
            tvTrackName.text = albumName.first()?.trim('[',']')

        }
    }

    private fun sendTrackWithWhatsapp(link: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, link)
        intent.setPackage("com.whatsapp")
        startActivity(intent)
    }

    private fun sendTrackWithTwitter(link: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, link)
        intent.setPackage("com.twitter.android")
        startActivity(intent)
    }

    private fun sendTrackWithMessage(link: String) {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.putExtra(Intent.EXTRA_TEXT, link)
        sendIntent.type = "text/plain"
        sendIntent.setPackage("messages.android")
        startActivity(sendIntent)
    }

    private fun sendTrackWithVia(link: String) {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.putExtra(Intent.EXTRA_TEXT, link)
        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, "Share via"))

    }

    private fun copyLink(context: Context, link: String) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Link Copied", link)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context, "Link copied to clipboard", Toast.LENGTH_SHORT).show()
    }

}