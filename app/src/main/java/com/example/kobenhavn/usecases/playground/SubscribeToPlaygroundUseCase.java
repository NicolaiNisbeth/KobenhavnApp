package com.example.kobenhavn.usecases.playground;


import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.remote.IRemoteRepository;

import io.reactivex.Completable;
import io.reactivex.Single;
import timber.log.Timber;

public class SubscribeToPlaygroundUseCase {
    private final ILocalRepository localRepository;
    private final IRemoteRepository remoteRepository;

    public SubscribeToPlaygroundUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public Completable subscribeTo(Playground playground){
        //return localRepository.subscribeTo(playground);
        return null;
    }
}
