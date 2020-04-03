package com.example.kobenhavn.dal.local;

import com.example.kobenhavn.dal.local.model.Playground;

public class LocalePlaygroundUtils {
    public static Playground clone(Playground from, boolean syncPending) {
        return new Playground(from.getId(), from.getName(), from.getImagepath(),
                from.isToiletPossibilities(), from.isHasSoccerField(), from.getStreetName(),
                from.getStreetNumber(), from.getCommune(), from.getZipCode(), syncPending);
    }

    public static Playground clone(Playground from, String id){
        return new Playground(id, from.getName(), from.getImagepath(),
                from.isToiletPossibilities(), from.isHasSoccerField(), from.getStreetName(),
                from.getStreetNumber(),from.getCommune(),from.getZipCode(), from.isSyncPending());
    }

}
