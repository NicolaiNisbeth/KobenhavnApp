package com.example.kobenhavn.dal.sync;

import com.jakewharton.rxrelay2.PublishRelay;

import io.reactivex.Observable;

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

    public void post(RemoteResponseType eventType, Boolean user){
        relay.accept(new JoinEventResponse(eventType, user));
    }

    public Observable<JoinEventResponse> toObservable() {
        return relay;
    }


    public static class JoinEventResponse  {
        private final RemoteResponseType eventType;
        private final Boolean user;

        public JoinEventResponse(RemoteResponseType eventType, Boolean user) {

            this.eventType = eventType;
            this.user = user;
        }
    }
}
