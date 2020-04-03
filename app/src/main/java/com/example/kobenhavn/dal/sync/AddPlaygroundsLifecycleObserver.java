package com.example.kobenhavn.dal.sync;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.usecases.playground.AddPlaygroundsToDbUseCase;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Updates local database after remote comment sync requests
 */
public class AddPlaygroundsLifecycleObserver implements LifecycleObserver {
    private final AddPlaygroundsToDbUseCase addPlaygroundsToDbUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public AddPlaygroundsLifecycleObserver(AddPlaygroundsToDbUseCase addPlaygroundsToDbUseCase) {
        this.addPlaygroundsToDbUseCase = addPlaygroundsToDbUseCase;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Timber.e("onResume lifecycle event.");
        disposables.add(SyncPlaygroundsRxBus.getInstance()
                .toObservable()
                .subscribe(this::handleSyncResponse, t -> Timber.e(t, "error handling sync response")));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Timber.e("onPause lifecycle event.");
        disposables.clear();
    }

    private void handleSyncResponse(SyncPlaygroundsRxBus.SyncPlaygroundResponse response) {
        if (response.type == SyncResponseType.SUCCESS) {
            onFetchingPlaygroundsSuccess(response.playgrounds);
        } else {
            onFetchingPlaygroundsFailed(response.playgrounds);
        }
    }

    private void onFetchingPlaygroundsSuccess(List<Playground> playgrounds) {
        Timber.e("received successfully fetched playgrounds %s", playgrounds);
        disposables.add(addPlaygroundsToDbUseCase.addPlaygrounds(playgrounds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("updated locale playgrounds successfully"),
                        t -> Timber.e(t, "Error in updating locale playgrounds")));
    }

    private void onFetchingPlaygroundsFailed(List<Playground> playground) {
        Timber.d("Error in adding playgrounds %s", playground);
        /*
        disposables.add(unsubscribeToPlaygroundUseCase.deletePlayground(playground)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("delete playground success"),
                        t -> Timber.e(t, "delete playground error")));

         */
    }
}