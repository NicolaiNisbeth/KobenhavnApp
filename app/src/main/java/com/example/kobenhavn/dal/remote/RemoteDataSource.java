package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *
 */
public class RemoteDataSource {
    public static User loggedInUser;
    private static RemoteDataSource instance;
    private static RemoteEndpoint API;

    public RemoteDataSource(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(RemoteEndpoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        API = retrofit.create(RemoteEndpoint.class);
    }

    public static synchronized RemoteDataSource getInstance(){
        if (instance == null)
            instance = new RemoteDataSource();
        return instance;
    }

    public static User loginUser(String username, String password) throws RemoteException, IOException {
        getInstance();
        Response<User> response = API.loginUser(new User(username, password)).execute();
        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);

        loggedInUser = response.body();
        if (loggedInUser.getPhoneNumbers() == null) loggedInUser.setPhoneNumbers(new ArrayList<>());
        if (loggedInUser.getEvents() == null) loggedInUser.setEvents(new ArrayList<>());
        return loggedInUser;
    }

    public void signupUser(String name, String username, String password) throws IOException, RemoteException {
        User user = new User(name,"", username, password, username, "", "", "", Collections.emptyList());
        String stringify = new Gson().toJson(user);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("usermodel", stringify)
                .build();

        Response<User> response = API.signupUser(requestBody, username).execute();
        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);
    }

    public void updateUser(User user) throws RemoteException, IOException {
        user.setEvents(null);
        String stringify = new Gson().toJson(user);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("usermodel", stringify)
                .build();

        Response<User> response = API.updateUserInfo(requestBody, user.getUsername()).execute();
        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);
    }

    public void joinEvent(String playgroundName, String eventID, String username) throws IOException, RemoteException {
        Response<User> response = API.joinEvent(playgroundName, eventID, username).execute();
        if (!response.isSuccessful())
            throw new RemoteException(response);
    }

    public void leaveEvent(String playgroundName, String eventID, String username) throws RemoteException, IOException {
        Response<User> response = API.leaveEvent(playgroundName, eventID, username).execute();
        if (!response.isSuccessful())
            throw new RemoteException(response);
    }

    public List<Playground> getPlaygrounds() throws RemoteException, IOException {
        Response<List<Playground>> response = API.getPlaygrounds().execute();
        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);

        return new ArrayList<>(response.body());
    }
}
