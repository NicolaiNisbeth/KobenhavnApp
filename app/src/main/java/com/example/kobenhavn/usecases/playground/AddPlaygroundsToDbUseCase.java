package com.example.kobenhavn.usecases.playground;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Playground;

import java.util.List;

import io.reactivex.Completable;

public class AddPlaygroundsToDbUseCase {
    private final ILocalRepository localRepository;

    public AddPlaygroundsToDbUseCase(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Completable addPlaygrounds(List<Playground> playgrounds){
        return localRepository.add(playgrounds);
    }
}
