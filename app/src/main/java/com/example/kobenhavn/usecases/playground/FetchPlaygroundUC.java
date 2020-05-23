package com.example.kobenhavn.usecases.playground;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.remote.IRemoteRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class FetchPlaygroundUC {
    private ILocalRepository localRepository;
    private IRemoteRepository remoteRepository;

    public FetchPlaygroundUC(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;

    }

    public Completable  fetchPlaygrounds(){
        return remoteRepository.fetchPlaygrounds();
    }

}
