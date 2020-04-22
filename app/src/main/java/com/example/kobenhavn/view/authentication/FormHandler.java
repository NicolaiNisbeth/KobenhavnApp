package com.example.kobenhavn.view.authentication;

import android.util.Patterns;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.kobenhavn.R;

/**
 * Data validation state of the login form.
 */
class FormHandler {

    private MutableLiveData<State> formStateLive = new MutableLiveData<>();
    LiveData<State> getFormStateLive() {
        return formStateLive;
    }

    void loginDataChanged(String username, String password) {
        if (!username.isEmpty() && !password.isEmpty())
            formStateLive.setValue(new State(true));
    }

    void signupDataChanged(String name, String username, String password) {
        if (!isNameValid(name)) {
            formStateLive.setValue(new State(R.string.invalid_name, null, null));
        } else if (!isUserNameValid(username)) {
            formStateLive.setValue(new State(null, R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            formStateLive.setValue(new State(null, null, R.string.invalid_password));
        } else {
            formStateLive.setValue(new State(true));
        }
    }

    private boolean isNameValid(String name) {
        if (name == null) return false;
        return !name.trim().isEmpty();
    }

    private boolean isUserNameValid(String username) {
        if (username == null) return false;
        if (username.contains("@") && username.length() <= 30) return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        return false;
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public static class State {
        @Nullable
        private Integer nameError;
        @Nullable
        private Integer usernameError;
        @Nullable
        private Integer passwordError;
        private boolean isDataValid;

        State(@Nullable Integer nameError, @Nullable Integer usernameError, @Nullable Integer passwordError) {
            this.nameError = nameError;
            this.usernameError = usernameError;
            this.passwordError = passwordError;
            this.isDataValid = false;
        }

        State(boolean isDataValid) {
            this.nameError = null;
            this.usernameError = null;
            this.passwordError = null;
            this.isDataValid = isDataValid;
        }

        @Nullable
        Integer getNameError() {
            return nameError;
        }

        @Nullable
        Integer getUsernameError() {
            return usernameError;
        }

        @Nullable
        Integer getPasswordError() {
            return passwordError;
        }

        boolean isDataValid() {
            return isDataValid;
        }
    }
}
