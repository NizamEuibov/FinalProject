package com.example.finalproject.ui.libraryfragment.fragment

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.finalproject.databinding.FragmentEditProfileBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val CAMERA_REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false)

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

        binding.ibChangeImage.setOnClickListener {
            alertDialog()
            dismiss()
        }
        binding.ibCancel.setOnClickListener {
            dismiss()
        }

    }

    private fun alertDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Change Profile Photo")
        alertDialog.setPositiveButton("Choose Photo") { dialog, which ->
        }

        alertDialog.setNegativeButton("Take Photo") { dialog, _ ->
            cameraPermissionCheck()

        }
        alertDialog.setNeutralButton("Remove Photo") { dialog, which ->

        }

        val alert = alertDialog.create()
        alert.show()
    }

    private fun cameraPermissionCheck(){
   Dexter.withContext(context)
       .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
           Manifest.permission.CAMERA).withListener(
               object :MultiplePermissionsListener{
                   override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                       report?.let {
                           if (report.areAllPermissionsGranted()){
                               camera()

                           }
                       }
                   }

                   override fun onPermissionRationaleShouldBeShown(
                       p0: MutableList<PermissionRequest>?,
                       p1: PermissionToken?
                   ) {
                       showDialog()
                   }

               }
           ).onSameThread().check()
    }

    private fun showDialog() {
        AlertDialog.Builder(context)
            .setMessage("You have turned off permissions required for this feature. " +
                    "it can be enabled it under App settings")
            .setPositiveButton("Go to setting"){_,_ ->

                val intent =Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                startActivity(intent)
            }
            .setNegativeButton("Cancel"){dialog,_ ->
                dialog.dismiss()
            }.show()
    }

    private fun camera() {
        if (isAdded){
        val intent =Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,CAMERA_REQUEST_CODE)}
    }

}