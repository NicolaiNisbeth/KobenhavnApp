package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.usecases.playground.FetchPlaygroundsUseCase;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsInDbUseCase;
import com.example.kobenhavn.usecases.playground.SubscribeToPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.UnsubscribeToPlaygroundUseCase;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PlaygroundsViewModel extends ViewModel {
    private final GetPlaygroundsInDbUseCase getPlaygroundsInDbUseCase;
    private final FetchPlaygroundsUseCase fetchPlaygroundsUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<List<Playground>> playgroundsLiveData = new MutableLiveData<>();

    public PlaygroundsViewModel(GetPlaygroundsInDbUseCase getPlaygroundsInDbUseCase,
                                FetchPlaygroundsUseCase fetchPlaygroundsUseCase){

        this.getPlaygroundsInDbUseCase = getPlaygroundsInDbUseCase;
        this.fetchPlaygroundsUseCase = fetchPlaygroundsUseCase;

        loadPlaygrounds();
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public LiveData<List<Playground>> playgroundsLive() {
        return playgroundsLiveData;
    }

    public void fetchPlaygrounds() {
        disposables.add(fetchPlaygroundsUseCase.fetchPlaygrounds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Timber.e("Fetched playgrounds successfully");
                    loadPlaygrounds();
                    }, t -> Timber.e(t, "Error in fetching playgrounds")));
    }

    public void loadPlaygrounds(){
        disposables.add(getPlaygroundsInDbUseCase.getPlaygrounds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(playgroundsLiveData::setValue,
                        t -> Timber.e(t, "Get playgrounds from db error")));
    }
}
