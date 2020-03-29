package com.example.kobenhavn.ui.events.future;

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
import com.example.kobenhavn.ui.events.EventModel;
import com.example.kobenhavn.ui.events.CardActivity;

import java.util.ArrayList;

public class FutureFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<EventModel> eventData;
    private FutureAdapter adapter;

    public FutureFragment() { }
    public static FutureFragment newInstance() {
        return new FutureFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.events_future_fragment, container, false);
        recyclerView = root.findViewById(R.id.recycler_view_future);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        eventData = new ArrayList<>();
        adapter = new FutureAdapter(root.getContext(), eventData);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(eventModel -> {
            Intent intent = new Intent(getContext(), CardActivity.class);
            intent.putExtra(CardActivity.EXTRA_DATE, eventModel.getDate());
            intent.putExtra(CardActivity.EXTRA_TITLE, eventModel.getTitle());
            intent.putExtra(CardActivity.EXTRA_TIME, eventModel.getTime());
            intent.putExtra(CardActivity.EXTRA_SUBTITLE, eventModel.getSubtitle());
            intent.putExtra(CardActivity.EXTRA_DESCRIPTION, eventModel.getDescription());
            intent.putExtra(CardActivity.EXTRA_INTERESTED, eventModel.getInterested());
            startActivity(intent);
        });

        initializeData();
        return root;
    }

    private void initializeData() {
        String[] aktivitets_dato = new String[]{"Fredag, 29 Januar 2020", "Lørdag, 30 Januar 2020", "Søndag, 31 Januar 2020"};
        String[] aktivitets_subtitle = new String[]{"Lindevangsparken", "Sørbyparken", "Frederiksbergparken"};
        String[] aktivitets_title = new String[]{"Snobrød og Boller", "Fodbold og Grill", "Fangeleg og dåsekast"};
        String[] aktivitets_tid = new String[]{"12:00 - 14:00", "15:00 - 17:00", "09:00 - 16:00"};
        String[] aktivitets_beskrivelse = new String[]{"Lang beskrivelse af aktivitet...", "Lang beskrivelse af aktivitet...", "Lang beskrivelse af aktivitet..."};
        String[] aktivitets_interesseret = new String[]{"20 er interesseret", "199 er interesseret", "3 er interesseret"};

        eventData.clear();

        for(int i=0;i<aktivitets_dato.length;i++){
            eventData.add(new EventModel(aktivitets_dato[i], aktivitets_subtitle[i], aktivitets_title[i], aktivitets_tid[i], aktivitets_beskrivelse[i], aktivitets_interesseret[i]));
        }

        adapter.notifyDataSetChanged();
    }
}
