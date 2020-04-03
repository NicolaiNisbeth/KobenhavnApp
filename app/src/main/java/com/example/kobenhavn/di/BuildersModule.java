package com.example.kobenhavn.di;

import com.example.kobenhavn.view.MenuActivity;
import com.example.kobenhavn.view.authentication.LoginActivity;
import com.example.kobenhavn.view.authentication.SignUpActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-components within the app.
 */
@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = {AuthenticationModule.class, MenuModule.class})
    abstract LoginActivity bindLoginsActivity();

    @ContributesAndroidInjector(modules = AuthenticationModule.class)
    abstract SignUpActivity bindSignupActivity();

    @ContributesAndroidInjector(modules = {MenuModule.class, MenuFragmentBuildersModule.class})
    abstract MenuActivity bindMenuActivity();

    // Add bindings for other sub-components here
}
