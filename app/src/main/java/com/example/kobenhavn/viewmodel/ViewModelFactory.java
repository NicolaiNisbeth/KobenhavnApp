package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kobenhavn.usecases.playground.AddPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.DeletePlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsUseCase;

import org.jetbrains.annotations.NotNull;


public class ViewModelFactory implements ViewModelProvider.Factory {
    private GetPlaygroundsUseCase getPlaygroundsUseCase;
    private AddPlaygroundUseCase addPlaygroundsUseCase;
    private DeletePlaygroundUseCase removePlaygroundsUseCase;


    public ViewModelFactory(GetPlaygroundsUseCase getPlaygroundsUseCase,
                             AddPlaygroundUseCase addPlaygroundsUseCase,
                             DeletePlaygroundUseCase removePlaygroundsUseCase){

        this.getPlaygroundsUseCase = getPlaygroundsUseCase;
        this.addPlaygroundsUseCase = addPlaygroundsUseCase;
        this.removePlaygroundsUseCase = removePlaygroundsUseCase;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PlaygroundsViewModel.class)){
            return (T) new PlaygroundsViewModel(getPlaygroundsUseCase, addPlaygroundsUseCase, removePlaygroundsUseCase);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
