package com.example.kobenhavn.usecases.user;

import com.example.kobenhavn.dal.remote.IRemoteRepository;
import com.example.kobenhavn.dal.remote.RemoteRepository;

import io.reactivex.Completable;

public class LoginUserUseCase {
    private final IRemoteRepository remoteRepository;

    public LoginUserUseCase(IRemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }

    public Completable loginUser(String username, String password){
        return remoteRepository.loginUser(username, password);
    }
}
