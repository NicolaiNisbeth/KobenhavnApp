package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kobenhavn.usecases.playground.AddPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.DeletePlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsUseCase;
import com.example.kobenhavn.usecases.user.LoginUserUseCase;
import com.example.kobenhavn.usecases.user.LogoutUserUseCase;
import com.example.kobenhavn.usecases.user.SignupUserUseCase;

import org.jetbrains.annotations.NotNull;


public class ViewModelFactory implements ViewModelProvider.Factory {
    private LoginUserUseCase loginUser;
    private SignupUserUseCase signupUser;
    private LogoutUserUseCase logoutUser;
    private GetPlaygroundsUseCase getPlayground;
    private AddPlaygroundUseCase addPlayground;
    private DeletePlaygroundUseCase removePlayground;


    public ViewModelFactory(GetPlaygroundsUseCase getPlayground,
                             AddPlaygroundUseCase addPlayground,
                             DeletePlaygroundUseCase removePlayground){

        this.getPlayground = getPlayground;
        this.addPlayground = addPlayground;
        this.removePlayground = removePlayground;
    }

    public ViewModelFactory(LoginUserUseCase loginUser,
                            SignupUserUseCase signupUser,
                            LogoutUserUseCase logoutUser){

        this.loginUser = loginUser;
        this.signupUser = signupUser;
        this.logoutUser = logoutUser;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PlaygroundsViewModel.class)){
            return (T) new PlaygroundsViewModel(getPlayground, addPlayground, removePlayground);
        }
        else if (modelClass.isAssignableFrom(AuthenticationViewModel.class)){
            return (T) new AuthenticationViewModel(loginUser, signupUser, logoutUser);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
