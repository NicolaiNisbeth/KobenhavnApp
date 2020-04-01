package com.example.kobenhavn.injections;

import com.example.kobenhavn.view.authentication.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-components within the app.
 */
@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = PlaygroundsActivityModule.class)
    abstract LoginActivity bindCommentsActivity();

    // Add bindings for other sub-components here
}
