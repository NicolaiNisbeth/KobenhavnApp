package com.example.kobenhavn.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kobenhavn.usecases.event.JoinUserEventUseCase;
import com.example.kobenhavn.usecases.event.LeaveEventUseCase;
import com.example.kobenhavn.usecases.user.AddUserToDbUseCase;
import com.example.kobenhavn.usecases.user.GetSubscriptionsFromDbUseCase;
import com.example.kobenhavn.usecases.user.GetUserFromDbUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserSubscriptionUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserUseCase;

public class UserViewModelFactory implements ViewModelProvider.Factory {

    private AddUserToDbUseCase addUserToDbUseCase;
    private UpdateUserSubscriptionUseCase updateUserSubscriptionUseCase;
    private GetUserFromDbUseCase getUserFromDbUseCase;
    private UpdateUserUseCase updateUserUseCase;
    private JoinUserEventUseCase joinUserEventUseCase;
    private LeaveEventUseCase leaveEventUseCase;
    private GetSubscriptionsFromDbUseCase getSubscriptionsFromDbUseCase;

    public UserViewModelFactory(AddUserToDbUseCase addUserToDbUseCase, UpdateUserSubscriptionUseCase updateUserSubscriptionUseCase, GetUserFromDbUseCase getUserFromDbUseCase, UpdateUserUseCase updateUserUseCase, JoinUserEventUseCase joinUserEventUseCase, LeaveEventUseCase leaveEventUseCase, GetSubscriptionsFromDbUseCase getSubscriptionsFromDbUseCase) {
        this.addUserToDbUseCase = addUserToDbUseCase;
        this.updateUserSubscriptionUseCase = updateUserSubscriptionUseCase;
        this.getUserFromDbUseCase = getUserFromDbUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.joinUserEventUseCase = joinUserEventUseCase;
        this.leaveEventUseCase = leaveEventUseCase;
        this.getSubscriptionsFromDbUseCase = getSubscriptionsFromDbUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class))
            return (T) new UserViewModel(addUserToDbUseCase, updateUserSubscriptionUseCase, getUserFromDbUseCase, updateUserUseCase, joinUserEventUseCase, leaveEventUseCase, getSubscriptionsFromDbUseCase);

        throw new IllegalArgumentException("Unknown ViewmModel class");


    }
}
