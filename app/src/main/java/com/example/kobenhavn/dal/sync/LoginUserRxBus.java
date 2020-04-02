package com.example.kobenhavn.dal.sync;

import com.example.kobenhavn.dal.local.model.User;
import com.jakewharton.rxrelay2.PublishRelay;
import io.reactivex.Observable;

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

    public void post(SyncResponseType eventType, User user) {
        relay.accept(new LoginResponse(eventType, user));
    }

    public Observable<LoginResponse> toObservable() {
        return relay;
    }


    public static class LoginResponse {
        public final SyncResponseType type;
        public final User user;

        public LoginResponse(SyncResponseType type, User user) {
            this.type = type;
            this.user = user;
        }
    }
}
