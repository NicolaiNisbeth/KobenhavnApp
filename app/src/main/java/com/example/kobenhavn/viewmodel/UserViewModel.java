package com.example.kobenhavn.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.Subscription;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.usecases.event.GetEventsInDbUC;
import com.example.kobenhavn.usecases.event.JoinEventUC;
import com.example.kobenhavn.usecases.event.LeaveEventUC;
import com.example.kobenhavn.usecases.subscription.InsertSubscriptionInDb;
import com.example.kobenhavn.usecases.subscription.RemoveSubscriptionInDb;
import com.example.kobenhavn.usecases.subscription.GetSubscriptionsInDbUC;
import com.example.kobenhavn.usecases.event.InsertEventsInDbUC;
import com.example.kobenhavn.usecases.user.InsertUserInDbUC;
import com.example.kobenhavn.usecases.user.GetUserInDbUC;
import com.example.kobenhavn.usecases.user.UpdateUserUC;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class UserViewModel extends ViewModel {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final InsertUserInDbUC insertUserInDbUC;
    private final GetUserInDbUC getUserInDbUC;
    private final UpdateUserUC updateUserUC;
    private final JoinEventUC joinEventUC;
    private final LeaveEventUC leaveEventUC;
    private final GetSubscriptionsInDbUC getSubscriptionsInDbUC;
    private final GetEventsInDbUC getEventsInDbUC;
    private final InsertEventsInDbUC insertEventsInDbUC;
    private final RemoveSubscriptionInDb removeSubscriptionInDbUC;
    private final InsertSubscriptionInDb insertSubscriptionInDb;

    // TODO: assign less responsibility by refactoring into user, event, subscription modules!
    public UserViewModel(InsertUserInDbUC insertUserInDbUC, GetUserInDbUC getUserInDbUC, UpdateUserUC updateUserUC, JoinEventUC joinEventUC, LeaveEventUC leaveEventUC, GetSubscriptionsInDbUC getSubscriptionsInDbUC, GetEventsInDbUC getEventsInDbUC, InsertEventsInDbUC insertEventsInDbUC, RemoveSubscriptionInDb removeSubscriptionInDbUC, InsertSubscriptionInDb insertSubscriptionInDb) {
        this.insertUserInDbUC = insertUserInDbUC;
        this.getUserInDbUC = getUserInDbUC;
        this.updateUserUC = updateUserUC;
        this.joinEventUC = joinEventUC;
        this.leaveEventUC = leaveEventUC;
        this.getSubscriptionsInDbUC = getSubscriptionsInDbUC;
        this.getEventsInDbUC = getEventsInDbUC;
        this.insertEventsInDbUC = insertEventsInDbUC;
        this.removeSubscriptionInDbUC = removeSubscriptionInDbUC;
        this.insertSubscriptionInDb = insertSubscriptionInDb;
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
                .subscribe(u -> Timber.e("insert user success %s", u.getId()),
                        t -> Timber.e("insert user failure")));
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

    public void insertEvents(List<Event> events) {
        disposables.add(insertEventsInDbUC.insertEvents(events)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("Insert event success"),
                        t -> Timber.e("Insert event failure")));

    }

    public void joinEvent(Event event, User user, String playgroundName){
        disposables.add(joinEventUC.joinEventForUser(event, user, playgroundName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("User joined event successfully"),
                        t -> Timber.e("Error in user joining event")));
    }

    public LiveData<List<Event>> getEventsLive(String username){
        return getEventsInDbUC.getEventsLive(username);
    }

    public void removeEventFromUser(Event event, User user, String playgroundName){
        disposables.add(leaveEventUC.RemoveEventFromUser(event, user, playgroundName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("remove event success"),
                        t -> Timber.e("remove event failure")));
    }

    public void insertSubscriptionLocally(User user, Subscription subscription) {
        disposables.add(insertSubscriptionInDb.insertSubscription(user, subscription)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("Insert subscription success"),
                        t -> Timber.e("Insert subscription failure")));
    }

    public void removeSubscriptionLocally(User user, Playground playground, long id){
        disposables.add(removeSubscriptionInDbUC.removeSubscription(user.getUsername(), playground, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.e("remove subscription success"),
                        t -> Timber.e("remove subscription failure")));
    }

    public LiveData<List<Subscription>> getSubscriptionsLive(String username){
        return getSubscriptionsInDbUC.getSubscriptionsFromDb(username);
    }
}
