package com.example.kobenhavn.view.events.future;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.view.events.CardActivity;

import java.util.List;

public class FutureFragment extends Fragment {
    private static List<Event> futureEvents;
    private RecyclerView recyclerView;
    private FutureAdapter adapter;

    public FutureFragment() { }

    public static FutureFragment newInstance(List<Event> futureEvents) {
        FutureFragment.futureEvents = futureEvents;
        return new FutureFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.events_future_fragment, container, false);
        recyclerView = root.findViewById(R.id.recycler_view_future);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        adapter = new FutureAdapter(root.getContext(), futureEvents);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(event -> {
            Intent intent = new Intent(getContext(), CardActivity.class);
            intent.putExtra(CardActivity.EXTRA_DATE, event.getDate());
            intent.putExtra(CardActivity.EXTRA_TITLE, event.getTitle());
            intent.putExtra(CardActivity.EXTRA_TIME, event.getTime());
            intent.putExtra(CardActivity.EXTRA_SUBTITLE, event.getSubtitle());
            intent.putExtra(CardActivity.EXTRA_DESCRIPTION, event.getDescription());
            intent.putExtra(CardActivity.EXTRA_INTERESTED, event.getInterested());
            startActivity(intent);
        });
        return root;
    }
}
