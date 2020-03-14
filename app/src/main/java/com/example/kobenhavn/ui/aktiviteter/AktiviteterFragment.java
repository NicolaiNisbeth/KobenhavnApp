package com.example.kobenhavn.ui.aktiviteter;

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

import java.util.ArrayList;

public class AktiviteterFragment extends Fragment {
    //Member variables
    private RecyclerView recyclerView;
    private ArrayList<AktivitetModel> aktivitetsData;
    private AktivitetsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.aktiviteter_fragment, container, false);

        //Initialize the RecyclerView
        recyclerView = root.findViewById(R.id.recyclerView);

        //Set the Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        //Initialize the ArrayLIst that will contain the data
        aktivitetsData = new ArrayList<>();

        //Initialize the adapter and set it ot the RecyclerView
        adapter = new AktivitetsAdapter(root.getContext(), aktivitetsData);
        recyclerView.setAdapter(adapter);

        //Make the views listen on onclick
        adapter.setOnItemClickListener(new AktivitetsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AktivitetModel aktivitetModel) {
                System.out.println("something was clicked " + aktivitetModel.getDato());
                Intent intent = new Intent(getContext(), AktivitetActivity.class);
                intent.putExtra(AktivitetActivity.EXTRA_DATO, aktivitetModel.getDato());
                intent.putExtra(AktivitetActivity.EXTRA_TITLE, aktivitetModel.getTitle());
                intent.putExtra(AktivitetActivity.EXTRA_SUBTITLE, aktivitetModel.getSubtitle());
                intent.putExtra(AktivitetActivity.EXTRA_BESKRIVELSE, aktivitetModel.getBeskrivelse());
                intent.putExtra(AktivitetActivity.EXTRA_INTERESSERET, aktivitetModel.getInteresseret());
                startActivity(intent);
            }
        });


        //Get the data
        initializeData();

        return root;
    }

    private void initializeData() {
        //Get the resources from the XML file
        String[] aktivitets_dato = new String[]{"Fredag, 29 Januar 2020", "Lørdag, 30 Januar 2020", "Søndag, 31 Januar 2020"};
        String[] aktivitets_subtitle = new String[]{"Lindevangsparken", "Sørbyparken", "Frederiksbergparken"};
        String[] aktivitets_title = new String[]{"Snobrød og Boller", "Fodbold og Grill", "Fangeleg og dåsekast"};
        String[] aktivitets_beskrivelse = new String[]{"Lang beskrivelse af aktivitet...", "Lang beskrivelse af aktivitet...", "Lang beskrivelse af aktivitet..."};
        String[] aktivitets_interesseret = new String[]{"20 er interesseret", "199 er interesseret", "3 er interesseret"};

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
