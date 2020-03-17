package com.example.kobenhavn.ui.autentificering.data;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
public class FormState {
    //TODO: builder pattern

    @Nullable
    private Integer nameError;
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    public FormState(@Nullable Integer nameError, @Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.nameError = nameError;
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    public FormState(boolean isDataValid) {
        this.nameError = null;
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getNameError() {
        return nameError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
