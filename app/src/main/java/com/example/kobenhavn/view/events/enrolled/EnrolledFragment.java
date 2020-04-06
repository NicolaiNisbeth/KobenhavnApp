package com.example.kobenhavn.view.events.enrolled;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.view.events.CardActivity;

import java.util.ArrayList;
import java.util.List;

public class EnrolledFragment extends Fragment {
    private static List<Event> enrolledEvents;
    private RecyclerView recyclerView;
    private EnrolledAdapter adapter;

    public EnrolledFragment() { }
    public static EnrolledFragment newInstance(List<Event> enrolledEvents) {
        EnrolledFragment.enrolledEvents = enrolledEvents;
        return new EnrolledFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.events_enrolled_fragment, container, false);
        recyclerView = root.findViewById(R.id.recycler_view_enrolled);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        adapter = new EnrolledAdapter(root.getContext(), enrolledEvents);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(event -> {
            Intent intent = new Intent(getContext(), CardActivity.class);
            intent.putExtra(CardActivity.EXTRA_DATE, event.getDetails().getDate().toString());
            intent.putExtra(CardActivity.EXTRA_TITLE, event.getName());
            intent.putExtra(CardActivity.EXTRA_TIME, event.getDetails().getStartTime().toString());
            intent.putExtra(CardActivity.EXTRA_SUBTITLE, event.getSubtitle());
            intent.putExtra(CardActivity.EXTRA_DESCRIPTION, event.getDescription());
            intent.putExtra(CardActivity.EXTRA_INTERESTED, event.getParticipants());
            startActivity(intent);
        });

        return root;
    }

}
