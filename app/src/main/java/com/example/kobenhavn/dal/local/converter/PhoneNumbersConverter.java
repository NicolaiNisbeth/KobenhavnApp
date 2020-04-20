package com.example.kobenhavn.dal.local.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PhoneNumbersConverter {
    @TypeConverter
    public ArrayList<String> gettingListFromString(String genreIds) {
        ArrayList<String> list = new ArrayList<>();
        for (String s : genreIds.split(",")) if (!s.isEmpty())
            list.add(s);

        return list;
    }

    @TypeConverter
    public String writingStringFromList(ArrayList<String> list) {
        StringBuilder genreIds = new StringBuilder();
        for (String i : list) {
            genreIds.append(",").append(i);
        }
        return genreIds.toString();
    }
}
