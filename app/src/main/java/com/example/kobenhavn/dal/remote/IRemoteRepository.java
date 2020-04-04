package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IRemoteRepository {
    Completable sync(Playground playground);
    Completable sync(User user);
    Completable fetchPlaygrounds();

    Completable loginUser(String username, String password);

    Completable signupUser(String name, String username, String password);
}
