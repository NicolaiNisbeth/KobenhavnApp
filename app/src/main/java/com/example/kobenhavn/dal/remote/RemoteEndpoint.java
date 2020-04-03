package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.local.model.Playground;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RemoteEndpoint {

    /*
    String BASE_URL = "http://jsonplaceholder.typicode.com/";
    @GET("posts/1")
    Call<Playground> testResource();
     */

    // stationær
    String BASE_URL = "http://192.168.1.15:8088/rest/";

    // laptop
    //String BASE_URL = "http://192.168.1.56:8088/rest/";

    @GET("playground_list")
    Call<List<Playground>> getPlaygrounds();








    // call for each subscribed playground
    // separate in future and enrolled
    @GET("post/{id}")
    Call<List<Event>> getEvents(@Path("id") long playgroundName);





    // if user is not fetched then call get user
    @POST("user_login")
    Call<User> loginUser(@Body User user);

    @POST("posts")
    Call<Boolean> signupUser(String name, String username, String password);





    @PUT("posts")
    Call<User> updateUser(@Body User user);

    @PUT("posts")  //eventID, username
    Call<Event> addUserToPlaygroundEvent(int eventID, String usenname);

    @PUT("posts") // eventID, username
    Call<Event> removeUserFromPlaygroundEvent(int eventID, String username);



}
