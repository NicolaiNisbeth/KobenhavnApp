package com.example.kobenhavn.ui.authentication.data;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
public class SignupResult {
    @Nullable
    private boolean success;
    @Nullable
    private Integer error;

    public SignupResult(@Nullable Integer error) {
        this.error = error;
    }

    public SignupResult(@Nullable boolean success) {
        this.success = success;
    }

    @Nullable
    public boolean getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
