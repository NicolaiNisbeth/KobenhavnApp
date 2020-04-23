package com.example.kobenhavn.dal.sync;

import com.example.kobenhavn.dal.local.model.User;
import com.jakewharton.rxrelay2.PublishRelay;
import io.reactivex.Observable;

/**
 * Taken from https://proandroiddev.com/offline-apps-its-easier-than-you-think-9ff97701a73f
 */
public class LoginUserRxBus {
    private static LoginUserRxBus instance;
    private final PublishRelay<LoginResponse> relay;

    public static synchronized LoginUserRxBus getInstance() {
        if (instance == null) {
            instance = new LoginUserRxBus();
        }
        return instance;
    }

    private LoginUserRxBus() {
        relay = PublishRelay.create();
    }

    public void post(RemoteResponseType eventType, User user) {
        relay.accept(new LoginResponse(eventType, user));
    }

    public Observable<LoginResponse> toObservable() {
        return relay;
    }

    public static class LoginResponse {
        public final RemoteResponseType type;
        public final User user;

        LoginResponse(RemoteResponseType type, User user) {
            this.type = type;
            this.user = user;
        }
    }
}
