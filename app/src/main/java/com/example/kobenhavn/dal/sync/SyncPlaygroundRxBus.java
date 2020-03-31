package com.example.kobenhavn.dal.sync;

import com.example.offline.dal.local.model.Playground;
import com.jakewharton.rxrelay2.PublishRelay;

import io.reactivex.Observable;

public class SyncPlaygroundRxBus {

    private static SyncPlaygroundRxBus instance;
    private final PublishRelay<SyncPlaygroundResponse> relay;

    public static synchronized SyncPlaygroundRxBus getInstance() {
        if (instance == null) {
            instance = new SyncPlaygroundRxBus();
        }
        return instance;
    }

    private SyncPlaygroundRxBus() {
        relay = PublishRelay.create();
    }

    public void post(SyncResponseType eventType, Playground playground) {
        relay.accept(new SyncPlaygroundResponse(eventType, playground));
    }

    public Observable<SyncPlaygroundResponse> toObservable() {
        return relay;
    }


    public static class SyncPlaygroundResponse {
        public final SyncResponseType type;
        public final Playground playground;

        public SyncPlaygroundResponse(SyncResponseType type, Playground playground) {
            this.type = type;
            this.playground = playground;
        }
    }
}
