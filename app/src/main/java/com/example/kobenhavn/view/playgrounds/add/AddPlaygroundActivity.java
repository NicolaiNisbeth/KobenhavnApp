package com.example.kobenhavn.view.playgrounds.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.TextView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.dal.sync.FetchPlaygroundsRxBus;
import com.example.kobenhavn.dal.sync.LoginUserRxBus;
import com.example.kobenhavn.dal.sync.RemoteResponseType;
import com.example.kobenhavn.usecases.playground.InsertPlaygroundsInDbUC;
import com.example.kobenhavn.view.EmptyRecyclerView;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModel;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModelFactory;
import com.example.kobenhavn.viewmodel.UserViewModel;
import com.example.kobenhavn.viewmodel.UserViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class AddPlaygroundActivity extends AppCompatActivity {

    @Inject PlaygroundsViewModelFactory playgroundViewModelFactory;
    @Inject UserViewModelFactory userViewModelFactory;

    @BindView(R.id.events_enrolled_empty_msg) TextView _emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playgrounds_add_activity);
        ButterKnife.bind(this);
        setupToolbar();

        UserViewModel userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);
        AddPlaygroundAdapter recyclerViewAdapter = new AddPlaygroundAdapter(this, new ArrayList<>(), userViewModel);
        EmptyRecyclerView recyclerView = findViewById(R.id.recycler_view_tilfoj_legeplads);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setEmptyView(_emptyView);

        PlaygroundsViewModel playgroundsViewModel = ViewModelProviders.of(this, playgroundViewModelFactory).get(PlaygroundsViewModel.class);
        playgroundsViewModel.playgroundsLive().observe(this, recyclerViewAdapter::updatePlaygroundList);
        userViewModel.getSubscriptionsLive(RemoteDataSource.loggedInUser.getUsername()).observe(this, recyclerViewAdapter::filterSubscribedPlaygrounds);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.legepladser_tilfoj_toolbar);
        setSupportActionBar(toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Tilføj Legeplads");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
