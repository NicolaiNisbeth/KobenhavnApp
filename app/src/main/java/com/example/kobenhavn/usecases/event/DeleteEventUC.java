package com.example.kobenhavn.usecases.event;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;

import io.reactivex.Completable;

public class DeleteEventUC {
    private final ILocalRepository localRepository;

    public DeleteEventUC(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Completable deleteEvent(Event event, User user){
        return localRepository.deleteEvent(event, user);
    }
}
