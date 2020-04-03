package com.example.kobenhavn.di;

import com.example.kobenhavn.view.settings.SettingsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class MenuFragmentBuildersModule {

    @ContributesAndroidInjector(modules = AuthenticationModule.class)
    abstract SettingsFragment contributeSettingsFragment();
}
