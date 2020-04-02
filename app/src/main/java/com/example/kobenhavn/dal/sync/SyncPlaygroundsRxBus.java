package com.example.kobenhavn.dal.sync;

import com.example.kobenhavn.dal.local.model.Playground;
import com.jakewharton.rxrelay2.PublishRelay;

import java.util.List;

import io.reactivex.Observable;

public class SyncPlaygroundsRxBus {

    private static SyncPlaygroundsRxBus instance;
    private final PublishRelay<SyncPlaygroundResponse> relay;

    public static synchronized SyncPlaygroundsRxBus getInstance() {
        if (instance == null) {
            instance = new SyncPlaygroundsRxBus();
        }
        return instance;
    }

    private SyncPlaygroundsRxBus() {
        relay = PublishRelay.create();
    }

    /*
    public void post(SyncResponseType eventType, Playground playground) {
        relay.accept(new SyncPlaygroundResponse(eventType, playground));
    }

     */

    public void post(SyncResponseType eventType, List<Playground> playgrounds) {
        relay.accept(new SyncPlaygroundResponse(eventType, playgrounds));
    }


    public Observable<SyncPlaygroundResponse> toObservable() {
        return relay;
    }


    public static class SyncPlaygroundResponse {
        public final SyncResponseType type;
        public final List<Playground> playgrounds;

        public SyncPlaygroundResponse(SyncResponseType type, List<Playground> playgrounds) {
            this.type = type;
            this.playgrounds = playgrounds;
        }
    }
}
