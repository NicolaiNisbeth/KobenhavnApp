package com.example.kobenhavn.usecases.user;

import com.example.kobenhavn.dal.remote.IRemoteRepository;

import io.reactivex.Completable;

public class SignupUserUseCase {
    private final IRemoteRepository remoteRepository;

    public SignupUserUseCase(IRemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }

    public Completable signupUser(String name, String username, String password){
        return remoteRepository.signupUser(name, username, password);
    }
}
