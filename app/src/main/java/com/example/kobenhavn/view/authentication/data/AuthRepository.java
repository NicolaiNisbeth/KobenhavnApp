package com.example.kobenhavn.view.authentication.data;

import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.dal.remote.RemoteException;

import java.io.IOException;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class AuthRepository {
    private static volatile AuthRepository instance;
    private User user = null;

    private AuthRepository() {
    }

    public static AuthRepository getInstance() {
        if (instance == null) {
            instance = new AuthRepository();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        // dataSource.logout(); // TODO: revoke authentication
    }

    public Result<User> login(String username, String password) {
        try {
            this.user = RemoteDataSource.getInstance().loginUser(username, password);
            return new Result.Success<>(user);
        } catch (IOException | RemoteException e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result signup(String name, String username, String password){
        try {
            RemoteDataSource.getInstance().signupUser(name, username, password);
            return new Result.Success(null);
        } catch (IOException | RemoteException e) {
            return new Result.Error(e);
        }

    }

    private void setLoggedInUser(User user) {
        this.user = user;
    }
}
