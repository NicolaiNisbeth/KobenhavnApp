package com.example.kobenhavn.view.authentication.data;

import androidx.annotation.Nullable;

import com.example.kobenhavn.dal.local.model.User;

/**
 * Authentication result : success (user details) or error message.
 */
public class LoginResult {
    @Nullable
    private User success;
    @Nullable
    private Integer error;

    public LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    public LoginResult(@Nullable User success) {
        this.success = success;
    }

    @Nullable
    public User getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}