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
import com.example.kobenhavn.dal.sync.LeaveEventRxBus;
import com.example.kobenhavn.dal.sync.SyncUserRxBus;
import com.example.kobenhavn.usecases.event.DeleteEventUC;
import com.example.kobenhavn.usecases.event.JoinEventUC;
import com.example.kobenhavn.usecases.event.UpdateEventUC;
import com.example.kobenhavn.usecases.playground.InsertPlaygroundsInDbUC;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Updates local database after remote sync requests
 */
public class MainLifecycleObserver implements LifecycleObserver {
    private final InsertPlaygroundsInDbUC insertPlaygroundsInDbUC;
    private final DeleteEventUC deleteEventUC;
    private final UpdateEventUC updateEventUC;
    private final JoinEventUC joinEventUC;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public MainLifecycleObserver(InsertPlaygroundsInDbUC insertPlaygrounds, DeleteEventUC deleteEventUC, UpdateEventUC updateEventUC, JoinEventUC joinEventUC) {
        this.insertPlaygroundsInDbUC = insertPlaygrounds;
        this.deleteEventUC = deleteEventUC;
        this.updateEventUC = updateEventUC;
        this.joinEventUC = joinEventUC;
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
                .subscribe(this::handleFetchResponse,
                        t -> Timber.e(t, "error handling playground fetch response")));

        disposables.add(SyncUserRxBus.getInstance()
                .toObservable()
                .subscribe(this::handleUserResponse,
                        t -> Timber.e(t, "error handling user sync response")));

        disposables.add(JoinEventRxBus.getInstance()
                .toObservable()
                .subscribe(this::handleJoinEventResponse,
                        t -> Timber.e("error handling sync event response")));

        disposables.add(LeaveEventRxBus.getInstance()
                .toObservable()
                .subscribe(this::handleLeaveEventResponse,
                        t -> Timber.e("error handling sync event response")));
    }

    private void handleJoinEventResponse(JoinEventRxBus.JoinEventResponse response) {
        Event joinedEvent = response.event;
        User user = response.user;
        if (response.type == RemoteResponseType.SUCCESS) onEventSucces(joinedEvent, user);
        else onEventFailure(joinedEvent, user);
    }

    private void onEventFailure(Event joinedEvent, User user) {
        Timber.d("received sync event failed event for comment %s", joinedEvent);
        disposables.add(deleteEventUC.deleteEvent(joinedEvent, user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.d("delete event success"),
                        t -> Timber.e(t, "delete event error")));
    }

    private void onEventSucces(Event joinedEvent, User user) {
        Timber.d("received sync event success event for comment %s", joinedEvent);
        disposables.add(updateEventUC.updateEvent(joinedEvent, user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.d("update event success"),
                        t -> Timber.e(t, "update event error")));
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

    private void handleLeaveEventResponse(LeaveEventRxBus.RemoveEventResponse response) {
        Event leftEvent = response.event;
        User user = response.user;
        if (response.type == RemoteResponseType.FAILED)
            onLeavingFailure(leftEvent, user);
    }

    private void onLeavingFailure(Event leftEvent, User user) {
        /*
        disposables.add(joinEventUC.joinEventForUser(leftEvent, user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.d("deleted event is restored"),
                        t -> Timber.e(t, "deleted event is not restored and data is inconsistent!!!")));
         */
    }

    private void onFetchingFailed(List<Playground> playground) { }

    private void handleUserResponse(SyncUserRxBus.SyncUserResponse response) { }
}