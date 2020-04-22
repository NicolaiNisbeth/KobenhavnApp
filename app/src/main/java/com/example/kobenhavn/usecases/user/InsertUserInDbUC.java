package com.example.kobenhavn.usecases.user;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.User;

import io.reactivex.Single;

public class InsertUserInDbUC {
    private ILocalRepository localRepository;

    public InsertUserInDbUC(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Single<User> addUser(User user){
        return localRepository.insertUser(user);
    }
}
