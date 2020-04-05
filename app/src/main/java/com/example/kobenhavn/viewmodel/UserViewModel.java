package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.usecases.user.AddUserToDbUseCase;
import com.example.kobenhavn.usecases.user.GetUserFromDbUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserSubscriptionInDbUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserUseCase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class UserViewModel extends ViewModel {
    private final AddUserToDbUseCase addUserToDbUseCase;
    private final UpdateUserSubscriptionInDbUseCase updateUserSubscriptionInDbUseCase;
    private final GetUserFromDbUseCase getUserFromDbUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public UserViewModel(AddUserToDbUseCase addUserToDbUseCase, UpdateUserSubscriptionInDbUseCase updateUserSubscriptionInDbUseCase, GetUserFromDbUseCase getUserFromDbUseCase, UpdateUserUseCase updateUserUseCase) {
        this.addUserToDbUseCase = addUserToDbUseCase;
        this.updateUserSubscriptionInDbUseCase = updateUserSubscriptionInDbUseCase;
        this.getUserFromDbUseCase = getUserFromDbUseCase;
        this.updateUserUseCase = updateUserUseCase;
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
                    }, t -> {
                    Timber.e("User alread existed");
                    getUser(user.getUsername());
                }));

    }

    public void updateSubscriptions(User user){
        disposables.add(updateUserSubscriptionInDbUseCase.updateUserSubscriptions(user.getUsername(), user.getSubscribedPlaygrounds())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Timber.e("Update user subscriptions success");
                    getUser(user.getUsername());
                    }, t -> Timber.e("Update user subscriptions error")));
    }

    public void updateUser(User user){
        disposables.add(updateUserUseCase.updateUser(user)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> {
            Timber.e("update user success");
            getUser(user.getUsername());
        }, t -> Timber.e("update user error")));
    }

    public void getUser(String username){
        Timber.e("trying to get user");
        disposables.add(getUserFromDbUseCase.getUser(username)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(u -> {
            Timber.e("Got user success %s", u);
            RemoteDataSource.loggedInUser = u;
        }, t -> Timber.e("Got user error")));
    }

    public LiveData<User> observerUserAlive() {
        Timber.e("!!!User livedata changed!!!");
        return getUserFromDbUseCase.getUserLive(RemoteDataSource.loggedInUser.getUsername());
    }

}
