package com.example.kobenhavn.dal.local;

import androidx.lifecycle.LiveData;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.Subscription;
import com.example.kobenhavn.dal.local.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Responsible for CRUD operations against local repository
 */
public interface ILocalRepository {

    // user
    Single<User> insertUser(User user);
    LiveData<User> getUserLive(String username);
    Single<User> updateUserFields(User user);

    // playground
    Completable insertPlaygrounds(List<Playground> playgrounds);
    Flowable<List<Playground>> getPlaygrounds();

    // subscription
    Completable insertSubscription(User user, Subscription subscription);
    LiveData<List<Subscription>> getSubscriptionsLiveData(String username);
    Completable removeSubscription(String username, Playground playground, long id);

    // event
    Completable joinEvent(Event event, User user);
    Completable insertEvents(List<Event> events);
    Completable updateEvent(Event event, User user);
    Completable deleteEvent(Event event, User user);
    LiveData<List<Event>> getEventsLiveData(String username);

    Completable clearAll();
}
