package com.example.kobenhavn.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kobenhavn.usecases.event.JoinEventUC;
import com.example.kobenhavn.usecases.event.LeaveEventUC;
import com.example.kobenhavn.usecases.user.GetSubscriptionsInDbUC;
import com.example.kobenhavn.usecases.user.GetUserInDbUC;
import com.example.kobenhavn.usecases.user.InsertUserInDbUC;
import com.example.kobenhavn.usecases.user.UpdateSubscriptionUC;
import com.example.kobenhavn.usecases.user.UpdateUserUC;

public class UserViewModelFactory implements ViewModelProvider.Factory {

    private InsertUserInDbUC insertUserInDbUC;
    private UpdateSubscriptionUC updateSubscriptionUC;
    private GetUserInDbUC getUserInDbUC;
    private UpdateUserUC updateUserUC;
    private JoinEventUC joinEventUC;
    private LeaveEventUC leaveEventUC;
    private GetSubscriptionsInDbUC getSubscriptionsInDbUC;

    public UserViewModelFactory(InsertUserInDbUC insertUserInDbUC, UpdateSubscriptionUC updateSubscriptionUC, GetUserInDbUC getUserInDbUC, UpdateUserUC updateUserUC, JoinEventUC joinEventUC, LeaveEventUC leaveEventUC, GetSubscriptionsInDbUC getSubscriptionsInDbUC) {
        this.insertUserInDbUC = insertUserInDbUC;
        this.updateSubscriptionUC = updateSubscriptionUC;
        this.getUserInDbUC = getUserInDbUC;
        this.updateUserUC = updateUserUC;
        this.joinEventUC = joinEventUC;
        this.leaveEventUC = leaveEventUC;
        this.getSubscriptionsInDbUC = getSubscriptionsInDbUC;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class))
            return (T) new UserViewModel(insertUserInDbUC, updateSubscriptionUC, getUserInDbUC, updateUserUC, joinEventUC, leaveEventUC, getSubscriptionsInDbUC);

        throw new IllegalArgumentException("Unknown ViewmModel class");
    }
}
