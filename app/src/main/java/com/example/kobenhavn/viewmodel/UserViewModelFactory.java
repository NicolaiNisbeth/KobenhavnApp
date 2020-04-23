package com.example.kobenhavn.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kobenhavn.usecases.event.GetEventsInDbUC;
import com.example.kobenhavn.usecases.event.InsertEventsInDbUC;
import com.example.kobenhavn.usecases.event.JoinEventUC;
import com.example.kobenhavn.usecases.event.LeaveEventUC;
import com.example.kobenhavn.usecases.subscription.InsertSubscriptionInDb;
import com.example.kobenhavn.usecases.subscription.RemoveSubscriptionInDb;
import com.example.kobenhavn.usecases.subscription.GetSubscriptionsInDbUC;
import com.example.kobenhavn.usecases.user.GetUserInDbUC;
import com.example.kobenhavn.usecases.user.InsertUserInDbUC;
import com.example.kobenhavn.usecases.user.UpdateUserUC;

public class UserViewModelFactory implements ViewModelProvider.Factory {
    private InsertUserInDbUC insertUserInDbUC;
    private GetUserInDbUC getUserInDbUC;
    private UpdateUserUC updateUserUC;
    private JoinEventUC joinEventUC;
    private LeaveEventUC leaveEventUC;
    private GetSubscriptionsInDbUC getSubscriptionsInDbUC;
    private GetEventsInDbUC getEventsInDbUC;
    private InsertEventsInDbUC insertEventsInDbUC;
    private RemoveSubscriptionInDb removeSubscriptionInDb;
    private InsertSubscriptionInDb insertSubscriptionInDb;

    // TODO: assign less responsibility by refactoring into user, event, subscription modules!
    public UserViewModelFactory(InsertUserInDbUC insertUserInDbUC, GetUserInDbUC getUserInDbUC, UpdateUserUC updateUserUC, JoinEventUC joinEventUC, LeaveEventUC leaveEventUC, GetSubscriptionsInDbUC getSubscriptionsInDbUC, GetEventsInDbUC getEventsInDbUC, InsertEventsInDbUC insertEventsInDbUC, RemoveSubscriptionInDb removeSubscriptionInDb, InsertSubscriptionInDb insertSubscriptionInDb) {
        this.insertUserInDbUC = insertUserInDbUC;
        this.getUserInDbUC = getUserInDbUC;
        this.updateUserUC = updateUserUC;
        this.joinEventUC = joinEventUC;
        this.leaveEventUC = leaveEventUC;
        this.getSubscriptionsInDbUC = getSubscriptionsInDbUC;
        this.getEventsInDbUC = getEventsInDbUC;
        this.insertEventsInDbUC = insertEventsInDbUC;
        this.removeSubscriptionInDb = removeSubscriptionInDb;
        this.insertSubscriptionInDb = insertSubscriptionInDb;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class))
            return (T) new UserViewModel(insertUserInDbUC, getUserInDbUC, updateUserUC, joinEventUC, leaveEventUC, getSubscriptionsInDbUC, getEventsInDbUC, insertEventsInDbUC, removeSubscriptionInDb, insertSubscriptionInDb);

        throw new IllegalArgumentException("Unknown ViewmModel class");
    }
}
