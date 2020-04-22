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

public interface ILocalRepository {
    Completable add(List<Playground> playgrounds);
    Completable update(List<Playground> playground);
    Completable delete(Playground playground);
    Flowable<List<Playground>> getPlaygrounds();
    Completable insertAllPlaygrounds(List<Playground> playgrounds);

    Single<User> add(User user);

    Single<User> updateFields(User user);

    Completable updateSubscription(String username, List<Playground> playgrounds);

    LiveData<User> getUserLive(String username);

    Single<User> getUser(String username);


    Completable joinEvents(String username, ArrayList<Event> enrolledEvents);

    Completable removeEventFromUser(String username, ArrayList<Event> events);

    LiveData<Subscriptions> getSubscriptionsLive(String username);
}
