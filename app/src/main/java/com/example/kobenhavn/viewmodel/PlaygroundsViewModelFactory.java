package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kobenhavn.usecases.playground.FetchPlaygroundUC;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsInDbUC;

public class PlaygroundsViewModelFactory implements ViewModelProvider.Factory {
    private final GetPlaygroundsInDbUC getPlaygroundsInDbUC;
    private final FetchPlaygroundUC fetchPlaygroundUC;

    public PlaygroundsViewModelFactory(GetPlaygroundsInDbUC getPlaygroundsInDbUC, FetchPlaygroundUC fetchPlaygroundUC) {
        this.getPlaygroundsInDbUC = getPlaygroundsInDbUC;
        this.fetchPlaygroundUC = fetchPlaygroundUC;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PlaygroundsViewModel.class)){
            return (T) new PlaygroundsViewModel(getPlaygroundsInDbUC, fetchPlaygroundUC);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
