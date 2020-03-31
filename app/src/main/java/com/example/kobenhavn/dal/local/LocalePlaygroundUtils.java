package com.example.kobenhavn.dal.local;

import com.example.kobenhavn.dal.local.model.Playground;

public class LocalePlaygroundUtils {
    public static Playground clone(Playground from, boolean syncPending) {
        return new Playground(from.getId(), from.getCommentText(), syncPending);
    }

    public static Playground clone(Playground from, long id){
        return new Playground(id, from.getCommentText(), from.isSyncPending());
    }

}
