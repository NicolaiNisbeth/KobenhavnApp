package com.example.kobenhavn.usecases.subscription;

import androidx.lifecycle.LiveData;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Subscription;

import java.util.List;

public class GetSubscriptionsInDbUC {
    private ILocalRepository localRepository;

    public GetSubscriptionsInDbUC(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public LiveData<List<Subscription>> getSubscriptionsFromDb(String username){
        return localRepository.getSubscriptionsLiveData(username);
    }
}
