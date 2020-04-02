package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.kobenhavn.usecases.user.LoginUserUseCase;
import com.example.kobenhavn.usecases.user.LogoutUserUseCase;
import com.example.kobenhavn.usecases.user.SignupUserUseCase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class AuthenticationViewModel extends ViewModel {
    private final LoginUserUseCase loginUser;
    private final SignupUserUseCase signupUser;
    private final LogoutUserUseCase logoutUser;

    private final CompositeDisposable disposables = new CompositeDisposable();


    public AuthenticationViewModel(LoginUserUseCase loginUser, SignupUserUseCase signupUser, LogoutUserUseCase logoutUser) {
        this.loginUser = loginUser;
        this.signupUser = signupUser;
        this.logoutUser = logoutUser;
    }

    public void loginUser(String username, String password){
        disposables.add(loginUser.loginUser(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("login user success"),
                        t -> Timber.e(t, "login user error")));
    }


    public void signupUser(String name, String username, String password){
        disposables.add(signupUser.signupUser(name, username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("Sign up success"),
                        t -> Timber.e(t, "Sign up error")));
    }

    public void logoutUser(){
        logoutUser.logoutUser();
    }


}
