package com.example.kobenhavn.dal.local;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;

public class LocaleUtils {
    public static User cloneUser(User from, boolean syncPending){
        return new User(
                from.getId(), from.getFirstname(), from.getLastname(),
                from.getUsername(), from.getPassword(), from.getEmail(),
                from.getImagepath(), from.getStatus(), from.getWebsite(), from.getPhonenumbers(), syncPending, from.getEvents());
    }

    public static User cloneUser(User from, String id){
        return new User(id, from.getFirstname(), from.getLastname(),
                from.getUsername(), from.getPassword(), from.getEmail(),
                from.getImagepath(), from.getStatus(), from.getWebsite(), from.getPhonenumbers(), from.isSyncPending(), from.getEvents());
    }

    public static Event cloneEvent(Event from, String eventID, String username){
        return new Event(eventID, from.getName(), username, from.getImagepath(), from.getSubtitle(), from.getDescription(),
                from.getParticipants(), from.getPlaygroundName(), from.getDetails(), from.isSyncPending());
    }

    public static Event cloneEvent(Event from, String username, boolean syncPending){
        return new Event(from.getId(), username, from.getName(), from.getImagepath(), from.getSubtitle(), from.getDescription(),
                from.getParticipants(), from.getPlaygroundName(), from.getDetails(), syncPending);
    }

}
