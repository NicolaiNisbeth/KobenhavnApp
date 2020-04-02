package com.example.kobenhavn.injections;

import com.example.kobenhavn.view.MenuActivity;
import com.example.kobenhavn.view.authentication.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-components within the app.
 */
@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = AuthenticationModule.class)
    abstract LoginActivity bindCommentsActivity();

    @ContributesAndroidInjector(modules = MenuModule.class)
    abstract MenuActivity bindMenuActivity();

    // Add bindings for other sub-components here
}
