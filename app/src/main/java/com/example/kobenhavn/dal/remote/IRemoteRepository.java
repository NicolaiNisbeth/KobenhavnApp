package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Playground;

import io.reactivex.Completable;

public interface IRemoteRepository {
    Completable sync(Playground playground);
}
