package com.example.kobenhavn.usecases.user;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.User;

import io.reactivex.Single;
import timber.log.Timber;

public class AddUserToDbUseCase {
    private ILocalRepository localRepository;

    public AddUserToDbUseCase(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Single<User> addUser(User user){
        return localRepository.add(user);
    }
}
