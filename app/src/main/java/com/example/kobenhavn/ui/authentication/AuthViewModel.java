package com.example.kobenhavn.ui.authentication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.kobenhavn.R;
import com.example.kobenhavn.ui.authentication.data.SignupResult;
import com.example.kobenhavn.ui.authentication.data.model.LoggedInUser;
import com.example.kobenhavn.ui.authentication.data.FormState;
import com.example.kobenhavn.ui.authentication.data.LoginResult;
import com.example.kobenhavn.ui.authentication.data.Result;


public class AuthViewModel extends ViewModel {

    private MutableLiveData<FormState> formStateLive = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResultLive = new MutableLiveData<>();
    private MutableLiveData<SignupResult> signupResultLive = new MutableLiveData<>();
    private AuthRepository authRepository;

    public AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void login(String username, String password) {
        Result<LoggedInUser> result = authRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser user = ((Result.Success<LoggedInUser>) result).getData();
            loginResultLive.setValue(new LoginResult(user));
        } else {
            loginResultLive.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void signup(String name, String username, String password) {
        Result result = authRepository.signup(name, username, password);
        if (result instanceof Result.Success)
            signupResultLive.setValue(new SignupResult(true));
        else
            signupResultLive.setValue(new SignupResult(R.string.signup_failed));
    }

    public void loginDataChanged(String username, String password) {
        if (!username.isEmpty() && !password.isEmpty())
            formStateLive.setValue(new FormState(true));
    }

    public void signupDataChanged(String name, String username, String password) {
        if (!isNameValid(name)) {
            formStateLive.setValue(new FormState(R.string.invalid_name, null, null));
        } else if (!isUserNameValid(username)) {
            formStateLive.setValue(new FormState(null, R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            formStateLive.setValue(new FormState(null, null, R.string.invalid_password));
        } else {
            formStateLive.setValue(new FormState(true));
        }
    }

    private boolean isNameValid(String name) {
        if (name == null)
            return false;

        return !name.trim().isEmpty();
    }

    private boolean isUserNameValid(String username) {
        if (username == null)
            return false;

        if (username.contains("@"))
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();

        return false;
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public LiveData<FormState> getFormStateLive() {
        return formStateLive;
    }
    public LiveData<LoginResult> getLoginResultLive() {
        return loginResultLive;
    }
    public LiveData<SignupResult> getSignupResultLive() {
        return signupResultLive;
    }
}
