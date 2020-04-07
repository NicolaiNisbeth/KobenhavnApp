package com.example.kobenhavn.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kobenhavn.usecases.event.JoinEventUseCase;
import com.example.kobenhavn.usecases.event.LeaveEventUseCase;
import com.example.kobenhavn.usecases.user.AddUserToDbUseCase;
import com.example.kobenhavn.usecases.user.GetUserFromDbUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserSubscriptionInDbUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserUseCase;

public class UserViewModelFactory implements ViewModelProvider.Factory {

    private AddUserToDbUseCase addUserToDbUseCase;
    private UpdateUserSubscriptionInDbUseCase updateUserSubscriptionInDbUseCase;
    private GetUserFromDbUseCase getUserFromDbUseCase;
    private UpdateUserUseCase updateUserUseCase;
    private JoinEventUseCase joinEventUseCase;
    private LeaveEventUseCase leaveEventUseCase;

    public UserViewModelFactory(AddUserToDbUseCase addUserToDbUseCase, UpdateUserSubscriptionInDbUseCase updateUserSubscriptionInDbUseCase, GetUserFromDbUseCase getUserFromDbUseCase, UpdateUserUseCase updateUserUseCase, JoinEventUseCase joinEventUseCase, LeaveEventUseCase leaveEventUseCase) {
        this.addUserToDbUseCase = addUserToDbUseCase;
        this.updateUserSubscriptionInDbUseCase = updateUserSubscriptionInDbUseCase;
        this.getUserFromDbUseCase = getUserFromDbUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.joinEventUseCase = joinEventUseCase;
        this.leaveEventUseCase = leaveEventUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class))
            return (T) new UserViewModel(addUserToDbUseCase, updateUserSubscriptionInDbUseCase, getUserFromDbUseCase, updateUserUseCase, joinEventUseCase, leaveEventUseCase);

        throw new IllegalArgumentException("Unknown ViewmModel class");


    }
}
