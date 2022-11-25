package com.yadav.pawdoption.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.yadav.pawdoption.R
import com.yadav.pawdoption.adapter.PetDetailImageCorousalAdapter
import com.yadav.pawdoption.databinding.FragmentPetDetailBinding
import com.yadav.pawdoption.model.PendingAdoption
import com.yadav.pawdoption.model.Shelter
import com.yadav.pawdoption.model.ShelterPet
import com.yadav.pawdoption.model.UserLovedPet
import com.yadav.pawdoption.persistence.FirebaseDatabaseSingleton
import com.yadav.pawdoption.persistence.SheltersDAO
import me.relex.circleindicator.CircleIndicator
import java.util.*
import kotlin.collections.HashMap

class PetDetailFragment : Fragment() {

    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: PetDetailImageCorousalAdapter
    lateinit var indicator: CircleIndicator

    var _binding: FragmentPetDetailBinding? = null
    var liked: String? = null;
    var shelter: Shelter? = null;
    var pet: ShelterPet? = null;

    var userId: String = "uid1"
    var petId: String = "0"
    var shelterId: String = "2001"

    private val binding get() = _binding!!

    val args: PetDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPetDetailBinding.inflate(inflater, container, false)

        viewPager = binding.vpPetDetailsImage

        shelterId = args.shelterId
        petId = args.petId

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO shelter id hard code
        FirebaseDatabaseSingleton.getSheltersReference().child(shelterId).get().addOnSuccessListener { shelterDataSnapshot ->
            if(shelterDataSnapshot.getValue() != null){

                shelter = shelterDataSnapshot.getValue(Shelter::class.java)

                shelter?.let { s ->
                    //TODO pet id hardcode
                    FirebaseDatabaseSingleton.getSheltersReference().child(shelterId)
                        .child("pets").child(petId).get()
                        .addOnSuccessListener { petDataSnapshot ->
                            if(petDataSnapshot.getValue() != null){
                                pet = petDataSnapshot.getValue<ShelterPet>()
                                pet?.imageURL?.let{
                                    viewPagerAdapter = PetDetailImageCorousalAdapter(requireContext(), it)
                                    viewPager.adapter = viewPagerAdapter
                                    indicator = requireView().findViewById(R.id.inPetDetailsImage) as CircleIndicator
                                    indicator.setViewPager(viewPager)
                                }

                                binding.apply {
                                    tvPetDetailsPetName.text = pet?.name
                                    tvPetDetailsPetBreed.text = pet?.breed
                                    tvPetDetailsPetAge.text = pet?.age.toString() + " years old"
                                    tvPetDetailsPetDescription.text = pet?.description
                                    tvPetDetailsShelterName.text = shelter?.name
                                    tvPetDetailsShelterAddress.text = shelter?.address
                                    tvPetDetailsShelterDescription.text = shelter?.description
                                }

                                val mapFragment =
                                    childFragmentManager.findFragmentById(R.id.mapPetDetailsShelterLocation) as SupportMapFragment?
                                mapFragment?.getMapAsync(callback)
                            }
                        }
                }
            }
        }



        FirebaseDatabaseSingleton.getSheltersReference().child(shelterId).child("pendingAdoptions").addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot?.getValue() != null) {
                    val paList: HashMap<String, HashMap<String, String>> =
                        snapshot.getValue() as HashMap<String, HashMap<String, String>>
                    for ((key, value) in paList) {

                        //TODO static user id
                        if (value.get("userId").equals(userId) && value.get("petId").equals(petId)) {
                            binding.btnPetDetailsAdopt.isEnabled = false
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("PET DETAILS", "Failed to read value.", error.toException())
            }

        })

        val userRef = FirebaseDatabaseSingleton.getUsersReference().child(userId)

        userRef.child("lovedPets").get().addOnSuccessListener {
            if(it.getValue() != null) {

                for(ls in it.children){
                    val lovedPet: UserLovedPet? = ls.getValue(UserLovedPet::class.java)
                    if(lovedPet?.shelterId!!.equals(shelter) && lovedPet?.petId.equals(petId)){
                        liked = lovedPet?.id
                        binding.ivPetDetailsLikePet.setImageResource(R.drawable.ic_round_love_24)
                    }
                }

                if (liked.isNullOrBlank()) {
                    binding.ivPetDetailsLikePet.setImageResource(R.drawable.ic_round_love_black_24)
                }


            }

        }

        binding.ivPetDetailsLikePet.setOnClickListener {
            if (liked.isNullOrBlank()) {
                val userLovedPet: UserLovedPet =
                    UserLovedPet(UUID.randomUUID().toString(), petId, shelterId)
                userRef.child("lovedPets").child(userLovedPet.id!!).setValue(userLovedPet);
                liked = userLovedPet.id
                binding.ivPetDetailsLikePet.setImageResource(R.drawable.ic_round_love_24)
            } else {
                userRef.child("lovedPets").child(liked!!).removeValue()
                binding.ivPetDetailsLikePet.setImageResource(R.drawable.ic_round_love_black_24)
                liked = null;
            }
        }



        binding.btnPetDetailsAdopt.setOnClickListener {


            //TODO replace the static data here
            val action = PetDetailFragmentDirections.actionPetDetailFragmentToAdoptPetFragment(petId, shelterId)

            findNavController().navigate(action)
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        val latLng = LatLng(shelter?.latitude!!, shelter?.longitude!!)
        val markerOptions = MarkerOptions().position(latLng).title("Shelter")
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        googleMap.addMarker(markerOptions)
    }

}