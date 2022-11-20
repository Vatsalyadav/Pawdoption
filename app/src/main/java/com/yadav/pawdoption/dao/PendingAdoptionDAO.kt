package com.yadav.pawdoption.dao

import com.yadav.pawdoption.model.PendingAdoption


class PendingAdoptionDAO : IPendingAdoptionDAO {

    override fun getAdoptionList(shelterId: String): MutableList<PendingAdoption>{

        var adoptionList: MutableList<PendingAdoption> = mutableListOf();

        val pendingAdoption1: PendingAdoption = PendingAdoption(
            "1",
            "1",
            "1",
            "ftuyk",
            true,
            true,
            false
        );

        val pendingAdoption2: PendingAdoption = PendingAdoption(
            "2",
            "1",
            "2",
            "ftuyk",
            true,
            true,
            false
        );

        adoptionList.add(pendingAdoption1);
        adoptionList.add(pendingAdoption2);

        return adoptionList;
    }
}