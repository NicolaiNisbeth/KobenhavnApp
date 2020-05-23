package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.sync.job.FetchPlaygroundsJob;
import com.example.kobenhavn.dal.sync.job.LoginUserJob;
import com.example.kobenhavn.dal.sync.job.LeaveEventJob;
import com.example.kobenhavn.dal.sync.job.SignupUserJob;
import com.example.kobenhavn.dal.sync.job.UpdateUserJob;
import com.example.kobenhavn.dal.sync.job.JoinEventJob;
import com.example.kobenhavn.dal.sync.job.setup.JobManagerFactory;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * Adds a new job in background thread to be fired at remote REST API
 */
public class RemoteRepository implements IRemoteRepository {

    @Override
    public Completable loginUser(String username, String password) {
        return Completable.fromAction(() -> JobManagerFactory.getJobManager().addJobInBackground(new LoginUserJob(username, password)));
    }

    @Override
    public Completable signupUser(String name, String username, String password) {
        return Completable.fromAction(() -> JobManagerFactory.getJobManager().addJobInBackground(new SignupUserJob(name, username, password)));
    }

    @Override
    public Completable updateUser(User user) {
        return Completable.fromAction(() -> JobManagerFactory.getJobManager().addJobInBackground(new UpdateUserJob(user)));
    }

    @Override
    public Completable fetchPlaygrounds() {
        return Completable.fromAction(() -> JobManagerFactory.getJobManager().addJobInBackground(new FetchPlaygroundsJob()));
    }

    @Override
    public Completable joinEvent(String playgroundName, Event event, User user){
        return Completable.fromAction(() -> JobManagerFactory.getJobManager().addJobInBackground(new JoinEventJob(playgroundName, event, user)));
    }

    @Override
    public Completable leaveEvent(String playgroundName, Event event, User user) {
        return Completable.fromAction(() -> JobManagerFactory.getJobManager().addJobInBackground(new LeaveEventJob(playgroundName, event, user)));
    }
}
