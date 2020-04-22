package com.example.kobenhavn.dal.local;

import com.example.kobenhavn.dal.local.model.User;

public class LocaleUserUtils {
    public static User clone(User from, boolean syncPending){
        return new User(
                from.getId(), from.getFirstname(), from.getLastname(),
                from.getUsername(), from.getPassword(), from.getEmail(),
                from.getImagepath(), from.getStatus(), from.getWebsite(), from.getPhonenumbers(), syncPending,
                from.getEvents());
    }

    public static User clone(User from, String id){
        return new User(id, from.getFirstname(), from.getLastname(),
                from.getUsername(), from.getPassword(), from.getEmail(),
                from.getImagepath(), from.getStatus(), from.getWebsite(), from.getPhonenumbers(), from.isSyncPending(),
                from.getEvents());
    }





}
