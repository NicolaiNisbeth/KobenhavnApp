package com.example.kobenhavn.dal.sync;

import android.net.wifi.aware.PublishConfig;

import com.example.kobenhavn.dal.local.model.User;
import com.jakewharton.rxrelay2.PublishRelay;

import io.reactivex.Observable;

public class JoinEventUserRxBus {
    private static JoinEventUserRxBus instance;
    private final PublishRelay<JoinEventResponse> relay;

    public static synchronized JoinEventUserRxBus getInstance(){
        if (instance == null)
            instance = new JoinEventUserRxBus();

        return instance;
    }

    private JoinEventUserRxBus(){
        relay = PublishRelay.create();
    }

    public void post(SyncResponseType eventType, Boolean user){
        relay.accept(new JoinEventResponse(eventType, user));
    }

    public Observable<JoinEventResponse> toObservable() {
        return relay;
    }


    public static class JoinEventResponse  {
        private final SyncResponseType eventType;
        private final Boolean user;

        public JoinEventResponse(SyncResponseType eventType, Boolean user) {

            this.eventType = eventType;
            this.user = user;
        }
    }
}
