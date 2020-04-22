package com.example.kobenhavn.usecases.event;

import androidx.lifecycle.LiveData;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Event;

import java.util.List;

public class GetEventsInDbUC {
    private final ILocalRepository localRepository;

    public GetEventsInDbUC(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public LiveData<List<Event>> getEventsLive(String username){
        return localRepository.getEventsLiveData(username);
    }
}
