package com.example.kobenhavn.ui.autentificering;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kobenhavn.R;
import com.example.kobenhavn.ui.autentificering.data.model.LoggedInUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private ProgressDialog progressDialog;

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_username) EditText _usernameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autentificering_signup_activity);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(SignUpActivity.this);
        loginViewModel = new LoginViewModel(LoginRepository.getInstance(new LoginDataSource()));
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
                loginViewModel.signupDataChanged(
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
        loginViewModel.getFormStateLive().observe(this, formState -> {
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
        loginViewModel.getSignupResultLive().observe(this, signupResult -> {
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
                        loginViewModel.signup(
                                _nameText.getText().toString(),
                                _usernameText.getText().toString(),
                                _passwordText.getText().toString()),
                2000);
    }


    public void showSignupSuccess() {
        // TODO: maybe show user a dialog before sending him to login!
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void showSignupFailed(Integer errorID) {
        _nameText.setText("");
        _nameText.requestFocus();
        _usernameText.setText("");
        _usernameText.setError(getString(errorID));
        _passwordText.setText("");
        _signupButton.setEnabled(false);
    }
}
