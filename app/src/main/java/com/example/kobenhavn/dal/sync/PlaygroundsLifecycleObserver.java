package com.example.kobenhavn.dal.sync;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsInDbUseCase;
import com.example.kobenhavn.usecases.playground.InsertPlaygroundsInDbUseCase;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Updates local database after remote comment sync requests
 */
public class PlaygroundsLifecycleObserver implements LifecycleObserver {
    private final InsertPlaygroundsInDbUseCase insertPlaygroundsInDbUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public PlaygroundsLifecycleObserver(InsertPlaygroundsInDbUseCase insertPlaygrounds, GetPlaygroundsInDbUseCase getPlaygrounds) {
        this.insertPlaygroundsInDbUseCase = insertPlaygrounds;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Timber.e("onResume lifecycle event.");

        disposables.add(FetchPlaygroundsRxBus.getInstance()
                .toObservable()
                .subscribe(this::handleFetchResponse, t -> Timber.e(t, "error handling playground fetch response")));

        disposables.add(SyncUserRxBus.getInstance()
                .toObservable()
                .subscribe(this::handleUserResponse, t -> Timber.e(t, "error handling user sync response")));
    }

    private void handleUserResponse(SyncUserRxBus.SyncUserResponse response) {
        if (response.type == SyncResponseType.SUCCESS){
            onSyncingSuccess(response.user);
        } else {
            onSyncingFailed(response.user);
        }
    }

    private void onSyncingFailed(User user) {
        Timber.e("Failed to sync user");
    }

    private void onSyncingSuccess(User user) {
        Timber.e("Successfully synced users");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Timber.e("onPause lifecycle event.");
        disposables.clear();
    }

    private void handleFetchResponse(FetchPlaygroundsRxBus.FetchPlaygroundResponse response) {
        if (response.type == SyncResponseType.SUCCESS) {
            onFetchingSuccess(response.playgrounds);
        } else {
            onFetchingFailed(response.playgrounds);
        }
    }

    private void onFetchingSuccess(List<Playground> playgrounds) {
        Timber.e("Successfully fetched playgrounds");
        disposables.add(insertPlaygroundsInDbUseCase.insertPlaygrounds(playgrounds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("successfully inserted playgrounds in locale db"),
                        t -> Timber.e("error inserting playgrounds in locale db")));

    }

    private void onFetchingFailed(List<Playground> playground) {

    }
}