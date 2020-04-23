package com.example.kobenhavn.di;

import com.example.kobenhavn.dal.remote.IRemoteRepository;
import com.example.kobenhavn.usecases.user.LoginUserUC;
import com.example.kobenhavn.usecases.user.LogoutUserUC;
import com.example.kobenhavn.usecases.user.SignupUserUC;
import com.example.kobenhavn.viewmodel.AuthenticationViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 *Authentication-specific dependencies here.
 */
@Module
class AuthenticationModule {

    @Provides
    AuthenticationViewModelFactory provideAuthenticationViewModelFactory(LoginUserUC loginUser,
                                                                         SignupUserUC signupUser,
                                                                         LogoutUserUC logoutUser){
        return new AuthenticationViewModelFactory(loginUser, signupUser, logoutUser);
    }

    @Provides
    LoginUserUC provideLoginUserUseCase(IRemoteRepository remoteRepository){
        return new LoginUserUC(remoteRepository);
    }

    @Provides
    SignupUserUC provideSignupUserUseCase(IRemoteRepository remoteRepository){
        return new SignupUserUC(remoteRepository);
    }

    @Provides
    LogoutUserUC provideLogoutUserUseCase(){
        return new LogoutUserUC();
    }
}
