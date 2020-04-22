package com.example.kobenhavn.usecases.event;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.IRemoteRepository;

import java.util.ArrayList;

import io.reactivex.Completable;

public class JoinEventUC {
    private final ILocalRepository localRepository;
    private final IRemoteRepository remoteRepository;

    public JoinEventUC(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    /*
    public Completable joinEventForUser(String playgroundName, String username, String eventID, ArrayList<Event> events){
        return localRepository.joinEvent(username, events)
                .andThen(remoteRepository.joinEvent(playgroundName, eventID, username));
    }

     */

    public Completable joinEventForUser(Event event, User user, String playgroundName) {
        return localRepository.joinEvent(event, user)
                .andThen(remoteRepository.joinEvent(playgroundName, event, user));

    }
}
