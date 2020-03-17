package com.example.kobenhavn.ui.indstillinger;

import android.content.Intent;
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
import com.example.kobenhavn.ui.autentificering.LoginActivity;
import com.example.kobenhavn.ui.autentificering.AuthRepository;
import com.example.kobenhavn.ui.autentificering.AuthViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndstillingerFragment extends Fragment {
    private AuthViewModel authViewModel;

    @BindView(R.id.indstillinger_profil_navn) TextView _profilNavnText;
    @BindView(R.id.indstillinger_profil_nummer) TextView _profilNummerText;
    @BindView(R.id.indstillinger_log_ud) TextView _logUdText;
    @BindView(R.id.indstillinger_navn_og_adresse) View _navnOgAdresse;
    @BindView(R.id.indstillinger_email) View _email;
    @BindView(R.id.indstillinger_mobilnummer) View _mobilNummer;
    @BindView(R.id.indstillinger_skift_kode) View _skiftKode;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.indstillinger_fragment, container, false);
        ButterKnife.bind(this, root);

        // fetch data
        IndstillingerModel indstillingerModel = fetchIndstillinger();

        // setup toolbar
        Toolbar toolbar = root.findViewById(R.id.indstillinger_toolbar);
        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Indstillinger");


        _profilNavnText.setText(indstillingerModel.getNavn());
        _profilNummerText.setText(indstillingerModel.getTelefonNummer());

        ((TextView)_navnOgAdresse.findViewById(R.id.indstillinger_item_left)).setText("Navn og adresse");
        ((TextView)_navnOgAdresse.findViewById(R.id.indstillinger_item_middle)).setText(indstillingerModel.getNavn());

        ((TextView)_email.findViewById(R.id.indstillinger_item_left)).setText("E-mail");
        ((TextView)_email.findViewById(R.id.indstillinger_item_middle)).setText(indstillingerModel.getEmail());

        ((TextView)_mobilNummer.findViewById(R.id.indstillinger_item_left)).setText("Mobilnummer");
        ((TextView)_mobilNummer.findViewById(R.id.indstillinger_item_middle)).setText(indstillingerModel.getTelefonNummer());

        ((TextView)_skiftKode.findViewById(R.id.indstillinger_item_left)).setText("Skift kode");
        ((TextView)_skiftKode.findViewById(R.id.indstillinger_item_middle)).setText("****");

        _logUdText.setOnClickListener(v -> logUd());

        return root;
    }

    private void logUd(){
        // TODO: not sure how to access dataSource otherwise
        AuthRepository authRepository = AuthRepository.getInstance(AuthRepository.dataSource);
        authRepository.logout();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }


    public IndstillingerModel fetchIndstillinger(){
        IndstillingerModel indstillingerModel = new IndstillingerModel("Nicolai Nisbeth", "+45 28 77 19 95", "nicolai.nisbeth@hotmail.com", "nicolai1995");
        return indstillingerModel;
    }


}
