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
    @GET("posts/{id}")
    Call<Playground> getPlayground(@Path("id") long playgroundName);

     */

    // for now get all when logging in
    @GET("posts")
    Call<List<Playground>> getPlaygrounds();

    @GET("posts/1")
    Call<Playground> testResource();






    // call for each subscribed playground
    // separate in future and enrolled
    @GET("post/{id}")
    Call<List<Event>> getEvents(@Path("id") long playgroundName);





    // if user is not fetched then call get user
    @POST("posts")
    Call<User> loginUser(@Body User user);

    @POST("posts")
    Call<User> loginUser(String username, String password);

    @POST("posts")
    Call<User> signupUser(@Body User user);





    @PUT("posts")
    Call<User> updateUser(@Body User user);

    @PUT("posts")  //eventID, username
    Call<Event> addUserToPlaygroundEvent(int eventID, String usenname);

    @PUT("posts") // eventID, username
    Call<Event> removeUserFromPlaygroundEvent(int eventID, String username);



}
