package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.Subscriptions;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.usecases.event.JoinEventUC;
import com.example.kobenhavn.usecases.event.LeaveEventUC;
import com.example.kobenhavn.usecases.user.GetSubscriptionsInDbUC;
import com.example.kobenhavn.usecases.user.InsertUserInDbUC;
import com.example.kobenhavn.usecases.user.GetUserInDbUC;
import com.example.kobenhavn.usecases.user.UpdateSubscriptionUC;
import com.example.kobenhavn.usecases.user.UpdateUserUC;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class UserViewModel extends ViewModel {
    private final InsertUserInDbUC insertUserInDbUC;
    private final UpdateSubscriptionUC updateSubscriptionUC;
    private final GetUserInDbUC getUserInDbUC;
    private final UpdateUserUC updateUserUC;
    private final JoinEventUC joinEventUC;
    private final LeaveEventUC leaveEventUC;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final GetSubscriptionsInDbUC getSubscriptionsInDbUC;


    public UserViewModel(InsertUserInDbUC insertUserInDbUC, UpdateSubscriptionUC updateSubscriptionUC, GetUserInDbUC getUserInDbUC, UpdateUserUC updateUserUC, JoinEventUC joinEventUC, LeaveEventUC leaveEventUC, GetSubscriptionsInDbUC getSubscriptionsInDbUC) {
        this.insertUserInDbUC = insertUserInDbUC;
        this.updateSubscriptionUC = updateSubscriptionUC;
        this.getUserInDbUC = getUserInDbUC;
        this.updateUserUC = updateUserUC;
        this.joinEventUC = joinEventUC;
        this.leaveEventUC = leaveEventUC;
        this.getSubscriptionsInDbUC = getSubscriptionsInDbUC;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public void insertUser(User user){
        Timber.e("trying to insert user");
        disposables.add(insertUserInDbUC.addUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(u -> Timber.e("Insert user success %s", u),
                        t -> Timber.e("User alread existed")));
    }

    public LiveData<User> getUser(String username){
        return getUserInDbUC.getUserLive(username);
    }

    public void updateUserFields(User user) {
        disposables.add(updateUserUC.updateUserFields(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("update user success"),
                        t -> Timber.e("update user error")));
    }

    public void joinEvent(Event event, User user, String playgroundName){
        disposables.add(joinEventUC.joinEventForUser(event, user, playgroundName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("User joined event successfully"),
                        t -> Timber.e("Error in user joining event")));
    }

    public void removeEventFromUser(String playgroundName, String eventID, String username, ArrayList<Event> events){
        disposables.add(leaveEventUC.RemoveEventFromUser(playgroundName, username, eventID, events)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("User is removed from event successfully"),
                        t -> Timber.e("Error in removing user from event")));
    }

    public void updateSubscriptionsLocally(User user, List<Playground> updatedPlaygrounds){
        disposables.add(updateSubscriptionUC.updateUserSubscriptionsLocally(user.getUsername(), updatedPlaygrounds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("Update user subscriptions success"),
                        t -> Timber.e("Update user subscriptions error")));
    }

    public LiveData<Subscriptions> getSubscriptionsLive(String username){
        return getSubscriptionsInDbUC.getSubscriptionsFromDb(username);
    }
}
