package com.example.kobenhavn.view.events.future;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.view.EmptyRecyclerView;
import com.example.kobenhavn.view.events.CardActivity;
import com.example.kobenhavn.viewmodel.UserViewModel;
import com.example.kobenhavn.viewmodel.UserViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class FutureFragment extends Fragment {

    @Inject
    UserViewModelFactory userViewModelFactory;

    @BindView(R.id.events_enrolled_empty_msg)
    TextView _emptyView;

    public FutureFragment() { }

    public static FutureFragment newInstance() {
        return new FutureFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.events_future_fragment, container, false);
        ButterKnife.bind(this, root);

        EmptyRecyclerView recyclerView = root.findViewById(R.id.recycler_view_future);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        FutureAdapter adapter = new FutureAdapter(root.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(_emptyView);

        UserViewModel userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);
        userViewModel.getSubscriptionsLive(RemoteDataSource.loggedInUser.getUsername()).observe(getViewLifecycleOwner(), adapter::handleFutureEvents);

        // TODO: pass object instead of fields
        adapter.setOnItemClickListener(event -> {
            Intent intent = new Intent(getContext(), CardActivity.class);
            intent.putExtra(CardActivity.EXTRA_DATE, (Parcelable) event.getDetails());
            intent.putExtra(CardActivity.EXTRA_NAME, event.getName());
            intent.putExtra(CardActivity.EXTRA_IMAGE_PATH, event.getImagepath());
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
}
