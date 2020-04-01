package com.example.kobenhavn.usecases.user;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.IRemoteRepository;
import com.example.kobenhavn.view.authentication.data.AuthRepository;
import com.example.kobenhavn.view.authentication.data.Result;

import io.reactivex.Completable;
import io.reactivex.Single;
import timber.log.Timber;

public class SignupUserUseCase {
    private final AuthRepository authRepository;

    public SignupUserUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Result signupUser(String name, String username, String password){
        return authRepository.signup(name, username, password);
    }
}
