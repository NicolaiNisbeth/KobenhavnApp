package com.example.kobenhavn.usecases.playground;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Playground;

import java.util.List;

import io.reactivex.Completable;

public class UpdatePlaygroundsInDbUseCase {
    private final ILocalRepository localRepository;

    public UpdatePlaygroundsInDbUseCase(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Completable updatePlaygrounds(List<Playground> playgrounds){
        return localRepository.update(playgrounds);
    }
}
