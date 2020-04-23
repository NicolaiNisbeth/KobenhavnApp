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


    /**
     *
     * @return
     */
    @Override
    public Flowable<List<Playground>> getPlaygrounds() {
        return playgroundDAO.getPlaygrounds();
    }

    /**
     *
     * @param playgrounds
     * @return
     */
    @Override
    public Completable insertPlaygrounds(List<Playground> playgrounds) {
        return Completable.fromAction(() -> playgroundDAO.insertAllPlaygrounds(playgrounds));
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public Single<User> insertUser(User user) {
        return Single.fromCallable(() -> {
            userDAO.add(user);
            Timber.e("Saved user locally");
            return LocaleUtils.cloneUser(user, false);
        });
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public Single<User> updateUserFields(User user) {
        return Single.fromCallable(() -> {
            userDAO.updateUserFields(user.getFirstname(), user.getEmail(), user.getPhonenumbers().get(0), user.getPassword());
            Timber.e("User is updated locally");
            return LocaleUtils.cloneUser(user, false);
        });
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public LiveData<User> getUserLiveData(String username) {
        return userDAO.getUserLive(username);
    }

    @Override
    public Completable joinEvent(Event event, User user) {
        Event joinedEvent = LocaleUtils.cloneEvent(event, user.getUsername());
        Timber.e("event stored %s", joinedEvent);
        return Completable.fromAction(() -> eventDAO.add(joinedEvent));
    }

    @Override
    public Completable updateEvent(Event event, User user) {
        Timber.d("updating event sync status for event id %s", event.getId());
        return Completable.fromAction(() -> eventDAO.add(event));
    }

    @Override
    public Completable deleteEvent(Event event, User user) {
        Timber.d("deleting event with id %s", event.getId());
        Event toBeDeleted = LocaleUtils.cloneEvent(event, user.getUsername());
        return Completable.fromAction(() -> eventDAO.deleteEvent(toBeDeleted));
    }

    @Override
    public Completable insertEvents(List<Event> events) {
        Timber.e("Insert events %s", events);
        return Completable.fromAction(() -> eventDAO.insertAll(events));
    }

    @Override
    public LiveData<List<Event>> getEventsLiveData(String username){
        return eventDAO.getEventsLive(username);
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public LiveData<Subscriptions> getSubscriptionsLiveData(String username) {
        return subscriptionsDAO.getSubscriptionsLive(username);
    }

    /**
     *
     * @param username
     * @param playgrounds
     * @return
     */
    @Override
    public Completable updatePlaygroundSubscriptions(String username, List<Playground> playgrounds) {
        Subscriptions subscriptions = new Subscriptions(username, playgrounds);
        return Completable.fromAction(() -> {
            subscriptionsDAO.add(subscriptions);
            Timber.e("Updated user locally");
        });
    }
}
