package com.example.kobenhavn.injections;

import com.example.offline.dal.local.ILocalRepository;
import com.example.offline.dal.remote.IRemoteRepository;
import com.example.offline.dal.sync.SyncPlaygroundLifecycleObserver;
import com.example.offline.usecases.playground.AddPlaygroundUseCase;
import com.example.offline.usecases.playground.DeletePlaygroundUseCase;
import com.example.offline.usecases.playground.GetPlaygroundsUseCase;
import com.example.offline.usecases.playground.UpdatePlaygroundUseCase;
import com.example.offline.viewmodel.PlaygroundsViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Define CommentsActivity-specific dependencies here.
 */
@Module
public class PlaygroundsActivityModule {
    @Provides
    PlaygroundsViewModelFactory provideCommentsViewModelFactory(GetPlaygroundsUseCase getCommentsUseCase,
                                                                AddPlaygroundUseCase addCommentUseCase,
                                                                DeletePlaygroundUseCase deletePlaygroundUseCase) {
        return new PlaygroundsViewModelFactory(getCommentsUseCase, addCommentUseCase, deletePlaygroundUseCase);
    }

    @Provides
    SyncPlaygroundLifecycleObserver provideSyncCommentLifecycleObserver(UpdatePlaygroundUseCase updatePlaygroundUseCase,
                                                                     DeletePlaygroundUseCase deletePlaygroundUseCase) {
        return new SyncPlaygroundLifecycleObserver(updatePlaygroundUseCase, deletePlaygroundUseCase);
    }

    @Provides
    AddPlaygroundUseCase provideAddPlaygroundUseCase(ILocalRepository localRepository, IRemoteRepository remoteRepository) {
        return new AddPlaygroundUseCase(localRepository, remoteRepository);
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
    DeletePlaygroundUseCase provideDeletePlaygroundUseCase(ILocalRepository localRepository) {
        return new DeletePlaygroundUseCase(localRepository);
    }

}
