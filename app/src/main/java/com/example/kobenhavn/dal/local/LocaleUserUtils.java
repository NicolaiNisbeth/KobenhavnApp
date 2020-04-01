package com.example.kobenhavn.dal.local;

import com.example.kobenhavn.dal.local.model.User;

public class LocaleUserUtils {
    public static User clone(User from, boolean syncPending){
        return new User(
                from.getId(), from.getFirstname(), from.getLastname(),
                from.getUsername(), from.getPassword(), from.getEmail(),
                from.getImagepath(), from.getPhonenumber(), syncPending
        );
    }

    public static User clone(User from, long id){
        return new User(id, from.getFirstname(), from.getLastname(),
                from.getUsername(), from.getPassword(), from.getEmail(),
                from.getImagepath(), from.getPhonenumber(), from.isSyncPending());
    }





}
