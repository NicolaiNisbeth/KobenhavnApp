package com.example.kobenhavn.dal.sync;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;
import com.jakewharton.rxrelay2.PublishRelay;

import io.reactivex.Observable;

/**
 * Taken from https://proandroiddev.com/offline-apps-its-easier-than-you-think-9ff97701a73f
 */
public class JoinEventRxBus {
    private static JoinEventRxBus instance;
    private final PublishRelay<JoinEventResponse> relay;

    public static synchronized JoinEventRxBus getInstance(){
        if (instance == null)
            instance = new JoinEventRxBus();

        return instance;
    }

    private JoinEventRxBus(){
        relay = PublishRelay.create();
    }

    public void post(RemoteResponseType eventType, Event event, User user){
        relay.accept(new JoinEventResponse(eventType, event, user));
    }

    public Observable<JoinEventResponse> toObservable() {
        return relay;
    }

    public static class JoinEventResponse  {
        public final RemoteResponseType type;
        public final Event event;
        public final User user;

        JoinEventResponse(RemoteResponseType type, Event event, User user) {
            this.type = type;
            this.event = event;
            this.user = user;
        }
    }
}
