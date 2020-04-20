package com.example.kobenhavn.usecases.user;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.IRemoteRepository;

import io.reactivex.Completable;
import io.reactivex.Single;

public class UpdateUserUseCase {
    private final ILocalRepository localRepository;
    private final IRemoteRepository remoteRepository;

    public UpdateUserUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public Completable updateUserFields(User user) {
        Single<User> result = localRepository.updateFields(user);
        return result.flatMapCompletable(remoteRepository::sync);

    }
}
