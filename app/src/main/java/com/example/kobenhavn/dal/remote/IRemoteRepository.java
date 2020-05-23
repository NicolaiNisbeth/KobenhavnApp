package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Responsible for CRUD operations against remote repository
 */
public interface IRemoteRepository {
    Completable loginUser(String username, String password);
    Completable signupUser(String name, String username, String password);
    Completable updateUser(User user);

    Completable fetchPlaygrounds();

    Completable joinEvent(String playgroundName, Event event, User user);
    Completable leaveEvent(String playgroundName, Event event, User user);
}
