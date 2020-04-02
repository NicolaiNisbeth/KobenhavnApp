package com.example.kobenhavn.usecases.playground;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Playground;

import java.util.List;

import io.reactivex.Flowable;

public class GetPlaygroundsInDbUseCase {
    private final ILocalRepository localRepository;

    public GetPlaygroundsInDbUseCase(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Flowable<List<Playground>> getPlaygrounds(){
        return localRepository.getPlaygrounds();
    }
}
