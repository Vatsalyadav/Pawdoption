/**
 *  This file represents the First Fragment that shows the list of Notes
 *  and a FloatingActionButton to create a new Note
 *  @author Vatsal Yadav - B00893030
 */
package com.yadav.pawdoption.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yadav.pawdoption.R
import com.yadav.pawdoption.adapter.PetListAdapter
import com.yadav.pawdoption.model.ShelterPet
import com.yadav.pawdoption.persistence.FirebaseDatabaseSingleton
import com.yadav.pawdoption.persistence.SheltersDAO

class PetListFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var petListAdapter: PetListAdapter
    private val sheltersDAO = SheltersDAO()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pet_list, container, false)

        activity?.title = "Pets"
        setupRecyclerView(view)

        val fabAddPet = view.findViewById<FloatingActionButton>(R.id.fabAddPet)
        if (FirebaseDatabaseSingleton.getCurrentUserType().equals("PETADOPTER"))
            fabAddPet.visibility = View.GONE
        else
            fabAddPet.setOnClickListener {
                findNavController().navigate(R.id.action_petListFragment_to_uploadAnimalPosting)
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
            val shelterPetList: MutableList<ShelterPet> = mutableListOf()
            for (shelter in it) {
                Log.e("PetList", "dsfds: " + shelter.value.pets)
                for (pet in shelter.value.pets) {
                    if (pet != null) {
                        pet.shelterId = shelter.key
                        pet.shelterName = shelter.value.name.toString()
                        shelterPetList.add(pet)
                    }
                }
            }
            petListAdapter = PetListAdapter(requireContext(), shelterPetList)
            recyclerView.adapter = petListAdapter
            petListAdapter.notifyDataSetChanged()
        }
    }
}