package com.example.kobenhavn.dal.sync;

import com.jakewharton.rxrelay2.PublishRelay;

/**
 * Taken from https://proandroiddev.com/offline-apps-its-easier-than-you-think-9ff97701a73f
 */
public class RemoveEventRxBus {
    private static RemoveEventRxBus instance;
    private final PublishRelay<RemoveEventResponse> relay;

    public static synchronized RemoveEventRxBus getInstance(){
        if (instance == null)
            instance = new RemoveEventRxBus();

        return instance;
    }

    private RemoveEventRxBus(){
        relay = PublishRelay.create();
    }

    public void post(RemoteResponseType type, Boolean user){
        relay.accept(new RemoveEventResponse(type, user));
    }

    public static class RemoveEventResponse {
        private final RemoteResponseType type;
        private final Boolean user;

        RemoveEventResponse(RemoteResponseType type, Boolean user){
            this.type = type;
            this.user = user;
        }
    }

}
