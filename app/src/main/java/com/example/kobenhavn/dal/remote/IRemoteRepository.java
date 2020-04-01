package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;

import io.reactivex.Completable;

public interface IRemoteRepository {
    Completable sync(Playground playground);
    Completable sync(User user);
}
