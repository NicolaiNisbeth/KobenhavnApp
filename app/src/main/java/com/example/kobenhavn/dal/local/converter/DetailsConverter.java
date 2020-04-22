package com.example.kobenhavn.dal.local.converter;

import androidx.room.TypeConverter;
import com.example.kobenhavn.dal.local.model.Details;
import com.google.gson.Gson;

public class DetailsConverter {
    @TypeConverter
    public static Details fromStringToDetails(String value){
        return new Gson().fromJson(value, Details.class);
    }

    @TypeConverter
    public static String fromDetailsToString(Details details){
        Gson gson = new Gson();
        return gson.toJson(details);
    }
}
