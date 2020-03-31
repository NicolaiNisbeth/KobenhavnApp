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
import com.example.kobenhavn.dal.local.model.EventModel;
import com.example.kobenhavn.view.events.CardActivity;

import java.util.ArrayList;

public class EnrolledFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<EventModel> eventData;
    private EnrolledAdapter adapter;

    public EnrolledFragment() { }
    public static EnrolledFragment newInstance() {
        return new EnrolledFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.events_enrolled_fragment, container, false);
        recyclerView = root.findViewById(R.id.recycler_view_enrolled);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        eventData = new ArrayList<>();
        adapter = new EnrolledAdapter(root.getContext(), eventData);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(playgroundModel -> {
            Intent intent = new Intent(getContext(), CardActivity.class);
            intent.putExtra(CardActivity.EXTRA_DATE, playgroundModel.getDate());
            intent.putExtra(CardActivity.EXTRA_TITLE, playgroundModel.getTitle());
            intent.putExtra(CardActivity.EXTRA_TIME, playgroundModel.getTime());
            intent.putExtra(CardActivity.EXTRA_SUBTITLE, playgroundModel.getSubtitle());
            intent.putExtra(CardActivity.EXTRA_DESCRIPTION, playgroundModel.getDescription());
            intent.putExtra(CardActivity.EXTRA_INTERESTED, playgroundModel.getInterested());
            startActivity(intent);
        });

        initializeData();
        return root;
    }


    private void initializeData() {
        //Get the resources from the XML file
        String[] aktivitets_dato = new String[]{"Fredag, 29 Januar 2020", "Fredag, 29 Januar 2020", "Søndag, 31 Januar 2020"};
        String[] aktivitets_subtitle = new String[]{"Lindevangsparken", "Sørbyparken", "Frederiksbergparken"};
        String[] aktivitets_title = new String[]{"Snobrød og Boller", "Fodbold og Grill", "Fangeleg og dåsekast"};
        String[] aktivitets_tid = new String[]{"12:00 - 14:00", "15:00 - 17:00", "09:00 - 16:00"};
        String[] aktivitets_beskrivelse = new String[]{"Lang beskrivelse af aktivitet...", "Lang beskrivelse af aktivitet...", "Lang beskrivelse af aktivitet..."};
        String[] aktivitets_interesseret = new String[]{"20 er interesseret", "199 er interesseret", "3 er interesseret"};

        //Clear the existing data (to avoid duplication)
        eventData.clear();

        //Create the ArrayList of Sports objects with the titles and information about each sport
        for(int i=0;i<aktivitets_dato.length;i++){
            eventData.add(new EventModel(aktivitets_dato[i], aktivitets_subtitle[i], aktivitets_title[i], aktivitets_tid[i], aktivitets_beskrivelse[i], aktivitets_interesseret[i]));
        }

        //Notify the adapter of the change
        adapter.notifyDataSetChanged();
    }
}
