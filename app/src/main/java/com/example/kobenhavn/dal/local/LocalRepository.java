package com.example.kobenhavn.dal.local;


import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.local.model.Playground;

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
    public Single<Playground> add(String text) {
        Playground p = new Playground(text);

        return Single.fromCallable(() -> {
            long rowID = playgroundDAO.add(p);
            Timber.e("Saved playground locally with id: %s", rowID);
            return LocalePlaygroundUtils.clone(p, rowID);
        });
    }

    @Override
    public Completable update(Playground playground) {
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
    public Single<User> signupUser(User user) {

        return Single.fromCallable(() -> {
            long rowID = userDAO.add(user);
            Timber.e("Saved playground locally with id: %s", rowID);
            return LocaleUserUtils.clone(user, rowID);
        });
    }

    @Override
    public Completable update(User user) {
        return Completable.fromAction(() -> userDAO.update(user));
    }

    @Override
    public Completable delete(User user) {
        return Completable.fromAction(() -> userDAO.delete(user));
    }

    @Override
    public Single<User> getUser(long id) {
        return userDAO.getUser(id);
    }
}
