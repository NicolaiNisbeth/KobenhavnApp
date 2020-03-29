package com.example.kobenhavn.ui.playgrounds.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.ui.playgrounds.PlaygroundModel;

import java.util.ArrayList;

public class AddPlaygroundActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<PlaygroundModel> playgroundData;
    private AddPlaygroundAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playgrounds_add_activity);

        Toolbar toolbar = findViewById(R.id.legepladser_tilfoj_toolbar);
        setSupportActionBar(toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Tilføj Legeplads");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recycler_view_tilfoj_legeplads);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        playgroundData = new ArrayList<>();
        adapter = new AddPlaygroundAdapter(this, playgroundData);
        recyclerView.setAdapter(adapter);

        initializeData();
    }

    private void initializeData() {
        //Get the resources from the XML file
        String[] legeplads_titel = new String[]{"Lindevangsparken", "Sørbyparken", "Frederiksbergparken"};
        String[] legeplads_adresse = new String[]{"Agervænget 34", "Søndermarken 22", "Blågadeplads 5"};

        //Clear the existing data (to avoid duplication)
        playgroundData.clear();

        //Create the ArrayList of Sports objects with the titles and information about each sport
        for(int i=0;i<legeplads_titel.length;i++){
            playgroundData.add(new PlaygroundModel(legeplads_titel[i], legeplads_adresse[i], "", null, ""));
        }

        //Notify the adapter of the change
        adapter.notifyDataSetChanged();
    }
}
