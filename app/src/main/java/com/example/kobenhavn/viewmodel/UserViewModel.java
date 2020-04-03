package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.usecases.user.AddUserToDbUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserInDbUseCase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class UserViewModel extends ViewModel {
    private final AddUserToDbUseCase addUserToDbUseCase;
    private final UpdateUserInDbUseCase updateUserInDbUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private User loggedInUser;

    public UserViewModel(AddUserToDbUseCase addUserToDbUseCase, UpdateUserInDbUseCase updateUserInDbUseCase) {
        this.addUserToDbUseCase = addUserToDbUseCase;
        this.updateUserInDbUseCase = updateUserInDbUseCase;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public void addUser(User user){
        loggedInUser =  addUserToDbUseCase.addUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .blockingGet();
    }

    public void update(User user){
        disposables.add(updateUserInDbUseCase.updateUserInDB(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("Update user success"),
                        t -> Timber.e("Update user error")));
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

}
