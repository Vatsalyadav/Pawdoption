package com.yadav.pawdoption.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yadav.pawdoption.R
import com.yadav.pawdoption.adapter.MyAppointmentAdapter
import com.yadav.pawdoption.persistence.myAppointmentList


class MyAppointment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_appointment, container, false)

        /** Get recycler view reference **/
        val rvMyAppointments = view.findViewById<RecyclerView>(R.id.rvMyAppointments)
        val adapter = MyAppointmentAdapter(myAppointmentList)
        rvMyAppointments.adapter = adapter
        rvMyAppointments.layoutManager = LinearLayoutManager(activity)

        return view
    }
}