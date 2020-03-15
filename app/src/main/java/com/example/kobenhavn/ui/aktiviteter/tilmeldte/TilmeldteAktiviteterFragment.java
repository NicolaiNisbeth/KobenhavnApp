package com.example.kobenhavn.ui.aktiviteter.tilmeldte;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kobenhavn.R;
import com.example.kobenhavn.ui.aktiviteter.AktivitetModel;
import com.example.kobenhavn.ui.aktiviteter.CardActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TilmeldteAktiviteterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TilmeldteAktiviteterFragment extends Fragment {
    //Member variables
    private RecyclerView recyclerView;
    private ArrayList<AktivitetModel> aktivitetsData;
    private TilmeldteAdapter adapter;

    public TilmeldteAktiviteterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AktivitetTilmeldteFragment.
     */
    public static TilmeldteAktiviteterFragment newInstance() {
        return new TilmeldteAktiviteterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.aktiviteter_tilmeldte_fragment, container, false);

        //Initialize the RecyclerView
        recyclerView = root.findViewById(R.id.recycler_view_tilmeldte);

        //Set the Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        //Initialize the ArrayLIst that will contain the data
        aktivitetsData = new ArrayList<>();

        //Initialize the adapter and set it ot the RecyclerView
        adapter = new TilmeldteAdapter(root.getContext(), aktivitetsData);
        recyclerView.setAdapter(adapter);

        //Allows swipe left and right for delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TilmeldteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AktivitetModel aktivitetModel) {
                Intent intent = new Intent(getContext(), CardActivity.class);
                intent.putExtra(CardActivity.EXTRA_DATO, aktivitetModel.getDato());
                intent.putExtra(CardActivity.EXTRA_TITLE, aktivitetModel.getTitle());
                intent.putExtra(CardActivity.EXTRA_TID, aktivitetModel.getTid());
                intent.putExtra(CardActivity.EXTRA_SUBTITLE, aktivitetModel.getSubtitle());
                intent.putExtra(CardActivity.EXTRA_BESKRIVELSE, aktivitetModel.getBeskrivelse());
                intent.putExtra(CardActivity.EXTRA_INTERESSERET, aktivitetModel.getInteresseret());
                startActivity(intent);
            }
        });

        //Get the data
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
        aktivitetsData.clear();

        //Create the ArrayList of Sports objects with the titles and information about each sport
        for(int i=0;i<aktivitets_dato.length;i++){
            aktivitetsData.add(new AktivitetModel(aktivitets_dato[i], aktivitets_subtitle[i], aktivitets_title[i], aktivitets_tid[i], aktivitets_beskrivelse[i], aktivitets_interesseret[i]));
        }

        //Notify the adapter of the change
        adapter.notifyDataSetChanged();
    }
}
