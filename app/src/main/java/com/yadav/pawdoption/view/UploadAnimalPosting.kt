package com.yadav.pawdoption.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.yadav.pawdoption.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yadav.pawdoption.model.ShelterPet
import com.yadav.pawdoption.persistence.PetDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class UploadAnimalPosting : Fragment() {

    var curFile: Uri? = null
    val imageRef = Firebase.storage.reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upload_animal_posting, container, false)

        activity?.title = "Pet Posting"

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
            // Pet name field reference
            val tilPetName = view.findViewById<TextInputLayout>(R.id.tilPetName)
            val tiPetName = view.findViewById<TextInputEditText>(R.id.tiPetName)

            // Pet age field reference
            val tilPetAge = view.findViewById<TextInputLayout>(R.id.tilPetAge)
            val tiPetAge = view.findViewById<TextInputEditText>(R.id.tiPetAge)

            // Radio group reference
            val rgBreed = view.findViewById<RadioGroup>(R.id.rgBreed)
            val selectedRadioButtonId = rgBreed.checkedRadioButtonId
            val selectedRadioButton = view.findViewById<RadioButton>(selectedRadioButtonId)

            // Pet description field reference
            val tiPetDescription = view.findViewById<TextInputEditText>(R.id.tiPetDescription)
            val tilPetDescription = view.findViewById<TextInputLayout>(R.id.tilPetDescription)

            // Error text references
            val tvErrorPhoto = view.findViewById<TextView>(R.id.tvErrorPhoto)
            val tvErrorRadio = view.findViewById<TextView>(R.id.tvErrorRadio)

            // Validate
            val petName = tiPetName.text.toString()
            val petAge = tiPetAge.text.toString()
            val petDescription = tiPetDescription.text.toString()

            if (!isValid(petName, tilPetName, petAge, tilPetAge, petDescription,
                         tilPetDescription, tvErrorPhoto, tvErrorRadio, selectedRadioButtonId)) {
                return@setOnClickListener
            }

            // TODO: Upload image - Dynamically generate a file name
            // TODO: Dynamically pick the shelter ID
            val pet = ShelterPet(
                name = petName,
                age = petAge.toInt(),
                breed = selectedRadioButton.text.toString(),
                description = petDescription,
            )
            writeToDatabase("myImage", "2001", pet)

            findNavController().navigate(R.id.action_uploadAnimalPosting_to_petListFragment)
        }

        return view
    }

    private fun isValid(petName: String, tilPetName: TextInputLayout,
                        petAge: String, tilPetAge: TextInputLayout,
                        petDescription: String, tilPetDescription: TextInputLayout,
                        tvErrorPhoto: TextView, tvErrorRadio: TextView, checkedRadioButtonId: Int): Boolean {
        var isValid = true

        if (petName.equals("")) {
            tilPetName.setError("Name cannot be empty")
            isValid = false
        } else {
            tilPetName.setError(null)
        }

        if (petAge.equals("")) {
            tilPetAge.setError("Age cannot be empty")
            isValid = false
        } else {
            tilPetAge.setError(null)
        }

        if (petDescription.equals("")) {
            tilPetDescription.setError("Description cannot be empty")
            isValid = false
        } else {
            tilPetDescription.setError(null)
        }

        if (curFile == null) {
            tvErrorPhoto.text = "Choose a photo"
            isValid = false
        } else {
            tvErrorPhoto.text = ""
        }

        if (checkedRadioButtonId == -1) {
            tvErrorRadio.text = "Choose a breed"
            isValid = false
        } else {
            tvErrorRadio.text = ""
        }

        return isValid
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

            data?.data?.let {
                curFile = it
                image?.setImageURI(it)
            }

        }
    }

    private fun writeToDatabase(fileName: String, shelterId: String, pet: ShelterPet) = CoroutineScope(Dispatchers.IO).launch {
        try {
            curFile?.let {
                val downloadUrl = imageRef.child("images/$fileName").putFile(it).await().storage.downloadUrl.await()
                pet.imageURL.add(downloadUrl.toString())
                val petDAO = PetDAO()
                petDAO.postPet(shelterId, pet)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Successfully added pet", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}