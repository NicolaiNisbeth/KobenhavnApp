package com.example.kobenhavn.usecases.event;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Event;

import java.util.List;

import io.reactivex.Completable;

public class InsertEventsInDbUC {
    private final ILocalRepository localRepository;

    public InsertEventsInDbUC(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Completable insertEvents(List<Event> events){
        return localRepository.clearAll().andThen(localRepository.insertEvents(events));
    }

}
