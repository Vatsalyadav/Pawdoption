package com.yadav.pawdoption.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.yadav.pawdoption.model.DayAppointment
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yadav.pawdoption.R
import com.yadav.pawdoption.model.BookedSlot
import com.yadav.pawdoption.persistence.AppointmentDAO
import com.yadav.pawdoption.persistence.PetDAO


class DayAppointmentAdapter(
    private var dayAppointments: List<DayAppointment>,
    private var day: String
) : RecyclerView.Adapter<DayAppointmentAdapter.DayAppointmentViewHolder>() {

    inner class DayAppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cgTimeSlot: ChipGroup = itemView.findViewById(R.id.cgTimeSlot)
        val btnBookAppointment: Button = itemView.findViewById(R.id.btnBookAppointment)
    }

    override fun onBindViewHolder(holder: DayAppointmentViewHolder, position: Int) {
        val daTvShelterDoctor = holder.itemView.findViewById<TextView>(R.id.daTvShelterDoctor)
        val daTvQualification = holder.itemView.findViewById<TextView>(R.id.daTvQualification)

        holder.itemView.apply {
            daTvShelterDoctor.text = "${dayAppointments[position].shelterName} - ${dayAppointments[position].vetName}"
            daTvQualification.text = dayAppointments[position].vetQualification
        }

        holder.cgTimeSlot.setOnCheckedStateChangeListener {
            btnView, isChecked ->
            if (holder.cgTimeSlot.checkedChipId == -1) {
                holder.btnBookAppointment.visibility = View.GONE
            } else {
                holder.btnBookAppointment.visibility = View.VISIBLE
            }
        }


        holder.btnBookAppointment.setOnClickListener {
            MaterialAlertDialogBuilder(holder.btnBookAppointment.context,
                androidx.appcompat.R.style.Animation_AppCompat_Dialog)
                .setMessage("Confirm Appointment")
                .setNegativeButton("Decline") { dialog, which ->
                }
                .setPositiveButton("Accept") { dialog, which ->
                    val appointmentDAO = AppointmentDAO()
                    val timeSlot = holder.itemView.findViewById<Chip>(holder.cgTimeSlot.checkedChipId)
                    val bookedSlot = BookedSlot(
                        day = day,
                        timeSlot = timeSlot.text.toString(),
                    )
                    // TODO: Dynamically pick shelter ID
                    appointmentDAO.bookAppointment("2001", dayAppointments[position].vetId, bookedSlot)
                    Toast.makeText(holder.btnBookAppointment.context, "Slot Booked Successfully", Toast.LENGTH_LONG).show()
                }
                .show()
        }
    }

    /** Inflate the my appointment layout **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayAppointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.day_appointment_item, parent, false)

        return DayAppointmentViewHolder(view)
    }

    /** Get notes count **/
    override fun getItemCount(): Int {
        return dayAppointments.size
    }

}