package com.example.kobenhavn.view;

import android.os.Bundle;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.sync.PlaygroundsLifecycleObserver;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModel;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.lifecycle.LifecycleRegistry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;
import timber.log.Timber;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    PlaygroundsLifecycleObserver playgroundsLifecycleObserver;

    @Inject
    PlaygroundsViewModelFactory viewModelFactory;

    private PlaygroundsViewModel viewModel;
    private LifecycleRegistry registry;

    @NotNull
    @Override
    public LifecycleRegistry getLifecycle() {
        if (registry == null)
            registry = new LifecycleRegistry(this);

        return registry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Timber.plant(new Timber.DebugTree());
        getLifecycle().addObserver(playgroundsLifecycleObserver);

        // Bottom Navigation
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }
}
