package com.example.kobenhavn.dal.local;

import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface ILocalRepository {
    Completable subscribeTo(Playground playground);
    Completable add(List<Playground> playgrounds);
    Completable update(List<Playground> playground);
    Completable delete(Playground playground);
    Flowable<List<Playground>> getPlaygrounds();
    Completable insertAllPlaygrounds(List<Playground> playgrounds);

    Single<User> add(User user);

    Completable update(User user);
}
