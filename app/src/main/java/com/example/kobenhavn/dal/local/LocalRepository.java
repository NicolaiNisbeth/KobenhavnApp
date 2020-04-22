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

    public LocalRepository(PlaygroundDAO playgroundDAO, UserDAO userDAO, SubscriptionsDAO subscriptionsDAO){
        this.playgroundDAO = playgroundDAO;
        this.userDAO = userDAO;
        this.subscriptionsDAO = subscriptionsDAO;
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

    /**
     *
     * @param username
     * @param enrolledEvents
     * @return
     */

    @Override
    public Completable joinEvent(String username, ArrayList<Event> enrolledEvents) {
        return Completable.fromAction(() -> {
            userDAO.updateEventParticipation(username, enrolledEvents);
        });
    }



    @Override
    public Completable joinEvent(Event event, User user) {
        ArrayList<Event> enrolledEvents = user.getEvents();
        Event joinedEvent = LocaleUtils.cloneEvent(event, event.getId());
        enrolledEvents.add(joinedEvent);

        return Completable.fromAction(() -> {
            userDAO.updateEventParticipation(user.getUsername(), enrolledEvents);
            Timber.e("event stored %s", joinedEvent);
        });
    }

    @Override
    public Completable updateEvent(Event event, User user) {
        Timber.d("updating event sync status for event id %s", event.getId());
        ArrayList<Event> enrolledEvents = user.getEvents();
        enrolledEvents.remove(event);
        enrolledEvents.add(event);
        return Completable.fromAction(() -> userDAO.updateEventParticipation(user.getUsername(), enrolledEvents));
    }

    @Override
    public Completable deleteEvent(Event event, User user) {
        Timber.d("deleting event with id %s", event.getId());
        ArrayList<Event> enrolledEvents = user.getEvents();
        enrolledEvents.remove(event);
        return Completable.fromAction(() -> userDAO.updateEventParticipation(user.getUsername(), enrolledEvents));
    }

    /**
     *
     * @param username
     * @param events
     * @return
     */
    @Override
    public Completable removeEvent(String username, ArrayList<Event> events) {
        return Completable.fromAction(() -> {
            userDAO.updateEventParticipation(username, events);
            Timber.e("removed event");
        });
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
