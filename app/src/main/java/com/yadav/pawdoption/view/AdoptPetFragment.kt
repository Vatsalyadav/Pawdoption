package com.yadav.pawdoption.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.firebase.ktx.Firebase
import com.yadav.pawdoption.R
import com.yadav.pawdoption.databinding.FragmentAdoptPetBinding
import com.yadav.pawdoption.databinding.FragmentConfirmAdoptionBinding
import com.yadav.pawdoption.model.PendingAdoption
import com.yadav.pawdoption.persistence.FirebaseDatabaseSingleton
import com.yadav.pawdoption.persistence.SheltersDAO
import java.util.*


class AdoptPetFragment : Fragment() {

    var checkBox: CheckBox? = null;

    var _binding: FragmentAdoptPetBinding? = null

    private val binding get() = _binding!!

    val args: AdoptPetFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val userId: String = "uid1";

        val shelterId: String = args.shelterId
        val petId: String = args.petId

        _binding = FragmentAdoptPetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cbAgreeTnC.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.btnAdoptPetSubmit.isEnabled = isChecked
        }

        binding.btnAdoptPetSubmit.setOnClickListener {
            val shelterRef = FirebaseDatabaseSingleton.getSheltersReference().child(args.shelterId)
            shelterRef.get().addOnSuccessListener {

                val pendingAdoption: PendingAdoption = PendingAdoption(UUID.randomUUID().toString(),"uid1", args.petId, "fhedsjkf")

//                if(it.hasChild("pendingAdoptions")){
                    shelterRef.child("pendingAdoptions").child(pendingAdoption.id!!).setValue(pendingAdoption)
//                }

                binding.btnAdoptPetSubmit.isEnabled = false

                val myToast = Toast.makeText(requireContext(),"Your request adoption request has been created successfully, You will be notified about update on request", Toast.LENGTH_SHORT)

//                val myToast = Toast.makeText(applicationContext,"toast message with gravity",Toast.LENGTH_SHORT)
                myToast.setGravity(Gravity.LEFT,200,200)
                myToast.show()
            }
        }

    }
}