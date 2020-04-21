package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.local.model.Playground;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
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
    private final Retrofit retrofit;

    public RemoteDataSource(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();

        retrofit = new Retrofit.Builder()
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

    public void getPlayground(String name) throws RemoteException, IOException {
        /*
        RemoteEndpoint endpoint = retrofit.create(RemoteEndpoint.class);
        // Remote call can be executed synchronously since the job calling it is already backgrounded
        Response<Playground> response = endpoint.testResource().execute();

        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);

        Timber.e("Playground is fetched successfully");

         */
    }

    /**
     *
     * @return
     * @throws RemoteException
     * @throws IOException
     */
    public List<Playground> getPlaygrounds() throws RemoteException, IOException {
        Response<List<Playground>> response = API.getPlaygrounds().execute();
        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);

        return new ArrayList<>(response.body());
    }

    /**
     *
     * @param playgroundName
     * @return
     * @throws RemoteException
     * @throws IOException
     */
    public List<Event> getEvents(int playgroundName) throws RemoteException, IOException {
        Response<List<Event>> response =  API.getEvents(playgroundName).execute();
        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);

        return new ArrayList<>(response.body());
    }

    /**
     *
     * @param username
     * @param password
     * @return
     * @throws RemoteException
     * @throws IOException
     */
    public static User loginUser(String username, String password) throws RemoteException, IOException {
        Response<User> response = API.loginUser(new User(username, password)).execute();
        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);

        loggedInUser = response.body();
        if (loggedInUser.getPlaygrounds() == null) loggedInUser.setPlaygrounds(new ArrayList<>());
        if (loggedInUser.getEvents() == null) loggedInUser.setEvents(new ArrayList<>());
        return loggedInUser;
    }

    /**
     *
     * @param name
     * @param username
     * @param password
     * @throws IOException
     * @throws RemoteException
     */
    public void signupUser(String name, String username, String password) throws IOException, RemoteException {
        Response<Boolean> response = API.signupUser(name, username, password).execute();
        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);
    }

    /**
     *
     * @param eventID
     * @param username
     * @throws RemoteException
     * @throws IOException
     */
    public void removeUserFromPlaygroundEvent(int eventID, String username) throws RemoteException, IOException {
        Response<Event> response = API.removeUserFromPlaygroundEvent(eventID, username).execute();
        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);
    }

    /**
     *
     * @param user
     * @throws RemoteException
     * @throws IOException
     */
    public void updateUser(User user) throws RemoteException, IOException {
        Response<User> response = API.updateUserInfo(user).execute();
        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);
    }

    /**
     *
     * @param playgroundName
     * @param eventID
     * @param username
     * @return
     * @throws IOException
     * @throws RemoteException
     */
    public void joinUserWithEvent(String playgroundName, String eventID, String username) throws IOException, RemoteException {
        Response<User> response = API.joinUserWithEvent(playgroundName, eventID, username).execute();
        if (!response.isSuccessful())
            throw new RemoteException(response);
    }

    public void removeUserFromEvent(String playgroundName, String eventID, String username) throws RemoteException, IOException {
        Response<User> response = API.removeUserFromEvent(playgroundName, eventID, username).execute();
        if (!response.isSuccessful())
            throw new RemoteException(response);
    }

    /**
     *
     * @param username
     * @param playgrounds
     * @throws RemoteException
     */
    public void updateUserWithSubscription(String username, List<Playground> playgrounds) throws RemoteException, IOException {
        // how to do multiple calls
        Response<Boolean> response = API.updateUserWithSubscriptions(username, playgrounds.get(0).getId()).execute();
        if (response == null || !response.isSuccessful() || response.errorBody() != null)
            throw new RemoteException(response);
    }
}
