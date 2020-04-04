package com.example.kobenhavn.dal.sync;

import com.example.kobenhavn.dal.local.model.User;
import com.jakewharton.rxrelay2.PublishRelay;

public class SyncUserRxBus {
    private static SyncUserRxBus instance;
    private final PublishRelay<SyncUserResponse> relay;

    public static synchronized SyncUserRxBus getInstance(){
        if (instance == null)
            instance = new SyncUserRxBus();

        return instance;
    }

    private SyncUserRxBus(){relay = PublishRelay.create();}

    public void post(SyncResponseType eventType, User user){
        relay.accept(new SyncUserResponse(eventType, user));
    }

    public PublishRelay<SyncUserResponse> toObservable() {
        return relay;
    }

    public static class SyncUserResponse {
        public final SyncResponseType type;
        public final User user;

        public SyncUserResponse(SyncResponseType type, User user) {
            this.type = type;
            this.user = user;
        }
    }
}
