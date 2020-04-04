package com.example.kobenhavn.di;

import com.example.kobenhavn.view.MainActivity;
import com.example.kobenhavn.view.authentication.LoginActivity;
import com.example.kobenhavn.view.authentication.SignUpActivity;
import com.example.kobenhavn.view.playgrounds.add.AddPlaygroundActivity;

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

    @ContributesAndroidInjector(modules = {MenuModule.class, MainFragmentBuildersModule.class})
    abstract MainActivity bindMenuActivity();


    @ContributesAndroidInjector(modules = {MenuModule.class})
    abstract AddPlaygroundActivity bindAddPlaygroundActivity();

    // Add bindings for other sub-components here
}
