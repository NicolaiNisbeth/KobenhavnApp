package com.example.kobenhavn.usecases.user;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class UpdateUserSubscriptionInDbUseCase {
    private ILocalRepository localRepository;


    public UpdateUserSubscriptionInDbUseCase(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Completable updateUserSubscriptions(String username, List<Playground> playgrounds){
        return localRepository.updateSubscription(username, playgrounds);
    }
}
