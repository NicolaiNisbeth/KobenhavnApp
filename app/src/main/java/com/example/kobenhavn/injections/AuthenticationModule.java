package com.example.kobenhavn.injections;

import com.example.kobenhavn.dal.remote.IRemoteRepository;
import com.example.kobenhavn.usecases.user.LoginUserUseCase;
import com.example.kobenhavn.usecases.user.LogoutUserUseCase;
import com.example.kobenhavn.usecases.user.SignupUserUseCase;
import com.example.kobenhavn.viewmodel.AuthenticationViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Define CommentsActivity-specific dependencies here.
 */
@Module
class AuthenticationModule {

    @Provides
    AuthenticationViewModelFactory provideAuthenticationViewModelFactory(LoginUserUseCase loginUser,
                                                                          SignupUserUseCase signupUser,
                                                                          LogoutUserUseCase logoutUser){
        return new AuthenticationViewModelFactory(loginUser, signupUser, logoutUser);
    }

    @Provides
    LoginUserUseCase provideLoginUserUseCase(IRemoteRepository remoteRepository){
        return new LoginUserUseCase(remoteRepository);
    }

    @Provides
    SignupUserUseCase provideSignupUserUseCase(IRemoteRepository remoteRepository){
        return new SignupUserUseCase(remoteRepository);
    }

    @Provides
    LogoutUserUseCase provideLogoutUserUseCase(){
        return new LogoutUserUseCase();
    }
}
