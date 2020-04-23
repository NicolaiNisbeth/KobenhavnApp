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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.view.authentication.LoginActivity;
import com.example.kobenhavn.viewmodel.AuthenticationViewModel;
import com.example.kobenhavn.viewmodel.AuthenticationViewModelFactory;
import com.example.kobenhavn.viewmodel.UserViewModel;
import com.example.kobenhavn.viewmodel.UserViewModelFactory;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class SettingsFragment extends Fragment implements View.OnClickListener {

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

    private Toolbar toolbar;
    private AuthenticationViewModel authenticationViewModel;
    private UserViewModel userViewModel;

    public SettingsFragment(){}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this, root);

        // setup toolbar_item
        setupToolbar(root);

        authenticationViewModel = ViewModelProviders.of(this, authenticationViewModelFactory).get(AuthenticationViewModel.class);
        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);
        LiveData<User> liveData = userViewModel.getUser(RemoteDataSource.loggedInUser.getUsername());
        liveData.observe(getViewLifecycleOwner(), this::updateUI);

        _logoutText.setOnClickListener(v -> logUd());

        ((TextView) _nameView.findViewById(R.id.settings_item_left)).setText("Navn");
        toogleEditText(_nameView.findViewById(R.id.settings_item_middle), false, _nameView.findViewById(R.id.settings_item_right));
        _nameView.findViewById(R.id.settings_item_right).setOnClickListener(this);

        ((TextView) _emailView.findViewById(R.id.settings_item_left)).setText("E-mail");
        toogleEditText(_emailView.findViewById(R.id.settings_item_middle), false, _emailView.findViewById(R.id.settings_item_right));
        _emailView.findViewById(R.id.settings_item_right).setOnClickListener(this);

        ((TextView) _mobileNumberView.findViewById(R.id.settings_item_left)).setText("Mobilnummer");
        toogleEditText(_mobileNumberView.findViewById(R.id.settings_item_middle), false, _mobileNumberView.findViewById(R.id.settings_item_right));
        _mobileNumberView.findViewById(R.id.settings_item_right).setOnClickListener(this);

        ((TextView) _changePasswordView.findViewById(R.id.settings_item_left)).setText("Password");
        toogleEditText(_changePasswordView.findViewById(R.id.settings_item_middle), false, _changePasswordView.findViewById(R.id.settings_item_right));
        _changePasswordView.findViewById(R.id.settings_item_right).setOnClickListener(this);

        return root;
    }

    private void updateUI(User user) {
        if (user == null) return;
        RemoteDataSource.loggedInUser = user;

        Timber.e("UPDATE UI IS CALLED IN SETTINGS %s", user);
        _nameText.setText(user.getFirstname());
        _numberText.setText(user.getPhonenumbers().size() > 0 ? user.getPhonenumbers().get(0) : "");
        ((EditText) _nameView.findViewById(R.id.settings_item_middle)).setText(user.getFirstname());
        ((EditText) _emailView.findViewById(R.id.settings_item_middle)).setText(user.getEmail());
        ((EditText) _mobileNumberView.findViewById(R.id.settings_item_middle)).setText(user.getPhonenumbers().size() > 0 ? user.getPhonenumbers().get(0) : "");
        ((EditText) _changePasswordView.findViewById(R.id.settings_item_middle)).setText("****");
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
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (RemoteDataSource.loggedInUser == null) return true;

        int id = item.getItemId();
        if (id == R.id.save_settings) {
            EditText _nameEdit = _nameView.findViewById(R.id.settings_item_middle);
            EditText _emailEdit = _emailView.findViewById(R.id.settings_item_middle);
            EditText _phoneNumberEdit = _mobileNumberView.findViewById(R.id.settings_item_middle);
            EditText _passwordEdit = _changePasswordView.findViewById(R.id.settings_item_middle);

            toogleEditText(_nameEdit, false, _nameView.findViewById(R.id.settings_item_right));
            toogleEditText(_emailEdit, false, _emailView.findViewById(R.id.settings_item_right));
            toogleEditText(_phoneNumberEdit, false, _mobileNumberView.findViewById(R.id.settings_item_right));
            toogleEditText(_passwordEdit, false, _changePasswordView.findViewById(R.id.settings_item_right));
            Toast.makeText(getContext(), "Saved changes.", Toast.LENGTH_SHORT).show();

            String firstName = _nameEdit.getText().toString();
            String email = _emailEdit.getText().toString();
            String phoneNumber = _phoneNumberEdit.getText().toString();
            String password = _passwordEdit.getText().toString();
            RemoteDataSource.loggedInUser.setFirstname(firstName);
            RemoteDataSource.loggedInUser.setEmail(email);
            RemoteDataSource.loggedInUser.getPhonenumbers().add(0, phoneNumber);
            RemoteDataSource.loggedInUser.setPassword(password);

            userViewModel.updateUserFields(RemoteDataSource.loggedInUser);

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

    private void setupToolbar(View root) {
        setHasOptionsMenu(true);
        toolbar = root.findViewById(R.id.indstillinger_toolbar);
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Indstillinger");
    }
}
