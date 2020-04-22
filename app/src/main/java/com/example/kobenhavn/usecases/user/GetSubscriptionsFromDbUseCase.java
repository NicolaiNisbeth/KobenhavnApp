package com.example.kobenhavn.usecases.user;

import androidx.lifecycle.LiveData;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Subscriptions;

public class GetSubscriptionsFromDbUseCase {
    private ILocalRepository localRepository;

    public GetSubscriptionsFromDbUseCase(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public LiveData<Subscriptions> getSubscriptionsFromDb(String username){
        return localRepository.getSubscriptionsLive(username);
    }
}
