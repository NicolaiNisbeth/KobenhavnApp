package com.example.kobenhavn.usecases.user;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.User;

import io.reactivex.Completable;

public class UpdateUserInDbUseCase {
    private ILocalRepository localRepository;

    public UpdateUserInDbUseCase(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Completable updateUserInDB(User user){
        return localRepository.update(user);
    }
}
