package com.example.kobenhavn.injections;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.remote.IRemoteRepository;
import com.example.kobenhavn.dal.sync.FetchPlaygroundsLifecycleObserver;
import com.example.kobenhavn.usecases.playground.AddPlaygroundsToDbUseCase;
import com.example.kobenhavn.usecases.playground.FetchPlaygroundsUseCase;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsInDbUseCase;
import com.example.kobenhavn.usecases.playground.SubscribeToPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.UnsubscribeToPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.UpdatePlaygroundsInDbUseCase;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Define CommentsActivity-specific dependencies here.
 */
@Module
class MenuModule {

    @Provides
    FetchPlaygroundsLifecycleObserver provideSyncCommentLifecycleObserver(AddPlaygroundsToDbUseCase addPlaygroundsToDbUseCase) {
        return new FetchPlaygroundsLifecycleObserver(addPlaygroundsToDbUseCase);
    }

    @Provides
    PlaygroundsViewModelFactory providePlaygroundViewModelFactory(GetPlaygroundsInDbUseCase getPlayground,
                                                                  FetchPlaygroundsUseCase fetchPlaygrounds){
        return new PlaygroundsViewModelFactory(getPlayground, fetchPlaygrounds);
    }

    @Provides
    SubscribeToPlaygroundUseCase provideAddPlaygroundUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        return new SubscribeToPlaygroundUseCase(localRepository, remoteRepository);
    }

    @Provides
    AddPlaygroundsToDbUseCase provideAddPlaygroundsToDbUseCase(ILocalRepository localRepository){
        return new AddPlaygroundsToDbUseCase(localRepository);
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
