package com.example.kobenhavn.ui.legepladser.tilfoj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.ui.aktiviteter.AktivitetModel;
import com.example.kobenhavn.ui.legepladser.LegepladsModel;

import java.util.ArrayList;

public class TilfojLegepladsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<LegepladsModel> legepladsData;
    private TilfojLegepladsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.legepladser_tilfoj_activity);

        // setting up toolbar
        Toolbar toolbar = findViewById(R.id.legepladser_tilfoj_toolbar);
        setSupportActionBar(toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Tilføj Legeplads");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Initialize the RecyclerView
        recyclerView = findViewById(R.id.recycler_view_tilfoj_legeplads);

        //Set the Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initialize the ArrayLIst that will contain the data
        legepladsData = new ArrayList<>();

        //Initialize the adapter and set it ot the RecyclerView
        adapter = new TilfojLegepladsAdapter(this, legepladsData);
        recyclerView.setAdapter(adapter);

        initializeData();
    }

    private void initializeData() {
        //Get the resources from the XML file
        String[] legeplads_titel = new String[]{"Lindevangsparken", "Sørbyparken", "Frederiksbergparken"};
        String[] legeplads_adresse = new String[]{"Agervænget 34", "Søndermarken 22", "Blågadeplads 5"};

        //Clear the existing data (to avoid duplication)
        legepladsData.clear();

        //Create the ArrayList of Sports objects with the titles and information about each sport
        for(int i=0;i<legeplads_titel.length;i++){
            legepladsData.add(new LegepladsModel(legeplads_titel[i], legeplads_adresse[i], "", null, ""));
        }

        //Notify the adapter of the change
        adapter.notifyDataSetChanged();
    }
}
