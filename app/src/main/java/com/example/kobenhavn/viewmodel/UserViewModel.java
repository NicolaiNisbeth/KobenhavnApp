package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.usecases.event.JoinEventUseCase;
import com.example.kobenhavn.usecases.event.LeaveEventUseCase;
import com.example.kobenhavn.usecases.user.AddUserToDbUseCase;
import com.example.kobenhavn.usecases.user.GetUserFromDbUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserSubscriptionInDbUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserUseCase;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class UserViewModel extends ViewModel {
    private final AddUserToDbUseCase addUserToDbUseCase;
    private final UpdateUserSubscriptionInDbUseCase updateUserSubscriptionInDbUseCase;
    private final GetUserFromDbUseCase getUserFromDbUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final JoinEventUseCase joinEventUseCase;
    private final LeaveEventUseCase leaveEventUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();


    public UserViewModel(AddUserToDbUseCase addUserToDbUseCase, UpdateUserSubscriptionInDbUseCase updateUserSubscriptionInDbUseCase, GetUserFromDbUseCase getUserFromDbUseCase, UpdateUserUseCase updateUserUseCase, JoinEventUseCase joinEventUseCase, LeaveEventUseCase leaveEventUseCase) {
        this.addUserToDbUseCase = addUserToDbUseCase;
        this.updateUserSubscriptionInDbUseCase = updateUserSubscriptionInDbUseCase;
        this.getUserFromDbUseCase = getUserFromDbUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.joinEventUseCase = joinEventUseCase;
        this.leaveEventUseCase = leaveEventUseCase;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public void addUser(User user){
        Timber.e("trying to insert user");
        disposables.add(addUserToDbUseCase.addUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(u -> Timber.e("Insert user success %s", u),
                        t -> Timber.e("User alread existed")));

    }

    public void updateSubscriptions(User user, Playground playgroundToBeAdded){
        user.getSubscribedPlaygrounds().add(playgroundToBeAdded);
        disposables.add(updateUserSubscriptionInDbUseCase.updateUserSubscriptions(user.getUsername(), user.getSubscribedPlaygrounds())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("Update user subscriptions success"),
                        t -> Timber.e("Update user subscriptions error")));
    }

    public void updateUser(User user){
        disposables.add(updateUserUseCase.updateUser(user)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> Timber.e("update user success"),
                t -> Timber.e("update user error")));
    }

    public LiveData<User> getUser(String username){
        // https://developer.android.com/topic/libraries/architecture/livedata
        return getUserFromDbUseCase.getUserLive(username);
    }


    public void joinEvent(String playgroundName, String eventID, String username, ArrayList<Event> events){
        disposables.add(joinEventUseCase.joinEvent(playgroundName, username, eventID, events)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("User joined event successfully"),
                        t -> Timber.e("Error in user joining event")));
    }





}
