package com.example.kobenhavn.usecases.event;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.remote.IRemoteRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;

public class JoinEventUC {
    private final ILocalRepository localRepository;
    private final IRemoteRepository remoteRepository;

    public JoinEventUC(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public Completable joinEventForUser(String playgroundName, String username, String eventID, ArrayList<Event> events){
        return localRepository.joinEvents(username, events)
                .andThen(remoteRepository.joinUserWithEvent(playgroundName, eventID, username));
    }

}
