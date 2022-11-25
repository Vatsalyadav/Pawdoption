package com.yadav.pawdoption.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.yadav.pawdoption.R
import com.yadav.pawdoption.model.Shelter


class DonateFragment : Fragment() {

    val args: DonateFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val shelter: Shelter = args.shelter



        return inflater.inflate(R.layout.fragment_donate, container, false)
    }


}