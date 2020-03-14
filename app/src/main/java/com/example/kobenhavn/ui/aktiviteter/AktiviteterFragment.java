package com.example.kobenhavn.ui.aktiviteter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kobenhavn.R;

import java.util.ArrayList;

public class AktiviteterFragment extends Fragment {
    //Member variables
    private RecyclerView recyclerView;
    private ArrayList<AktivitetModel> aktivitetsData;
    private AktivitetsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.aktiviteter_fragment, container, false);

        //Initialize the RecyclerView
        recyclerView = root.findViewById(R.id.recyclerView);

        //Set the Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        //Initialize the ArrayLIst that will contain the data
        aktivitetsData = new ArrayList<>();

        //Initialize the adapter and set it ot the RecyclerView
        adapter = new AktivitetsAdapter(root.getContext(), aktivitetsData);
        recyclerView.setAdapter(adapter);

        //Get the data
        initializeData();

        return root;
    }

    private void initializeData() {
        //Get the resources from the XML file
        String[] aktivitets_dato = new String[]{"Fredag, 29 Januar 2020", "Fredag, 29 Januar 2020", "Fredag, 29 Januar 2020"};
        String[] aktivitets_subtitle = new String[]{"Lindevangsparken", "Lindevangsparken", "Lindevangsparken"};
        String[] aktivitets_title = new String[]{"Snobrød og Boller", "Snobrød og Boller", "Snobrød og Boller"};
        String[] aktivitets_beskrivelse = new String[]{"Lang beskrivelse af aktivitet...", "Lang beskrivelse af aktivitet...", "Lang beskrivelse af aktivitet..."};
        String[] aktivitets_interesseret = new String[]{"20 er interesseret", "20 er interesseret", "20 er interesseret"};

        //Clear the existing data (to avoid duplication)
        aktivitetsData.clear();

        //Create the ArrayList of Sports objects with the titles and information about each sport
        for(int i=0;i<aktivitets_dato.length;i++){
            aktivitetsData.add(new AktivitetModel(aktivitets_dato[i], aktivitets_subtitle[i], aktivitets_title[i], aktivitets_beskrivelse[i], aktivitets_interesseret[i]));
        }

        //Notify the adapter of the change
        adapter.notifyDataSetChanged();
    }
}
