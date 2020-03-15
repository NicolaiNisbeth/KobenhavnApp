package com.example.kobenhavn.ui.legepladser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.ui.aktiviteter.AktivitetModel;

import java.util.ArrayList;

public class TilfojLegepladsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<AktivitetModel> aktivitetsData;
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
        aktivitetsData = new ArrayList<>();

        //Initialize the adapter and set it ot the RecyclerView
        adapter = new TilfojLegepladsAdapter(this, aktivitetsData);
        recyclerView.setAdapter(adapter);

        initializeData();
    }

    private void initializeData() {
        //Get the resources from the XML file
        String[] aktivitets_title = new String[]{"Lindevangsparken", "Sørbyparken", "Frederiksbergparken"};
        String[] aktivitets_subtitle = new String[]{"Agervænget 34", "Søndermarken 22", "Blågadeplads 5"};

        //Clear the existing data (to avoid duplication)
        aktivitetsData.clear();

        //Create the ArrayList of Sports objects with the titles and information about each sport
        for(int i=0;i<aktivitets_title.length;i++){
            aktivitetsData.add(new AktivitetModel("", aktivitets_subtitle[i], aktivitets_title[i], "", "", ""));
        }

        //Notify the adapter of the change
        adapter.notifyDataSetChanged();
    }
}
