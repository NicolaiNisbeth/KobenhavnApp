package com.example.kobenhavn.view.authentication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kobenhavn.MainActivity;
import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.LoggedInUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;
    private AuthViewModel authViewModel;
    private ProgressDialog progressDialog;

    @BindView(R.id.input_username) EditText _usernameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autentificering_login_activity);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        authViewModel = new AuthViewModel(AuthRepository.getInstance(new DataSource()));
        _signupLink.setOnClickListener(v -> startSignUp());
        _loginButton.setOnClickListener(v -> login());
        _loginButton.setEnabled(false);

        // On every key press will username and password be reported to loginViewModel and the
        // loginFormState will be changed accordingly
        TextWatcher _afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                authViewModel.loginDataChanged(
                        _usernameText.getText().toString(),
                        _passwordText.getText().toString()
                );
            }
        };
        _usernameText.addTextChangedListener(_afterTextChangedListener);
        _passwordText.addTextChangedListener(_afterTextChangedListener);

        // Observe loginFormState and show errors or enable login button
        authViewModel.getFormStateLive().observe(this, formState -> {
            if (formState == null)
                return;

            _loginButton.setEnabled(formState.isDataValid());
        });

        // Observer loginResult and show errors or
        authViewModel.getLoginResultLive().observe(this, loginResult -> {
            if (loginResult == null)
                return;

            progressDialog.dismiss();
            if (loginResult.getError() != null)
                showLoginFailed(loginResult.getError());

            if (loginResult.getSuccess() != null){
                showLoginSuccess(loginResult.getSuccess());
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    private void startSignUp() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    private void login() {
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // call login after 2s to simulate handling of authentication
        new android.os.Handler().postDelayed(() ->
                        authViewModel.login(
                                _usernameText.getText().toString(),
                                _passwordText.getText().toString()),
                2000);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void showLoginSuccess(LoggedInUser user) {
        String welcome = getString(R.string.welcome) + user.getDisplayName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showLoginFailed(@StringRes Integer errorID) {
        _loginButton.setEnabled(false);
        _usernameText.setText("");
        _usernameText.requestFocus();
        _passwordText.setText("");
        _usernameText.setError(getString(errorID));
    }
}
