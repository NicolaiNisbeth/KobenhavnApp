package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.sync.jobs.FetchPlaygroundsJob;
import com.example.kobenhavn.dal.sync.jobs.LoginUserJob;
import com.example.kobenhavn.dal.sync.jobs.RemoveEventFromUserJob;
import com.example.kobenhavn.dal.sync.jobs.SignupUserJob;
import com.example.kobenhavn.dal.sync.jobs.SyncPlaygroundJob;
import com.example.kobenhavn.dal.sync.jobs.SyncUserJob;
import com.example.kobenhavn.dal.sync.jobs.UpdateUserWithSubscriptionsJob;
import com.example.kobenhavn.dal.sync.jobs.UserJoinEventJob;
import com.example.kobenhavn.dal.sync.jobs.setup.JobManagerFactory;

import java.util.List;

import io.reactivex.Completable;

/**
 * Adds a new job in background thread to be fired at remote REST API
 */
public class RemoteRepository implements IRemoteRepository {

    @Override
    public Completable sync(Playground playground) {
        return Completable.fromAction(() -> JobManagerFactory.getJobManager().addJobInBackground(new SyncPlaygroundJob(playground)));
    }

    @Override
    public Completable sync(User user) {
        return Completable.fromAction(() -> JobManagerFactory.getJobManager().addJobInBackground(new SyncUserJob(user)));
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

    @Override
    public Completable joinUserWithEvent(String playgroundName, String eventID, String username){
        return Completable.fromAction(() -> JobManagerFactory.getJobManager().addJobInBackground(new UserJoinEventJob(playgroundName, eventID, username)));
    }

    @Override
    public Completable removeEventFromUser(String playgroundName, String eventID, String username) {
        return Completable.fromAction(() -> JobManagerFactory.getJobManager().addJobInBackground(new RemoveEventFromUserJob(playgroundName, eventID, username)));
    }

    @Override
    public Completable updateUserWithSubscriptions(String username, List<Playground> playgrounds) {
        return Completable.fromAction(() -> JobManagerFactory.getJobManager().addJobInBackground(new UpdateUserWithSubscriptionsJob(username, playgrounds)));
    }


}
