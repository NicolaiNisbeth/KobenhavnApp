package com.example.kobenhavn.usecases.user;

import com.example.kobenhavn.view.authentication.data.AuthRepository;
import com.example.kobenhavn.view.authentication.data.Result;

public class SignupUserUseCase {
    private final AuthRepository authRepository;

    public SignupUserUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Result signupUser(String name, String username, String password){
        return authRepository.signup(name, username, password);
    }
}
