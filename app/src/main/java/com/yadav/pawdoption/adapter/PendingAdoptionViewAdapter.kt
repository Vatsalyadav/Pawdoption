package com.yadav.pawdoption.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.yadav.pawdoption.databinding.PendingAdoptionBinding
import com.yadav.pawdoption.dataclass.PendingAdoptionData
import com.yadav.pawdoption.model.PendingAdoption
import com.yadav.pawdoption.view.PendingAdoptionFragmentDirections


class PendingAdoptionViewAdapter(val pendingAdoptionList: MutableList<PendingAdoptionData>): RecyclerView.Adapter<PendingAdoptionViewAdapter.ViewHolder>() {

    private lateinit var binding: PendingAdoptionBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = PendingAdoptionBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pendingAdoptionList[position])

        holder.itemView.setOnClickListener {
            val navController = Navigation.findNavController(holder.itemView)

            val action = PendingAdoptionFragmentDirections.actionPendingAdoptionFragmentToConfirmAdoptionFragment(
                pendingAdoptionList[position].pendionAdoption,
                pendingAdoptionList[position].shelterPet!!,
                pendingAdoptionList[position].user!!)

            navController!!.navigate(action)
        }
    }

    override fun getItemCount() = pendingAdoptionList.size

    inner class ViewHolder(pendingAdoptionView: PendingAdoptionBinding) :
        RecyclerView.ViewHolder(pendingAdoptionView.root) {

        fun bind(pendingAdoption: PendingAdoptionData) {
            binding.apply {
                tvPetName.text = pendingAdoption.shelterPet?.name;
                tvAdopterName.text = pendingAdoption.user?.name;
                tvTimestamp.text = pendingAdoption.pendionAdoption.timestamp;
            }
        }
    }
}