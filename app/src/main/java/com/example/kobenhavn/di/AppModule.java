package com.example.kobenhavn.di;

import android.content.Context;

import com.example.kobenhavn.App;
import com.example.kobenhavn.dal.local.Database;
import com.example.kobenhavn.dal.local.EventDAO;
import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.LocalRepository;
import com.example.kobenhavn.dal.local.PlaygroundDAO;
import com.example.kobenhavn.dal.local.SubscriptionsDAO;
import com.example.kobenhavn.dal.local.UserDAO;
import com.example.kobenhavn.dal.remote.IRemoteRepository;
import com.example.kobenhavn.dal.remote.RemoteRepository;
import com.example.kobenhavn.dal.sync.job.setup.GCMJobService;
import com.example.kobenhavn.dal.sync.job.setup.SchedulerJobService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Application-wide dependencies.
 */
@Module
class AppModule {

    @Singleton
    @Provides
    ILocalRepository provideLocalRepository(PlaygroundDAO playgroundDAO, UserDAO userDAO, SubscriptionsDAO subscriptionsDAO, EventDAO eventDAO) {
        return new LocalRepository(playgroundDAO, userDAO, subscriptionsDAO, eventDAO);
    }

    @Singleton
    @Provides
    IRemoteRepository provideRemoteRepository() {
        return new RemoteRepository();
    }

    @Singleton
    @Provides
    EventDAO provideEventDAO(Context context){
        return Database.getInstance(context).eventDAO();
    }

    @Singleton
    @Provides
    PlaygroundDAO providePlaygroundDAO(Context context) {
        return Database.getInstance(context).playgroundDAO();
    }

    @Singleton
    @Provides
    UserDAO provideUserDAO(Context context){
        return Database.getInstance(context).userDAO();
    }

    @Singleton
    @Provides
    SubscriptionsDAO provideSubscriptionsDAO(Context context){
        return Database.getInstance(context).subscriptionsDAO();
    }

    @Provides
    Context provideContext(App application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    SchedulerJobService provideSchedulerJobService() {
        return new SchedulerJobService();
    }

    @Singleton
    @Provides
    GCMJobService provideGcmJobService() {
        return new GCMJobService();
    }
}
