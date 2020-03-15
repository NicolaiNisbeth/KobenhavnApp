package com.example.kobenhavn.ui.indstillinger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.kobenhavn.R;
import com.example.kobenhavn.ui.login.data.LoginRepository;

public class IndstillingerFragment extends Fragment {

    private View navn_adresse, e_mail, mobilnummber, skift_kode;
    private TextView profil_navn, profil_nummer, log_ud;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.indstillinger_fragment, container, false);

        // setup toolbar
        Toolbar toolbar = root.findViewById(R.id.indstillinger_toolbar);
        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Indstillinger");

        // fetch data
        IndstillingerModel indstillingerModel = fetchIndstillinger();

        profil_navn = root.findViewById(R.id.indstillinger_profil_navn);
        profil_nummer = root.findViewById(R.id.indstillinger_profil_nummer);

        profil_navn.setText(indstillingerModel.getNavn());
        profil_nummer.setText(indstillingerModel.getTelefonNummer());

        navn_adresse = root.findViewById(R.id.indstillinger_navn_og_adresse);
        e_mail = root.findViewById(R.id.indstillinger_email);
        mobilnummber = root.findViewById(R.id.indstillinger_mobilnummer);
        skift_kode = root.findViewById(R.id.indstillinger_skift_kode);

        ((TextView)navn_adresse.findViewById(R.id.indstillinger_item_left)).setText("Navn og adresse");
        ((TextView)e_mail.findViewById(R.id.indstillinger_item_left)).setText("E-mail");
        ((TextView)mobilnummber.findViewById(R.id.indstillinger_item_left)).setText("Mobilnummer");
        ((TextView)skift_kode.findViewById(R.id.indstillinger_item_left)).setText("Skift kode");

        ((TextView)navn_adresse.findViewById(R.id.indstillinger_item_middle)).setText(indstillingerModel.getNavn());
        ((TextView)e_mail.findViewById(R.id.indstillinger_item_middle)).setText(indstillingerModel.getEmail());
        ((TextView)mobilnummber.findViewById(R.id.indstillinger_item_middle)).setText(indstillingerModel.getTelefonNummer());
        ((TextView)skift_kode.findViewById(R.id.indstillinger_item_middle)).setText("****");

        log_ud = root.findViewById(R.id.indstillinger_log_ud);
        log_ud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }


    public IndstillingerModel fetchIndstillinger(){
        IndstillingerModel indstillingerModel = new IndstillingerModel("Nicolai Nisbeth", "+45 28 77 19 95", "nicolai.nisbeth@hotmail.com", "nicolai1995");
        return indstillingerModel;
    }


}
