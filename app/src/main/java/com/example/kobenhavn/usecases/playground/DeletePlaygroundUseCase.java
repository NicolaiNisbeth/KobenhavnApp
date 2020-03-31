package com.example.kobenhavn.usecases.playground;


import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Playground;

import io.reactivex.Completable;

public class DeletePlaygroundUseCase {
    private final ILocalRepository localRepository;

    public DeletePlaygroundUseCase(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Completable deletePlayground(Playground playground){
        return localRepository.delete(playground);
    }
}
