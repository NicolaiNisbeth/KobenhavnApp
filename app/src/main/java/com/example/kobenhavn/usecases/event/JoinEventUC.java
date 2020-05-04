package com.example.kobenhavn.usecases.event;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.IRemoteRepository;

import io.reactivex.Completable;

public class JoinEventUC {
    private final ILocalRepository localRepository;
    private final IRemoteRepository remoteRepository;

    public JoinEventUC(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public Completable joinEventForUser(Event event, User user, String playgroundName) {
        return localRepository.joinEvent(event, user)
                .andThen(remoteRepository.joinEvent(playgroundName, event, user));

    }

    public Completable joinEventForUser(Event event, User user){
        return localRepository.joinEvent(event, user);
    }
}
