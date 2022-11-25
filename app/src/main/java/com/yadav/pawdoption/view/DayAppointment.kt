package com.yadav.pawdoption.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.yadav.pawdoption.R
import com.yadav.pawdoption.adapter.DayAppointmentAdapter
import com.yadav.pawdoption.adapter.PetListAdapter
import com.yadav.pawdoption.model.DayAppointment
import com.yadav.pawdoption.persistence.SheltersDAO
import com.yadav.pawdoption.persistence.dayAppointmentList


class DayAppointment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var dayAppointmentAdapter: DayAppointmentAdapter
    private val sheltersDAO = SheltersDAO()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_day_appointment, container, false)
        val map: HashMap<Int, String> = hashMapOf(0 to "Monday", 1 to "Tuesday", 2 to "Wednesday", 3 to "Thursday", 4 to "Friday")
        setupRecyclerView(view, map)

        return view
    }

    private fun setupRecyclerView(view: View, map: HashMap<Int, String>) {
        dayAppointmentAdapter = DayAppointmentAdapter(mutableListOf())
        linearLayoutManager = LinearLayoutManager(activity)
        val recyclerView: RecyclerView = view.findViewById(R.id.rvDayAppointments)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = dayAppointmentAdapter
        sheltersDAO.getShelters().observe(viewLifecycleOwner
        ) {
            val dayAppointments: MutableList<DayAppointment> = mutableListOf()
            for (shelter in it) {
                for (vet in shelter.value.veterinarians) {
                    if (vet != null && map[this.arguments?.getInt("position")] in vet.days) {
                        dayAppointments.add(com.yadav.pawdoption.model.DayAppointment(
                            shelterName = shelter.value.name.toString(),
                            vetName = vet.name.toString(),
                            vetQualification = vet.qualification.toString()
                        ))
                    }
                }
            }

            dayAppointmentAdapter = DayAppointmentAdapter(dayAppointments)
            recyclerView.adapter = dayAppointmentAdapter
            dayAppointmentAdapter.notifyDataSetChanged()
        }
    }

}