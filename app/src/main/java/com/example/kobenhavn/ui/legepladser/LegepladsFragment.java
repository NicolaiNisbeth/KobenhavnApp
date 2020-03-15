package com.example.kobenhavn.ui.legepladser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kobenhavn.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LegepladsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LegepladsFragment extends Fragment {
    private static final String LEGEPLADS_MODEL = "com.example.kobenhavn.ui.legepladser.legepladsModel";

    // TODO: Rename and change types of parameters
    private LegepladsModel legepladsModel;

    private TextView titel, adresse, beskrivelse, kontaktpersoner;
    private ImageView img;


    public LegepladsFragment() {
        // Required empty public constructor
    }

    public static LegepladsFragment newInstance(LegepladsModel model) {
        LegepladsFragment fragment = new LegepladsFragment();
        Bundle args = new Bundle();
        args.putParcelable(LEGEPLADS_MODEL, model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            legepladsModel = getArguments().getParcelable(LEGEPLADS_MODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.legepladser_info_item, container, false);
        titel = root.findViewById(R.id.legepladser_info_titel);
        adresse = root.findViewById(R.id.legepladser_info_adresse);
        beskrivelse = root.findViewById(R.id.legepladser_info_beskrivelse);
        kontaktpersoner = root.findViewById(R.id.legepladser_info_kontaktpersoner);
        img = root.findViewById(R.id.legepladser_info_img_url);

        titel.setText(legepladsModel.getTitel());
        adresse.setText(legepladsModel.getAdresse());
        beskrivelse.setText(legepladsModel.getBeskrivelse());

        return root;
    }
}
