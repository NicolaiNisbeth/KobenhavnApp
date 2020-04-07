package com.example.kobenhavn.usecases.event;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.remote.IRemoteRepository;

public class LeaveEventUseCase {
    private final ILocalRepository localRepository;
    private final IRemoteRepository remoteRepository;

    public LeaveEventUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository) {

        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }
}
