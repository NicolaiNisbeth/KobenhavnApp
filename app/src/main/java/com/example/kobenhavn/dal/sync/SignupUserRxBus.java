package com.example.kobenhavn.dal.sync;

import com.jakewharton.rxrelay2.PublishRelay;

import io.reactivex.Observable;

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

    public void post(SyncResponseType eventType){
        relay.accept(new SignupResponse(eventType));
    }

    public Observable<SignupResponse> toObservable() {
        return relay;
    }

    public static class SignupResponse {
        public final SyncResponseType type;

        public SignupResponse(SyncResponseType type) {
            this.type = type;
        }
    }
}
