package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.usecases.event.JoinUserEventUseCase;
import com.example.kobenhavn.usecases.event.LeaveEventUseCase;
import com.example.kobenhavn.usecases.user.AddUserToDbUseCase;
import com.example.kobenhavn.usecases.user.GetUserFromDbUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserSubscriptionUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserUseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class UserViewModel extends ViewModel {
    private final AddUserToDbUseCase addUserToDbUseCase;
    private final UpdateUserSubscriptionUseCase updateUserSubscriptionUseCase;
    private final GetUserFromDbUseCase getUserFromDbUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final JoinUserEventUseCase joinUserEventUseCase;
    private final LeaveEventUseCase leaveEventUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();


    public UserViewModel(AddUserToDbUseCase addUserToDbUseCase, UpdateUserSubscriptionUseCase updateUserSubscriptionUseCase, GetUserFromDbUseCase getUserFromDbUseCase, UpdateUserUseCase updateUserUseCase, JoinUserEventUseCase joinUserEventUseCase, LeaveEventUseCase leaveEventUseCase) {
        this.addUserToDbUseCase = addUserToDbUseCase;
        this.updateUserSubscriptionUseCase = updateUserSubscriptionUseCase;
        this.getUserFromDbUseCase = getUserFromDbUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.joinUserEventUseCase = joinUserEventUseCase;
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

    public void updateSubscriptionsLocally(User user, List<Playground> updatedPlaygrounds){
        disposables.add(updateUserSubscriptionUseCase.updateUserSubscriptionsLocally(user.getUsername(), updatedPlaygrounds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("Update user subscriptions success"),
                        t -> Timber.e("Update user subscriptions error")));
    }

    public void updateSubscriptions(User user, List<Playground> updatedPlaygrounds){
        disposables.add(updateUserSubscriptionUseCase.updateUserSubscriptions(user.getUsername(), updatedPlaygrounds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("Update user subscriptions success"),
                        t -> Timber.e("Update user subscriptions error")));
    }


    public LiveData<User> getUser(String username){
        // https://developer.android.com/topic/libraries/architecture/livedata
        return getUserFromDbUseCase.getUserLive(username);
    }

    public Single<User> getUserObject(String username){
        return getUserFromDbUseCase.getUser(username);
    }


    public void joinEventUser(String playgroundName, String eventID, String username, ArrayList<Event> events){
        disposables.add(joinUserEventUseCase.joinEventForUser(playgroundName, username, eventID, events)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("User joined event successfully"),
                        t -> Timber.e("Error in user joining event")));
    }

    public void removeEventFromUser(String playgroundName, String eventID, String username, ArrayList<Event> events){
        disposables.add(leaveEventUseCase.RemoveEventFromUser(playgroundName, username, eventID, events)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("User is removed from event successfully"),
                        t -> Timber.e("Error in removing user from event")));
    }


    public void updateUserFields(User user) {
        disposables.add(updateUserUseCase.updateUserFields(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("update user success"),
                        t -> Timber.e("update user error")));
    }
}
