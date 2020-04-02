package com.example.kobenhavn.injections;

import android.content.Context;

import com.example.kobenhavn.App;
import com.example.kobenhavn.dal.local.Database;
import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.LocalRepository;
import com.example.kobenhavn.dal.local.PlaygroundDAO;
import com.example.kobenhavn.dal.remote.IRemoteRepository;
import com.example.kobenhavn.dal.remote.RemoteRepository;
import com.example.kobenhavn.dal.sync.jobs.setup.GCMJobService;
import com.example.kobenhavn.dal.sync.jobs.setup.SchedulerJobService;
import com.example.kobenhavn.view.authentication.data.AuthRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This is where you will inject application-wide dependencies.
 */
@Module
class AppModule {

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

    @Singleton
    @Provides
    PlaygroundDAO providePlaygroundDAO(Context context) {
        return Database.getInstance(context).playgroundDAO();
    }

    @Singleton
    @Provides
    ILocalRepository provideLocalRepository(PlaygroundDAO playgroundDAO) {
        return new LocalRepository(playgroundDAO);
    }

    @Singleton
    @Provides
    IRemoteRepository provideRemoteRepository() {
        return new RemoteRepository();
    }

    @Singleton
    @Provides
    AuthRepository provideAuthRepository(){
        return AuthRepository.getInstance();
    }
}
