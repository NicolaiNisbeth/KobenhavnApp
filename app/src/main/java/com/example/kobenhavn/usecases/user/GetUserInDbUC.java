package com.example.kobenhavn.usecases.user;

import androidx.lifecycle.LiveData;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.User;

public class GetUserInDbUC {
    private final ILocalRepository localRepository;

    public GetUserInDbUC(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public LiveData<User> getUserLive(String username){
        return localRepository.getUserLive(username);
    }

}
