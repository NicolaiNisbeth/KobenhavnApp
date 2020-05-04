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
import timber.log.Timber;

public class LocalRepository implements ILocalRepository {

    private final PlaygroundDAO playgroundDAO;
    private final UserDAO userDAO;
    private final SubscriptionsDAO subscriptionsDAO;
    private final EventDAO eventDAO;

    public LocalRepository(PlaygroundDAO playgroundDAO, UserDAO userDAO, SubscriptionsDAO subscriptionsDAO, EventDAO eventDAO){
        this.playgroundDAO = playgroundDAO;
        this.userDAO = userDAO;
        this.subscriptionsDAO = subscriptionsDAO;
        this.eventDAO = eventDAO;
    }

    @Override
    public Single<User> insertUser(User user) {
        return Single.fromCallable(() -> {
            userDAO.add(user);
            Timber.e("inserting user");
            return CloneUtils.cloneUser(user, false);
        });
    }

    @Override
    public LiveData<User> getUserLive(String username) {
        return userDAO.getUserLive(username);
    }

    @Override
    public Single<User> updateUserFields(User user) {
        return Single.fromCallable(() -> {
            userDAO.updateUserFields(user.getFirstname(), user.getEmail(), user.getPhoneNumbers().get(0), user.getPassword());
            Timber.e("updating user fields");
            return CloneUtils.cloneUser(user, false);
        });
    }

    @Override
    public Completable insertPlaygrounds(List<Playground> playgrounds) {
        Timber.d("inserting playgrounds");
        return Completable.fromAction(() -> playgroundDAO.insertAllPlaygrounds(playgrounds));
    }

    @Override
    public Flowable<List<Playground>> getPlaygrounds() {
        return playgroundDAO.getPlaygrounds();
    }

    @Override
    public Completable insertSubscription(User user, Subscription subscription) {
        Subscription toBeInserted = CloneUtils.cloneSubscription(subscription.getPlayground(), user.getUsername());
        Timber.d("inserting subscription %s", toBeInserted.getId());
        return Completable.fromAction(() -> subscriptionsDAO.add(toBeInserted));
    }

    @Override
    public LiveData<List<Subscription>> getSubscriptionsLiveData(String username) {
        return subscriptionsDAO.getSubscriptionsLive(username);
    }

    @Override
    public Completable removeSubscription(String username, Playground playground, long id) {
        Subscription toBeDeleted = CloneUtils.cloneSubscription(playground, username, id);
        Timber.d("deleting subscription");
        return Completable.fromAction(() -> subscriptionsDAO.removeSubscription(toBeDeleted));
    }

    @Override
    public Completable joinEvent(Event event, User user) {
        Event joinedEvent = CloneUtils.cloneEvent(event, user.getUsername());
        Timber.e("event stored %s", joinedEvent.getId());
        return Completable.fromAction(() -> eventDAO.add(joinedEvent));
    }

    @Override
    public Completable insertEvents(List<Event> events) {
        Timber.e("Insert events");
        return Completable.fromAction(() -> eventDAO.insertAll(events));
    }

    @Override
    public Completable updateEvent(Event event, User user) {
        Timber.d("updating event sync status for event id %s", event.getId());
        return Completable.fromAction(() -> eventDAO.add(event));
    }

    @Override
    public Completable deleteEvent(Event event, User user) {
        Timber.d("deleting event with id %s", event.getId());
        Event toBeDeleted = CloneUtils.cloneEvent(event, user.getUsername());
        return Completable.fromAction(() -> eventDAO.deleteEvent(toBeDeleted));
    }

    @Override
    public LiveData<List<Event>> getEventsLiveData(String username){
        return eventDAO.getEventsLive(username);
    }

    @Override
    public Completable clearAll() {
        return eventDAO.clearAll();
    }

}
