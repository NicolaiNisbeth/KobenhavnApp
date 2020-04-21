package com.example.kobenhavn.dal.sync;

import com.jakewharton.rxrelay2.PublishRelay;

public class RemoveUserFromEventRxBus {
    private static RemoveUserFromEventRxBus instance;
    private final PublishRelay<RemoveEventResponse> relay;

    public static synchronized RemoveUserFromEventRxBus getInstance(){
        if (instance == null)
            instance = new RemoveUserFromEventRxBus();

        return instance;
    }

    private RemoveUserFromEventRxBus(){
        relay = PublishRelay.create();
    }

    public void post(SyncResponseType type, Boolean user){
        relay.accept(new RemoveEventResponse(type, user));
    }

    public static class RemoveEventResponse {
        private final SyncResponseType type;
        private final Boolean user;

        public RemoveEventResponse(SyncResponseType type, Boolean user){
            this.type = type;
            this.user = user;
        }
    }

}
