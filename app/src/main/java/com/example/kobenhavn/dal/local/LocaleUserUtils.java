package com.example.kobenhavn.dal.local;

import com.example.kobenhavn.dal.local.model.User;

import io.reactivex.Single;

public class LocaleUserUtils {
    public static User clone(User from, boolean syncPending){
        return new User(
                from.getId(), from.getFirstname(), from.getLastname(),
                from.getUsername(), from.getPassword(), from.getEmail(),
                from.getImagepath(), from.getPhonenumber(), syncPending,
                from.getSubscribedPlaygrounds(), from.getEvents()
        );
    }

    public static User clone(User from, String id){
        return new User(id, from.getFirstname(), from.getLastname(),
                from.getUsername(), from.getPassword(), from.getEmail(),
                from.getImagepath(), from.getPhonenumber(), from.isSyncPending(),
                from.getSubscribedPlaygrounds(), from.getEvents());
    }





}
