package com.example.kobenhavn.view;

import android.os.Bundle;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.sync.SyncPlaygroundLifecycleObserver;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModel;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import timber.log.Timber;

public class MenuActivity extends AppCompatActivity {

    @Inject
    SyncPlaygroundLifecycleObserver syncPlaygroundLifecycleObserver;

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
        getLifecycle().addObserver(syncPlaygroundLifecycleObserver);

        // Bottom Navigation
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PlaygroundsViewModel.class);

        if (savedInstanceState == null){
            // fetch and update


        }
    }
}
