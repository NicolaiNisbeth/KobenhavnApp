package com.example.kobenhavn.dal.local.converter;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class StringListConverter {
    @TypeConverter
    public List<String> gettingListFromString(String genreIds) {
        List<String> list = new ArrayList<>();
        for (String s : genreIds.split(",")) if (!s.isEmpty())
            list.add(s);

        return list;
    }

    @TypeConverter
    public String writingStringFromList(List<String> list) {
        StringBuilder genreIds = new StringBuilder();
        for (String i : list)
            genreIds.append(",").append(i);

        return genreIds.toString();
    }
}
