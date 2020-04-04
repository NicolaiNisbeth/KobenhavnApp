package com.example.kobenhavn.usecases.user;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.User;

import io.reactivex.Single;

public class GetUserFromDbUseCase {
    private final ILocalRepository localRepository;

    public GetUserFromDbUseCase(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Single<User> getUser(String username){
        return localRepository.getUser(username);
    }
}
