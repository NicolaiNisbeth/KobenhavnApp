package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.usecases.playground.SubscribeToPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.UnsubscribeToPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsUseCase;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PlaygroundsViewModel extends ViewModel {
    private final GetPlaygroundsUseCase getPlaygroundsUseCase;
    private final SubscribeToPlaygroundUseCase subscribeToPlaygroundUseCase;
    private final UnsubscribeToPlaygroundUseCase unsubscribeToPlaygroundUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<List<Playground>> playgroundsLiveData = new MutableLiveData<>();

    public PlaygroundsViewModel(GetPlaygroundsUseCase getPlaygroundsUseCase,
              SubscribeToPlaygroundUseCase subscribeToPlaygroundUseCase,
              UnsubscribeToPlaygroundUseCase unsubscribeToPlaygroundUseCase){

        this.getPlaygroundsUseCase = getPlaygroundsUseCase;
        this.subscribeToPlaygroundUseCase = subscribeToPlaygroundUseCase;
        this.unsubscribeToPlaygroundUseCase = unsubscribeToPlaygroundUseCase;

        loadPlaygrounds();
    }


    @Override
    protected void onCleared() {
        disposables.clear();
    }

    /**
     * Adds new comment
     */
    public void addPlayground(String commentText) {
        disposables.add(subscribeToPlaygroundUseCase.addPlayground(commentText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("add playground success"),
                        t -> Timber.e(t, "add playground error")));
    }

    /**
     * Exposes the latest comments so the UI can observe it
     */
    public LiveData<List<Playground>> comments() {
        return playgroundsLiveData;
    }

    public void loadPlaygrounds() {
        disposables.add(getPlaygroundsUseCase.getPlaygrounds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(playgroundsLiveData::setValue,
                        t -> Timber.e(t, "get playground error")));
    }
}
