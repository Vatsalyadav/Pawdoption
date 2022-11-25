/**
 *  This file represents the the RecyclerView Adapter and ViewHolder for Notes.
 *  @author Vatsal Yadav - B00893030
 */

package com.yadav.pawdoption.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.yadav.pawdoption.R
import com.yadav.pawdoption.model.ShelterPet
import com.yadav.pawdoption.view.PetListFragmentDirections


// Code Reference: https://developer.android.com/develop/ui/views/layout/recyclerview#kotlin

class PetListAdapter(private val context: Context, private val petsList: MutableList<ShelterPet>) :
    RecyclerView.Adapter<PetListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val petNameTextView: TextView
        val shelterNameTextView: TextView
        val viewDetailsTextView: TextView
        val petBreedTextView: TextView
        val petDescTextView: TextView
        val listItemParent: CardView
        val petImageView: ImageView
        val petLoveImage: ImageView
        val petShareImage: ImageView


        init {
            petNameTextView = view.findViewById(R.id.pet_name)
            shelterNameTextView = view.findViewById(R.id.shelter_name)
            viewDetailsTextView = view.findViewById(R.id.view_details)
            petBreedTextView = view.findViewById(R.id.pet_breed)
            petDescTextView = view.findViewById(R.id.pet_description)
            listItemParent = view.findViewById(R.id.list_item_parent)
            petImageView = view.findViewById(R.id.pet_image)
            petLoveImage = view.findViewById(R.id.love_pet)
            petShareImage = view.findViewById(R.id.share_pet)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.pet_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // https://stackoverflow.com/a/35306315
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        viewHolder.petNameTextView.text = petsList[position].name
        viewHolder.shelterNameTextView.text = petsList[position].shelterName
        viewHolder.petBreedTextView.text = petsList[position].breed
        viewHolder.petDescTextView.text = petsList[position].description

        Glide.with(context)
            .load(petsList[position].imageURL[0])
            .centerCrop()
            .placeholder(circularProgressDrawable)
            .into(viewHolder.petImageView);

        viewHolder.itemView.setOnClickListener {
            val navController = Navigation.findNavController(viewHolder.itemView)
            val action = PetListFragmentDirections.actionPetListFragmentToPetDetailFragment(
                petsList[position].shelterId,
                petsList[position].id!!
            )
            navController!!.navigate(action)
        }


        viewHolder.petShareImage.setOnClickListener { view ->
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "I found a pet!")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }

    }

    override fun getItemCount(): Int {
        return petsList.size
    }

}

