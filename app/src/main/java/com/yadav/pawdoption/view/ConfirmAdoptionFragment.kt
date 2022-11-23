package com.yadav.pawdoption.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yadav.pawdoption.R
import com.yadav.pawdoption.databinding.FragmentPendingAdoptionsBinding
import com.yadav.pawdoption.model.PendingAdoption

class ConfirmAdoptionFragment : Fragment() {

    var _binding: FragmentPendingAdoptionsBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val args = arguments?.getParcelable<PendingAdoption>("PendingAdoption")

        return inflater.inflate(R.layout.fragment_confirm_adoption, container, false)
    }

}