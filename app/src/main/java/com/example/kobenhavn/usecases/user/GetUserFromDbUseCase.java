package com.example.kobenhavn.usecases.user;

import androidx.lifecycle.LiveData;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.User;

import io.reactivex.Single;

public class GetUserFromDbUseCase {
    private final ILocalRepository localRepository;

    public GetUserFromDbUseCase(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public LiveData<User> getUserLive(String username){
        return localRepository.getUserLive(username);
    }

    public Single<User> getUser(String username){
        return localRepository.getUser(username);
    }
}
