package com.example.kobenhavn.usecases.user;

import com.example.kobenhavn.view.authentication.data.AuthRepository;

public class LogoutUserUseCase {
    private final AuthRepository authRepository;

    public LogoutUserUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void logoutUser(){
        authRepository.logout();
    }
}
