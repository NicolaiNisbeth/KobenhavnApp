package com.example.kobenhavn.dal.local;


import com.example.kobenhavn.dal.local.model.Playground;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class LocalRepository implements ILocalRepository {

    private final PlaygroundDAO playgroundDAO;

    public LocalRepository(PlaygroundDAO playgroundDAO){
        this.playgroundDAO = playgroundDAO;
    }

    @Override
    public Single<Playground> subscribeTo(Playground playground) {
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

}
