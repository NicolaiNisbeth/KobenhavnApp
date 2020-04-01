package com.example.kobenhavn.usecases.playground;


import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Playground;

import io.reactivex.Completable;

public class UnsubscribeToPlaygroundUseCase {
    private final ILocalRepository localRepository;

    public UnsubscribeToPlaygroundUseCase(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Completable deletePlayground(Playground playground){
        return localRepository.delete(playground);
    }
}
