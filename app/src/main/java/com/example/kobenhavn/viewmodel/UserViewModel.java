package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.local.model.stub.LoggedInUser;
import com.example.kobenhavn.usecases.user.AddUserToDbUseCase;
import com.example.kobenhavn.usecases.user.GetUserFromDbUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserInDbUseCase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class UserViewModel extends ViewModel {
    private final AddUserToDbUseCase addUserToDbUseCase;
    private final UpdateUserInDbUseCase updateUserInDbUseCase;
    private final GetUserFromDbUseCase getUserFromDbUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public UserViewModel(AddUserToDbUseCase addUserToDbUseCase, UpdateUserInDbUseCase updateUserInDbUseCase, GetUserFromDbUseCase getUserFromDbUseCase) {
        this.addUserToDbUseCase = addUserToDbUseCase;
        this.updateUserInDbUseCase = updateUserInDbUseCase;
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
        disposables.add(updateUserInDbUseCase.updateUserInDB(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(u -> {
                    Timber.e("Update user success %s" , u);
                    LoggedInUser.user = u;
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
