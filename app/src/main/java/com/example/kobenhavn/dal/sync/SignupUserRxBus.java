package com.example.kobenhavn.dal.sync;

import com.jakewharton.rxrelay2.PublishRelay;

import io.reactivex.Observable;

/**
 * Taken from https://proandroiddev.com/offline-apps-its-easier-than-you-think-9ff97701a73f
 */
public class SignupUserRxBus {
    private static SignupUserRxBus instance;
    private final PublishRelay<SignupResponse> relay;

    public static synchronized SignupUserRxBus getInstance(){
        if (instance == null)
            instance = new SignupUserRxBus();

        return instance;
    }

    private SignupUserRxBus() {
        relay = PublishRelay.create();
    }

    public void post(RemoteResponseType eventType){
        relay.accept(new SignupResponse(eventType));
    }

    public Observable<SignupResponse> toObservable() {
        return relay;
    }

    public static class SignupResponse {
        public final RemoteResponseType type;

        SignupResponse(RemoteResponseType type) {
            this.type = type;
        }
    }
}
