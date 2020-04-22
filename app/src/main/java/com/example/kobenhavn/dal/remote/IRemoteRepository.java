package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IRemoteRepository {

    Completable loginUser(String username, String password);
    Completable signupUser(String name, String username, String password);
    Completable syncUser(User user);

    Completable fetchPlaygrounds();

    Completable joinUserWithEvent(String playgroundName, String eventID, String username);
    Completable removeEventFromUser(String playgroundName, String eventID, String username);
}
