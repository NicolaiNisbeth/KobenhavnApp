package com.example.kobenhavn.dal.sync;

import com.example.kobenhavn.dal.local.model.User;
import com.jakewharton.rxrelay2.PublishRelay;

public class UpdateSubscriptionsRxBus {
    private static UpdateSubscriptionsRxBus instance;
    private final PublishRelay<UpdateSubscriptionsResponse> relay;

    public static synchronized UpdateSubscriptionsRxBus getInstance(){
        if (instance == null)
            instance = new UpdateSubscriptionsRxBus();

        return instance;
    }

    private UpdateSubscriptionsRxBus(){relay = PublishRelay.create();}

    public void post(SyncResponseType eventType, String username){
        relay.accept(new UpdateSubscriptionsResponse(eventType, username));
    }

    public PublishRelay<UpdateSubscriptionsResponse> toObservable() {
        return relay;
    }

    public static class UpdateSubscriptionsResponse {
        public final SyncResponseType type;
        public final String username;

        public UpdateSubscriptionsResponse(SyncResponseType type, String username) {
            this.type = type;
            this.username = username;
        }
    }
}
