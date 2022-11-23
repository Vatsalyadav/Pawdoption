package com.yadav.pawdoption.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yadav.pawdoption.R
import com.yadav.pawdoption.dataclass.ShelterData

class ShelterAdapter(private val shelterList: ArrayList<ShelterData>):
    RecyclerView.Adapter<ShelterAdapter.ViewHolder>() {

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val shelterName:TextView = itemView.findViewById(R.id.shelter_name)
        val shelterAddress:TextView = itemView.findViewById(R.id.shelter_address)
        val shelterDescription:TextView = itemView.findViewById(R.id.shelter_description)
        val donateButton:Button = itemView.findViewById(R.id.donate_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.donate_shelter_card,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = shelterList[position]

        holder.shelterName.text = currentItem.shelterName
        holder.shelterAddress.text = currentItem.shelterAddress
        holder.shelterDescription.text = currentItem.shelterDescription

    }

    override fun getItemCount(): Int {
        return shelterList.size
    }
}