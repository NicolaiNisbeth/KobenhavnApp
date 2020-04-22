package com.example.kobenhavn.usecases.event;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;

import io.reactivex.Completable;

public class UpdateEventUC {
    private final ILocalRepository localRepository;

    public UpdateEventUC(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Completable updateEvent(Event event, User user){
        return localRepository.updateEvent(event, user);
    }
}
