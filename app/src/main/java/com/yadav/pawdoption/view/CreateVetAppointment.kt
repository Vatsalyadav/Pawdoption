package com.yadav.pawdoption.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.yadav.pawdoption.R
import com.google.android.material.textfield.TextInputEditText


class CreateVetAppointment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_vet_appointment, container, false)

        val btnCreateSchedule = view.findViewById<Button>(R.id.btnCreateSchedule)
        btnCreateSchedule.setOnClickListener {
            val tiVetName = view.findViewById<TextInputEditText>(R.id.tiVetName)

            val tiPetAge = view.findViewById<TextInputEditText>(R.id.tiVetQualification)

        }

        return view
    }

}