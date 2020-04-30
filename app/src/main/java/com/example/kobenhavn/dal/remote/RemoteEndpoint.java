package com.example.kobenhavn.dal.remote;

import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 *
 */
public interface RemoteEndpoint {
    String BASE_URL = "http://192.168.1.60:8080/rest/"; // Locale IP
    //String BASE_URL = "http://18.185.121.182:8080/rest/"; // amazon IP

    @POST("employee/login")
    Call<User> loginUser(@Body User user);

    @POST("posts") // TODO: not implemented
    Call<Boolean> signupUser(String name, String username, String password);

    @PUT("employee/update")
    Call<User> updateUserInfo(@Body User user);

    @POST("playgrounds/{pID}/events/{eID}/participants/{uID}")
    Call<User> joinEvent(@Path("pID") String playgroundName,
                         @Path("eID") String eventID,
                         @Path("uID") String username);

    @DELETE("playgrounds/{pID}/events/{eID}/participants/{uID}")
    Call<User> leaveEvent(@Path("pID") String playgroundName,
                          @Path("eID") String eventID,
                          @Path("uID") String username);

    @GET("playgrounds")
    Call<List<Playground>> getPlaygrounds();
}
