package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.local.model.Playground;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class RemoteDataSource {

    private static RemoteDataSource instance;
    private final Retrofit retrofit;

    public RemoteDataSource(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized RemoteDataSource getInstance(){
        if (instance == null)
            instance = new RemoteDataSource();

        return instance;
    }

    public void getPlayground(long name) throws RemoteException, IOException {
        RemoteEndpoint endpoint = retrofit.create(RemoteEndpoint.class);

        // Remote call can be executed synchronously since the job calling it is already backgrounded
        Response<Playground> response = endpoint.testResource().execute();

        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);

        Timber.e("Playground is fetched successfully");
    }

    public List<Playground> getPlaygrounds() throws RemoteException, IOException {
        RemoteEndpoint endpoint = retrofit.create(RemoteEndpoint.class);
        Response<List<Playground>> response = endpoint.getPlaygrounds().execute();

        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);

        return new ArrayList<>(response.body());
    }

    public List<Event> getEvents(int playgroundName) throws RemoteException, IOException {
        RemoteEndpoint endpoint = retrofit.create(RemoteEndpoint.class);
        Response<List<Event>> response = endpoint.getEvents(playgroundName).execute();

        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);

        return new ArrayList<>(response.body());
    }

    public void loginUser(String username, String password) throws IOException, RemoteException {
        RemoteEndpoint endpoint = retrofit.create(RemoteEndpoint.class);
        Response<User> response = endpoint.loginUser(username, password).execute();

        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);
    }

    public void signupUser(User user) throws IOException, RemoteException {
        RemoteEndpoint endpoint = retrofit.create(RemoteEndpoint.class);
        Response<User> response = endpoint.signupUser(user).execute();

        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);
    }

    public void addUserToPlaygroundEvent(int eventID, String username) throws RemoteException, IOException {
        RemoteEndpoint endpoint = retrofit.create(RemoteEndpoint.class);
        Response<Event> response = endpoint.addUserToPlaygroundEvent(eventID, username).execute();

        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);
    }

    public void removeUserFromPlaygroundEvent(int eventID, String username) throws RemoteException, IOException {
        RemoteEndpoint endpoint = retrofit.create(RemoteEndpoint.class);
        Response<Event> response = endpoint.removeUserFromPlaygroundEvent(eventID, username).execute();

        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);
    }

    public void updateUser(User user) throws RemoteException, IOException {
        RemoteEndpoint endpoint = retrofit.create(RemoteEndpoint.class);
        Response<User> response = endpoint.updateUser(user).execute();

        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);
    }
}
