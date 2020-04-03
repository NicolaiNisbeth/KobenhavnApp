package com.example.kobenhavn.dal.sync;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsInDbUseCase;
import com.example.kobenhavn.usecases.playground.InsertPlaygroundsInDbUseCase;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Updates local database after remote comment sync requests
 */
public class PlaygroundsLifecycleObserver implements LifecycleObserver {
    private final InsertPlaygroundsInDbUseCase insertPlaygroundsInDbUseCase;
    private final GetPlaygroundsInDbUseCase getPlaygroundsInDbUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<List<Playground>> playgroundsLiveData = new MutableLiveData<>();


    public PlaygroundsLifecycleObserver(InsertPlaygroundsInDbUseCase insertPlaygrounds, GetPlaygroundsInDbUseCase getPlaygrounds) {
        this.insertPlaygroundsInDbUseCase = insertPlaygrounds;
        this.getPlaygroundsInDbUseCase = getPlaygrounds;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Timber.e("onResume lifecycle event.");

        disposables.add(FetchPlaygroundsRxBus.getInstance()
                .toObservable()
                .subscribe(this::handleFetchResponse, t -> Timber.e(t, "error handling sync response")));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Timber.e("onPause lifecycle event.");
        disposables.clear();
    }

    private void handleFetchResponse(FetchPlaygroundsRxBus.FetchPlaygroundResponse response) {
        if (response.type == SyncResponseType.SUCCESS) {
            onFetchingSuccess(response.playgrounds);
        } else {
            onFetchingFailed(response.playgrounds);
        }
    }

    private void onFetchingSuccess(List<Playground> playgrounds) {
        Timber.e("received successfully fetched playgrounds");
        disposables.add(insertPlaygroundsInDbUseCase.insertPlaygrounds(playgrounds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getPlaygroundsInDB, t -> Timber.e("error inserting playgrounds")));

    }

    private void getPlaygroundsInDB() {
        disposables.add(getPlaygroundsInDbUseCase.getPlaygrounds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(playgroundsLiveData::setValue, t -> Timber.e(t, "error in getting playground from locale db")));
    }

    public MutableLiveData<List<Playground>> getPlaygroundsLiveData() {
        return playgroundsLiveData;
    }

    private void onFetchingFailed(List<Playground> playground) {

    }
}