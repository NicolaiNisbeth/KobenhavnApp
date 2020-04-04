package com.example.kobenhavn.di;

import com.example.kobenhavn.view.playgrounds.ContainerPlaygroundsFragment;
import com.example.kobenhavn.view.playgrounds.PlaygroundsFragment;
import com.example.kobenhavn.view.settings.SettingsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector(modules = AuthenticationModule.class)
    abstract SettingsFragment contributeSettingsFragment();

    @ContributesAndroidInjector(modules = AuthenticationModule.class)
    abstract ContainerPlaygroundsFragment contributeContainerPlaygroundsFragment();
}
