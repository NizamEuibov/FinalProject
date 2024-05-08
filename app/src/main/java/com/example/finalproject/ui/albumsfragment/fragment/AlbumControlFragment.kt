package com.example.finalproject.ui.albumsfragment.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.data.networkdata.models.UIState
import com.example.finalproject.databinding.FragmentAlbumControlBinding
import com.example.finalproject.ui.albumsfragment.viewmodel.AlbumViewModel
import com.example.finalproject.ui.`object`.ConstVal.ERROR
import com.example.finalproject.ui.track.fragment.TrackShareFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumControlFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAlbumControlBinding
    private val viewModel: AlbumViewModel by viewModels()
    private var list: List<DataTypeModel.NameAndImage> = emptyList()
    private var id: Int? = null
    private var artistId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumControlBinding.inflate(layoutInflater, container, false)
        return (binding.root)
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

        binding.tbClose.setOnClickListener {
            dismiss()
        }
        binding.tvAlbumControlShare.setOnClickListener {
            val bundle = bundleOf(
                "idAlbum" to id
            )
            val trackControlFragment = TrackShareFragment().apply {
                arguments = bundle
            }
            trackControlFragment.show(childFragmentManager, "")
        }
        binding.tvAlbumControlArtist.setOnClickListener {
            val bundle = bundleOf(
                "id" to artistId
            )
            findNavController().navigate(R.id.artistViewFragment, bundle)
            dismiss()
        }
    }


    private fun init() {
        viewModel.albumsList.observe(viewLifecycleOwner) { data ->
            when (data) {
                is UIState.Loading -> {
                    binding.progressBar.visibility =
                        if (data.isLoading) View.VISIBLE else View.GONE
                }

                is UIState.Data -> {
                    list = data.data
                    albumInformation()
                }

                else -> {
                    UIState.Error(ERROR)
                }
            }
        }
    }


    private fun albumInformation() {
        artistId =
            list.filter { it.albums.map { it.id }.contains(id) }.map { it.id }.toString()
                .trim('[', ']').toInt()

        val albumImage =
            list.map { it.albums.filter { it.id == id } }.map { it.map { it.image } }
                .flatten().toString().trim('[',']')

        val artistName =
            list.filter { it.albums.map { it.id }.contains(id) }.map { it.name }.toString()

        val albumName =
            list.map { it.albums.filter { it.id == id } }.map { it.map { it.name } }.flatten()

        with(binding) {
            tvAlbumName.text = albumName.toString().trim('[', ']')
            tvArtistsName.text = artistName.trim('[', ']')
            Glide.with(requireContext()).load(albumImage).placeholder(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_exit_background
                )
            ).into(ivAlbumControl)
        }
    }

}