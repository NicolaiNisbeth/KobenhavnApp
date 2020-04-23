package com.example.kobenhavn.usecases.subscription;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Playground;

import io.reactivex.Completable;

public class RemoveSubscriptionInDb {
    private final ILocalRepository localRepository;

    public RemoveSubscriptionInDb(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Completable removeSubscription(String username, Playground playground, long id){
        return localRepository.removeSubscription(username, playground, id);
    }
}
