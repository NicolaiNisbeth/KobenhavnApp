package com.example.kobenhavn.usecases.user;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.IRemoteRepository;

import io.reactivex.Completable;

public class UpdateUserUseCase {
    private ILocalRepository localRepository;
    private IRemoteRepository remoteRepository;

    public UpdateUserUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public Completable updateUser(User user){
        return localRepository.update(user).flatMapCompletable(remoteRepository::sync);
    }
}
