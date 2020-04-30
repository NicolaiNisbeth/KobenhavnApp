package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;

import io.reactivex.Completable;

/**
 * Responsible for CRUD operations against remote repository
 */
public interface IRemoteRepository {
    Completable loginUser(String username, String password);
    Completable signupUser(String name, String username, String password);
    Completable updateUser(User user);

    Completable fetchPlaygrounds();

    Completable joinEvent(String playgroundName, Event event, User user);
    Completable leaveEvent(String playgroundName, String eventID, String username);
}
