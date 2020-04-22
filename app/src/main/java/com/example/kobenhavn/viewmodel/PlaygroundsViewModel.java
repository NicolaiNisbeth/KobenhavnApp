package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.usecases.playground.FetchPlaygroundUC;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsInDbUC;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PlaygroundsViewModel extends ViewModel {
    private final GetPlaygroundsInDbUC getPlaygroundsInDbUC;
    private final FetchPlaygroundUC fetchPlaygroundUC;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<List<Playground>> playgroundsLiveData = new MutableLiveData<>();

    PlaygroundsViewModel(GetPlaygroundsInDbUC getPlaygroundsInDbUC,
                         FetchPlaygroundUC fetchPlaygroundUC){

        this.getPlaygroundsInDbUC = getPlaygroundsInDbUC;
        this.fetchPlaygroundUC = fetchPlaygroundUC;

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
        disposables.add(fetchPlaygroundUC.fetchPlaygrounds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Timber.e("Fetched playgrounds successfully");
                    }, t -> Timber.e(t, "Error in fetching playgrounds")));
    }

    private void loadPlaygrounds(){
        disposables.add(getPlaygroundsInDbUC.getPlaygrounds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(playgroundsLiveData::setValue,
                        t -> Timber.e(t, "Get playgrounds from db error")));
    }
}
