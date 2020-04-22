package com.example.kobenhavn.view;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.sync.FetchPlaygroundsRxBus;
import com.example.kobenhavn.dal.sync.JoinEventRxBus;
import com.example.kobenhavn.dal.sync.RemoteResponseType;
import com.example.kobenhavn.dal.sync.SyncUserRxBus;
import com.example.kobenhavn.usecases.event.DeleteEventUC;
import com.example.kobenhavn.usecases.event.UpdateEventUC;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsInDbUC;
import com.example.kobenhavn.usecases.playground.InsertPlaygroundsInDbUC;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Updates local database after remote comment sync requests
 */
public class MainLifecycleObserver implements LifecycleObserver {
    private final InsertPlaygroundsInDbUC insertPlaygroundsInDbUC;
    private final DeleteEventUC deleteEventUC;
    private final UpdateEventUC updateEventUC;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public MainLifecycleObserver(InsertPlaygroundsInDbUC insertPlaygrounds, DeleteEventUC deleteEventUC, UpdateEventUC updateEventUC) {
        this.insertPlaygroundsInDbUC = insertPlaygrounds;
        this.deleteEventUC = deleteEventUC;
        this.updateEventUC = updateEventUC;


        disposables.add(FetchPlaygroundsRxBus.getInstance()
                .toObservable()
                .subscribe(this::handleFetchResponse, t -> Timber.e(t, "error handling playground fetch response")));

        /*

        disposables.add(SyncUserRxBus.getInstance()
                .toObservable()
                .subscribe(this::handleUserResponse, t -> Timber.e(t, "error handling user sync response")));

        disposables.add(JoinEventRxBus.getInstance()
                .toObservable()
                .subscribe(this::handlerEventResponse, t -> Timber.e("error handling sync event response")));

 */
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        Timber.e("onPause lifecycle event.");
        disposables.clear();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {
        Timber.e("onResume lifecycle event.");

        disposables.add(FetchPlaygroundsRxBus.getInstance()
                .toObservable()
                .subscribe(this::handleFetchResponse, t -> Timber.e(t, "error handling playground fetch response")));

        disposables.add(SyncUserRxBus.getInstance()
                .toObservable()
                .subscribe(this::handleUserResponse, t -> Timber.e(t, "error handling user sync response")));

        disposables.add(JoinEventRxBus.getInstance()
                .toObservable()
                .subscribe(this::handlerEventResponse, t -> Timber.e("error handling sync event response")));
    }

    private void handlerEventResponse(JoinEventRxBus.JoinEventResponse response) {
        Event joinedEvent = response.event;
        User user = response.user;

        if (response.type == RemoteResponseType.SUCCESS){
            Timber.d("received sync comment success event for comment %s", joinedEvent);
            disposables.add(updateEventUC.updateEvent(joinedEvent, user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> Timber.d("update event success"),
                            t -> Timber.e(t, "update event error")));
        }
        else {
            Timber.d("received sync comment failed event for comment %s", joinedEvent);
            disposables.add(deleteEventUC.deleteEvent(joinedEvent, user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> Timber.d("delete event success"),
                            t -> Timber.e(t, "delete event error")));
        }
    }

    private void handleUserResponse(SyncUserRxBus.SyncUserResponse response) {
        if (response.type == RemoteResponseType.SUCCESS){
            onSyncingSuccess(response.user);
        } else {
            onSyncingFailed(response.user);
        }
    }

    private void onSyncingSuccess(User user) {
        Timber.e("Successfully synced users");
        // update subscriptions

    }

    private void onSyncingFailed(User user) {
        // TODO: revert local update to ensure data integrity
        Timber.e("Failed to sync user");
    }

    private void handleFetchResponse(FetchPlaygroundsRxBus.FetchPlaygroundResponse response) {
        if (response.type == RemoteResponseType.SUCCESS) {
            onFetchingSuccess(response.playgrounds);
        } else {
            onFetchingFailed(response.playgrounds);
        }
    }

    private void onFetchingSuccess(List<Playground> playgrounds) {
        Timber.e("Successfully fetched playgrounds");
        disposables.add(insertPlaygroundsInDbUC.insertPlaygrounds(playgrounds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("successfully inserted playgrounds in locale db"),
                        t -> Timber.e("error inserting playgrounds in locale db")));

    }

    private void onFetchingFailed(List<Playground> playground) { }
}