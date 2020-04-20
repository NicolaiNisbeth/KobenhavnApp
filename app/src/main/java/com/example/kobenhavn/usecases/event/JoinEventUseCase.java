package com.example.kobenhavn.usecases.event;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.IRemoteRepository;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;

public class JoinEventUseCase {
    private final ILocalRepository localRepository;
    private final IRemoteRepository remoteRepository;

    public JoinEventUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public Completable joinEvent(String playgroundName, String username, String eventID, ArrayList<Event> events){
        localRepository.joinEvent(username, events);
        return remoteRepository.updateUserWithEvent(playgroundName, eventID, username);
    }

}
