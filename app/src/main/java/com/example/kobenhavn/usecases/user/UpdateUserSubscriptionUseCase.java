package com.example.kobenhavn.usecases.user;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.IRemoteRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class UpdateUserSubscriptionUseCase {
    private ILocalRepository localRepository;
    private IRemoteRepository remoteRepository;


    public UpdateUserSubscriptionUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public Completable updateUserSubscriptions(String username, List<Playground> playgrounds){
        localRepository.updateSubscription(username, playgrounds);
        return remoteRepository.updateUserWithSubscriptions(username, playgrounds);
    }
}
