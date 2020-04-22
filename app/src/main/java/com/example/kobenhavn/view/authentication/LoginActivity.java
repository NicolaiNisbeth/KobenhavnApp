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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.kobenhavn.dal.sync.LoginUserRxBus;
import com.example.kobenhavn.dal.sync.RemoteResponseType;
import com.example.kobenhavn.view.MainActivity;
import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.viewmodel.AuthenticationViewModel;
import com.example.kobenhavn.viewmodel.AuthenticationViewModelFactory;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModel;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModelFactory;
import com.example.kobenhavn.viewmodel.UserViewModel;
import com.example.kobenhavn.viewmodel.UserViewModelFactory;
import com.google.android.material.textfield.TextInputLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {

    @Inject PlaygroundsViewModelFactory playgroundViewModelFactory;
    @Inject AuthenticationViewModelFactory authenticationViewModelFactory;
    @Inject UserViewModelFactory userViewModelFactory;

    @BindView(R.id.input_username) EditText _usernameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupText;
    @BindView(R.id.text_input_email) TextInputLayout _emailLayout;
    @BindView(R.id.text_input_password) TextInputLayout _passwordInputLayout;

    private static final int REQUEST_SIGNUP = 0;
    private ProgressDialog progressDialog;
    private AuthenticationViewModel authViewModel;
    private PlaygroundsViewModel playgroundsViewModel;
    private UserViewModel userViewModel;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private FormHandler formHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_login_activity);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        authViewModel = ViewModelProviders.of(this, authenticationViewModelFactory).get(AuthenticationViewModel.class);
        playgroundsViewModel = ViewModelProviders.of(this, playgroundViewModelFactory).get(PlaygroundsViewModel.class);
        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);

        _signupText.setOnClickListener(v -> startSignUp());
        _loginButton.setOnClickListener(v -> login());
        _loginButton.setEnabled(false);
        formHandler = new FormHandler();

        disposables.add(LoginUserRxBus.getInstance().toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleLoginResponse, t -> Timber.e(t, "error handling login response"))
        );

        // On every key press will username and password will be reported to loginViewModel and the
        // loginFormState will be changed accordingly
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                _emailLayout.setError(null);
                formHandler.loginDataChanged(
                        _usernameText.getText().toString(),
                        _passwordText.getText().toString()
                );
            }
        };
        _usernameText.addTextChangedListener(afterTextChangedListener);
        _passwordText.addTextChangedListener(afterTextChangedListener);

        // Observe loginFormState and show errors or enable login button
        formHandler.getFormStateLive().observe(this, formState -> {
            if (formState == null)
                return;

            _loginButton.setEnabled(formState.isDataValid());
        });

        //_usernameText.setText("s175565");
        //_passwordText.setText("kodeNWHN");
        _usernameText.setText("s185020");
        _passwordText.setText("nlj_nykode");
    }

    private void handleLoginResponse(LoginUserRxBus.LoginResponse response) {
        progressDialog.dismiss();
        if (response.type == RemoteResponseType.SUCCESS) {
            showLoginSuccess(response.user);
        } else {
            showLoginFailed();
        }
    }

    private void login() {
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autentificere...");
        progressDialog.show();
        authViewModel.loginUser(_usernameText.getText().toString(), _passwordText.getText().toString());
    }

    private void startSignUp() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void showLoginSuccess(User user) {
        userViewModel.insertUser(user);
        playgroundsViewModel.fetchPlaygrounds();
        //Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        String welcome = getString(R.string.welcome) + user.getFirstname();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        setResult(Activity.RESULT_OK);
        startActivity(intent);
        finish();
    }

    private void showLoginFailed() {
        _loginButton.setEnabled(false);
        _usernameText.setText("");
        _usernameText.requestFocus();
        _passwordText.setText("");
        _emailLayout.setError("Ugyldige logininformationer.");
    }
}
