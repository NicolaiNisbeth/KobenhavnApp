package com.example.kobenhavn.dal.sync;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;
import com.jakewharton.rxrelay2.PublishRelay;

import io.reactivex.Observable;

/**
 * Taken from https://proandroiddev.com/offline-apps-its-easier-than-you-think-9ff97701a73f
 */
public class LeaveEventRxBus {
    private static LeaveEventRxBus instance;
    private final PublishRelay<RemoveEventResponse> relay;

    public static synchronized LeaveEventRxBus getInstance(){
        if (instance == null)
            instance = new LeaveEventRxBus();

        return instance;
    }

    private LeaveEventRxBus(){
        relay = PublishRelay.create();
    }

    public Observable<LeaveEventRxBus.RemoveEventResponse> toObservable() {
        return relay;
    }

    public void post(RemoteResponseType type, User user, Event event){
        relay.accept(new RemoveEventResponse(type, user, event));
    }

    public static class RemoveEventResponse {
        public final RemoteResponseType type;
        public final Event event;
        public final User user;

        RemoveEventResponse(RemoteResponseType type, User user, Event event){
            this.type = type;
            this.user = user;
            this.event = event;
        }
    }

}
