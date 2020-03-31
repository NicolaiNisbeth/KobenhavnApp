package com.example.kobenhavn;

import android.app.Activity;
import android.app.Application;

import com.example.kobenhavn.dal.sync.jobs.setup.JobManagerFactory;
import com.example.kobenhavn.injections.DaggerAppComponent;
import javax.inject.Inject;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);

        JobManagerFactory.getJobManager(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
