package com.example.kobenhavn.dal.sync;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.usecases.playground.UnsubscribeToPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.UpdatePlaygroundUseCase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Updates local database after remote comment sync requests
 */
public class SyncPlaygroundLifecycleObserver implements LifecycleObserver {
    private final UpdatePlaygroundUseCase updatePlaygroundUseCase;
    private final UnsubscribeToPlaygroundUseCase unsubscribeToPlaygroundUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public SyncPlaygroundLifecycleObserver(UpdatePlaygroundUseCase updatePlaygroundUseCase, UnsubscribeToPlaygroundUseCase unsubscribeToPlaygroundUseCase) {
        this.updatePlaygroundUseCase = updatePlaygroundUseCase;
        this.unsubscribeToPlaygroundUseCase = unsubscribeToPlaygroundUseCase;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Timber.e("onResume lifecycle event.");
        disposables.add(SyncPlaygroundRxBus.getInstance()
                .toObservable()
                .subscribe(this::handleSyncResponse, t -> Timber.e(t, "error handling sync response")));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Timber.e("onPause lifecycle event.");
        disposables.clear();
    }

    private void handleSyncResponse(SyncPlaygroundRxBus.SyncPlaygroundResponse response) {
        if (response.type == SyncResponseType.SUCCESS) {
            onSyncCommentSuccess(response.playground);
        } else {
            onSyncCommentFailed(response.playground);
        }
    }

    private void onSyncCommentSuccess(Playground playground) {
        Timber.e("received sync playground success event for %s", playground);
        disposables.add(updatePlaygroundUseCase.updatePlayground(playground)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("update playground success"),
                        t -> Timber.e(t, "update playground error")));
    }

    private void onSyncCommentFailed(Playground playground) {
        Timber.d("received sync playground failed event for playground %s", playground);
        disposables.add(unsubscribeToPlaygroundUseCase.deletePlayground(playground)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("delete playground success"),
                        t -> Timber.e(t, "delete playground error")));
    }
}