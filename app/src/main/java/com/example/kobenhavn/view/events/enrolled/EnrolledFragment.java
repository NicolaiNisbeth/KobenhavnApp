package com.example.kobenhavn.view.events.enrolled;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.view.events.CardActivity;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModel;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModelFactory;
import com.example.kobenhavn.viewmodel.UserViewModel;
import com.example.kobenhavn.viewmodel.UserViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class EnrolledFragment extends Fragment {
    private RecyclerView recyclerView;
    private EnrolledAdapter adapter;

    @Inject
    UserViewModelFactory userViewModelFactory;

    private UserViewModel userViewModel;

    public EnrolledFragment() { }
    public static EnrolledFragment newInstance() {
        return new EnrolledFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        final View root = inflater.inflate(R.layout.events_enrolled_fragment, container, false);
        recyclerView = root.findViewById(R.id.recycler_view_enrolled);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        adapter = new EnrolledAdapter(root.getContext());
        recyclerView.setAdapter(adapter);

        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);
        userViewModel.getUser(RemoteDataSource.loggedInUser.getUsername()).observe(getViewLifecycleOwner(), adapter::updateEnrolledList);

        adapter.setOnItemClickListener(event -> {
            Intent intent = new Intent(getContext(), CardActivity.class);
            intent.putExtra(CardActivity.EXTRA_DATE, event.getDetails().getDate().toString());
            intent.putExtra(CardActivity.EXTRA_TITLE, event.getName());
            intent.putExtra(CardActivity.EXTRA_TIME, event.getDetails().getStartTime().toString());
            intent.putExtra(CardActivity.EXTRA_SUBTITLE, event.getSubtitle());
            intent.putExtra(CardActivity.EXTRA_DESCRIPTION, event.getDescription());
            intent.putExtra(CardActivity.EXTRA_INTERESTED, event.getParticipants());
            intent.putExtra(CardActivity.EXTRA_PLAYGROUND_NAME, event.getPlaygroundName());
            intent.putExtra(CardActivity.EXTRA_EVENT_ID, event.getId());
            startActivity(intent);
        });

        return root;
    }
}
