package com.example.kobenhavn.di;

import android.graphics.Paint;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.remote.IRemoteRepository;
import com.example.kobenhavn.usecases.event.DeleteEventUC;
import com.example.kobenhavn.usecases.event.GetEventsInDbUC;
import com.example.kobenhavn.usecases.event.InsertEventsInDbUC;
import com.example.kobenhavn.usecases.event.JoinEventUC;
import com.example.kobenhavn.usecases.event.LeaveEventUC;
import com.example.kobenhavn.usecases.event.UpdateEventUC;
import com.example.kobenhavn.usecases.playground.FetchPlaygroundUC;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsInDbUC;
import com.example.kobenhavn.usecases.playground.InsertPlaygroundsInDbUC;
import com.example.kobenhavn.usecases.subscription.InsertSubscriptionInDb;
import com.example.kobenhavn.usecases.subscription.RemoveSubscriptionInDb;
import com.example.kobenhavn.usecases.subscription.GetSubscriptionsInDbUC;
import com.example.kobenhavn.usecases.user.GetUserInDbUC;
import com.example.kobenhavn.usecases.user.InsertUserInDbUC;
import com.example.kobenhavn.usecases.user.UpdateUserUC;
import com.example.kobenhavn.view.MainLifecycleObserver;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModelFactory;
import com.example.kobenhavn.viewmodel.UserViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Define MainActivity-specific dependencies here.
 */
@Module
class MenuModule {

    //TODO Declare less responsible by refactoring into submodules

    @Provides
    MainLifecycleObserver providePlaygroundLifecycleObserver(InsertPlaygroundsInDbUC insertPlaygroundsInDbUC,
                                                             DeleteEventUC deleteEventUC, UpdateEventUC updateEventUC, JoinEventUC joinEventUC) {
        return new MainLifecycleObserver(insertPlaygroundsInDbUC, deleteEventUC, updateEventUC, joinEventUC);
    }

    @Provides
    DeleteEventUC provideDeleteEventUC(ILocalRepository localRepository){
        return new DeleteEventUC(localRepository);
    }

    @Provides
    UpdateEventUC provideUpdateEventUC(ILocalRepository localRepository){
        return new UpdateEventUC(localRepository);
    }

    @Provides
    PlaygroundsViewModelFactory providePlaygroundViewModelFactory(GetPlaygroundsInDbUC getPlayground,
                                                                  FetchPlaygroundUC fetchPlaygrounds){
        return new PlaygroundsViewModelFactory(getPlayground, fetchPlaygrounds);
    }

    @Provides
    UserViewModelFactory provideUserViewModelFactory(InsertUserInDbUC insertUserInDbUC, GetUserInDbUC getUserInDbUC,
                                                     UpdateUserUC updateUserUC, JoinEventUC joinEventUC,
                                                     LeaveEventUC leaveEventUC, GetSubscriptionsInDbUC getSubscriptionsInDbUC,
                                                     GetEventsInDbUC getEventsInDbUC, InsertEventsInDbUC insertEventsInDbUC,
                                                     RemoveSubscriptionInDb removeSubscriptionInDb, InsertSubscriptionInDb insertSubscriptionInDb){
        return new UserViewModelFactory(insertUserInDbUC, getUserInDbUC, updateUserUC, joinEventUC,
                leaveEventUC, getSubscriptionsInDbUC, getEventsInDbUC, insertEventsInDbUC, removeSubscriptionInDb,
                insertSubscriptionInDb);
    }

    @Provides
    RemoveSubscriptionInDb provideRemoveSubscription(ILocalRepository localRepository){
        return new RemoveSubscriptionInDb(localRepository);
    }

    @Provides
    InsertSubscriptionInDb provideInsertSubscription(ILocalRepository localRepository){
        return new InsertSubscriptionInDb(localRepository);
    }

    @Provides
    GetEventsInDbUC provideGetEvents(ILocalRepository localRepository){
        return new GetEventsInDbUC(localRepository);
    }

    @Provides
    InsertEventsInDbUC provideInsertEvents(ILocalRepository localRepository){
        return new InsertEventsInDbUC(localRepository);
    }

    @Provides
    GetSubscriptionsInDbUC provideGetSubscriptions(ILocalRepository localRepository){
        return new GetSubscriptionsInDbUC(localRepository);
    }

    @Provides
    JoinEventUC provideJoinEvent(ILocalRepository localRepository, IRemoteRepository remoteRepository){
        return new JoinEventUC(localRepository, remoteRepository);
    }

    @Provides
    LeaveEventUC provideLeaveEvent(ILocalRepository localRepository, IRemoteRepository remoteRepository){
        return new LeaveEventUC(localRepository, remoteRepository);
    }

    @Provides
    UpdateUserUC provideUpdateUser(ILocalRepository localRepository, IRemoteRepository remoteRepository){
        return new UpdateUserUC(localRepository, remoteRepository);
    }

    @Provides
    InsertUserInDbUC provideAddUSerToDbUseCase(ILocalRepository localRepository){
        return new InsertUserInDbUC(localRepository);
    }

    @Provides
    GetUserInDbUC provideGetUserFromDbUseCase(ILocalRepository localRepository){
        return new GetUserInDbUC(localRepository);
    }

    @Provides
    InsertPlaygroundsInDbUC provideAddPlaygroundsToDbUseCase(ILocalRepository localRepository){
        return new InsertPlaygroundsInDbUC(localRepository);
    }

    @Provides
    GetPlaygroundsInDbUC provideGetPlaygroundsUseCase(ILocalRepository localRepository) {
        return new GetPlaygroundsInDbUC(localRepository);
    }

    @Provides
    FetchPlaygroundUC provideFetchPlaygroundsUseCase(ILocalRepository localRepository,
                                                     IRemoteRepository remoteRepository){
        return new FetchPlaygroundUC(localRepository, remoteRepository);
    }
}
