package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.sync.jobs.FetchPlaygroundsJob;
import com.example.kobenhavn.dal.sync.jobs.LoginUserJob;
import com.example.kobenhavn.dal.sync.jobs.SignupUserJob;
import com.example.kobenhavn.dal.sync.jobs.SyncPlaygroundJob;
import com.example.kobenhavn.dal.sync.jobs.setup.JobManagerFactory;

import io.reactivex.Completable;

public class RemoteRepository implements IRemoteRepository {

    @Override
    public Completable sync(Playground playground) {
        // adds a new job in the background
        return Completable.fromAction(() -> JobManagerFactory.getJobManager().addJobInBackground(new SyncPlaygroundJob(playground)));
    }

    @Override
    public Completable sync(User user) {
        return null;
    }

    @Override
    public Completable fetchPlaygrounds() {
        return Completable.fromAction(() -> JobManagerFactory.getJobManager().addJobInBackground(new FetchPlaygroundsJob()));
    }

    @Override
    public Completable loginUser(String username, String password) {
        return Completable.fromAction(() -> JobManagerFactory.getJobManager().addJobInBackground(new LoginUserJob(username, password)));
    }

    @Override
    public Completable signupUser(String name, String username, String password) {
        return Completable.fromAction(() -> JobManagerFactory.getJobManager().addJobInBackground(new SignupUserJob(name, username, password)));
    }
}
