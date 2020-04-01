package com.example.kobenhavn.usecases.user;

import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.view.authentication.data.AuthRepository;
import com.example.kobenhavn.view.authentication.data.Result;

public class LoginUserUseCase {
    private final AuthRepository authRepository;

    public LoginUserUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Result<User> loginUser(String username, String password){
        return authRepository.login(username, password);
    }
}
