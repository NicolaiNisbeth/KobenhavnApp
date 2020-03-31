package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.sync.jobs.SyncPlaygroundJob;
import com.example.kobenhavn.dal.sync.jobs.setup.JobManagerFactory;

import io.reactivex.Completable;

public class RemoteRepository implements IRemoteRepository {

    @Override
    public Completable sync(Playground playground) {
        // adds a new job in the background
        return Completable.fromAction(() -> JobManagerFactory.getJobManager().addJobInBackground(new SyncPlaygroundJob(playground)));
    }
}
