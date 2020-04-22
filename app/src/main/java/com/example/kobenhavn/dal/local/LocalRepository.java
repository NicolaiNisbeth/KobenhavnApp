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


    @Override
    public Completable add(List<Playground> playgrounds) {

        /*
        return Single.fromCallable(() -> {
            long rowID = playgroundDAO.add(p);
            Timber.e("Saved playground locally with id: %s", rowID);
            return LocalePlaygroundUtils.clone(p, rowID);
        });

         */
        return null;
    }

    @Override
    public Completable update(List<Playground> playground) {
        return Completable.fromAction(() -> playgroundDAO.update(playground));
    }

    @Override
    public Completable delete(Playground playground) {
        return Completable.fromAction(() -> playgroundDAO.delete(playground));
    }

    @Override
    public Flowable<List<Playground>> getPlaygrounds() {
        return playgroundDAO.getPlaygrounds();
    }

    @Override
    public Completable insertAllPlaygrounds(List<Playground> playgrounds) {
        return Completable.fromAction(() -> playgroundDAO.insertAllPlaygrounds(playgrounds));
    }

    @Override
    public Single<User> add(User user) {
        return Single.fromCallable(() -> {
            userDAO.add(user);
            Timber.e("Saved user locally");
            return LocaleUserUtils.clone(user, false);
        });
    }

    @Override
    public Single<User> updateFields(User user) {
        return Single.fromCallable(() -> {
            userDAO.updateUserFields(user.getFirstname(), user.getEmail(), user.getPhonenumbers().get(0), user.getPassword());
            Timber.e("User is updated locally");
            return LocaleUserUtils.clone(user, false);
        });
    }



    @Override
    public LiveData<User> getUserLive(String username) {
        return userDAO.getUserLive(username);
    }

    @Override
    public Single<User> getUser(String username) {
        return userDAO.getUser(username);
    }

    @Override
    public Completable joinEvents(String username, ArrayList<Event> enrolledEvents) {
        return Completable.fromAction(() -> {
            userDAO.joinEvents(username, enrolledEvents);
            Timber.e("Joined event");
        });
    }

    @Override
    public Completable removeEventFromUser(String username, ArrayList<Event> events) {
        return Completable.fromAction(() -> {
            userDAO.joinEvents(username, events);
            Timber.e("removed event");
        });
    }

    @Override
    public LiveData<Subscriptions> getSubscriptionsLive(String username) {
        return subscriptionsDAO.getSubscriptionsLive(username);
    }

    @Override
    public Completable updateSubscription(String username, List<Playground> playgrounds) {
        Subscriptions subscriptions = new Subscriptions(username, playgrounds);
        return Completable.fromAction(() -> {
            subscriptionsDAO.add(subscriptions);
            //subscriptionsDAO.updateSubscribedPlaygrounds(username, playgrounds);
            Timber.e("Updated user locally");
        });
    }
}
