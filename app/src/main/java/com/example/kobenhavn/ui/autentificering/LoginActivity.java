package com.example.kobenhavn.ui.autentificering;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kobenhavn.MainActivity;
import com.example.kobenhavn.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_username) EditText _usernameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autentificering_login_activity);
        ButterKnife.bind(this);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);

        _signupLink.setOnClickListener(v -> {
            // Start the Signup activity
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
        });


        loginViewModel = new LoginViewModel(LoginRepository.getInstance(new LoginDataSource()));

        // Observe login from state
        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null)
                return;

            _loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null)
                _usernameText.setError(getString(loginFormState.getUsernameError()));

            if (loginFormState.getPasswordError() != null)
                _passwordText.setError(getString(loginFormState.getPasswordError()));
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(_usernameText.getText().toString(), _passwordText.getText().toString());
            }
        };
        _usernameText.addTextChangedListener(afterTextChangedListener);
        _passwordText.addTextChangedListener(afterTextChangedListener);


        // Observer login result
        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null)
                return;

            progressDialog.dismiss();
            if (loginResult.getError() != null)
                showLoginFailed(loginResult.getError());

            if (loginResult.getSuccess() != null)
                showLoginSuccess(loginResult.getSuccess());

            setResult(Activity.RESULT_OK);

            //Complete and destroy login activity once successful
            finish();
        });

        _loginButton.setOnClickListener(v -> {
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();

            new android.os.Handler().postDelayed(
                    () -> {
                        loginViewModel.login(_usernameText.getText().toString(), _passwordText.getText().toString());
                    }, 3000);
        });

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void showLoginSuccess(LoggedInUserView user) {
        String welcome = getString(R.string.welcome) + user.getDisplayName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
        _usernameText.setText("");
        _usernameText.requestFocus();
        _passwordText.setText("");
        _loginButton.setEnabled(true);
    }
}
