/**
 *  This file represents the First Fragment that shows the list of Notes
 *  and a FloatingActionButton to create a new Note
 *  @author Vatsal Yadav - B00893030
 */
package com.yadav.pawdoption.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yadav.pawdoption.R
import com.yadav.pawdoption.adapter.PetListAdapter
import com.yadav.pawdoption.model.Shelter
import com.yadav.pawdoption.model.ShelterPet
import com.yadav.pawdoption.persistence.FirebaseDatabaseSingleton
import com.yadav.pawdoption.persistence.SheltersDAO
import com.yadav.pawdoption.persistence.UsersDAO


class PetListFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var petListAdapter: PetListAdapter
    private val sheltersDAO = SheltersDAO()
    private val usersDAO = UsersDAO()
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pet_list, container, false)

        activity?.title = "Pets"
        setupRecyclerView(view)

        val fabAddPet = view.findViewById<FloatingActionButton>(R.id.fabAddPet)

        Log.e(
            "PetListFrag",
            "FirebaseDatabaseSingleton.getCurrentUser() = " + FirebaseDatabaseSingleton.getCurrentUser()
        )
        if (FirebaseDatabaseSingleton.getCurrentUser() == null) {
            FirebaseDatabaseSingleton.setCurrentUser()
            Log.e(
                "PetListFrag",
                "FirebaseDatabaseSingleton.getCurrentUser()" + FirebaseDatabaseSingleton.getCurrentUid()
            )
            usersDAO.setCurrentUserTypeByUid(FirebaseDatabaseSingleton.getCurrentUid())
            usersDAO.getCurrentUserTypeByUid().observe(viewLifecycleOwner) {
                Log.e("PetListFrag", "usersDAO.getCurrentUserTypeByUid() updated")
//                if (it.equals("petAdopter"))
//                    fabAddPet.visibility = View.GONE
//                else {
//                    fabAddPet.visibility = View.VISIBLE
//                }
                setBottomNavigation(it)
            }
        }

        if (FirebaseDatabaseSingleton.getCurrentUserType().uppercase().equals("PETADOPTER"))
            fabAddPet.visibility = View.GONE
        else {
            fabAddPet.visibility = View.VISIBLE
        }

        fabAddPet.setOnClickListener {
            val intent = Intent(requireContext(), UploadPet::class.java)
            startActivity(intent);
        }

        return view
    }

    private fun setupRecyclerView(view: View) {
        petListAdapter = PetListAdapter(requireContext(), mutableListOf())
        linearLayoutManager = LinearLayoutManager(activity)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = petListAdapter
        sheltersDAO.getShelters().observe(
            viewLifecycleOwner
        ) {

            if (FirebaseDatabaseSingleton.getCurrentUserType().uppercase().equals("PETADOPTER"))
                petListAdapter = PetListAdapter(requireContext(), getAllPets(it))
            else
                petListAdapter = PetListAdapter(requireContext(), getCurrentShelterPets(it))
            recyclerView.adapter = petListAdapter
            petListAdapter.notifyDataSetChanged()
        }
    }

    private fun getCurrentShelterPets(it: HashMap<String, Shelter>): MutableList<ShelterPet> {
        val currentShelterPetList: MutableList<ShelterPet> = mutableListOf()
        if (it.get(FirebaseDatabaseSingleton.getCurrentUid())?.pets != null) {
            for (pet in it.get(FirebaseDatabaseSingleton.getCurrentUid())?.pets!!) {
                if (pet != null) {
                    pet.shelterId =
                        it.get(FirebaseDatabaseSingleton.getCurrentUid())!!.id.toString()
                    pet.shelterName =
                        it.get(FirebaseDatabaseSingleton.getCurrentUid())!!.id.toString()
                    currentShelterPetList.add(pet)
                }
            }
        }
        return currentShelterPetList
    }

    private fun getAllPets(it: HashMap<String, Shelter>): MutableList<ShelterPet> {
        val allPetList: MutableList<ShelterPet> = mutableListOf()
        Log.e("mainac", it.toString())
        for (shelter in it) {
            for (pet in shelter.value.pets) {
                if (pet != null) {
                    pet.shelterId = shelter.key
                    pet.shelterName = shelter.value.name.toString()
                    allPetList.add(pet)
                }
            }
        }
        return allPetList
    }

    fun setBottomNavigation(userType: String) {
        Log.e("MainActivity", "userType: " + userType)
        bottomNavigationView = if (userType == "petAdopter")
            activity?.findViewById(R.id.bottom_nav_pet_owner)!!
        else
            activity?.findViewById(R.id.bottom_nav_shelter)!!
        bottomNavigationView.visibility = View.VISIBLE
        bottomNavigationView.selectedItemId = R.id.pets
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.pets -> {
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.petListFragment)
                    true
                }
                R.id.shelters -> {
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.mapsFragment)
                    true
                }
                R.id.vet -> {
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.bookAppointment)
                    true
                }
//                TODO: Add others too
                else -> true
            }

        }
    }

}