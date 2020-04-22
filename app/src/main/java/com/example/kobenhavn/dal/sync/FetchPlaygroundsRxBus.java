package com.example.kobenhavn.dal.sync;

import com.example.kobenhavn.dal.local.model.Playground;
import com.jakewharton.rxrelay2.PublishRelay;

import java.util.List;

import io.reactivex.Observable;

public class FetchPlaygroundsRxBus {

    private static FetchPlaygroundsRxBus instance;
    private final PublishRelay<FetchPlaygroundResponse> relay;

    public static synchronized FetchPlaygroundsRxBus getInstance() {
        if (instance == null) {
            instance = new FetchPlaygroundsRxBus();
        }
        return instance;
    }

    private FetchPlaygroundsRxBus() {
        relay = PublishRelay.create();
    }

    public void publishFetchingResponse(RemoteResponseType eventType, List<Playground> playgrounds) {
        relay.accept(new FetchPlaygroundResponse(eventType, playgrounds));
    }


    public Observable<FetchPlaygroundResponse> toObservable() {
        return relay;
    }


    public static class FetchPlaygroundResponse {
        public final RemoteResponseType type;
        public final List<Playground> playgrounds;

        public FetchPlaygroundResponse(RemoteResponseType type, List<Playground> playgrounds) {
            this.type = type;
            this.playgrounds = playgrounds;
        }
    }
}
