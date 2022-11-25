package com.yadav.pawdoption.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
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
import com.yadav.pawdoption.model.UserLovedPet
import com.yadav.pawdoption.persistence.FirebaseDatabaseSingleton
import me.relex.circleindicator.CircleIndicator
import java.util.*
import kotlin.collections.HashMap

class PetDetailFragment : Fragment() {

    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: PetDetailImageCorousalAdapter
    lateinit var indicator: CircleIndicator

    var _binding: FragmentPetDetailBinding? = null

    var liked: String? = null;

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPetDetailBinding.inflate(inflater, container, false)

        viewPager = binding.vpPetDetailsImage

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val images: MutableList<String> = mutableListOf()
        images.add("https://firebasestorage.googleapis.com/v0/b/cosmic-kite-278709.appspot.com/o/Screen%20Shot%202022-10-14%20at%205.59.47%20PM.png?alt=media&token=7c161da6-dbe0-4885-8500-b77d24ea1114")
        images.add("https://firebasestorage.googleapis.com/v0/b/cosmic-kite-278709.appspot.com/o/Screen%20Shot%202022-10-15%20at%206.07.25%20PM.png?alt=media&token=b6f38fa9-3144-4ebe-97f0-021c58a892b0")


        images?.let{
            viewPagerAdapter = PetDetailImageCorousalAdapter(requireContext(), it)
            viewPager.adapter = viewPagerAdapter
            indicator = requireView().findViewById(R.id.inPetDetailsImage) as CircleIndicator
            indicator.setViewPager(viewPager)
        }

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapPetDetailsShelterLocation) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        FirebaseDatabaseSingleton.getSheltersReference().child("2001").child("pendingAdoptions").addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot?.getValue() != null) {
                    val paList: HashMap<String, HashMap<String, String>> =
                        snapshot.getValue() as HashMap<String, HashMap<String, String>>
                    for ((key, value) in paList) {

                        //TODO static user id
                        if (value.get("userId").equals("uid1") && value.get("petId").equals("0")) {
                            binding.btnPetDetailsAdopt.isEnabled = false
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("PET DETAILS", "Failed to read value.", error.toException())
            }

        })

        val userRef = FirebaseDatabaseSingleton.getUsersReference().child("uid1")

        userRef.child("lovedPets").get().addOnSuccessListener {
            if(it.getValue() != null) {
                val paList: HashMap<String, HashMap<String, String>> =
                    it.getValue() as HashMap<String, HashMap<String, String>>
                for ((key, value) in paList) {

                    //TODO static user id
                    if (value.get("shelterId").equals("2001") && value.get("petId").equals("0")) {
                        liked = value.get("id")
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
                    UserLovedPet(UUID.randomUUID().toString(), "0", "2001")
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
            val action = PetDetailFragmentDirections.actionPetDetailFragmentToAdoptPetFragment("0", "2001")

            findNavController().navigate(action)
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        val latLng = LatLng(44.65423, -63.625859)
        val markerOptions = MarkerOptions().position(latLng).title("Shelter")
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        googleMap.addMarker(markerOptions)
    }

}