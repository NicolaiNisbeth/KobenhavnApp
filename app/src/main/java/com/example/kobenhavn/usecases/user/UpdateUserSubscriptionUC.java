package com.example.kobenhavn.usecases.user;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.remote.IRemoteRepository;

import java.util.List;

import io.reactivex.Completable;

public class UpdateUserSubscriptionUC {
    private ILocalRepository localRepository;
    private IRemoteRepository remoteRepository;


    public UpdateUserSubscriptionUC(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public Completable updateUserSubscriptionsLocally(String username, List<Playground> playgrounds){
        return localRepository.updatePlaygroundSubscriptions(username, playgrounds);
    }

}
