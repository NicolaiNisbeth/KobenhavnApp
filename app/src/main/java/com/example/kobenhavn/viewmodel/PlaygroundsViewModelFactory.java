package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kobenhavn.usecases.playground.AddPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.DeletePlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsUseCase;

public class PlaygroundsViewModelFactory implements ViewModelProvider.Factory {
    private final GetPlaygroundsUseCase getPlaygroundsUseCase;
    private final AddPlaygroundUseCase addPlaygroundsUseCase;
    private final DeletePlaygroundUseCase removePlaygroundsUseCase;

    public PlaygroundsViewModelFactory(GetPlaygroundsUseCase getPlaygroundsUseCase,
                                       AddPlaygroundUseCase addPlaygroundsUseCase,
                                       DeletePlaygroundUseCase removePlaygroundsUseCase){

        this.getPlaygroundsUseCase = getPlaygroundsUseCase;
        this.addPlaygroundsUseCase = addPlaygroundsUseCase;
        this.removePlaygroundsUseCase = removePlaygroundsUseCase;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PlaygroundsViewModel.class)){
            return (T) new PlaygroundsViewModel(getPlaygroundsUseCase, addPlaygroundsUseCase, removePlaygroundsUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
