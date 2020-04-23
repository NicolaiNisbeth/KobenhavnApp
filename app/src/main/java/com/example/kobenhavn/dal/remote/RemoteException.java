package com.example.kobenhavn.dal.remote;

import retrofit2.Response;

public class RemoteException extends Exception {
    private final Response response;

    RemoteException(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
