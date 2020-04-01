package com.example.kobenhavn.injections;

import com.example.kobenhavn.dal.local.ILocalRepository;
import com.example.kobenhavn.dal.remote.IRemoteRepository;
import com.example.kobenhavn.dal.sync.SyncPlaygroundLifecycleObserver;
import com.example.kobenhavn.usecases.playground.SubscribeToPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.UnsubscribeToPlaygroundUseCase;
import com.example.kobenhavn.usecases.playground.GetPlaygroundsUseCase;
import com.example.kobenhavn.usecases.playground.UpdatePlaygroundUseCase;
import com.example.kobenhavn.usecases.user.LoginUserUseCase;
import com.example.kobenhavn.usecases.user.LogoutUserUseCase;
import com.example.kobenhavn.usecases.user.SignupUserUseCase;
import com.example.kobenhavn.view.authentication.data.AuthRepository;
import com.example.kobenhavn.viewmodel.AuthenticationViewModelFactory;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Define CommentsActivity-specific dependencies here.
 */
@Module
public class PlaygroundsActivityModule {

    @Provides
    AuthenticationViewModelFactory provideAuthenticationViewModelFactory(LoginUserUseCase loginUser,
                                                                          SignupUserUseCase signupUser,
                                                                          LogoutUserUseCase logoutUser){
        return new AuthenticationViewModelFactory(loginUser, signupUser, logoutUser);
    }

    @Provides
    PlaygroundsViewModelFactory providePlaygroundViewModelFactory(GetPlaygroundsUseCase getPlayground,
                                                                  SubscribeToPlaygroundUseCase addPlayground,
                                                                  UnsubscribeToPlaygroundUseCase deletePlayground){
        return new PlaygroundsViewModelFactory(getPlayground, addPlayground, deletePlayground);
    }

    @Provides
    SyncPlaygroundLifecycleObserver provideSyncCommentLifecycleObserver(UpdatePlaygroundUseCase updatePlaygroundUseCase,
                                                                        UnsubscribeToPlaygroundUseCase unsubscribeToPlaygroundUseCase) {
        return new SyncPlaygroundLifecycleObserver(updatePlaygroundUseCase, unsubscribeToPlaygroundUseCase);
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
