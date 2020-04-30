package com.example.kobenhavn.view;

import android.os.Bundle;

import com.example.kobenhavn.R;
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

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    MainLifecycleObserver mainLifecycleObserver;

    @Inject
    PlaygroundsViewModelFactory viewModelFactory;

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

        getLifecycle().addObserver(mainLifecycleObserver);
        // TO test with no connection
        //RemoteDataSource.loggedInUser = new User("anonymous", "123");

        // Bottom Navigation
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }
}
