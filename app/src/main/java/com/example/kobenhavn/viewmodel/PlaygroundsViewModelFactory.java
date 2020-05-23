package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kobenhavn.usecases.playground.FetchPlaygroundUC;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsInDbUC;
import com.example.kobenhavn.usecases.playground.InsertPlaygroundsInDbUC;

public class PlaygroundsViewModelFactory implements ViewModelProvider.Factory {
    private final GetPlaygroundsInDbUC getPlaygroundsInDbUC;
    private final FetchPlaygroundUC fetchPlaygroundUC;
    private final InsertPlaygroundsInDbUC insertPlaygroundsInDb;

    public PlaygroundsViewModelFactory(GetPlaygroundsInDbUC getPlaygroundsInDbUC, FetchPlaygroundUC fetchPlaygroundUC, InsertPlaygroundsInDbUC insertPlaygroundsInDb) {
        this.getPlaygroundsInDbUC = getPlaygroundsInDbUC;
        this.fetchPlaygroundUC = fetchPlaygroundUC;
        this.insertPlaygroundsInDb = insertPlaygroundsInDb;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PlaygroundsViewModel.class)){
            return (T) new PlaygroundsViewModel(getPlaygroundsInDbUC, fetchPlaygroundUC, insertPlaygroundsInDb);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
