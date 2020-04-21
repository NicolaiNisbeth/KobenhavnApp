package com.example.kobenhavn.di;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.remote.IRemoteRepository;
import com.example.kobenhavn.usecases.event.JoinUserEventUseCase;
import com.example.kobenhavn.usecases.event.LeaveEventUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserSubscriptionUseCase;
import com.example.kobenhavn.view.MainLifecycleObserver;
import com.example.kobenhavn.usecases.playground.InsertPlaygroundsInDbUseCase;
import com.example.kobenhavn.usecases.playground.FetchPlaygroundsUseCase;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsInDbUseCase;
import com.example.kobenhavn.usecases.playground.SubscribeToPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.UnsubscribeToPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.UpdatePlaygroundsInDbUseCase;
import com.example.kobenhavn.usecases.user.AddUserToDbUseCase;
import com.example.kobenhavn.usecases.user.GetUserFromDbUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserUseCase;
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
    MainLifecycleObserver providePlaygroundLifecycleObserver(InsertPlaygroundsInDbUseCase insertPlaygroundsInDbUseCase, GetPlaygroundsInDbUseCase getPlaygroundsInDbUseCase) {
        return new MainLifecycleObserver(insertPlaygroundsInDbUseCase, getPlaygroundsInDbUseCase);
    }

    @Provides
    PlaygroundsViewModelFactory providePlaygroundViewModelFactory(GetPlaygroundsInDbUseCase getPlayground,
                                                                  FetchPlaygroundsUseCase fetchPlaygrounds){
        return new PlaygroundsViewModelFactory(getPlayground, fetchPlaygrounds);
    }

    @Provides
    UserViewModelFactory provideUserViewModelFactory(AddUserToDbUseCase addUserToDbUseCase, UpdateUserSubscriptionUseCase updateUserSubscriptionUseCase, GetUserFromDbUseCase getUserFromDbUseCase, UpdateUserUseCase updateUserUseCase, JoinUserEventUseCase joinUserEventUseCase, LeaveEventUseCase leaveEventUseCase){
        return new UserViewModelFactory(addUserToDbUseCase, updateUserSubscriptionUseCase, getUserFromDbUseCase, updateUserUseCase, joinUserEventUseCase, leaveEventUseCase);
    }

    @Provides
    JoinUserEventUseCase provideJoinEvent(ILocalRepository localRepository, IRemoteRepository remoteRepository){
        return new JoinUserEventUseCase(localRepository, remoteRepository);
    }

    @Provides
    LeaveEventUseCase provideLeaveEvent(ILocalRepository localRepository, IRemoteRepository remoteRepository){
        return new LeaveEventUseCase(localRepository, remoteRepository);
    }

    @Provides
    UpdateUserUseCase provideUpdateUser(ILocalRepository localRepository, IRemoteRepository remoteRepository){
        return new UpdateUserUseCase(localRepository, remoteRepository);
    }

    @Provides
    AddUserToDbUseCase provideAddUSerToDbUseCase(ILocalRepository localRepository){
        return new AddUserToDbUseCase(localRepository);
    }

    @Provides
    UpdateUserSubscriptionUseCase provideUpdateUserInDbUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository){
        return new UpdateUserSubscriptionUseCase(localRepository, remoteRepository);
    }

    @Provides
    GetUserFromDbUseCase provideGetUserFromDbUseCase(ILocalRepository localRepository){
        return new GetUserFromDbUseCase(localRepository);
    }

    @Provides
    SubscribeToPlaygroundUseCase provideAddPlaygroundUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        return new SubscribeToPlaygroundUseCase(localRepository, remoteRepository);
    }

    @Provides
    InsertPlaygroundsInDbUseCase provideAddPlaygroundsToDbUseCase(ILocalRepository localRepository){
        return new InsertPlaygroundsInDbUseCase(localRepository);
    }

    @Provides
    GetPlaygroundsInDbUseCase provideGetPlaygroundsUseCase(ILocalRepository localRepository) {
        return new GetPlaygroundsInDbUseCase(localRepository);
    }

    @Provides
    FetchPlaygroundsUseCase provideFetchPlaygroundsUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository){
        return new FetchPlaygroundsUseCase(localRepository, remoteRepository);
    }

    @Provides
    UpdatePlaygroundsInDbUseCase provideUpdatePlaygroundUseCase(ILocalRepository localRepository) {
        return new UpdatePlaygroundsInDbUseCase(localRepository);
    }

    @Provides
    UnsubscribeToPlaygroundUseCase provideDeletePlaygroundUseCase(ILocalRepository localRepository) {
        return new UnsubscribeToPlaygroundUseCase(localRepository);
    }



}
