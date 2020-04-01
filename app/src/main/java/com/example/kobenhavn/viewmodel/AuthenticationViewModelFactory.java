package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kobenhavn.usecases.user.LoginUserUseCase;
import com.example.kobenhavn.usecases.user.LogoutUserUseCase;
import com.example.kobenhavn.usecases.user.SignupUserUseCase;

public class AuthenticationViewModelFactory implements ViewModelProvider.Factory {
    private final LoginUserUseCase loginUser;
    private final SignupUserUseCase signupUser;
    private final LogoutUserUseCase logoutUser;

    public AuthenticationViewModelFactory(LoginUserUseCase loginUser, SignupUserUseCase signupUser, LogoutUserUseCase logoutUser) {
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