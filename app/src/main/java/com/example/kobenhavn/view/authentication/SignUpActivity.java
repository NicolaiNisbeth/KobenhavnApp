package com.example.kobenhavn.view.authentication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.sync.SignupUserRxBus;
import com.example.kobenhavn.dal.sync.RemoteResponseType;
import com.example.kobenhavn.viewmodel.AuthenticationViewModel;
import com.example.kobenhavn.viewmodel.AuthenticationViewModelFactory;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SignUpActivity extends AppCompatActivity {

    @Inject AuthenticationViewModelFactory viewModelFactory;

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_username) EditText _usernameText;
    @BindView(R.id.input_password) TextInputEditText _passwordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;
    @BindView(R.id.input_name_layout) TextInputLayout _nameLayout;
    @BindView(R.id.input_email_layout) TextInputLayout _usernameLayout;
    @BindView(R.id.input_password_layout) TextInputLayout _passwordLayout;

    private ProgressDialog progressDialog;
    private AuthenticationViewModel authViewModel;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private FormHandler formHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_signup_activity);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(SignUpActivity.this);
        authViewModel = ViewModelProviders.of(this, viewModelFactory).get(AuthenticationViewModel.class);
        _loginLink.setOnClickListener(v -> finish());
        _signupButton.setOnClickListener(v -> signup());
        _signupButton.setEnabled(false);
        formHandler = new FormHandler();

        disposables.add(SignupUserRxBus.getInstance().toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSignupResponse, t -> Timber.e(t, "error handling login response"))
        );

        // On every key press will username and password be reported to loginViewModel and the
        // loginFormState will be changed accordingly
        TextWatcher textChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                _nameLayout.setError(null);
                _usernameLayout.setError(null);
                _passwordLayout.setError(null);
                formHandler.signupDataChanged(
                        _nameText.getText().toString(),
                        _usernameText.getText().toString(),
                        _passwordText.getText().toString()
                );
            }
        };
        _nameText.addTextChangedListener(textChangedListener);
        _usernameText.addTextChangedListener(textChangedListener);
        _passwordText.addTextChangedListener(textChangedListener);

        // Observe loginFormState and show errors or enable login button
        formHandler.getFormStateLive().observe(this, formState -> {
            if (formState == null)
                return;

            _signupButton.setEnabled(formState.isDataValid());
            if (formState.getNameError() != null)
                _nameLayout.setError(getString(formState.getNameError()));

            if (formState.getUsernameError() != null)
                _usernameLayout.setError(getString(formState.getUsernameError()));

            if (formState.getPasswordError() != null)
                _passwordLayout.setError(getString(formState.getPasswordError()));

        });
    }

    private void handleSignupResponse(SignupUserRxBus.SignupResponse response) {
        progressDialog.dismiss();
        if (response.type == RemoteResponseType.SUCCESS) {
            showSignupSuccess();
        } else {
            showSignupFailed();
        }
    }

    private void signup() {
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        authViewModel.signupUser(_nameText.getText().toString(), _usernameText.getText().toString(), _passwordText.getText().toString());
    }

    public void showSignupSuccess() {
        Toast.makeText(this, "Du er hermed oprettet. Vær venlig at logge ind.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        setResult(Activity.RESULT_OK);
        startActivity(intent);
        finish();
    }

    public void showSignupFailed() {
        _usernameText.setText("");
        _usernameText.requestFocus();
        _usernameLayout.setError("Email er allerede taget.");
        _signupButton.setEnabled(false);
    }
}
