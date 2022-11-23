/**
 *  This file represents the First Fragment that shows the list of Notes
 *  and a FloatingActionButton to create a new Note
 *  @author Vatsal Yadav - B00893030
 */
package com.yadav.pawdoption.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yadav.pawdoption.R
import com.yadav.pawdoption.adapter.PetListAdapter

class PetListFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var petListAdapter: PetListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pet_list, container, false)

        activity?.title = "Pets"
//        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // Code Reference 3: https://www.kodeco.com/1560485-android-recyclerview-tutorial-with-kotlin

//        petListAdapter = PetListAdapter(NotesListSingleton.getNotes())
        setupRecyclerView(view)

        return view
    }

    private fun setupRecyclerView(view: View) {
        linearLayoutManager = LinearLayoutManager(activity)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = petListAdapter
    }
}