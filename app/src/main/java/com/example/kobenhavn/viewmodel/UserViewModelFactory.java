package com.example.kobenhavn.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kobenhavn.usecases.user.AddUserToDbUseCase;
import com.example.kobenhavn.usecases.user.GetUserFromDbUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserSubscriptionInDbUseCase;

public class UserViewModelFactory implements ViewModelProvider.Factory {

    private AddUserToDbUseCase addUserToDbUseCase;
    private UpdateUserSubscriptionInDbUseCase updateUserSubscriptionInDbUseCase;
    private GetUserFromDbUseCase getUserFromDbUseCase;

    public UserViewModelFactory(AddUserToDbUseCase addUserToDbUseCase, UpdateUserSubscriptionInDbUseCase updateUserSubscriptionInDbUseCase, GetUserFromDbUseCase getUserFromDbUseCase) {
        this.addUserToDbUseCase = addUserToDbUseCase;
        this.updateUserSubscriptionInDbUseCase = updateUserSubscriptionInDbUseCase;
        this.getUserFromDbUseCase = getUserFromDbUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class))
            return (T) new UserViewModel(addUserToDbUseCase, updateUserSubscriptionInDbUseCase, getUserFromDbUseCase);

        throw new IllegalArgumentException("Unknown ViewmModel class");


    }
}
