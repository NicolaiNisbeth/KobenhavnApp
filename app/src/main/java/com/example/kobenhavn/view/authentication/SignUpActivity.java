package com.example.kobenhavn.view.authentication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.kobenhavn.dal.remote.RemoteException;
import com.example.kobenhavn.dal.sync.SignupUserRxBus;
import com.example.kobenhavn.dal.sync.RemoteResponseType;
import com.example.kobenhavn.viewmodel.AuthenticationViewModel;
import com.example.kobenhavn.viewmodel.AuthenticationViewModelFactory;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

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
        if (response.type == RemoteResponseType.SUCCESS) {
            showSignupSuccess();
        } else {
            showSignupFailed(response.throwable);
        }
    }

    private void signup() {
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        if (isOnline())
            authViewModel.signupUser(_nameText.getText().toString(), _usernameText.getText().toString(), _passwordText.getText().toString());
        else
            showSignupFailed(new InterruptedException());
    }

    public void showSignupSuccess() {
        progressDialog.dismiss();
        Toast.makeText(this, "Du er hermed oprettet. Vær venlig at logge ind.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        setResult(Activity.RESULT_OK);
        startActivity(intent);
        finish();
    }

    public void showSignupFailed(Throwable throwable) {
        progressDialog.dismiss();
        _usernameText.requestFocus();
        _signupButton.setEnabled(false);
        showErrorMessage(throwable);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm != null ? cm.getActiveNetworkInfo() : null;
        return netInfo != null && netInfo.isConnected();
    }

    private void showErrorMessage(Throwable throwable) {
        if (throwable instanceof ConnectException || throwable instanceof SocketTimeoutException)
            _usernameText.setError("Service er nede. Prøv igen senere.");
        else if (throwable instanceof InterruptedException)
            _usernameText.setError("Ingen forbindelse. Tjek netværk.");
        else if (throwable instanceof RemoteException) {
            RemoteException exception = (RemoteException) throwable;
            int statusCode = exception.getResponse().code();
            if (statusCode == HttpURLConnection.HTTP_CONFLICT)
                _usernameText.setError("Email er allerede i brug.");
            else if (statusCode >= HttpURLConnection.HTTP_INTERNAL_ERROR)
                _usernameText.setError("Service er nede. Prøv igen senere.");
        }
    }
}
