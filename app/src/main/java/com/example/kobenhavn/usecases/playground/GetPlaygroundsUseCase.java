package com.example.kobenhavn.usecases.playground;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.Playground;

import java.util.List;

import io.reactivex.Flowable;

public class GetPlaygroundsUseCase {
    private final ILocalRepository localRepository;

    public GetPlaygroundsUseCase(ILocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Flowable<List<Playground>> getPlaygrounds(){
        return localRepository.getPlaygrounds();
    }
}
