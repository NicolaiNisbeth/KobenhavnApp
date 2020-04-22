package com.example.kobenhavn.usecases.user;

import com.example.kobenhavn.dal.remote.IRemoteRepository;
import com.example.kobenhavn.dal.remote.RemoteRepository;

import io.reactivex.Completable;

public class LoginUserUC {
    private final IRemoteRepository remoteRepository;

    public LoginUserUC(IRemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }

    public Completable loginUser(String username, String password){
        return remoteRepository.loginUser(username, password);
    }
}
