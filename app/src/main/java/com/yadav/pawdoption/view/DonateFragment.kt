package com.yadav.pawdoption.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.navArgs
import com.yadav.pawdoption.R
import com.yadav.pawdoption.model.Shelter
import kotlinx.android.synthetic.main.sheltercard.view.*


class DonateFragment : Fragment() {

    val args: DonateFragmentArgs by navArgs()
    lateinit var tvShelterTitle:TextView
    lateinit var tvShelterDescription:TextView
    lateinit var bDonate:Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_donate, container, false)
        val shelter: Shelter = args.shelter

         tvShelterTitle = view.findViewById(R.id.tvShelterTitle)
        tvShelterDescription = view.findViewById(R.id.tvShelterDescription)
        bDonate = view.findViewById(R.id.payButton)

        tvShelterTitle.text = shelter.name?.uppercase()
        tvShelterDescription.text = shelter.description
        bDonate.setOnClickListener {
            Toast.makeText(activity?.applicationContext,"Thank you for you donation.",Toast.LENGTH_LONG).show()
        }


        return view
    }


}