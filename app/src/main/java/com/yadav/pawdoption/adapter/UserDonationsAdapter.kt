package com.yadav.pawdoption.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.yadav.pawdoption.R
import com.yadav.pawdoption.model.Shelter
import com.yadav.pawdoption.model.UserDonation
import com.yadav.pawdoption.view.DonateListDirections
import com.yadav.pawdoption.view.PendingAdoptionFragmentDirections
import com.yadav.pawdoption.view.UserDonations

// Code Reference: https://developer.android.com/develop/ui/views/layout/recyclerview#kotlin

class UserDonationsAdapter(private val context: Context, private val userdonations: ArrayList<UserDonation>) :
    RecyclerView.Adapter<UserDonationsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDonateAmount: TextView
        val tvDonatedToShelter: TextView
        val tvDonationTimeStamp: TextView


        init {
            tvDonateAmount = view.findViewById(R.id.tvShelterName)
            tvDonatedToShelter = view.findViewById(R.id.tvShelterAddress)
            tvDonationTimeStamp = view.findViewById(R.id.tvShelterDescription)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.user_donations_card, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.tvDonateAmount.text = userdonations[position].amount.toString() + " CAD"
        viewHolder.tvDonatedToShelter.text = userdonations[position].shelterID
        viewHolder.tvDonationTimeStamp.text = userdonations[position].timestamp


    }

    override fun getItemCount(): Int {
        return userdonations.size
    }

}

