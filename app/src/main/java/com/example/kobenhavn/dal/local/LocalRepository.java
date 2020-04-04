package com.example.kobenhavn.dal.local;


import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import timber.log.Timber;

public class LocalRepository implements ILocalRepository {

    private final PlaygroundDAO playgroundDAO;
    private final UserDAO userDAO;

    public LocalRepository(PlaygroundDAO playgroundDAO, UserDAO userDAO){
        this.playgroundDAO = playgroundDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Completable subscribeTo(Playground playground) {


        return null;
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
    public Single<User> update(User user) {
        return Single.fromCallable(() -> {
            userDAO.update(user);
            Timber.e("Updated user locally");
            return LocaleUserUtils.clone(user, false);
        });
    }

    @Override
    public Single<User> getUser(String username) {
        return userDAO.getUser(username);
    }

}
