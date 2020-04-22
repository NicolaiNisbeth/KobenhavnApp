package com.example.kobenhavn.dal.local;

import androidx.lifecycle.LiveData;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.Subscriptions;
import com.example.kobenhavn.dal.local.model.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 *
 */
public interface ILocalRepository {
    Flowable<List<Playground>> getPlaygrounds();
    Completable insertPlaygrounds(List<Playground> playgrounds);

    Single<User> insertUser(User user);

    Single<User> updateUserFields(User user);

    Completable updatePlaygroundSubscriptions(String username, List<Playground> playgrounds);

    LiveData<User> getUserLiveData(String username);

    LiveData<List<Event>> getEventsLiveData(String username);

    LiveData<Subscriptions> getSubscriptionsLiveData(String username);

    Completable joinEvent(Event event, User user);


    Completable updateEvent(Event event, User user);

    Completable deleteEvent(Event event, User user);

    Completable insertEvents(List<Event> events);
}
