package com.example.kobenhavn.di;

import androidx.room.Update;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.remote.IRemoteRepository;
import com.example.kobenhavn.dal.sync.PlaygroundsLifecycleObserver;
import com.example.kobenhavn.usecases.playground.InsertPlaygroundsInDbUseCase;
import com.example.kobenhavn.usecases.playground.FetchPlaygroundsUseCase;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsInDbUseCase;
import com.example.kobenhavn.usecases.playground.SubscribeToPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.UnsubscribeToPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.UpdatePlaygroundsInDbUseCase;
import com.example.kobenhavn.usecases.user.AddUserToDbUseCase;
import com.example.kobenhavn.usecases.user.GetUserFromDbUseCase;
import com.example.kobenhavn.usecases.user.UpdateUserSubscriptionInDbUseCase;
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
    PlaygroundsLifecycleObserver providePlaygroundLifecycleObserver(InsertPlaygroundsInDbUseCase insertPlaygroundsInDbUseCase, GetPlaygroundsInDbUseCase getPlaygroundsInDbUseCase) {
        return new PlaygroundsLifecycleObserver(insertPlaygroundsInDbUseCase, getPlaygroundsInDbUseCase);
    }

    @Provides
    PlaygroundsViewModelFactory providePlaygroundViewModelFactory(GetPlaygroundsInDbUseCase getPlayground,
                                                                  FetchPlaygroundsUseCase fetchPlaygrounds){
        return new PlaygroundsViewModelFactory(getPlayground, fetchPlaygrounds);
    }

    @Provides
    UserViewModelFactory provideUserViewModelFactory(AddUserToDbUseCase addUserToDbUseCase, UpdateUserSubscriptionInDbUseCase updateUserSubscriptionInDbUseCase, GetUserFromDbUseCase getUserFromDbUseCase, UpdateUserUseCase updateUserUseCase){
        return new UserViewModelFactory(addUserToDbUseCase, updateUserSubscriptionInDbUseCase, getUserFromDbUseCase, updateUserUseCase);
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
    UpdateUserSubscriptionInDbUseCase provideUpdateUserInDbUseCase(ILocalRepository localRepository){
        return new UpdateUserSubscriptionInDbUseCase(localRepository);
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
