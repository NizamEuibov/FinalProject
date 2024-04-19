package com.example.finalproject.ui.libraryfragment.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentEditProfileBinding
import com.example.finalproject.ui.extension.TextView.disable
import com.example.finalproject.ui.extension.TextView.enable
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentEditProfileBinding
   private val CAMERA_REQUEST_CODE = 1
    private lateinit var someActivityResultLauncher: ActivityResultLauncher<Intent>



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
        result()

        binding.ibChangeImage.setOnClickListener {
            alertDialog()
            dismiss()
        }
        binding.ibCancel.setOnClickListener {
            dismiss()
        }


        binding.etName.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                        binding.tvSave.enable()
                    }
                    else{
                        binding.tvSave.disable()
                    binding.etName.error="Enter name"

                    }

                }


            override fun afterTextChanged(s: Editable?) {
            }


        })
        binding.tvSave.setOnClickListener {
            changeName()
        }

    }

    private fun alertDialog() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Change Profile Photo")
      val alertDialogItems = arrayOf("Choose Photo" , "Take Photo" , "Remove Photo")
        alertDialog.setItems(alertDialogItems){dialog , which ->
             when(which){
                 0 ->cameraPermissionCheck()
                 1 -> galleryCheck()
             }
        }
        val alert = alertDialog.create()
        alert.show()
    }




    private fun cameraPermissionCheck() {
        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(
                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            if (report.areAllPermissionsGranted()) {
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
            .setMessage(
                "You have turned off permissions required for this feature. " +
                        "it can be enabled it under App settings"
            )
            .setPositiveButton("Go to setting") { _, _ ->

                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun handleActivityResult(requestCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            val bitmap = data?.extras?.get("data") as Bitmap
            binding.civEditProfile.load(bitmap) {
                crossfade(true)
                crossfade(1000)
                transformations(CircleCropTransformation())
            }
        }
        else{
            binding.civEditProfile.load(data?.data){
                crossfade(true)
                crossfade(1000)
                transformations(CircleCropTransformation())
            }
        }
    }

    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        someActivityResultLauncher.launch(intent)
    }

    private fun galleryCheck() {
       Dexter.withContext(requireContext())
           .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
           .withListener(object :PermissionListener{
               override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                  gallery()
               }

               override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                  alertDialog()
               }

               override fun onPermissionRationaleShouldBeShown(
                   p0: PermissionRequest?,
                   p1: PermissionToken?
               ) {
                   alertDialog()
               }

           }).onSameThread().check()
    }


    private fun gallery(){
        val intent =Intent(Intent.ACTION_PICK)
        intent.type="image/*"
      someActivityResultLauncher.launch(intent
      )

    }

    private fun result(){
        someActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val requestCode = result.resultCode
                handleActivityResult(requestCode, data)
            }

        }
    }


    private fun changeName (){
    val name = binding.etName.text.toString()
        val bundle= bundleOf(
            "name" to name
        )
  findNavController().navigate(R.id.action_editProfileFragment_to_librarySettingFragment,bundle)
    }
}