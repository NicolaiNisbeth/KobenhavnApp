package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kobenhavn.usecases.playground.FetchPlaygroundsUseCase;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsInDbUseCase;
import com.example.kobenhavn.usecases.playground.SubscribeToPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.UnsubscribeToPlaygroundUseCase;

public class PlaygroundsViewModelFactory implements ViewModelProvider.Factory {
    private final GetPlaygroundsInDbUseCase getPlaygroundsInDbUseCase;
    private final FetchPlaygroundsUseCase fetchPlaygroundsUseCase;

    public PlaygroundsViewModelFactory(GetPlaygroundsInDbUseCase getPlaygroundsInDbUseCase, FetchPlaygroundsUseCase fetchPlaygroundsUseCase) {
        this.getPlaygroundsInDbUseCase = getPlaygroundsInDbUseCase;
        this.fetchPlaygroundsUseCase = fetchPlaygroundsUseCase;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PlaygroundsViewModel.class)){
            return (T) new PlaygroundsViewModel(getPlaygroundsInDbUseCase, fetchPlaygroundsUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
