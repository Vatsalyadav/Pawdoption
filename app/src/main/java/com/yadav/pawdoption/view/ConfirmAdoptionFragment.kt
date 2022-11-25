package com.yadav.pawdoption.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.yadav.pawdoption.R
import com.yadav.pawdoption.databinding.FragmentConfirmAdoptionBinding
import com.yadav.pawdoption.model.PendingAdoption
import com.yadav.pawdoption.model.ShelterPet
import com.yadav.pawdoption.model.User
import kotlin.String

class ConfirmAdoptionFragment : Fragment() {

    var _binding: FragmentConfirmAdoptionBinding? = null

    var pendingAdoption: PendingAdoption? = null;
    var shelterPet: ShelterPet? = null;
    var user: User? = null;
    var image: String? = null;

    val args: ConfirmAdoptionFragmentArgs by navArgs()

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        pendingAdoption = arguments?.getParcelable<PendingAdoption>("pendingAdoption")
//
//        shelterPet = arguments?.getParcelable<ShelterPet>("shelterPet")
//
//        user = arguments?.getParcelable<User>("user")

//        https://developer.android.com/guide/navigation/navigation-pass-data


        pendingAdoption = args.pendingAdoption
        shelterPet = args.shelterPet
        user = args.user

        image = args.petImage

        _binding = FragmentConfirmAdoptionBinding.inflate(inflater, container, false)

        binding.apply {
            Picasso.with(requireContext()).load(image).into(ivConfirmAdoptionPetImage)
            tvConfirmAdoptionPetName.text = shelterPet?.name
            tvConfirmAdoptionPetBreed.text = shelterPet?.breed
            tvConfirmAdoptionPetAge.text = shelterPet?.age.toString() + " years old"
            tvConfirmAdoptionPetDescription.text = shelterPet?.description

            tvConfirmAdoptionAdopterName.text = user?.name
            tvConfirmAdoptionAdopterMobileNumber.text = user?.mobileNumber
            tvConfirmAdoptionAdopterAddress.text = user?.address
        }

        return binding.root
//        return inflater.inflate(R.layout.fragment_confirm_adoption, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfirmAdopterApprove.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, user?.email)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Approval of adoption request")
            intent.putExtra(Intent.EXTRA_TEXT, "Hi, " +
                    "Your request for the pet adoption is approved." +
                    "You can visit the shelter and complete the remaining procedure." +
                    "Thanks")
            intent.data = Uri.parse("mailto:")
            intent.type = "text/plain"
            try {
                //start email intent
                startActivity(Intent.createChooser(intent, "Choose Email Client..."))
            }
            catch (e: Exception){
                //if any thing goes wrong for example no email client application or any exception
                //get and show exception message
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }

    }

}