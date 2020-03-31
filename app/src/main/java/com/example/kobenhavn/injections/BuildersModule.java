package com.example.kobenhavn.injections;

import com.example.offline.view.PLaygroundsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-components within the app.
 */
@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = PlaygroundsActivityModule.class)
    abstract PLaygroundsActivity bindCommentsActivity();

    // Add bindings for other sub-components here
}
