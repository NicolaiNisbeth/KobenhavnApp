package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kobenhavn.usecases.user.LoginUserUC;
import com.example.kobenhavn.usecases.user.LogoutUserUC;
import com.example.kobenhavn.usecases.user.SignupUserUC;

public class AuthenticationViewModelFactory implements ViewModelProvider.Factory {
    private final LoginUserUC loginUser;
    private final SignupUserUC signupUser;
    private final LogoutUserUC logoutUser;

    public AuthenticationViewModelFactory(LoginUserUC loginUser, SignupUserUC signupUser, LogoutUserUC logoutUser) {
        this.loginUser = loginUser;
        this.signupUser = signupUser;
        this.logoutUser = logoutUser;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthenticationViewModel.class)){
            return (T) new AuthenticationViewModel(loginUser, signupUser, logoutUser);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}