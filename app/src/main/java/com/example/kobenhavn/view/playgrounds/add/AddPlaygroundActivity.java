package com.example.kobenhavn.view.playgrounds.add;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.view.EmptyRecyclerView;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModel;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModelFactory;
import com.example.kobenhavn.viewmodel.UserViewModel;
import com.example.kobenhavn.viewmodel.UserViewModelFactory;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import timber.log.Timber;

public class AddPlaygroundActivity extends AppCompatActivity {

    private EmptyRecyclerView recyclerView;
    private AddPlaygroundAdapter recyclerViewAdapter;

    @Inject
    PlaygroundsViewModelFactory playgroundViewModelFactory;

    @BindView(R.id.events_enrolled_empty_msg)
    TextView _emptyView;

    @Inject
    UserViewModelFactory userViewModelFactory;

    private PlaygroundsViewModel playgroundsViewModel;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playgrounds_add_activity);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.legepladser_tilfoj_toolbar);
        setSupportActionBar(toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Tilføj Legeplads");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);
        recyclerViewAdapter = new AddPlaygroundAdapter(this, new ArrayList<>(), userViewModel);
        recyclerView = findViewById(R.id.recycler_view_tilfoj_legeplads);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setEmptyView(_emptyView);

        playgroundsViewModel = ViewModelProviders.of(this, playgroundViewModelFactory).get(PlaygroundsViewModel.class);
        playgroundsViewModel.playgroundsLive().observe(this, recyclerViewAdapter::updatePlaygroundList);
        userViewModel.getUser(RemoteDataSource.loggedInUser.getUsername()).observe(this, recyclerViewAdapter::filterPlaygroundList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
