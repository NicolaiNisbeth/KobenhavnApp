package com.example.kobenhavn.usecases.playground;


import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.remote.IRemoteRepository;

import io.reactivex.Completable;
import io.reactivex.Single;
import timber.log.Timber;

public class AddPlaygroundUseCase {
    private final ILocalRepository localRepository;
    private final IRemoteRepository remoteRepository;

    public AddPlaygroundUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public Completable addPlayground(String text){
        // step 1: save locally
        Timber.e("Step 1: Save playground locally");
        Single<Playground> p = localRepository.add(text);

        // step 2: sync with remote
        Timber.e("Step 2: Sync with remote");
        Completable completable = p.flatMapCompletable(remoteRepository::sync);
        return completable;
    }
}
