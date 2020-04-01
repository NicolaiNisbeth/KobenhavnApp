package com.example.kobenhavn.dal.local;

import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.local.model.Playground;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface ILocalRepository {
    Single<Playground> add(String text);
    Completable update(Playground playground);
    Completable delete(Playground playground);
    Flowable<List<Playground>> getPlaygrounds();

}
