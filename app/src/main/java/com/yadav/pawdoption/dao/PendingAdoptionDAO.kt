package com.yadav.pawdoption.dao

import com.yadav.pawdoption.model.PendingAdoption


class PendingAdoptionDAO : IPendingAdoptionDAO {

    override fun getAdoptionList(shelterId: String): MutableList<PendingAdoption>{

        var adoptionList: MutableList<PendingAdoption> = mutableListOf();

        val pendingAdoption1: PendingAdoption = PendingAdoption(
            "1",
            "1",
            "1",
            true,
            true,
            true,
            haveDogHouse = true,
            false
        );

        val pendingAdoption2: PendingAdoption = PendingAdoption(
            "2",
            "1",
            "2",
            true,
            true,
            true,
            true,
            false
        );

        adoptionList.add(pendingAdoption1);
        adoptionList.add(pendingAdoption2);

        return adoptionList;
    }
}