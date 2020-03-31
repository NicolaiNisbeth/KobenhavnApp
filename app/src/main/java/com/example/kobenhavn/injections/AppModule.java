package com.example.kobenhavn.injections;

import android.content.Context;

import com.example.offline.App;
import com.example.offline.dal.local.Database;
import com.example.offline.dal.local.ILocalRepository;
import com.example.offline.dal.local.LocalRepository;
import com.example.offline.dal.local.PlaygroundDAO;
import com.example.offline.dal.remote.IRemoteRepository;
import com.example.offline.dal.remote.RemoteRepository;
import com.example.offline.dal.sync.jobs.setup.GCMJobService;
import com.example.offline.dal.sync.jobs.setup.SchedulerJobService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This is where you will inject application-wide dependencies.
 */
@Module
public class AppModule {

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
    PlaygroundDAO providePlaygroundDao(Context context) {
        return Database.getInstance(context).playgroundDAO();
    }

    @Singleton
    @Provides
    ILocalRepository provideLocalCommentRepository(PlaygroundDAO playgroundDAO) {
        return new LocalRepository(playgroundDAO);
    }

    @Singleton
    @Provides
    IRemoteRepository provideRemoteCommentRepository() {
        return new RemoteRepository();
    }
}
