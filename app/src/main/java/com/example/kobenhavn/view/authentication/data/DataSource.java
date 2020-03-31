package com.example.kobenhavn.view.authentication.data;


import com.example.kobenhavn.dal.local.model.LoggedInUser;
import com.example.kobenhavn.view.authentication.data.Result;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class DataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication - make call to remote authentication server
            //   make every student able to login
            LoggedInUser fakeUser = new LoggedInUser(java.util.UUID.randomUUID().toString(), username);
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result signup(String name, String username, String password){
        try {
            // TODO: make singup call with param1, param2, param3
            //return new Result.Success<>(null);
            return new Result.Error((new IOException("Error")));
        } catch (Exception e){
            return new Result.Error(new IOException("Error creating account", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
