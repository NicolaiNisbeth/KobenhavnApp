package com.example.kobenhavn.view.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.SettingsModel;
import com.example.kobenhavn.view.authentication.LoginActivity;
import com.example.kobenhavn.view.authentication.data.AuthRepository;
import com.example.kobenhavn.viewmodel.AuthenticationViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    private Toolbar toolbar;

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
        setHasOptionsMenu(true);
        toolbar = root.findViewById(R.id.indstillinger_toolbar);
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Indstillinger");


        _nameText.setText(settingsModel.getName());
        _numberText.setText(settingsModel.getPhoneNumber());

        ((TextView) _nameAndAddressView.findViewById(R.id.settings_item_left)).setText("Navn og adresse");
        ((EditText) _nameAndAddressView.findViewById(R.id.settings_item_middle)).setText(settingsModel.getName());
        toogleEditText(_nameAndAddressView.findViewById(R.id.settings_item_middle), false, _nameAndAddressView.findViewById(R.id.settings_item_right));
        _nameAndAddressView.findViewById(R.id.settings_item_right).setOnClickListener(this);

        ((TextView) _emailView.findViewById(R.id.settings_item_left)).setText("E-mail");
        ((EditText) _emailView.findViewById(R.id.settings_item_middle)).setText(settingsModel.getEmail());
        toogleEditText(_emailView.findViewById(R.id.settings_item_middle), false, _emailView.findViewById(R.id.settings_item_right));
        _emailView.findViewById(R.id.settings_item_right).setOnClickListener(this);

        ((TextView) _mobileNumberView.findViewById(R.id.settings_item_left)).setText("Mobilnummer");
        ((EditText) _mobileNumberView.findViewById(R.id.settings_item_middle)).setText(settingsModel.getPhoneNumber());
        toogleEditText(_mobileNumberView.findViewById(R.id.settings_item_middle), false, _mobileNumberView.findViewById(R.id.settings_item_right));
        _mobileNumberView.findViewById(R.id.settings_item_right).setOnClickListener(this);

        ((TextView) _changePasswordView.findViewById(R.id.settings_item_left)).setText("Skift kode");
        ((EditText) _changePasswordView.findViewById(R.id.settings_item_middle)).setText(settingsModel.getPassword());
        toogleEditText(_changePasswordView.findViewById(R.id.settings_item_middle), false, _changePasswordView.findViewById(R.id.settings_item_right));
        _changePasswordView.findViewById(R.id.settings_item_right).setOnClickListener(this);

        _logoutText.setOnClickListener(v -> logUd());

        return root;
    }

    @Override
    public void onClick(View v) {
        if (v == _nameAndAddressView.findViewById(R.id.settings_item_right)) {
            toogleEditText(_nameAndAddressView.findViewById(R.id.settings_item_middle), true, _nameAndAddressView.findViewById(R.id.settings_item_right));

        } else if (v == _emailView.findViewById(R.id.settings_item_right)) {
            toogleEditText(_emailView.findViewById(R.id.settings_item_middle), true, _emailView.findViewById(R.id.settings_item_right));

        } else if (v == _mobileNumberView.findViewById(R.id.settings_item_right)) {
            toogleEditText(_mobileNumberView.findViewById(R.id.settings_item_middle), true, _mobileNumberView.findViewById(R.id.settings_item_right));

        } else if (v == _changePasswordView.findViewById(R.id.settings_item_right)) {
            toogleEditText(_changePasswordView.findViewById(R.id.settings_item_middle), true, _changePasswordView.findViewById(R.id.settings_item_right));
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.top_nav_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_settings) {
            toogleEditText(_nameAndAddressView.findViewById(R.id.settings_item_middle), false, _nameAndAddressView.findViewById(R.id.settings_item_right));
            toogleEditText(_emailView.findViewById(R.id.settings_item_middle), false, _emailView.findViewById(R.id.settings_item_right));
            toogleEditText(_mobileNumberView.findViewById(R.id.settings_item_middle), false, _mobileNumberView.findViewById(R.id.settings_item_right));
            toogleEditText(_changePasswordView.findViewById(R.id.settings_item_middle), false, _changePasswordView.findViewById(R.id.settings_item_right));
            Toast.makeText(getContext(), "Saved changes.", Toast.LENGTH_SHORT).show();
            // TODO: save bruger and make API call
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logUd() {
        AuthenticationViewModel a = new AuthenticationViewModel(AuthRepository.getInstance());
        a.logoutUser();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }


    public SettingsModel fetchIndstillinger() {
        SettingsModel settingsModel = new SettingsModel("Nicolai Nisbeth", "+45 28 77 19 95", "nicolai.nisbeth@hotmail.com", "nicolai1995");
        return settingsModel;
    }

    private void toogleEditText(EditText editText, boolean activated, ImageButton viewById) {
        editText.setEnabled(activated);
        editText.setFocusableInTouchMode(activated);
        editText.setFocusable(activated);
        editText.setClickable(activated);
        editText.setCursorVisible(activated);
        editText.setPressed(activated);

        if (activated){
            editText.requestFocus();
            editText.setSelection(editText.getText().length());
            viewById.setImageResource(R.drawable.ic_arrow_left);
        } else {
            viewById.setImageResource(R.drawable.ic_arrow_right);
        }
    }
}
