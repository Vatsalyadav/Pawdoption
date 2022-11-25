package com.yadav.pawdoption.persistence

import com.yadav.pawdoption.model.PendingAdoption
import java.util.*


class PendingAdoptionDAOMock : IPendingAdoptionDAO {

    override fun getAdoptionList(shelterId: String): MutableList<PendingAdoption>{

        var adoptionList: MutableList<PendingAdoption> = mutableListOf();

        val pendingAdoption1: PendingAdoption = PendingAdoption(
            UUID.randomUUID().toString(),
            "1",
            "1",
            "1",
//            true,
//            true,
//            true,
//            true,
//            false
        );

        val pendingAdoption2: PendingAdoption = PendingAdoption(
            UUID.randomUUID().toString(),
            "2",
            "1",
            "2",
//            true,
//            true,
//            true,
//            true,
//            false
        );

        adoptionList.add(pendingAdoption1);
        adoptionList.add(pendingAdoption2);

        return adoptionList;
    }
}