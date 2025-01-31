package com.example.kobenhavn.view.events.enrolled;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.view.EmptyRecyclerView;
import com.example.kobenhavn.view.events.CardActivity;
import com.example.kobenhavn.viewmodel.UserViewModel;
import com.example.kobenhavn.viewmodel.UserViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class EnrolledFragment extends Fragment {

    @Inject
    UserViewModelFactory userViewModelFactory;

    @BindView(R.id.events_enrolled_empty_msg)
    TextView _emptyView;

    EnrolledAdapter adapter;

    public EnrolledFragment() { }
    public static EnrolledFragment newInstance() {
        return new EnrolledFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.events_enrolled_fragment, container, false);
        ButterKnife.bind(this, root);

        initRecyclerView(root);

        // setup observer
        UserViewModel userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);
        LiveData<List<Event>> liveEvent = userViewModel.getEventsLive(RemoteDataSource.loggedInUser.getUsername());
        liveEvent.observe(getViewLifecycleOwner(), adapter::updateEnrolledList);

        // TODO: pass object instead of fields
        adapter.setOnItemClickListener(event -> {
            Intent intent = new Intent(getContext(), CardActivity.class);
            intent.putExtra(CardActivity.EXTRA_DATE, (Parcelable) event.getDetails());
            intent.putExtra(CardActivity.EXTRA_NAME, event.getName());
            if (event.getDetails() != null)
                intent.putExtra(CardActivity.EXTRA_STARTTIME, event.getDetails().getStartTime().toString());
            intent.putExtra(CardActivity.EXTRA_SUBTITLE, event.getSubtitle());
            intent.putExtra(CardActivity.EXTRA_DESCRIPTION, event.getDescription());
            intent.putExtra(CardActivity.EXTRA_INTERESTED, event.getParticipants());
            intent.putExtra(CardActivity.EXTRA_PLAYGROUND_NAME, event.getPlaygroundName());
            intent.putExtra(CardActivity.EXTRA_EVENT_ID, event.getId());
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    private void initRecyclerView(View root) {
        EmptyRecyclerView recyclerView = root.findViewById(R.id.recycler_view_enrolled);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        adapter = new EnrolledAdapter(root.getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(_emptyView);
    }
}
