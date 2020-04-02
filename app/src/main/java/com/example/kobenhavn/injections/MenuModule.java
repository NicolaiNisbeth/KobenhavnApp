package com.example.kobenhavn.injections;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.remote.IRemoteRepository;
import com.example.kobenhavn.dal.sync.SyncPlaygroundLifecycleObserver;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsUseCase;
import com.example.kobenhavn.usecases.playground.SubscribeToPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.UnsubscribeToPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.UpdatePlaygroundUseCase;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Define CommentsActivity-specific dependencies here.
 */
@Module
class MenuModule {

    @Provides
    SyncPlaygroundLifecycleObserver provideSyncCommentLifecycleObserver(UpdatePlaygroundUseCase updatePlaygroundUseCase,
                                                                        UnsubscribeToPlaygroundUseCase unsubscribeToPlaygroundUseCase) {
        return new SyncPlaygroundLifecycleObserver(updatePlaygroundUseCase, unsubscribeToPlaygroundUseCase);
    }

    @Provides
    PlaygroundsViewModelFactory providePlaygroundViewModelFactory(GetPlaygroundsUseCase getPlayground,
                                                                  SubscribeToPlaygroundUseCase addPlayground,
                                                                  UnsubscribeToPlaygroundUseCase deletePlayground){
        return new PlaygroundsViewModelFactory(getPlayground, addPlayground, deletePlayground);
    }

    @Provides
    SubscribeToPlaygroundUseCase provideAddPlaygroundUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        return new SubscribeToPlaygroundUseCase(localRepository, remoteRepository);
    }

    @Provides
    GetPlaygroundsUseCase provideGetPlaygroundssUseCase(ILocalRepository localRepository) {
        return new GetPlaygroundsUseCase(localRepository);
    }

    @Provides
    UpdatePlaygroundUseCase provideUpdatePlaygroundUseCase(ILocalRepository localRepository) {
        return new UpdatePlaygroundUseCase(localRepository);
    }

    @Provides
    UnsubscribeToPlaygroundUseCase provideDeletePlaygroundUseCase(ILocalRepository localRepository) {
        return new UnsubscribeToPlaygroundUseCase(localRepository);
    }
}
