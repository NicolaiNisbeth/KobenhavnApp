package com.example.kobenhavn.usecases.playground;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.remote.IRemoteRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import timber.log.Timber;

public class FetchPlaygroundsUseCase {
    private ILocalRepository localRepository;
    private IRemoteRepository remoteRepository;

    public FetchPlaygroundsUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;

    }

    public Completable fetchPlaygrounds(){
        // fetch from remote source
        Timber.e("Step 1: Fetch playgrounds from remote source");
        return remoteRepository.fetchPlaygrounds();
    }

}
