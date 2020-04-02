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
import com.example.kobenhavn.viewmodel.AuthenticationViewModel;
import com.example.kobenhavn.viewmodel.AuthenticationViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private AuthenticationViewModel authViewModel;

    @Inject
    AuthenticationViewModelFactory viewModelFactory;

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_username) EditText _usernameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_signup_activity);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(SignUpActivity.this);
        authViewModel = ViewModelProviders.of(this, viewModelFactory).get(AuthenticationViewModel.class);
        _loginLink.setOnClickListener(v -> finish());
        _signupButton.setOnClickListener(v -> signup());
        _signupButton.setEnabled(false);

        // On every key press will username and password be reported to loginViewModel and the
        // loginFormState will be changed accordingly
        TextWatcher textChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                authViewModel.signupDataChanged(
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
        authViewModel.getFormStateLive().observe(this, formState -> {
            if (formState == null)
                return;

            _signupButton.setEnabled(formState.isDataValid());
            if (formState.getNameError() != null)
                _nameText.setError(getString(formState.getNameError()));

            if (formState.getUsernameError() != null)
                _usernameText.setError(getString(formState.getUsernameError()));

            if (formState.getPasswordError() != null)
                _passwordText.setError(getString(formState.getPasswordError()));

        });

        // Observer loginResult and show errors or
        authViewModel.getSignupResultLive().observe(this, signupResult -> {
            if (signupResult == null)
                return;

            progressDialog.dismiss();
            if (signupResult.getError() != null)
                showSignupFailed(signupResult.getError());

            if (signupResult.getSuccess()){
                showSignupSuccess();
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    private void signup() {
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        new android.os.Handler().postDelayed(() ->
                        authViewModel.signupUser(
                                _nameText.getText().toString(),
                                _usernameText.getText().toString(),
                                _passwordText.getText().toString()),
                2000);
    }

    public void showSignupSuccess() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Du er hermed oprettet. Vær venlig at logge ind.", Toast.LENGTH_SHORT).show();
    }

    public void showSignupFailed(Integer errorID) {
        //_nameText.setText("");
        //_nameText.requestFocus();
        _usernameText.setText("");
        _usernameText.requestFocus();
        _usernameText.setError(getString(errorID));
        //_passwordText.setText("");
        _signupButton.setEnabled(false);
    }
}
