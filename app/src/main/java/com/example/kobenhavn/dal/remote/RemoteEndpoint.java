package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.local.model.Playground;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
    String BASE_URL = "http://192.168.1.24:8088/rest/";

    // laptop
    //String BASE_URL = "http://192.168.1.62:8088/";

    @GET("playgrounds")
    Call<List<Playground>> getPlaygrounds();

    /*
    @FormUrlEncoded
    @PUT("employee/update")
    Call<User> updateUserInfo(@Field("usermodel" User user);

     */
    @PUT("employee/update")
    Call<User> updateUserInfo(@Body User user);

    // if user is not fetched then call get user
    @POST("employee/login")
    Call<User> loginUser(@Body User user);


    @PUT("playgrounds/{pID}/events/{eID}/participants/{uID}")
    Call<User> updateUserWithEvent(@Path("pID") String playgroundName,
                                   @Path("eID") String eventID,
                                   @Path("uID") String username);


    // call for each subscribed playground
    // separate in future and enrolled
    @GET("post/{id}")
    Call<List<Event>> getEvents(@Path("id") long playgroundName);





    @POST("posts")
    Call<Boolean> signupUser(String name, String username, String password);


    @PUT("posts") // eventID, username
    Call<Event> removeUserFromPlaygroundEvent(int eventID, String username);



}
