package com.yadav.pawdoption.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yadav.pawdoption.R
import com.yadav.pawdoption.adapter.DayAppointmentAdapter
import com.yadav.pawdoption.persistence.dayAppointmentList


class DayAppointment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_day_appointment, container, false)
        // tvDummy.text = "Category ${this.arguments?.getInt("position")}"

        /** Get recycler view reference **/
        val rvDayAppointments = view.findViewById<RecyclerView>(R.id.rvDayAppointments)
        val adapter = DayAppointmentAdapter(dayAppointmentList)
        rvDayAppointments.adapter = adapter
        rvDayAppointments.layoutManager = LinearLayoutManager(activity)

        return view
    }

}