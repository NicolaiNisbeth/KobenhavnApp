package com.example.kobenhavn;

import android.os.Bundle;

import com.example.kobenhavn.dal.sync.SyncPlaygroundLifecycleObserver;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleRegistry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject
    SyncPlaygroundLifecycleObserver syncPlaygroundLifecycleObserver;

    private LifecycleRegistry registry;

    @Override
    public LifecycleRegistry getLifecycle() {
        if (registry == null)
            registry = new LifecycleRegistry(this);

        return registry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        Timber.plant(new Timber.DebugTree());
        getLifecycle().addObserver(syncPlaygroundLifecycleObserver);



        // Bottom Navigation
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }
}
