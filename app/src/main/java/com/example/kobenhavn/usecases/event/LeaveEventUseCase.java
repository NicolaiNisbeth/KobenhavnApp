package com.example.kobenhavn.usecases.event;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.remote.IRemoteRepository;

import java.util.ArrayList;

import io.reactivex.Completable;

public class LeaveEventUseCase {
    private final ILocalRepository localRepository;
    private final IRemoteRepository remoteRepository;

    public LeaveEventUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository) {

        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public Completable RemoveEventFromUser(String playgroundName, String username, String eventID, ArrayList<Event> events){
        return localRepository.removeEventFromUser(username, events)
                .andThen(remoteRepository.removeEventFromUser(playgroundName, eventID, username));
    }
}
