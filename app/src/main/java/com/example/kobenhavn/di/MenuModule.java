package com.example.kobenhavn.di;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.remote.IRemoteRepository;
import com.example.kobenhavn.usecases.event.JoinEventUC;
import com.example.kobenhavn.usecases.event.LeaveEventUC;
import com.example.kobenhavn.usecases.playground.FetchPlaygroundUC;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsInDbUC;
import com.example.kobenhavn.usecases.playground.InsertPlaygroundsInDbUC;
import com.example.kobenhavn.usecases.user.GetSubscriptionsInDbUC;
import com.example.kobenhavn.usecases.user.GetUserInDbUC;
import com.example.kobenhavn.usecases.user.InsertUserInDbUC;
import com.example.kobenhavn.usecases.user.UpdateUserSubscriptionUC;
import com.example.kobenhavn.usecases.user.UpdateUserUC;
import com.example.kobenhavn.view.MainLifecycleObserver;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModelFactory;
import com.example.kobenhavn.viewmodel.UserViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Define CommentsActivity-specific dependencies here.
 */
@Module
class MenuModule {

    @Provides
    MainLifecycleObserver providePlaygroundLifecycleObserver(InsertPlaygroundsInDbUC insertPlaygroundsInDbUC, GetPlaygroundsInDbUC getPlaygroundsInDbUC) {
        return new MainLifecycleObserver(insertPlaygroundsInDbUC, getPlaygroundsInDbUC);
    }

    @Provides
    PlaygroundsViewModelFactory providePlaygroundViewModelFactory(GetPlaygroundsInDbUC getPlayground,
                                                                  FetchPlaygroundUC fetchPlaygrounds){
        return new PlaygroundsViewModelFactory(getPlayground, fetchPlaygrounds);
    }

    @Provides
    UserViewModelFactory provideUserViewModelFactory(InsertUserInDbUC insertUserInDbUC, UpdateUserSubscriptionUC updateUserSubscriptionUC, GetUserInDbUC getUserInDbUC, UpdateUserUC updateUserUC, JoinEventUC joinEventUC, LeaveEventUC leaveEventUC, GetSubscriptionsInDbUC getSubscriptionsInDbUC){
        return new UserViewModelFactory(insertUserInDbUC, updateUserSubscriptionUC, getUserInDbUC, updateUserUC, joinEventUC, leaveEventUC, getSubscriptionsInDbUC);
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
    UpdateUserSubscriptionUC provideUpdateUserInDbUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository){
        return new UpdateUserSubscriptionUC(localRepository, remoteRepository);
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
    FetchPlaygroundUC provideFetchPlaygroundsUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository){
        return new FetchPlaygroundUC(localRepository, remoteRepository);
    }
}
