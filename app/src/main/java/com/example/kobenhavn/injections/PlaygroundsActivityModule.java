package com.example.kobenhavn.injections;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.remote.IRemoteRepository;
import com.example.kobenhavn.dal.sync.SyncPlaygroundLifecycleObserver;
import com.example.kobenhavn.usecases.playground.AddPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.DeletePlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsUseCase;
import com.example.kobenhavn.usecases.playground.UpdatePlaygroundUseCase;
import com.example.kobenhavn.usecases.user.LoginUserUseCase;
import com.example.kobenhavn.usecases.user.LogoutUserUseCase;
import com.example.kobenhavn.usecases.user.SignupUserUseCase;
import com.example.kobenhavn.view.authentication.data.AuthRepository;
import com.example.kobenhavn.viewmodel.ViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Define CommentsActivity-specific dependencies here.
 */
@Module
public class PlaygroundsActivityModule {
    @Provides
    ViewModelFactory provideViewModelFactory(GetPlaygroundsUseCase getCommentsUseCase,
                                             AddPlaygroundUseCase addCommentUseCase,
                                             DeletePlaygroundUseCase deletePlaygroundUseCase) {
        return new ViewModelFactory(getCommentsUseCase, addCommentUseCase, deletePlaygroundUseCase);
    }

    @Provides
    ViewModelFactory provideViewModelFactoryy(LoginUserUseCase loginUserUseCase,
                                             LogoutUserUseCase logoutUserUseCase,
                                             SignupUserUseCase signupUserUseCase) {
        return new ViewModelFactory(loginUserUseCase, signupUserUseCase, logoutUserUseCase);
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

    @Provides
    LoginUserUseCase provideLoginUserUseCase(AuthRepository authRepository){
        return new LoginUserUseCase(authRepository);
    }

    @Provides
    SignupUserUseCase provideSignupUserUseCase(AuthRepository authRepository){
        return new SignupUserUseCase(authRepository);
    }

    @Provides
    LogoutUserUseCase provideLogoutUserUseCase(AuthRepository authRepository){
        return new LogoutUserUseCase(authRepository);
    }

}
