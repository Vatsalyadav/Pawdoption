/**
 *  This file represents the First Fragment that shows the list of Notes
 *  and a FloatingActionButton to create a new Note
 *  @author Vatsal Yadav - B00893030
 */
package com.yadav.pawdoption.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yadav.pawdoption.R
import com.yadav.pawdoption.adapter.PetListAdapter
import com.yadav.pawdoption.databinding.FragmentUserProfileBinding
import com.yadav.pawdoption.model.Shelter
import com.yadav.pawdoption.model.ShelterPet
import com.yadav.pawdoption.model.UserLovedPet
import com.yadav.pawdoption.persistence.FirebaseDatabaseSingleton
import com.yadav.pawdoption.persistence.SheltersDAO
import com.yadav.pawdoption.persistence.UsersDAO


class PetListFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var petListAdapter: PetListAdapter
    private val sheltersDAO = SheltersDAO()
    private val usersDAO = UsersDAO()
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var searchView: SearchView
    private var petsList: MutableList<ShelterPet> = mutableListOf()

    //added button but was removed
    private var _binding: FragmentUserProfileBinding? = null

    private val binding get() = _binding!!

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pet_list, container, false)

        activity?.title = "Pets"

        searchView = view.findViewById(R.id.searchView)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filterList(newText)
                }
                return false
            }
        })

        setupRecyclerView(view)

        val fabAddPet = view.findViewById<FloatingActionButton>(R.id.fabAddPet)


        if (FirebaseDatabaseSingleton.getCurrentUser() == null) {
            FirebaseDatabaseSingleton.setCurrentUser()

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

//        var b = view.findViewById<Button>(R.id.sign_out_button).setOnClickListener {
//            Firebase.auth.signOut()
//            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
//                .navigate(R.id.loginFragment)
//        }
        return view
    }

    private fun filterList(text: String) {
        var filteredList: MutableList<ShelterPet> = mutableListOf()
        for (pet in petsList) {
            if (pet.breed?.lowercase()?.contains(text.lowercase()) == true) {
                filteredList.add(pet)
            }
        }

        petListAdapter.setFilteredList(filteredList)
    }

    private fun setupRecyclerView(view: View) {
        petListAdapter = PetListAdapter(requireContext(), mutableListOf())
        linearLayoutManager = LinearLayoutManager(activity)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = petListAdapter
        sheltersDAO.getShelters().observe(
            viewLifecycleOwner
        ) { item ->

            if (FirebaseDatabaseSingleton.getCurrentUserType().uppercase().equals("PETADOPTER"))
                usersDAO.getLovedPetsByUid(FirebaseDatabaseSingleton.getCurrentUid()).observe(viewLifecycleOwner) {
                    Log.e("LobvedPets", "it: " + it)
                    petsList = getAllPets(item, it)
                    petListAdapter = PetListAdapter(requireContext(), petsList)
                    recyclerView.adapter = petListAdapter
                    petListAdapter.notifyDataSetChanged()
                }
            else
                    petsList = getCurrentShelterPets(item)
            petListAdapter = PetListAdapter(requireContext(), petsList)
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

    private fun getAllPets(it: HashMap<String, Shelter>, lovedPetsList: HashMap<String,UserLovedPet>): MutableList<ShelterPet> {
        val allPetList: MutableList<ShelterPet> = mutableListOf()
        for (shelter in it) {
            for (pet in shelter.value.pets) {
                if (pet != null) {
                    pet.shelterId = shelter.key
                    pet.shelterName = shelter.value.name.toString()
                    pet.lovedPetsList = lovedPetsList
                    allPetList.add(pet)
                }
            }
        }
        return allPetList
    }

    fun setBottomNavigation(userType: String) {
        Log.e("PetList", "userType: " + userType)
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
                        .navigate(R.id.createVetAppointment)
                    true
                }
                R.id.profile -> {
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.userProfileFragment)
                    true
                }

                R.id.pending -> {
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.pendingAdoptionFragment)
                    true
                }
//                TODO: Add others too
                else -> true
            }

        }
    }

}