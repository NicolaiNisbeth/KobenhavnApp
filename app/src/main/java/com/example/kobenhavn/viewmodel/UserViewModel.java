package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.local.model.inmemory.LoggedInUser;
import com.example.kobenhavn.usecases.user.AddUserToDbUseCase;
import com.example.kobenhavn.usecases.user.GetUserFromDbUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserSubscriptionInDbUseCase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class UserViewModel extends ViewModel {
    private final AddUserToDbUseCase addUserToDbUseCase;
    private final UpdateUserSubscriptionInDbUseCase updateUserSubscriptionInDbUseCase;
    private final GetUserFromDbUseCase getUserFromDbUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public UserViewModel(AddUserToDbUseCase addUserToDbUseCase, UpdateUserSubscriptionInDbUseCase updateUserSubscriptionInDbUseCase, GetUserFromDbUseCase getUserFromDbUseCase) {
        this.addUserToDbUseCase = addUserToDbUseCase;
        this.updateUserSubscriptionInDbUseCase = updateUserSubscriptionInDbUseCase;
        this.getUserFromDbUseCase = getUserFromDbUseCase;
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
                .subscribe(u -> {
                    Timber.e("Insert user success %s", u);
                    getUser(u.getUsername());
                    }, t -> getUser(user.getUsername())));

    }

    public void update(User user){
        disposables.add(updateUserSubscriptionInDbUseCase.updateUserSubscriptions(user.getUsername(), user.getSubscribedPlaygrounds())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Timber.e("Update user success");
                    getUser(user.getUsername());
                    }, t -> Timber.e("Update user error")));
    }

    public void getUser(String username){
        Timber.e("trying to get user");
        disposables.add(getUserFromDbUseCase.getUser(username)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(u -> {
            Timber.e("Got user success %s", u);
            LoggedInUser.user = u;
        }, t -> Timber.e("Got user error")));
    }


}
