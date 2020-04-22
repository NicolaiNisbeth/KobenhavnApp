package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.local.model.Playground;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 *
 */
public interface RemoteEndpoint {
    // computer locale ip
    String BASE_URL = "http://192.168.1.12:8088/rest/";

    @GET("playgrounds")
    Call<List<Playground>> getPlaygrounds();

    @GET("post/{id}")
    Call<List<Event>> getEvents(@Path("id") long playgroundName);

    @POST("employee/login")
    Call<User> loginUser(@Body User user);

    @POST("posts")
    Call<Boolean> signupUser(String name, String username, String password);

    @POST("playgrounds/{pID}/events/{eID}/participants/{uID}")
    Call<User> joinUserWithEvent(@Path("pID") String playgroundName,
                                 @Path("eID") String eventID,
                                 @Path("uID") String username);
    @PUT("employee/update")
    Call<User> updateUserInfo(@Body User user);

    @DELETE("playgrounds/{pID}/events/{eID}/participants/{uID}")
    Call<User> removeUserFromEvent(@Path("pID") String playgroundName,
                                   @Path("eID") String eventID,
                                   @Path("uID") String username);
}
