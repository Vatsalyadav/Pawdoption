package com.yadav.pawdoption.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import com.yadav.pawdoption.R
import com.google.android.material.textfield.TextInputEditText


class UploadAnimalPosting : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upload_animal_posting, container, false)

        val btnUploadPhoto = view.findViewById<Button>(R.id.btnUploadPhoto)
        btnUploadPhoto.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    pickImageFromGallery()
                }
            } else {
                pickImageFromGallery()
            }
        }

        val btnSubmit = view.findViewById<Button>(R.id.btnAnimalPostingSubmit)
        btnSubmit.setOnClickListener {
            val tiPetName = view.findViewById<TextInputEditText>(R.id.tiPetName)
            val tiPetAge = view.findViewById<TextInputEditText>(R.id.tiPetAge)

            val rgBreed = view.findViewById<RadioGroup>(R.id.rgBreed)
            val selectedRadioButtonId = rgBreed.checkedRadioButtonId
            val selectedRadioButton = view.findViewById<RadioButton>(selectedRadioButtonId)
            val tiPetDescription = view.findViewById<TextInputEditText>(R.id.tiPetDescription)
        }

        return view
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val image = view?.findViewById<ImageView>(R.id.ivPetUpload)
            image?.setImageURI(data?.data)
        }
    }

}