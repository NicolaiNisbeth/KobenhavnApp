package com.example.kobenhavn.view.authentication.data;

import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.RemoteDataSource;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class AuthRepository {
    private static volatile AuthRepository instance;
    public static DataSource dataSource;
    private User user = null;

    private AuthRepository(DataSource dataSource) {
        AuthRepository.dataSource = dataSource;
    }

    public static AuthRepository getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new AuthRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    public Result<User> login(String username, String password) {
        //Result<User> result = dataSource.login(username, password);
        RemoteDataSource.getInstance().loginUser(username, password);

        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<User>) result).getData());
        }
        return result;
    }

    public Result signup(String name, String username, String password){
        return dataSource.signup(name, username, password);

    }

    private void setLoggedInUser(User user) {
        this.user = user;
    }
}
