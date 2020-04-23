package com.example.kobenhavn.usecases.event;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.IRemoteRepository;

import io.reactivex.Completable;

public class LeaveEventUC {
    private final ILocalRepository localRepository;
    private final IRemoteRepository remoteRepository;

    public LeaveEventUC(ILocalRepository localRepository, IRemoteRepository remoteRepository) {

        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public Completable RemoveEventFromUser(Event event, User user, String playgroundName){
        return localRepository.deleteEvent(event, user)
                .andThen(remoteRepository.leaveEvent(playgroundName, event.getId(), user.getUsername()));
    }
}
