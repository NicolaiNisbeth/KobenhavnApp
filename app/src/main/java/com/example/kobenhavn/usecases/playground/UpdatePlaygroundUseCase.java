package com.example.kobenhavn.usecases.playground;

import com.example.offline.dal.local.ILocalRepository;
import com.example.offline.dal.local.model.Playground;

import io.reactivex.Completable;

public class UpdatePlaygroundUseCase {
    private final ILocalRepository localRepository;

    public UpdatePlaygroundUseCase(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Completable updatePlayground(Playground playground){
        return localRepository.update(playground);
    }
}
