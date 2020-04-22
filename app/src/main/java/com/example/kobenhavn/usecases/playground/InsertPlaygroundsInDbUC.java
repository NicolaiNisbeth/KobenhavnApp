package com.example.kobenhavn.usecases.playground;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Playground;

import java.util.List;

import io.reactivex.Completable;

public class InsertPlaygroundsInDbUC {
    private final ILocalRepository localRepository;

    public InsertPlaygroundsInDbUC(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Completable insertPlaygrounds(List<Playground> playgrounds){
        return localRepository.insertPlaygrounds(playgrounds);
    }
}
