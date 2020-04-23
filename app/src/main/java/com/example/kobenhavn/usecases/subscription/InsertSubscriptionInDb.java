package com.example.kobenhavn.usecases.subscription;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Subscription;
import com.example.kobenhavn.dal.local.model.User;

import io.reactivex.Completable;

public class InsertSubscriptionInDb {
    private final ILocalRepository localRepository;

    public InsertSubscriptionInDb(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Completable insertSubscription(User user, Subscription subscription){
        return localRepository.insertSubscription(user, subscription);
    }
}
