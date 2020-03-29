package com.example.kobenhavn.ui.settings;

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
import com.example.kobenhavn.ui.authentication.LoginActivity;
import com.example.kobenhavn.ui.authentication.AuthRepository;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends Fragment {

    @BindView(R.id.settings_profile_name) TextView _nameText;
    @BindView(R.id.settings_profile_number) TextView _numberText;
    @BindView(R.id.settings_logout) TextView _logoutText;
    @BindView(R.id.settings_name_and_address) View _nameAndAddressView;
    @BindView(R.id.settings_email) View _emailView;
    @BindView(R.id.settings_mobile_number) View _mobileNumberView;
    @BindView(R.id.settings_change_password) View _changePasswordView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this, root);

        // fetch data
        SettingsModel settingsModel = fetchIndstillinger();

        // setup toolbar
        Toolbar toolbar = root.findViewById(R.id.indstillinger_toolbar);
        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Indstillinger");


        _nameText.setText(settingsModel.getName());
        _numberText.setText(settingsModel.getPhoneNumber());

        ((TextView) _nameAndAddressView.findViewById(R.id.settings_item_left)).setText("Navn og adresse");
        ((TextView) _nameAndAddressView.findViewById(R.id.settings_item_middle)).setText(settingsModel.getName());

        ((TextView) _emailView.findViewById(R.id.settings_item_left)).setText("E-mail");
        ((TextView) _emailView.findViewById(R.id.settings_item_middle)).setText(settingsModel.getEmail());

        ((TextView) _mobileNumberView.findViewById(R.id.settings_item_left)).setText("Mobilnummer");
        ((TextView) _mobileNumberView.findViewById(R.id.settings_item_middle)).setText(settingsModel.getPhoneNumber());

        ((TextView) _changePasswordView.findViewById(R.id.settings_item_left)).setText("Skift kode");
        ((TextView) _changePasswordView.findViewById(R.id.settings_item_middle)).setText("****");

        _logoutText.setOnClickListener(v -> logUd());
        return root;
    }

    private void logUd(){
        // TODO: not sure how to access dataSource otherwise
        AuthRepository authRepository = AuthRepository.getInstance(AuthRepository.dataSource);
        authRepository.logout();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }


    public SettingsModel fetchIndstillinger(){
        SettingsModel settingsModel = new SettingsModel("Nicolai Nisbeth", "+45 28 77 19 95", "nicolai.nisbeth@hotmail.com", "nicolai1995");
        return settingsModel;
    }


}
