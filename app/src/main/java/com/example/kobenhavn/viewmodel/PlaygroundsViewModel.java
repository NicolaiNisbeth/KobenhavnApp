package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.kobenhavn.dal.local.model.Playground;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PlaygroundsViewModel extends ViewModel {
    private final GetPlaygroundsUseCase getPlaygroundsUseCase;
    private final AddPlaygroundUseCase addPlaygroundUseCase;
    private final DeletePlaygroundUseCase deletePlaygroundUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<List<Playground>> playgroundsLiveData = new MutableLiveData<>();

    public PlaygroundsViewModel(GetPlaygroundsUseCase getPlaygroundsUseCase,
              AddPlaygroundUseCase addPlaygroundUseCase,
              DeletePlaygroundUseCase deletePlaygroundUseCase){

        this.getPlaygroundsUseCase = getPlaygroundsUseCase;
        this.addPlaygroundUseCase = addPlaygroundUseCase;
        this.deletePlaygroundUseCase = deletePlaygroundUseCase;

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
        disposables.add(addPlaygroundUseCase.addPlayground(commentText)
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
