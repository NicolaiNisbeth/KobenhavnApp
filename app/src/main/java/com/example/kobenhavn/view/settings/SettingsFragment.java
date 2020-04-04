package com.example.kobenhavn.view.settings;

import android.content.Context;
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
import androidx.lifecycle.ViewModelProviders;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.local.model.inmemory.LoggedInUser;
import com.example.kobenhavn.view.authentication.LoginActivity;
import com.example.kobenhavn.viewmodel.AuthenticationViewModel;
import com.example.kobenhavn.viewmodel.AuthenticationViewModelFactory;
import com.example.kobenhavn.viewmodel.UserViewModel;
import com.example.kobenhavn.viewmodel.UserViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    private Toolbar toolbar;
    private AuthenticationViewModel authenticationViewModel;
    private UserViewModel userViewModel;

    @Inject
    AuthenticationViewModelFactory authenticationViewModelFactory;

    @Inject
    UserViewModelFactory userViewModelFactory;

    @BindView(R.id.settings_profile_name) TextView _nameText;
    @BindView(R.id.settings_profile_number) TextView _numberText;
    @BindView(R.id.settings_logout) TextView _logoutText;

    @BindView(R.id.settings_name_and_address) View _nameView;
    @BindView(R.id.settings_email) View _emailView;
    @BindView(R.id.settings_mobile_number) View _mobileNumberView;
    @BindView(R.id.settings_change_password) View _changePasswordView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this, root);

        // setup toolbar
        setHasOptionsMenu(true);
        toolbar = root.findViewById(R.id.indstillinger_toolbar);
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Indstillinger");

        authenticationViewModel = ViewModelProviders.of(this, authenticationViewModelFactory).get(AuthenticationViewModel.class);
        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);

        _nameText.setText(LoggedInUser.user.getFirstname());
        _numberText.setText(LoggedInUser.user.getPhonenumber());
        _logoutText.setOnClickListener(v -> logUd());

        ((TextView) _nameView.findViewById(R.id.settings_item_left)).setText("Navn");
        ((EditText) _nameView.findViewById(R.id.settings_item_middle)).setText(LoggedInUser.user.getFirstname());
        toogleEditText(_nameView.findViewById(R.id.settings_item_middle), false, _nameView.findViewById(R.id.settings_item_right));
        _nameView.findViewById(R.id.settings_item_right).setOnClickListener(this);

        ((TextView) _emailView.findViewById(R.id.settings_item_left)).setText("E-mail");
        ((EditText) _emailView.findViewById(R.id.settings_item_middle)).setText(LoggedInUser.user.getEmail());
        toogleEditText(_emailView.findViewById(R.id.settings_item_middle), false, _emailView.findViewById(R.id.settings_item_right));
        _emailView.findViewById(R.id.settings_item_right).setOnClickListener(this);

        ((TextView) _mobileNumberView.findViewById(R.id.settings_item_left)).setText("Mobilnummer");
        ((EditText) _mobileNumberView.findViewById(R.id.settings_item_middle)).setText(LoggedInUser.user.getPhonenumber());
        toogleEditText(_mobileNumberView.findViewById(R.id.settings_item_middle), false, _mobileNumberView.findViewById(R.id.settings_item_right));
        _mobileNumberView.findViewById(R.id.settings_item_right).setOnClickListener(this);

        ((TextView) _changePasswordView.findViewById(R.id.settings_item_left)).setText("Skift kode");
        ((EditText) _changePasswordView.findViewById(R.id.settings_item_middle)).setText(LoggedInUser.user.getPassword());
        toogleEditText(_changePasswordView.findViewById(R.id.settings_item_middle), false, _changePasswordView.findViewById(R.id.settings_item_right));
        _changePasswordView.findViewById(R.id.settings_item_right).setOnClickListener(this);

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {
        if (v == _nameView.findViewById(R.id.settings_item_right)) {
            toogleEditText(_nameView.findViewById(R.id.settings_item_middle), true, _nameView.findViewById(R.id.settings_item_right));

        } else if (v == _emailView.findViewById(R.id.settings_item_right)) {
            toogleEditText(_emailView.findViewById(R.id.settings_item_middle), true, _emailView.findViewById(R.id.settings_item_right));

        } else if (v == _mobileNumberView.findViewById(R.id.settings_item_right)) {
            toogleEditText(_mobileNumberView.findViewById(R.id.settings_item_middle), true, _mobileNumberView.findViewById(R.id.settings_item_right));

        } else if (v == _changePasswordView.findViewById(R.id.settings_item_right)) {
            //toogleEditText(_changePasswordView.findViewById(R.id.settings_item_middle), true, _changePasswordView.findViewById(R.id.settings_item_right));
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
            EditText _nameEdit = _nameView.findViewById(R.id.settings_item_middle);
            EditText _emailEdit = _emailView.findViewById(R.id.settings_item_middle);
            EditText _mobileEdit = _mobileNumberView.findViewById(R.id.settings_item_middle);
            EditText _passwordEdit = _changePasswordView.findViewById(R.id.settings_item_middle);

            toogleEditText(_nameEdit, false, _nameView.findViewById(R.id.settings_item_right));
            toogleEditText(_emailEdit, false, _emailView.findViewById(R.id.settings_item_right));
            toogleEditText(_mobileEdit, false, _mobileNumberView.findViewById(R.id.settings_item_right));
            toogleEditText(_passwordEdit, false, _changePasswordView.findViewById(R.id.settings_item_right));
            Toast.makeText(getContext(), "Saved changes.", Toast.LENGTH_SHORT).show();

            LoggedInUser.user.setFirstname(_nameEdit.getText().toString());
            LoggedInUser.user.setEmail(_emailEdit.getText().toString());
            LoggedInUser.user.setPhonenumber(_mobileEdit.getText().toString());
            LoggedInUser.user.setPassword(_passwordEdit.getText().toString());

            userViewModel.updateUser(LoggedInUser.user);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logUd() {
        authenticationViewModel.logoutUser();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
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
