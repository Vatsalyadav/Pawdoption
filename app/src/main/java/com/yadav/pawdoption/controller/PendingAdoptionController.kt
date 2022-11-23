package com.yadav.pawdoption.controller

import androidx.recyclerview.widget.RecyclerView
import com.yadav.pawdoption.adapter.PendingAdoptionViewAdapter
import com.yadav.pawdoption.persistence.PendingAdoptionDAO

class PendingAdoptionController(
    val pendingAdoptionViewAdapter: RecyclerView.Adapter<PendingAdoptionViewAdapter.ViewHolder>,
    val pendingAdoptionDAO: PendingAdoptionDAO
) : IPendingAdoptionController {


}